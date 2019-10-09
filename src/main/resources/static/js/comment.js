
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
        var data = e.getAttribute("data");
        var collapse=e.getAttribute("data-collapse")
        if(collapse){
            $("#comment-" + data).removeClass("in");
            e.removeAttribute("data-collapse");

        }else {
            $("#comment-" + data).addClass("in");
            e.setAttribute("data-collapse", "in");
            alert(1);
            $.ajax({
                url:"/comment/"+data,
                type:"GET",
                success:function(data){
                    console.log(data)
                }

            });
        }
    }