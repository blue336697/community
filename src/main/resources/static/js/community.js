/*其实这里写的东西就是展示前后端分离的思想，我们这是最原始的写法，现在有很多优秀的前端框架直接能与
* 后端进行交互*/


/*将放松评论和回复评论的二级评论封装一下*/
function comment(targetId, type, content){
    if(!content){
        alert("不能回复空内容！");
        return;
    }

    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parentId" : targetId,
            "content" : content,
            "type":type
        }),
        success: function (response) {
            if(response.code == 200){
                window.location.reload();
            }else{
                if(response.code == 2003){
                    var isLoginOrNot = confirm(response.message);
                    if(isLoginOrNot)
                        window.open("https://github.com/login/oauth/authorize?client_id=253f8d41c9d3b362306a&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                    window.localStorage.setItem("closable", true);  //这一句就是实现登录后跳转显示原来登录的页面以及页面的补充进度
                }else {
                    alert(response.message);
                }
            }
            console.log(response);
        },
        dataType: "json"
    });
}


/*提交评论*/
function postComment(){
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    comment(questionId, 1, content);
}

/*回复评论，即提交二级评论*/
function secondaryComment(e){
    var id = e.getAttribute("data-id");
    var content = $("#input-"+id).val();
    comment(id, 2, content);
}



/*展开评论并回复*/
function replyComment(e){
    //第一次点击展开，第二次关闭
    var id = e.getAttribute("data-id");

    var comments = $("#comment-" + id);

    // toggleClass() 方法用于检查元素是否有某个 class。如果 class 不存在，则为元素添加该 class；
    // 如果 class 已经存在，则为元素删除该 class。
    /*console.log(toggleClass);*/

    var collapse = e.getAttribute("data-collapse");


    if(collapse) {
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    }else {
        var commentBody = $("#comment-" + id);

        //这里就是防止加载过了的评论重新续写加载，防止重复访问数据库
        if(commentBody.children().length != 1){
            comments.addClass("in");
            //标记二级评论展开状态
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        }else {
            $.getJSON( "/comment/" + id, function (data) {
                $.each(data.data.reverse(), function (index, comment) {
                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>",{
                        "class" : "media-object img-rounded" ,
                        "src": comment.user.avatarUrl
                    }));

                    var mediaBodyElement = $("<div/>",{
                        "class" : "media-body"
                    }).append($("<h5/>", {
                        "class":"media-heading" ,
                        "html":comment.user.name
                    })).append($("<div/>", {
                        "html":comment.content
                    })).append($("<div/>", {
                        "class":"menu"
                    }).append($("<span/>",{
                        "class" : "pull-right" ,
                        "html" : moment(comment.gmtCreate).format("YYYY-MM-DD")
                    })));

                    var mediaElement =$( "<div/>", {
                            "class": "media"
                        }).append(mediaLeftElement).append( mediaBodyElement);

                    var commentElement = $( "<div/>",{
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12",
                        "id":"comment_section"
                    }).append(mediaElement);
                    ;

                    commentBody.prepend(commentElement);

                });
                comments.addClass("in");
                //标记二级评论展开状态
                e.setAttribute("data-collapse", "in");
                e.classList.add("active");
            });
        }
    }
}

function showSelectTag(){
    $("#select-tag").show();
}

function selectTag(e) {
    var value = e.getAttribute("data-tag");
    var previous =$("#tag").val();
    if (previous.indexOf(value) == -1){
        if (previous) {
            $("#tag").val(previous + ',' + value);
        }else {
            $("#tag").val(value);
        }
    }
}


