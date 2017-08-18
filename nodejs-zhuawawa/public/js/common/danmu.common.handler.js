$.ajaxSetup({
    timeout: 10000,
    dataType: 'html',
    //请求成功后触发
    success: function (data) {
        console.log('success invoke!' + data + '<br/>');
    },
    //请求失败遇到异常触发
    error: function (xhr, status, e) {
        console.log('error invoke!' + e + '<br/>');
    },
    //完成请求后触发。即在success或error触发后触发
    complete: function (xhr, status) {
        console.log('complete invoke! status:' + status + '<br/>');

        if (xhr.status == 444) {
            window.location = '/login';
            return;
        }
        if (xhr.status == 443) {
            window.location = '/film/danmuCheck';
            return;
        }

    },
    //发送请求前触发
    beforeSend: function (xhr) {
        //可以设置自定义标头
        //xhr.setRequestHeader('Content-Type', 'application/xml;charset=utf-8');
        console.log('beforeSend invoke!' +'<br/>');
    },
})

var setAdminNick = function(){
    var nick = $.cookie('nick');
    var adminNickHtml = '<a href="#" class="dropdown-toggle" data-toggle="dropdown" > <i class="icon-user"></i>'+nick+
   '<b class="caret"></b> </a><ul class="dropdown-menu"><li><a href="javascript:;" onclick="openUpdatePassword()">修改密码</a></li><li><a href="javascript:;" onclick="logout()">登出</a></li></ul>';
    $('.adminNick').html(adminNickHtml);
}

var openUpdatePassword = function(){
    var htmlStr = '<form id="edit-profile" class="form-horizontal"><div class="control-group" style="margin-top: 18px;">'+
       '<label class="control-label" style="width:50px">原始密码</label><div class="controls" style="margin-left:60px;">'+
       '<input type="password" class="span4" maxlength="10" autocomplete="off" id="oldPassword"/></div><br>'+
       '<label class="control-label" style="width:50px">新密码</label><div class="controls" style="margin-left:60px;">'+
           '<input type="password" class="span4" maxlength="10" autocomplete="off" id="newPassword" /></div><br>'+
       '<label class="control-label" style="width:50px">确认新密码</label><div class="controls" style="margin-left:60px;">'+
              '<input type="password" class="span4" maxlength="10" autocomplete="off" id="renewPassword">'+
       '</div></div></form>';
    $('#updatePassword-modal-body').html(htmlStr);
    var footerHtml = '<a class="btn btn-primary" onclick="updatePassword()">修改</a>';
        $('#updatePassword-modal-footer').html(footerHtml);
    $('#updatePassword').modal('show');
}

var updatePassword = function(){
    var oldPassword = $('#oldPassword').val();
    var newPassword = $('#newPassword').val();
    var renewPassword = $('#renewPassword').val();
    if( '' == oldPassword){
        alert('原始密码不能为空');
        return;
    }
    if( '' == newPassword){
        alert('新密码不能为空');
        return;
    }
    if( '' == renewPassword){
        alert('确认新密码不能为空');
        return;
    }
    if( newPassword.length < 6){
        alert('密码长度需要大于6位');
        return;
    }
    if( newPassword != renewPassword){
        alert('两次密码不一样');
        return;
    }
    var obj = {
        oldPassword:oldPassword,
        newPassword:newPassword
    }
    $.ajax({
            url:"/v1/api/admin/adminUser/updatePassword",
            type: "POST",
            data: obj,
            dataType: "json",
    }).done(function (data) {
        if (data.result == 200) {
            logout();
        } else {
            alert('原始密码不正确');
        };
    });
}

var logout = function(){
    $.ajax({
            url:"/v1/logout",
            type: "get",
            dataType: "json",
    }).done(function (data) {
        if (data.result == 200) {
            window.location.href='/login';
        } else {
            alert(data.result_msg);
        };
    });

}

var navbarSelect = function(){
    var url = window.location.href;
    if( url.indexOf('/wawa')!=-1){
        $('.icon-gamepad').parent().parent().attr('class','active');
    }

    if( url.indexOf('/user')!=-1){
        $('.icon-user').parent().parent().attr('class','active');
    }

    if( url.indexOf('/wechat')!=-1){
        $('.icon-camera').parent().parent().attr('class','active');
    }

    if( url.indexOf('/reward')!=-1){
        $('.icon-font').parent().parent().attr('class','active');
    }

    if( url.indexOf('/adminmanager')!=-1){
        $('.icon-bold').parent().parent().attr('class','active');
    }

}

