<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>抓娃娃</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, minimum-scale=1, width=device-width, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-touch-fullscreen" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <script src="/wcstatic/js/jquery-3.2.1.min.js" ></script>
    <style>
        body{padding: 0;margin: 0;height: 100%;}
        .startButton {
            color:rgb(255, 255, 255);
            padding-top:10px;
            padding-bottom:10px;
            padding-left:25px;
            padding-right:25px;
            border-width:2px;
            border-color:rgb(197, 229, 145);
            border-style:solid;
            border-radius:39px;
            background-color:rgb(120, 195, 0);}
        .startButton:hover{color:#ffffff;background-color:#78c300;border-color:#c5e591;}
        .gray {
            filter: grayscale(100%);
            -webkit-filter: grayscale(100%);
            -moz-filter: grayscale(100%);
            -ms-filter: grayscale(100%);
            -o-filter: grayscale(100%);
        }
        .confirm {
            position:fixed;
            z-index: 10;
            top: 30%;
            width: 70%;
            left: 15%;
            display: none;
            background-color:#ffffff;
            text-align:center;
            border-radius:15px;

        }
        .mask {
            position:fixed;
            z-index: 5;
            width: 100%;
            height: 100%;
            top: 0;
            left: 0;
            background: rgba(0, 0, 0, 0.4);
            display: none;
        }
        .endButton {
            border-color:rgb(197, 229, 145);
            border-style:solid;
            border-radius:15px;
            background-color:rgb(120, 195, 0);
        }
    </style>
</head>
<body>
<div>
    <div style="overflow:hidden;">
         <img src="/wcstatic/imgs/timg.jpeg" style="width:100%;height:290px;"/>
    </div>
    <div>
        <#if gameStatus == 1>
        <div id="gameInit">
            <div style="margin-top:3%;">
                <span style="color:#78c300">剩余的游戏币:</span><span style="color:red">${user.money!0}</span><span style="color:#78c300">个</span><button class="startButton"  style="font-size:10px;" onclick="recharge()">充值</button>
            </div>
            <#if machine.wechatUserModel??>
            <div style="margin-top:3%;margin-bottom:3%">
                <span style="color:red">${machine.wechatUserModel.nick}</span><span style="color:#78c300">正在游戏中</span>
            </div>
            </#if>
        </div>
        </#if>

        <div id="gameTime" style="margin-top:3%;text-align:center;<#if gameStatus == 1>display:none</#if>">
            <span style="color:#78c300">游戏时间:</span><span style="color:red;font-size:40px" id="gameTimeVal">${gameTime!30}</span><span style="color:#78c300">秒</span>
        </div>
    </div>
    <#if gameStatus == 1>
    <div id="startBtn" style="text-align:center;width:100%;margin-top:10px;">
        <#if machine.wechatUserModel ??>
            <button class="startButton gray" style="font-size:40px;" >开始游戏</button>
        <#else>
            <button class="startButton" style="font-size:40px;" onclick="start()">开始游戏</button>
        </#if>
    </div>
    </#if>

    <div id="controller" style="width:100%;margin-top:10px;<#if gameStatus == 1>display:none</#if>">
            <div style="float:left;width:60%;margin-left:5%">
                <div>
                    <img src="/wcstatic/imgs/btn2.png" btnNum="1" style="width:50px;padding-left:60px;" class="btn"/>
                </div>
                <div style="float:left;margin-left:5%;margin-right:20%;">
                    <img src="/wcstatic/imgs/btn3.png" btnNum="2" style="width:50px;" class="btn"/>
                </div>
                <div style="float:left;">
                    <img src="/wcstatic/imgs/btn1.png" btnNum="3" style="width:50px;" class="btn"/>
                </div>
                <div  style="margin-top:20%;">
                    <img src="/wcstatic/imgs/btn4.png" btnNum="4" style="width:50px;padding-right:50px;padding-left:60px;" class="btn"/>
                </div>
            </div>
            <div style="float:left;margin-top:5%;">
                <img src="/wcstatic/imgs/btn.png" btnNum="5" style="width:94px" class="btn" />
            </div>
    </div>
    <div class="confirm">
        <h3>游戏结束，再来一局</h3>
        <div>
            时间：<span style="color:red;font-size:20px" id="gameOverTime">30</span>
        </div>
        <div style="margin-top: 10px;margin-bottom:10px;">
            <button class="endButton" onclick="restart()">好的</button>
            <button class="endButton" onclick="endGame()">不玩了</button>
        </div>
    </div>
    <div class="confirm recharge" >
        <div style="background-color:#78c300;text-align:right;border-radius:10px 10px 0px 0px;">
            <img src="/wcstatic/imgs/delete.png" style="width:20px;" onclick="closeRecharge()"/>
        </div>
        <div style="margin-top:5px;margin-bottom:5px;">
            <span style="background-color:#339900;padding-left:10px;padding-right: 10px;color:FFFFFF" >首次分享免费得50个游戏币</span>
        </div>
        <div style="margin-top:20px;margin-bottom:20px;">
            <span style="border:2px solid black;">
                <span style="margin-right:20px;padding-left:8px;">100游戏币</span><span style="margin-right:20px;">送20游戏币</span><span style="background-color:#339900;color:FFFFFF">10元</span>
            </span>
        </div>
        <div style="margin-top:20px;margin-bottom:20px;">
            <span style="border:2px solid black;x">
                <span style="margin-right:20px;padding-left:4px;">200游戏币</span><span style="margin-right:20px;">送80游戏币</span><span style="background-color:#339900;color:FFFFFF">20元</span>
            </span>
        </div>
    </div>
</div>
<div class="mask"></div>
<script>
    var code='${machine.machineModel.code}';
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
                timeOutEvent=setTimeout("longPress()",200);
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
                    if(btnNum != 5){
                        console.log('end');
                        sendMessage('{type:"action",code:"'+code+'",name:"end"}');
                    }
                }
                return false;
            }
        })

    });



    function longPress(){
        timeOutEvent = 0;
    }

    var websoctAddress;
    var ws;
    var websoctAddress = "ws://${websocketUrl}/ws/user?wechatId=${user.id}";
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
        var object = $.parseJSON(event.data);
        if(object.result==200){
            if(object.cmd == 'login'){
                $('#gameTime').show();
                $('#controller').show();
                $('#gameInit').hide();
                $('#startBtn').hide();
                gameTimeStart();
            }else if(object.cmd == 'end' ){
                initStart();
            }
        }
    }

    var gameTimeInterval;
    function gameTimeStart(){
        gameTimeInterval = window.setInterval('gameTime()',1000);
    }

    var gameOverTimeInterval;
    function gameOverTimeStart(){
        gameTimeInterval = window.setInterval('gameOverTime()',1000);
    }

    function gameOverTime(){
        var gameOverTime = $('#gameOverTime').html();
        if(gameOverTime==0){
            window.clearInterval(gameOverTimeInterval);
            initStart();
        }else{
            $('#gameOverTime').html(gameOverTime-1);
        }

    }

    function gameTime(){
        var gameTime = $('#gameTimeVal').html();
        console.log('gameTime:'+gameTime);
        if( gameTime == 0){
            window.clearInterval(gameTimeInterval);
            //$('#gameTime').html('<div style="text-align:center;"><h1>游戏结束</h1></div>');
            $('.confirm,.mask').show();
            gameOverTimeStart();
        }else{
            $('#gameTimeVal').html(gameTime-1);
        }
    }

    function initStart(){
         window.location.href='https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx0bd61f1f517bfa54&redirect_uri=http://zhua.party-time.cn/wechat/zhuawawa?machineId=599bcf07e4b0ed3ecbc57b8f&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect';
    }

    function start(){
        console.log('start');
        $(this).attr('class','startButton gray');
        $.ajax({
          url: "/wechat/start?machineId=${machine.machineModel.id}",
          type: "get"
        }).done(function (data) {
             if(data.result == 200){
                webSocketInit();
             }else{
                alert(data.result_msg);
             }
        });
    }

    function restart(){
        $('.confirm,.mask').hide();
        start();
    }

    function endGame(){
        $.ajax({
          url: "/wechat/end?machineId=${machine.machineModel.id}",
          type: "get"
        }).done(function (data) {
             if(data.result == 200){
                initStart();
             }else{
                alert(data.result_msg);
             }
        });
    }

    function recharge(){
        $('.confirm.recharge').show();
    }

    function closeRecharge(){
        $('.confirm.recharge').hide();
    }

    window.onbeforeunload=function(){
        event.returnValue="确定离开当前页面吗？";
    }

    <#if gameStatus == 0>
    gameTimeStart();
    </#if>

</script>
</body>
</html>