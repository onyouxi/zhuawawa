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
var tableUrl = '/v1/api/admin/wechatmanager/page';
var columnsArray = [
    {
        title: '序号',
        align: 'center',
        formatter: function (value, row, index) {
            return index+1;
        }
    },
    {
        title: '微信编号',
        align: 'center',
        formatter: function (value, row, index) {
            if(row.wechatUser){
                return row.wechatUser.id;
            }
        }
    },
    {
        title: '微信头像',
        align: 'center',
        width:'15%',
        formatter: function (value, row, index) {
            if(row.wechatUser){
                return '<img width="40%" src="'+row.wechatUser.imgUrl+'" />';
            }
        }
    },
    {
        field: 'wechatUser.nick',
        title: '微信昵称',
        align: 'center'
    },
    {
        title: '性别',
        align: 'center',
        formatter: function (value, row, index) {
            if(row.wechatUser.sex == 1){
                return '男';
            }else if(row.wechatUser.sex == 2){
                 return '女';
            }else{
                return '未知';
            }

        }
    },
    {
        title: '关注状态',
        align: 'center',
        formatter: function (value, row, index) {
            if(row.wechatUser.subscribeState == 0){
                return '关注中';
            }else if(row.wechatUser.subscribeState == 1){
                 return '取消关注';
            }else{
                return '未知';
            }
        }
    },
    {
        title: '注册场地',
        align: 'center',
        formatter: function (value, row, index) {
            if(row.registAddress){
                return row.registAddress.name;
            }else {
                return '未知';
            }
        }
    },
    {
        title: '注册时间',
        align: 'center',
        formatter: function (value, row, index) {
            if(row.wechatUserInfo){
                return new Date(parseInt(row.wechatUserInfo.registDate)).format('yyyy-MM-dd hh:mm:ss');
            }
        }
    },
    {
        title: '最后登录场地',
        align: 'center',
        formatter: function (value, row, index) {
            if(row.lastAddress){
                return row.lastAddress.name;
            }else {
                return '未知';
            }
        }
    },
    {
        title: '最后登录时间',
        align: 'center',
        formatter: function (value, row, index) {
            if(row.wechatUser && row.wechatUser.lastOpenDate){
                return new Date(parseInt(row.wechatUser.lastOpenDate)).format('yyyy-MM-dd hh:mm:ss');
            }
        }
    },
    {
        title: '操作',
        align: 'center',
        formatter: function (value, row, index) {
            return '<a class="btn" onclick="openAddress(\''+row.wechatUser.id+'\')">修改场地</a><a class="btn" onclick="delUser(\''+row.wechatUser.id+'\',\''+row.wechatUser.nick+'\')">删除</a>';
        },
        events: 'operateEvents'
    }
];
var quaryObject = {
    pageSize: 20
};


var searchNick = function(){
    var nick = $("#searchNick").val();
    if( '' != nick){
        quaryObject = {
            nick:nick,
            pageSize:20
        }
        $.initTable('tableList', columnsArray, quaryObject, tableUrl);
    }
}

var delUser = function(id,nick){
    if (confirm('确认要删除用户“' + nick + '”吗？')) {

             $.danmuAjax('/v1/api/admin/wechatmanager/del?id='+id, 'GET','json',null, function (data) {
              if (data.result == 200) {
                  console.log(data);

                  var nick = $("#searchNick").val();
                  if( '' != nick){
                       searchNick();
                  }else{
                        $.initTable('tableList', columnsArray, quaryObject, tableUrl);
                  }
              }else{
                 alert('删除失败')
              }
            }, function (data) {
                console.log(data);
            });
        }
}


var openAddress = function(wechatUserId){
    var aList = $('#selectAddress').children('a');
    var addressIds = '';
    if( aList && aList.length > 0){
        for(var i=0;i<aList.length;i++){
            addressIds += $(aList[i]).attr('addressId');
            if( i < aList.length-1){
                addressIds += ',';
            }
        }
    }
    var addressTableUrl = '/v1/api/admin/address/queryAll';
    var addressQueryObject = {
        addressIds:addressIds,
        pageSize:6
    }
    var addressColumnsArray =[
        {
            field: 'name',
            title: '名称',
            align: 'center'
        },
        {
           title: '操作',
           align: 'center',
           formatter: function (value, row, index) {
                return '<a class="btn" onclick="assignAddress(\''+row.id+'\',\''+wechatUserId+'\')">选择</a>';
           }
        }
    ];

    var tableSuccess = function(){
        $('#modalBody').find('.pull-left').remove();
    }
    $.initTable('addressTableList', addressColumnsArray, addressQueryObject, addressTableUrl,tableSuccess);
    $('#myModalLabel').html('场地管理');
    var buttonHtml = '<button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>';
    $('#modalFooter').html(buttonHtml);
    $('#modalody').find('.pull-left').remove();
    $('#myModal').modal('show');
}

var assignAddress = function(addressId,wechatId){
     $.danmuAjax('/v1/api/admin/wechatmanager/assignAddress?addressId='+addressId+'&wechatId='+wechatId, 'GET','json',null, function (data) {
          if(data.result == 200){
              console.log(data);
              $('#myModal').modal('hide');
              $.initTable('tableList', columnsArray, quaryObject, tableUrl);
              alert('设置成功');

          }else{
             alert('设置失败');
          }
    }, function (data) {
        console.log(data);
    });
}

//加载表格数据
$.initTable('tableList', columnsArray, quaryObject, tableUrl);