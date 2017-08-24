<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>抓娃娃s</title>
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
    </style>
</head>
<body>
<div>
    <div>
         <img src="/wcstatic/imgs/timg.jpeg" style="width:100%"/>
    </div>
    <div>
        <#if gameStatus == 1>
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
        </#if>
        <#if gameStatus == 0>
        <div style="margin-top:3%;text-align:center;">
            <span style="color:#78c300">游戏时间:</span><span style="color:red;font-size:40px">${gameTime!30}</span><span style="color:#78c300">秒</span>
        </div>
        </#if>
    </div>
    <#if gameStatus == 1>
    <div style="text-align:center;width:100%;">
        <button class="startButton" style="font-size:40px;">开始游戏</button>
    </div>
    </#if>
     <#if gameStatus == 0>
    <div style="width:100%;margin-top:10px;">
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
    </#if>
<div>
</body>
</html>