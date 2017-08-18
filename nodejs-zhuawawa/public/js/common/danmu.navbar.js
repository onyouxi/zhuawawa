var drawNavbar = function(){
    var htmlStr = '<li><a href="/party"><i class="icon-gamepad"></i><span>活动管理</span> </a> </li>';
        htmlStr +='<li><a href="/predanmu"><i class="icon-list-alt"></i><span>预制弹幕管理</span> </a> </li>';
        htmlStr +='<li><a href="/adDanmuLibrary"><i class="icon-play-circle"></i><span>广告管理</span> </a> </li>';
        htmlStr += '<li><a href="/reward"><i class="icon-font"></i><span>支付管理</span> </a> </li>';
        htmlStr += '<li><a href="/user"><i class="icon-user"></i><span>用户管理</span> </a></li>';
        htmlStr += '<li><a href="/cinema"><i class="icon-film"></i><span>影院管理</span> </a> </li>';
        htmlStr +='<li><a href="/blockword"><i class="icon-bar-chart"></i><span>屏蔽词管理</span> </a> </li>';
        htmlStr += '<li><a href="/wechat"><i class="icon-camera"></i><span>微信自动回复管理</span> </a> </li>';
        htmlStr += '<li><a href="/shopManager"><i class="icon-bold"></i><span>商品管理</span> </a> </li>';
        htmlStr += '<li><a href="/order"><i class="icon-text-height"></i><span>订单管理</span> </a> </li>';
    var role = $.cookie('role');
    if(role == '589a98cd77c8afdcbdeaeeb4'){
        htmlStr += '<li class="dropdown"><a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown"> <i class="icon-long-arrow-down"></i><span>其他</span> <b class="caret"></b></a>';
        htmlStr += '<ul class="dropdown-menu">';
        htmlStr +=  '<li><a href="/adminmanager">管理员管理</a></li>';
        htmlStr += '<li><a href="/version">版本管理</a></li>';
        htmlStr += '<li><a href="/param">参数管理</a></li>';
        htmlStr += '<li><a href="/danmucmd">指令管理</a></li>';
        htmlStr += '<li><a href="/h5temp">页面管理</a></li>';
         htmlStr += '<li><a href="/monitor">通知管理</a></li>';
        htmlStr +=  '</ul></li>';
    }

    if(role != '589a98cd77c8afdcbdeaeeb6'){
        $('.mainnav').html(htmlStr);
    }

}

