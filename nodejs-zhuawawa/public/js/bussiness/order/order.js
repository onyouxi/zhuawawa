var tableUrl = '/v1/api/admin/order/page';
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
        title: '商品名称',
        align: 'center',
        formatter: function (value, row, index) {
            if(null != row && null != row.item && "null" != row.item){
              return row.item.name;
            }
        }
    },
    {
        title: '金额',
        align: 'center',
        formatter: function (value, row, index) {
            if(null != row && null != row.item && "null" != row.item){
               var price = row.item.showPrice /100;
              return price;
            }
        }
    },
    {
        title: '数量',
        align: 'center',
        formatter: function (value, row, index) {
            if(null != row && null != row.order && "null" != row.order){
              return row.order.num;
            }
        }
    },
    {
        title: '支付状态',
        align: 'center',
        formatter: function (value, row, index) {
            if(null != row && null != row.order && "null" != row.order){
              if(row.order.status == 0){
                    return "支付成功";
              }else if(row.order.status == 1){
                    return "未支付";
              }else{
                    return "支付成功";
              }
            }
        }
    },
    {
        title: '购买时间',
        align: 'center',
        formatter: function (value, row, index) {
            if(null != row && null != row.order){
              return new Date(parseInt(row.order.createTime)).format('yyyy-MM-dd hh:mm:ss');
            }
        }
    },
    {
        title: '取货码',
        align: 'center',
        formatter: function (value, row, index) {
            if(null != row && null != row.order){
              return row.order.code;
            }
        }
    },
    {
        title: '取货时间',
        align: 'center',
        formatter: function (value, row, index) {
            if(null != row && null != row.order){
              if( null == row.order.getItemTime){
                return "未取货";
              }else{
                return new Date(parseInt(row.order.getItemTime)).format('yyyy-MM-dd hh:mm:ss');
              }
            }
        }
    },
    {
        title: '发货人',
        align: 'center',
        formatter: function (value, row, index) {
            if(null != row && null != row.adminUser){
              return row.adminUser.userName;
            }else{
               return "未发货";
            }
        }

    }
];
var quaryObject = {
    pageSize: 20
};

//加载表格数据
$.initTable('tableList', columnsArray, quaryObject, tableUrl);