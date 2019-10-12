
    function post_comment(){
        var data={};
        data["parentId"]=$("#questionId").val();
        data["content"]=$("#comment_content").val();
        data["type"]=1;
        comment(data);

    }
    function comment(data){
        $.ajax({
            url:"/comment",
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(data),
            type:"POST",
            dataType:"json",
            success:function(data){
                if(data.code==200){
                    $("#comment_content").hide();
                    $("#comment_content").val("");
                    $("#submit").hide();
                    $("#comment").show();
                    location.reload();
                }else{
                    if(data.code==203){
                        var status= confirm("账户未登录是否登录")
                        if(status){
                            window.open("https://github.com/login/oauth/authorize?client_id=7e518a40f1fdb0465463&redirect_uri=http://localhost:8085/callback&scope=user&state=1")
                            window.localStorage.setItem("closable","true");
                        }
                    }
                    else{
                        alert(data["message"]);
                    }
                }
            }
        });
    }
    function post_subcomment(e){
        id = e.getAttribute("data-sub_comment")
        var data={};
        data["parentId"]=id;
        data["content"]=$("#sub_comment"+id).val();
        data["type"]=2;
        comment(data);
    }
    function showTextarea() {
        $("#comment_content").show();
        $("#submit").show();
        $("#comment").hide();
    }
    /*展开二级评论*/
    function collapseComment(e){
        var commentId = e.getAttribute("data");
        var comments = $("#comment-"+commentId);
        var collapse=e.getAttribute("data-collapse")
        if(collapse){
            $("#comment-" + commentId).removeClass("in");
            e.removeAttribute("data-collapse");

        }else {
            $("#comment-" + commentId).addClass("in");
            e.setAttribute("data-collapse", "in");
            $.ajax({
                url:"/comment/"+commentId,
                type:"GET",
                success:function(data){
                    var newdata = $(data.data);
                    console.log(newdata)
                    if(data.code!=200){
                        // alert(data.message);
                    }else{
                        var comments = $("#comment-"+commentId);
                        if($(comments).children().length<2){
                        $.each(newdata,function(index,comment){
                            var div =$("<div/>",{"class":"col-lg-12 col-md-12"});
                            var img = $("<img/>",{
                                "class":"media-object img_size img_comment",
                                "src":comment.user.avatarUrl
                            });
                            var spanName = $("<span/>",{
                                "html":comment.user.name
                            });
                            var divIner = $("<div/>").append(
                            $("<p/>",{"html":comment.content})).append(
                                $("<span/>",{"class":"time_comment","html":moment(comment.gmtCreate).format('YYYY-MM-DD')}));

                            var hr = $("<hr/>",{"class":"col-lg-12 col-md-12",
                                "width": "97%"});
                            comments.prepend(div);
                            div.append(img).append(spanName).append(divIner).append(hr);



                        })
                        }
                    }

                }

            });
        }
    }

    //标签提示展示
    function selectTag(){
        $("#questionTag").css("display","block");
    }
    function showTag() {
        $("#tag").blur(0);
    }
    function hideTag(){
        var objID =
            setTimeout( function(){
                $("#questionTag").hide();
            }, 200);
    }
    $(function(){
        $("#tag").focus(function () {
            $("#questionTag").show();
        });
        var tag = document.getElementById("tag");
        tag.onblur=hideTag;
        $("#questionTag").mouseenter(function(){
            tag.onblur=null;
        });
        $("#questionTag").mouseleave(function(){
            $("#questionTag").hide();
            tag.onblur=hideTag;
        });

    });
//标签单机事件
    function addTag(e){
        var value = e.getAttribute("data-tag");
        var tag = $("#tag").val();
        if(tag.indexOf(value)==-1){
            if(tag){
                $("#tag").val(tag+"，"+value);
            }else{
            $("#tag").val(value);
            }
        }
    }