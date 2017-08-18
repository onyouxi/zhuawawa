$(function () {
	
	jQuery.support.placeholder = false;
	test = document.createElement('input');
	if('placeholder' in test) jQuery.support.placeholder = true;


	if (!$.support.placeholder) {
		$('.field').find ('label').show ();
	}

});

var login = function(){
    $.ajax({
        url:"/v1/login",
        type: "post",
        dataType: "json",
        data: {
            name: $('#username').val(),
            password: $('#password').val(),
        }
    }).done(function (data) {
        if (data.result == 200) {
            window.location.href='/wawa';
        } else {
            alert(data.result_msg);
        };
    });
}

var loginByCode = function(){
    var userName = $('#username').val();
    if( userName=='' ){
        alert('请填写用户名');
        return;
    }
    $.ajax({
        url:"/v1/loginByWechat",
        type: "post",
        dataType: "json",
        data: {
            name: userName,
            code: $('#code').val(),
        }
    }).done(function (data) {
        if (data.result == 200) {
            window.location.href='/party';
        } else {
            alert(data.result_msg);
        };
    });
}

