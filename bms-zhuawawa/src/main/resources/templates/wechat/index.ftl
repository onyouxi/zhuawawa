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
    </style>
</head>
<body>
<div>
    <div>
         <img src="/wcstatic/imgs/timg.jpeg" style="width:100%"/>
    </div>
    <div>
        <#if gameStatus == 1>
        <div id="gameInit">
        <div style="margin-top:3%;">
            <span style="color:#78c300">剩余的游戏次数:</span><span style="color:red">${user.playNum!0}</span><span style="color:#78c300">次</span><button class="startButton"  style="font-size:10px;">充值</button>
        </div>
        <#if machine.wechatUserModel??>
        <div style="margin-top:3%;">
            <span style="color:red">${machine.wechatUserModel.nick}</span><span style="color:#78c300">正在游戏中</span>
        </div>
        </#if>
        <div style="margin-top:3%;word-wrap:break-word;height:5.3em">
            <span>
                <span style="color:#78c300">目前排队中</span>：<span style="color:red">
                <#if wechatMachineModelList??>
                    <#list wechatMachineModelList as wm>
                        <#if wm.wechatUserModel??>
                            ${wm.wechatUserModel.nick}
                            <#if wm_index lt wechatMachineModelList?size>
                                ,
                            </#if>
                        </#if>
                    </#list>
                <#else>
                无
                </#if>
                </span>
            </span>
        </div>
        <div>
        </#if>

        <div id="gameTime" style="margin-top:3%;text-align:center;<#if gameStatus == 1>display:none</#if>">
            <span style="color:#78c300">游戏时间:</span><span style="color:red;font-size:40px">${gameTime!30}</span><span style="color:#78c300">秒</span>
        </div>
    </div>
    <#if gameStatus == 1>
    <div id="startBtn" style="text-align:center;width:100%;">
        <button class="startButton" style="font-size:40px;" onclick="start()">开始游戏</button>
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
<div>
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
            }
        }
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
</script>
</body>
</html>