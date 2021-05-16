<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <title>chatroom_websocket</title>
    <link rel="stylesheet" href="assets/css/bootstrap.css"/>
    <link rel="stylesheet" href="assets/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="assets/css/build.css"/>
    <link rel="stylesheet" type="text/css" href="assets/css/qq.css"/>

</head>
<body>

<div class="qqBox">
    <div class="context">
        <div class="conLeft">
            <ul id="userList">
            </ul>
        </div>
        <div class="conRight">
            <div class="Righthead">
                <#--从后端传来用户名的值-->
                <div id="headName" class="headName">${username}</div>
                <input id="userType" type="hidden" value="${userType}">
            </div>
            <div class="RightCont">
                <ul class="newsList" id="message">

                </ul>
            </div>
            <div class="RightMiddle">
                <div class="file">
                    <form id="form_photo" method="post" enctype="multipart/form-data"
                          style="width:auto;">
                        <input type="file" name="filename" id="filename" onchange="fileSelected();"
                               style="display:none;">
                    </form>
                </div>
            </div>
            <div class="RightFoot">
                    <textarea id="dope"
                              style="width: 100%;height: 100%; border: none;outline: none;padding:20px 0 0 3%;" name=""
                              rows="" cols=""></textarea>
                <button id="fasong" class="sendBtn" onclick="send()" style="border-radius: 5px">发送</button>
            </div>
        </div>
    </div>
</div>


<script src="assets/js/jquery_min.js"></script>
<script src="https://cdn.bootcss.com/jquery.form/4.2.2/jquery.form.min.js"></script>
<script type="text/javascript">
    var webSocketService = null;

    //判断当前浏览器是否支持WebSocket
    if ('WebSocket' in window) {
        /*服务器就一个，客户端有很多个，回传保证知道是哪个客户端再和你说话
        * ? 后面是传非后端的参数以k,v的形式
        * session.getQueryString()获取所有的值
        * 如果有多个则是username=username & password =
        * */
        webSocketService = new WebSocket('ws://127.0.0.1:8087/websocket?username=' + '${username}' + '&userType=' + '${userType}');
    } else {
        alert("当前浏览器不支持WebSocket");
    }

    //连接发生错误的回调方法
    webSocketService.onerror = function () {
        setLeftMessage("WebSocket连接发生错误！");
    };

    webSocketService.onopen = function () {
        //setLeftMessage("WebSocket连接成功！")
    };

    webSocketService.onmessage = function (event) {
        console.log("onmessage-username:" + $("#headName").val());
        $("#userList").html("");
        eval("var msg=" + event.data + ";");

        if (undefined != msg.content) {
            if (msg.userType == "1") {
                console.log("rightMessage")
                setRightMessage(msg.content);
            } 
            if (msg.userType == "2") {
                console.log("leftMessage")
                setLeftMessage(msg.content);
            }
        }

        if (undefined != msg.names) {
            $.each(msg.names, function (key, value) {
                console.log("names.value.indexOf:" + value.indexOf("用户"));
                console.log("names-each-userType:" + $("#userType").val());
                if (!($("#userType").val() == 2 && value.indexOf("用户") == 0)) {
                    console.log("onmessage-username:" + $("#headName").val() + "进来了")
                    // 左侧列表
                    var htmlstr =
                            '<li>'
                                + '<div class="checkbox checkbox-success checkbox-inline">'
                                    + '<input type="checkbox" class="styled" id="' + key + '" value="' + key + '" checked>'
                                    + '<label for="' + key + '"></label>'
                                + '</div>'
                                + '<div class="liLeft">' +
                                     '<img src="assets/img/robot2.jpg"/>' +
                                  '</div>'
                                + '<div class="liRight">'
                                    + '<span class="intername">' + value + '</span>'
                                + '</div>'
                            + '</li>'
                    $("#userList").append(htmlstr);
                }
            })
        }
    };


    //当前窗口得到焦点
    window.onfocus = function() {
        console.log("onfocus,userType:" + $("#userType").value)
        // $.each($("#userList"), function(index, item) {
        //     if (item.value)
        // }
    };

    function send() {
        var userType = $("#userType").val();
        console.log("userType:" + userType);
        var message = $("#dope").val();
        $("#dope").val("");
        if (userType == "1") {
            sendRightMessage(message);
        } else {
            webSocketSend(getLeftMessage(message), message, $("#userType").val());
        }
    };

    function sendRightMessage(message) {
        var time = new Date().toLocaleString();
        //发送消息
        var rightHtmlstr = '<li><div class="answerHead"><img src="assets/img/2.png"></div><div class="answers">'
                + '[客服]' + '   ' + time + '<br/>' + message + '</div></li>';
        webSocketSend(rightHtmlstr, message, $("#userType").val());
    };

    //将消息显示在网页上
    function setLeftMessage(innerHTML) {
        var msg = '<li>'
                + '<div class="nesHead">'
                + '<img src="assets/img/robot.jpg">'
                + ' </div>'
                + '<div class="news">'
                + innerHTML
                + '</div>'
                + '</li>';
        $("#message").append(msg);
    }

    function setRightMessage(innerHTML) {
        var time = new Date().toLocaleString();
        //发送消息
        var msg = '<li><div class="answerHead"><img src="assets/img/2.png"></div><div class="answers">'
                + '[客服]' + '   ' + time + '<br/>' + innerHTML + '</div></li>';
        $("#message").append(msg);
    }

    // 获取左侧消息html串
    function getLeftMessage(innerHTML) {
        var msg = '<li>'
                + '<div class="nesHead">'
                + '<img src="assets/img/robot.jpg">'
                + ' </div>'
                + '<div class="news">'
                + innerHTML
                + '</div>'
                + '</li>';
        return msg;
    }

    function getRightMessage(message) {
        var time = new Date().toLocaleString();
        //发送消息
        var rightHtmlstr = '<li><div class="answerHead"><img src="assets/img/2.png"></div><div class="answers">'
                + '[客服]' + '   ' + time + '<br/>' + message + '</div></li>';
        return rightHtmlstr;
    }

    function webSocketSend(htmlstr,message, fromUser){
        $("#message").append(htmlstr);
        var ss = $("#userList :checked");
        var to = "";
        $.each(ss, function (key, value) {
            to += value.getAttribute("value") + "-";
        });
        // type：1-群聊,2-私聊
        if (ss.size() == 0) {
            var obj = {
                msg: message,
                type: 1,
                fromUserType: fromUser
            }
        } else {
            var obj = {
                to: to,
                msg: message,
                type: 2,
                fromUserType: fromUser
            }
        }
        var msg = JSON.stringify(obj);
        console.info("发送的消息为：" + msg);
        webSocketService.send(msg);
        /*if(re){
            $("#jsonImg").attr("src", string.data);
            // loadDiv(re);
        }*/
    }

    //回车键发送消息
    $(document).keypress(function (e) {

        if ((e.keyCode || e.which) == 13) {
            $("#fasong").click();
        }

    });

    //局部刷新div
    function loadDiv(sJ) {
        $("#delayImgPer").html('<img src="'+sJ+'" class="delayImg" >');
    }

    webSocketService.onclose = function () {
        setLeftMessage("用户已离线");
    };

    window.onbeforeunload = function () {
        closeWebSocket();
    };

    function closeWebSocket() {
        webSocketService.close();
    }
</script>

</body>
</html>