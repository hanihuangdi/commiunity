
    function post(){
        var data={};
        data["parentId"]=$("#questionId").val();
        data["content"]=$("#comment_content").val();
        data["type"]=1;

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
                }else{
                    if(data.code==203){
                       var status= confirm("账户未登录是否登录")
                        if(status){
                            window.open("https://github.com/login/oauth/authorize?client_id=7e518a40f1fdb0465463&redirect_uri=http://localhost:8085/callback&scope=user&state=1")

                        }
                    }
                    else{
                        alert(data["message"]);
                    }
                }
            }
        });
    }

    function showTextarea() {
        $("#comment_content").show();
        $("#submit").show();
        $("#comment").hide();
    }