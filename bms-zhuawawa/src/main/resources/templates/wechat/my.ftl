<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>抓娃娃-我的</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, minimum-scale=1, width=device-width, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-touch-fullscreen" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <script src="/wcstatic/js/jquery-3.2.1.min.js" ></script>
    <script src="/wcstatic/js/jpage.js" ></script>
    <style>
        body{padding: 0;margin: 0;height: 100%;}
        .divcss5{
            text-align:center;
            background-color:rgb(197, 229, 145);
        }
        .divcss5 img{
            border-radius:50%;
            width:30%;
            border:5px solid rgb(197, 229, 145);
            margin-top: 10px;
        }
        .nav {
             background-color:#FFFF00;
             padding:5px;
             border-radius:10px;
        }
        .navselect {
             background-color:#cccccc;
             padding:5px;
             border-radius:10px;
        }
        .wrapper {
            margin:5px;
            border-top: #000 1px solid;
            border-left: #000 1px solid;
            overflow:hidden;clear:both;
        }
        .wrapper ul {
            margin: 0px;
            padding: 0px;

        }
        .wrapper li {
            float: left;
            width:33%;
            height: 40px;
            list-style-type: none;
            border-right:#000 1px solid;
            border-bottom: #000 1px solid;
            text-align: center;
            line-height: 40px;
        }

        .infowrapper {
            text-align:center;

        }
        .bootstrap-frm {
            margin-left:auto;
            margin-right:auto;
            max-width: 500px;
            background: #FFF;
            padding: 20px 30px 20px 30px;
            font: 12px "Helvetica Neue", Helvetica, Arial, sans-serif;
            color: #888;
            text-shadow: 1px 1px 1px #FFF;
            border-radius: 5px;
            -webkit-border-radius: 5px;
            -moz-border-radius: 5px;
            }
            .bootstrap-frm h1 {
            font: 25px "Helvetica Neue", Helvetica, Arial, sans-serif;
            padding: 0px 0px 10px 40px;
            display: block;
            border-bottom: 1px solid #DADADA;
            margin: -10px -30px 30px -30px;
            color: #888;
            }
            .bootstrap-frm h1>span {
            display: block;
            font-size: 11px;
            }
            .bootstrap-frm label {
            display: block;
            margin: 0px 0px 5px;
            }
            .bootstrap-frm label>span {
            float: left;
            width: 20%;
            text-align: right;
            padding-right: 10px;
            margin-top: 10px;
            color: #333;
            font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
            font-weight: bold;
            }
            .bootstrap-frm input[type="text"], .bootstrap-frm input[type="email"], .bootstrap-frm textarea, .bootstrap-frm select{
            border: 1px solid #CCC;
            color: #888;
            height: 20px;
            line-height:15px;
            margin-bottom: 16px;
            margin-right: 6px;
            margin-top: 2px;
            outline: 0 none;
            padding: 5px 0px 5px 5px;
            width: 70%;
            border-radius: 4px;
            -webkit-border-radius: 4px;
            -moz-border-radius: 4px;
            -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
            box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
            -moz-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
            }
            .bootstrap-frm select {
            background: #FFF url('down-arrow.png') no-repeat right;
            background: #FFF url('down-arrow.png') no-repeat right;
            appearance:none;
            -webkit-appearance:none;
            -moz-appearance: none;
            text-indent: 0.01px;
            text-overflow: '';
            width: 70%;
            height: 35px;
            line-height:15px;
            }
            .bootstrap-frm textarea{
            height:100px;
            padding: 5px 0px 0px 5px;
            width: 70%;
            }
            .bootstrap-frm .button {
            background: #FFF;
            border: 1px solid #CCC;
            padding: 10px 25px 10px 25px;
            color: #333;
            border-radius: 4px;
            }
            .bootstrap-frm .button:hover {
            color: #333;
            background-color: #EBEBEB;
            border-color: #ADADAD;
            }
            .startButton {
            padding:10px 20px 10px 20px;

            color:rgb(255, 255, 255);
            border-width:2px;
            border-color:rgb(197, 229, 145);
            border-style:solid;
            border-radius:15px;
            background-color:rgb(120, 195, 0);}

            .pageBtn{
                background-color:rgb(120, 195, 0);
                padding:5px 10px 5px 10px;
                border-radius:5px;
            }
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
        <div class="divcss5">
            <img src="${user.imgUrl}" />
            <div style="margin-top: 10px;padding-bottom:8px;">
                <a class="navselect" onclick="record()" id="recordA">游戏记录</a><a class="nav" style="margin-left:40px;margin-right:40px;" onclick="recharge()" id="rechargeA">充值记录</a><span class="nav" onclick="getInfo(this)" id="getInfoA">送货地址</span>
            </div>
        </div>
        <div id="content" class="wrapper">

        </div>
        <div id="pages" style="text-align:center;margin-top: 10px;">

        </div>
    </div>
<script>
    Date.prototype.format = function(f){
        var o ={
            "M+" : this.getMonth()+1, //month
            "d+" : this.getDate(),    //day
            "h+" : this.getHours(),   //hour
            "m+" : this.getMinutes(), //minute
            "s+" : this.getSeconds(), //second
            "q+" : Math.floor((this.getMonth()+3)/3),  //quarter
            "S" : this.getMilliseconds() //millisecond
        }
        if(/(y+)/.test(f))f=f.replace(RegExp.$1,(this.getFullYear()+"").substr(4 - RegExp.$1.length));
        for(var k in o)
            if(new RegExp("("+ k +")").test(f))f = f.replace(RegExp.$1,RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));return f
    }

    function recharge(){
        $('.navselect').attr('class','nav');
        $('#rechargeA').attr('class','navselect');
        $('#content').attr('class','wrapper');
        var html = '<ul><li>日期</li><li>金额</li><li>游戏币</li>';
        $('#content').html(html);
        rechargePage(1);
    }

    function rechargePage(pageNum){
        $.ajax({
          url: "/wechat/myRecharge?pageNum="+pageNum+"&pageSize=10",
          type: "get"
        }).done(function (data) {
            var html = '<ul><li>日期</li><li>金额</li><li>游戏币</li>';
            if(data && data.rows){
                for(var i=0;i<data.rows.length;i++){
                    html += '<li> 17/09/18 18:35</li><li>20元</li><li>400</li>';
                }
            }
            html += '</ul>';
            $('#content').html(html);
            drawPage(data.total,pageNum,'rechargePage');
        });

    }

    function record(){
        $('.navselect').attr('class','nav');
        $('#recordA').attr('class','navselect');
        $('#content').attr('class','wrapper');
        var html = '<ul><li>日期</li><li>行为</li><li>内容</li>';
        $('#content').html(html);
        recordPage(1);
    }

    function recordPage(pageNum){
        $.ajax({
          url: "/wechat/myPlay?pageNum="+pageNum+"&pageSize=10",
          type: "get"
        }).done(function (data) {
            var html = '<ul><li>日期</li><li>行为</li><li>内容</li>';
            if(data && data.rows){
                for(var i=0;i<data.rows.length;i++){
                    html += '<li>'+new Date(parseInt(data.rows[i].wechatUserPlayModel.startTime)).format('yy/MM/dd hh:mm')+'</li>';
                    if(data.rows[i].wechatUserPlayModel.status==0){
                        html += '<li>进行中</li><li></li>';
                    }else if(data.rows[i].wechatUserPlayModel.status==10 || data.rows[i].wechatUserPlayModel.status==11){
                        html += '<li>未抓到</li><li></li>';
                    }else if(data.rows[i].wechatUserPlayModel.status==20){
                        html += '<li>抓到</li>';
                        if(data.rows[i].prizeModel){
                            html += '<li>'+data.rows[i].prizeModel.name+'</li>';
                        }
                    }
                }
            }
            html += '</ul>';
            $('#content').html(html);
            drawPage(data.total,pageNum,'recordPage');
        });

    }

    function drawPage(total,pageNum,pageFunc){
        var pageSize = 10;
        if(total > 10){
            var pageHtml = '';
            if( pageNum == 1){
                pageHtml += '<a class="pageBtn gray" style="text-align:center;" ><</a>';
            }else{
                var last = pageNum-1;
                pageHtml += '<a class="pageBtn" style="text-align:center;" onclick="'+pageFunc+'('+last+')"><</a>';
            }
            console.log("total:"+total+",pageNum:"+pageNum);
            if(total - pageSize*pageNum > 0){
                 var next = pageNum+1;
                 pageHtml += '<a class="pageBtn" style="text-align:center;margin-left:20px;" onclick="'+pageFunc+'('+next+')" >></a>';
            }else{
                 pageHtml += '<a class="pageBtn gray" style="text-align:center;margin-left:20px;">></a>';
            }
            $('#pages').html(pageHtml);
        }
    }

    function getInfo(obj){
        $('.navselect').attr('class','nav');
        $(obj).attr('class','navselect');
        $('#content').attr('class','bootstrap-frm');
        $.ajax({
          url: "/wechat/getInfo",
          type: "get"
        }).done(function (data) {
             if(data.result == 200){
                var name='',phone='',wechatNum='',address='',remark='',id='';
                if(data.data){
                    if(data.data.name){
                        name = data.data.name;
                    }
                    if(data.data.phone){
                        phone = data.data.phone;
                    }
                    if(data.data.wechatNum){
                        wechatNum = data.data.wechatNum;
                    }
                    if(data.data.address){
                        address = data.data.address;
                    }
                    if(data.data.remark){
                        remark = data.data.remark;
                    }
                    if(data.data.id){
                        id = data.data.id;
                    }
                }
                var html = '<label><span>姓名 :</span><input id="name" type="text" name="name"  value="'+name+'"/></label>';
                html += '<label><span>电话 :</span><input id="phone" type="text" name="phone"  value="'+phone+'"/></label>';
                html += '<label><span>微信号 :</span><input id="wechatNum" type="text" name="wechatNum"  value="'+wechatNum+'"/></label>';
                html += '<label><span>地址 :</span><textarea id="address" >'+address+'</textarea></label>';
                html += '<label><span>备注 :</span><textarea id="remark">'+remark+'</textarea></label>';
                html += '<div style="text-align:center"><a class="startButton" onclick="updateInfo(\''+id+'\')">保存</a></div>'
                $('#content').html(html);
             }else{
                alert(data.result_msg);
             }
        });

    }

    function updateInfo(id){
        var wechatUserInfo = {
            "id":id,
            "name":$('#name').val(),
            "phone":$('#phone').val(),
            "wechatNum":$('#wechatNum').val(),
            "address":$('#address').val(),
            "remark":$('#remark').val()
        }
        $.ajax({
          url: "/wechat/updateInfo",
          type: "post",
          dataType: "json",
          data:JSON.stringify(wechatUserInfo),
          contentType:"application/json; charset=utf-8"
        }).done(function (data) {
             if(data.result == 200){
                alert('保存成功');
             }else{
                alert(data.result_msg);
             }
        });
    }

    record();
</script>
</body>
</html>