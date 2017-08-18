var drawNavbar = function(){
    var htmlStr = '<li><a href="/wawa"><i class="icon-gamepad"></i><span>娃娃机管理</span> </a> </li>';
        htmlStr += '<li><a href="/reward"><i class="icon-font"></i><span>支付管理</span> </a> </li>';
        htmlStr += '<li><a href="/user"><i class="icon-user"></i><span>用户管理</span> </a></li>';
        htmlStr += '<li><a href="/reply"><i class="icon-camera"></i><span>微信自动回复管理</span> </a> </li>';
        htmlStr +=  '<li><a href="/adminmanager"><i class="icon-bold"></i><span>管理员管理</span></a></li>';
        $('.mainnav').html(htmlStr);

}

