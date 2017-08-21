var tableUrl = '/v1/api/admin/machine/page';
var columnsArray = [
    {
        title: '序号',
        align: 'center',
        formatter: function (value, row, index) {
            return index+1;
        }
    },
    {
        field:'name',
        title: '设备名称',
        align: 'center'

    },
    {
        field: 'code',
        title: '设备序列号',
        align: 'center'
    },
    {
        title: '设备状态',
        align: 'center',
        formatter: function (value, row, index) {
            if(row.canUse == 0){
                return '运行中';
            }else if(row.canUse == 1){
                 return '停止使用';
            }else{
                return '未知';
            }
        }
    },
    {
        title: '排队情况',
        align: 'center',
        formatter: function (value, row, index) {
            if(row.status == 0){
                return '空闲';
            }else if(row.status == 1){
                 return '使用中';
            }else{
                return '未知';
            }
        }
    },
    {
        title: '操作',
        align: 'center',
        formatter: function (value, row, index) {
            var btn;
            if(row.canUse == 0){
                btn='<a>停止使用</a>';
            }else{
                btn='<a>启用</a>';
            }
            return btn+'<a class="btn" onclick="delUser(\''+row.id+'\',\''+row.nick+'\')">删除</a>';
        },
        events: 'operateEvents'
    }
];
var quaryObject = {
    pageSize: 20
};

//加载表格数据
$.initTable('tableList', columnsArray, quaryObject, tableUrl);