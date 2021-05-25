<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <title>${username}</title>
    <link rel="stylesheet" href="assets/css/bootstrap.css"/>
    <link rel="stylesheet" href="assets/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="assets/css/build.css"/>
    <link rel="stylesheet" type="text/css" href="assets/css/qq.css"/>

</head>
<body>

<div class="qqBox">
    <div id="topConfig" class="btn-wrap">
        <div class="problem" onclick="goToRobotConfig()">机器人问题管理</div>
        <#--<div class="time" onclick="goToNoteConfig">工作时间配置</div>-->
    </div>
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
                <input id="id" type="hidden" value="${id}">
            </div>
            <div id="rightCont" class="RightCont">
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
        webSocketService = new WebSocket('ws://127.0.0.1:8087/websocket?username=' + '${username}' + '&userType=' + '${userType}' + '&id=' + '${id}' + '&city=' + '${city}');
    } else {
        alert("当前浏览器不支持WebSocket");
    }

    //连接发生错误的回调方法
    webSocketService.onerror = function () {
        setLeftMessage("WebSocket连接发生错误！");
    };

    webSocketService.onopen = function () {
        //setLeftMessage("WebSocket连接成功！")
        if ($("#userType").val() == 1)  {
            document.getElementById("topConfig").style.display="";//显示
        } else {
            document.getElementById("topConfig").style.display="none";//隐藏
        }
        document.getElementById('rightCont').scrollTop = document.getElementById('rightCont').scrollHeight;
    };

    webSocketService.onmessage = function (event) {
        console.log("onmessage-username:" + $("#headName").text());
        eval("var msg=" + event.data + ";");

        if (undefined != msg.content) {
            // 客服或者机器人
            if (msg.type == 1) {
                console.log("rightMessage")
                setRightMessage(msg);
            }
            if (msg.type == 2) {
                console.log("leftMessage")
                setLeftMessage(msg);
            }
            if (msg.type == 3) {
                setRightRobotMessage(msg);
            }
        }
        document.getElementById('rightCont').scrollTop = document.getElementById('rightCont').scrollHeight;
        // 上线是true，下线是false，其他发消息情况是空
        if ((msg.isOnOpen || msg.isOnOpen == false) && undefined != msg.names) {
            $("#userList").html("");
            $.each(msg.names, function (key, value) {
                console.log("names-each-userType:" + $("#userType").val());
                // 客户只渲染客服列表，客服渲染处自己外的所有客服和客户的列表
                if (!($("#userType").val() == 2 && value.indexOf("用户") == 0)) {
                    console.log("onmessage-username:" + $("#headName").text() + ",value:" + value);
                    // 去除自己
                    if (value != $("#headName").text()) {
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
        var message = $("#dope").val().replace(/[\r\n]/g,"");
        $("#dope").val("");
        if (userType == "1") {
            sendRightMessage(message);
        } else {
            webSocketSend(getLeftMessage(message), message, $("#userType").val());
        }
        document.getElementById('rightCont').scrollTop = document.getElementById('rightCont').scrollHeight;
    };

    function sendRightMessage(message) {
        var time = new Date().toLocaleString();
        //发送消息
        var rightHtmlstr = '<li><div class="answerHead"><img src="assets/img/kefu.gif"></div><div class="answers" style="background:#A9F5BC; color:#000;">'
                + '[' + $("#headName").text() + ']' + '   ' + time + '<br/>' + message + '</div></li>';
        webSocketSend(rightHtmlstr, message, $("#userType").val());
    };

    //将消息显示在网页上
    function setLeftMessage(clientMsg) {
        var time = clientMsg.messageTime ? clientMsg.messageTime : new Date().toLocaleString();
        var msg = '<li>'
                + '<div class="nesHead">'
                + '<img src="assets/img/yonghu.gif">'
                + ' </div>'
                + '<div class="answers">'
                   +'[' + clientMsg.titleName + ']' + time + '<br/>' + clientMsg.content
                + '</div>'
                + '</li>';
        $("#message").append(msg);
    }

    function setRightMessage(clientMsg) {
        var time = clientMsg.messageTime ? clientMsg.messageTime : new Date().toLocaleString();
        //发送消息
        var msg = '<li>' +
                    '<div class="answerHead">' +
                        '<img src="assets/img/kefu.gif">' +
                    '</div>' +
                    '<div class="answers" style="background:#A9F5BC; color:#000;" >'
                        + '[' + clientMsg.titleName + ']' + '   ' + time + '<br/>'
                        + clientMsg.content +
                    '</div>'
                    + '</li>';
        $("#message").append(msg);
    }

    function setRightRobotMessage(clientMsg) {
        var time = clientMsg.messageTime ? clientMsg.messageTime : new Date().toLocaleString();
        //发送消息
        var msg = '<li><div class="answerHead"><img src="assets/img/robot.gif"></div><div class="answers" style="background:#819FF7; color:#000;" >'
                + '[' + clientMsg.titleName + ']' + '   ' + time + '<br/>' + clientMsg.content + '</div></li>';
        $("#message").append(msg);
    }

    // 获取左侧消息html串
    function getLeftMessage(message) {
        var time = new Date().toLocaleString();
        var msg = '<li>'
                + '<div class="nesHead">'
                + '<img src="assets/img/yonghu.gif">'
                + ' </div>'
                + '<div class="answers">'
                + '[' + $("#headName").text() + ']' + time + '<br/>' + message
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
                fromUserType: fromUser,
                titleName: $("#headName").text()
            }
        } else {
            var obj = {
                to: to,
                msg: message,
                type: 2,
                fromUserType: fromUser,
                titleName: $("#headName").text()
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

    function goToRobotConfig() {
        window.open("http://localhost:8087/robot/list", "_search");
    };


    function goToNoteConfig() {
        window.open("http://localhost:8087/note-config/list", "_search");
    };


</script>

</body>
</html>