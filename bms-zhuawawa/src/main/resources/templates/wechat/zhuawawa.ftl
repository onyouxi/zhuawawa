<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>按钮操作</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, minimum-scale=1, width=device-width, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-touch-fullscreen" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <script src="/wcstatic/js/jquery-3.2.1.min.js" ></script>
    <style>
        .gray {
            filter: grayscale(100%);
            -webkit-filter: grayscale(100%);
            -moz-filter: grayscale(100%);
            -ms-filter: grayscale(100%);
            -o-filter: grayscale(100%);
        }

    </style>
</head>
<body>
<div style="text-align:center;width:100%;position:absolute;">
            <div style="margin-top:10%;">
                <div style="width:100%">
                    <img src="/wcstatic/img/button1.png" btnNum="1" style="width:20%" class="btn"/>
                </div>
                <div style="float:left;width:20%;margin-left:20%;margin-right:20%;">
                    <img src="/wcstatic/img/button4.png" btnNum="2" style="width:100%" class="btn"/>
                </div>
                <div style="float:left;width:20%">
                    <img src="/wcstatic/img/button2.png" btnNum="3" style="width:100%" class="btn"/>
                </div>
                <div  style="margin-top:24%;">
                    <img src="/wcstatic/img/button3.png" btnNum="4" style="width:20%" class="btn"/>
                </div>
            </div>
            <div style="margin-top:20%">
                <img src="/wcstatic/img/button.png" btnNum="5" style="width:40%" class="btn" />
            </div>
</div>
<script>
    var code='123456789';
    $(function(){
        $('.btn').on({
            touchstart:function(e){
                var btnNum = $(this).attr('btnNum');
                $(this).attr('class','btn gray');
                var name;
                if(btnNum==1){
                    name='left';
                }else if(btnNum==2){
                    name='down';
                }else if(btnNum==3){
                    name='up';
                }else if(btnNum==4){
                    name='right';
                }else if(btnNum==5){
                    name='zhua';
                }
                if( name ){
                    sendMessage('{type:"action",code:"'+code+'",name:"'+name+'"}');
                    console.log(btnNum);
                }
                timeOutEvent=setTimeout("longPress()",100);
                e.preventDefault();
            },
            touchmove:function(){
                clearTimeout(timeOutEvent);
                timeOutEvent=0;
            },
            touchend:function(){
                clearTimeout(timeOutEvent);
                $(this).attr('class','btn');
                 var btnNum = $(this).attr('btnNum');
                if(timeOutEvent!=0 && btnNum != 5){
                    alert("请长时间按按钮");
                }else{
                    if(timeOutEvent==0){
                        sendMessage('{type:"action",code:"'+code+'",name:"end"}');
                    }
                }
                return false;
            }
        })

    });



    function longPress(){
        timeOutEvent = 0;
        sendMessage('{type:"action",code:"'+code+'",name:"end"}');
    }

    var websoctAddress;
    var ws;
    var websoctAddress = "ws://${websocketUrl}/ws/user?openId=123123123";
    var webSocketInit = function () {
        //初始化websocket
        if (WebSocket) {
            ws = new WebSocket(websoctAddress);
            ws.onopen = function (event) {
                ws.onmessage = function (event) {
                    //收到消息后处理
                    acceptMessageHandler(event);
                }
                ws.onerror = function (event) {
                    return;
                }
                ws.onclose = function (event) {
                    return false;
                }
            }
        } else {
            alert('浏览器不支持webscoket，请使用支持html5的浏览器');
        }
    }

    /**
     * 发送消息
     */
    function sendMessage(msg) {
        if (webSocketIsConnect()) {
            ws.send(msg);
        }
    }

    function webSocketIsConnect() {
        if (ws.readyState == 1) {
            return true;
        }
        return false;
    }
    function acceptMessageHandler(event) {

    }
    webSocketInit();
</script>
</body>
</html>