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
                btn='<a class="btn" >停止使用</a>';
            }else{
                btn='<a class="btn" >启用</a>';
            }
            return btn+'<a class="btn" onclick="delUser(\''+row.id+'\',\''+row.nick+'\')">修改</a><a class="btn" onclick="delUser(\''+row.id+'\',\''+row.nick+'\')">删除</a>';
        },
        events: 'operateEvents'
    }
];
var quaryObject = {
    pageSize: 20
};

var openSave = function(){
    $('#myModalLabel').html('新建设备');
    var htmlStr = '<form id="edit-profile" class="form-horizontal"><div class="control-group" style="margin-top: 18px;">'+
   '<label class="control-label" style="width:50px">设备名称</label><div class="controls" style="margin-left:60px;">'+
   '<input type="text" class="span4" id="name" maxlength="6" /></div><br>'+
   '<label class="control-label" style="width:50px">描述信息</label><div class="controls" style="margin-left:60px;">'+
   '<textarea id="des" class="span6"></textarea></div><br>'+
   '</div><br>';
    $('#modalBody').html(htmlStr);
    var footerHtml = '<button class="btn btn-primary" onclick="saveMachine()">保存</button>';
    $('#modalFooter').html(footerHtml);
    $('#myModal').modal('show');
}

var saveMachine = function(){
    var obj = {
        name:$('#name').val(),
        des:$('#des').val()
    }
    $.danmuAjax('/v1/api/admin/machine/save', 'POST','json',obj, function (data) {
      if (data.result == 200) {
          console.log(data);
          $('#myModal').modal('hide');
          $.initTable('tableList', columnsArray, quaryObject, tableUrl);
          alert('创建成功')
      }else{
         alert('创建失败')
      }
    }, function (data) {
        console.log(data);
    });
}

var openUpdate = function(id){
    var obj = {
        id:id
    }
    $.danmuAjax('/v1/api/admin/machine/get', 'GET','json',obj, function (data) {
          if (data.result == 200) {
             $('#myModalLabel').html('修改设备信息');
             var htmlStr = '<form id="edit-profile" class="form-horizontal"><div class="control-group" style="margin-top: 18px;">'+
            '<label class="control-label" style="width:50px">设备名称</label><div class="controls" style="margin-left:60px;">'+
            '<input type="text" class="span4" id="name" maxlength="6" value="'+data.data.name+'"/></div><br>'+
            '<label class="control-label" style="width:50px">描述信息</label><div class="controls" style="margin-left:60px;">'+
            '<textarea id="des" class="span6">'+data.data.des+'</textarea></div><br>'+
            '</div><br>';
             $('#modalBody').html(htmlStr);
             var footerHtml = '<button class="btn btn-primary" onclick="updateMachine()">修改</button>';
             $('#modalFooter').html(footerHtml);
             $('#myModal').modal('show');
          }else{
             alert('查询失败')
          }
        }, function (data) {
            console.log(data);
        });
}

var updateMachine = function(){
    var obj = {
        name:$('#name').val(),
        des:$('#des').val()
    }
    $.danmuAjax('/v1/api/admin/machine/update', 'POST','json',obj, function (data) {
      if (data.result == 200) {
          console.log(data);
          $('#myModal').modal('hide');
          $.initTable('tableList', columnsArray, quaryObject, tableUrl);
          alert('更新成功')
      }else{
         alert('更新失败')
      }
    }, function (data) {
        console.log(data);
    });
}

//加载表格数据
$.initTable('tableList', columnsArray, quaryObject, tableUrl);