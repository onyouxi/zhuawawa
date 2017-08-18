var tableUrl = '/v1/api/admin/reward/page';
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

var columnsArray = [
    {
        title: '序号',
        align: 'center',
        formatter: function (value, row, index) {
            return index+1;
        }
    },
    {
        title: '微信头像',
        align: 'center',
        width:'30%',
        formatter: function (value, row, index) {
            if(null != row && null != row.wechatUser){
                return '<img width="30%" src="'+row.wechatUser.imgUrl+'" />';
            }else{
               return "";
            }
        }
    },
    {
        title: '微信昵称',
        align: 'center',
        formatter: function (value, row, index) {
            if(null != row && null != row.wechatUser){
                return row.wechatUser.nick;
            }else{
               return "";
            }
        }
    },
    {
        title: '活动名称',
        align: 'center',
        formatter: function (value, row, index) {
            if(null != row && null != row.party && "null" != row.party){
              return row.party.name;
            }
        }
    },
    {
        field: 'wechatReward.total_fee',
        title: '金额',
        align: 'center',
    },
    {
        title: '打赏类型',
        align: 'center',
        formatter: function (value, row, index) {
            if(null != row && null != row.h5Template){
              return row.h5Template.tempTitle;
            }
        }
    },
    {
        field: 'wechatReward.createTime',
        title: '时间',
        align: 'center',
        formatter: function (value, row, index) {
              return new Date(parseInt(row.wechatReward.createTime)).format('yyyy-MM-dd hh:mm:ss');
        }
    }
];
var quaryObject = {
    pageSize: 20
};

var openPay = function(){
    $.initTable('tableList', columnsArray, quaryObject, tableUrl);
}

//加载表格数据
$.initTable('tableList', columnsArray, quaryObject, tableUrl);


var loveTableUrl = '/v1/api/admin/lovePay/page';

var loveColumnsArray = [
    {
        title: '序号',
        align: 'center',
        formatter: function (value, row, index) {
            return index+1;
        }
    },
    {
        title: '微信头像',
        align: 'center',
        width:'30%',
        formatter: function (value, row, index) {
            if(null != row && null != row.wechatUser){
                return '<img width="30%" src="'+row.wechatUser.imgUrl+'" />';
            }else{
               return "";
            }
        }
    },
    {
        title: '微信昵称',
        align: 'center',
        formatter: function (value, row, index) {
            if(null != row && null != row.wechatUser){
                return row.wechatUser.nick;
            }else{
               return "";
            }
        }
    },
    {
        title: '活动名称',
        align: 'center',
        formatter: function (value, row, index) {
            if(null != row && null != row.party && "null" != row.party){
              return row.party.name;
            }
        }
    },
    {
        field: 'lovePay.name',
        title: '表白昵称',
        align: 'center'

    },
    {
        field: 'lovePay.toName',
        title: '表白对象昵称',
        align: 'center'

    },
    {
        field: 'lovePay.messsage',
        title: '情话',
        align: 'center'
    },
    {
        title: '金额',
        align: 'center',
        formatter: function (value, row, index) {
            return row.lovePay.price/100;
        }
    },
    {
        title: '时间',
        align: 'center',
        formatter: function (value, row, index) {
              if( null != row.lovePay.createTime ){
                return new Date(parseInt(row.lovePay.createTime)).format('yyyy-MM-dd hh:mm:ss');
              }else{
                return "";
              }

        }
    },
    {
        title: '支付状态',
        align: 'center',
        formatter: function (value, row, index) {
            if(row.lovePay.status == 0){
                return "支付成功";
            }else if(row.lovePay.status == 1){
                return "未支付";
            }else if(row.lovePay.status == 2){
                return "支付失败";
            }else{
                return "未支付";
            }

        }
    }
];

var loveQuaryObject = {
    pageSize: 20
};

var openLovePay = function(){
    $.initTable('tableList', loveColumnsArray, loveQuaryObject, loveTableUrl);
}



