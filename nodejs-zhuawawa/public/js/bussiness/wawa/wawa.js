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
                btn='<a class="btn" onclick="updateCanUse(\''+row.id+'\',\''+row.name+'\',1)">停止使用</a>';
            }else{
                btn='<a class="btn" onclick="updateCanUse(\''+row.id+'\',\''+row.name+'\',0)">启用</a>';
            }
            return btn+'<a class="btn" onclick="openSelectPrize()">选择奖品</a><a class="btn" onclick="openUpdate(\''+row.id+'\')">修改</a><a class="btn" onclick="delMachine(\''+row.id+'\',\''+row.name+'\')">删除</a>';
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
             var footerHtml = '<button class="btn btn-primary" onclick="updateMachine(\''+row.id+'\')">修改</button>';
             $('#modalFooter').html(footerHtml);
             $('#myModal').modal('show');
          }else{
             alert('查询失败')
          }
        }, function (data) {
            console.log(data);
        });
}

var updateMachine = function(id){
    var obj = {
        id:id,
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

var delMachine = function(id,name){
    var obj = {
        id:id
    }
    if (confirm('确认要删除设备“' + name + '”吗？')) {
        $.danmuAjax('/v1/api/admin/machine/del', 'GET','json',obj, function (data) {
          if (data.result == 200) {
              console.log(data);
              $('#myModal').modal('hide');
              $.initTable('tableList', columnsArray, quaryObject, tableUrl);
              alert('删除成功')
          }else{
             alert('删除s失败')
          }
        }, function (data) {
            console.log(data);
        });
    }

}

var updateCanUse = function(id,name,canUse){
    var obj = {
        id:id,
        canUse:canUse
    }
    var confirmTitle;
    if(canUse == 0){
        confirmTitle='确认要启用';
    }else{
        confirmTitle='确认要停止使用';
    }
    if (confirm(confirmTitle+'“' + name + '”吗？')) {
        $.danmuAjax('/v1/api/admin/machine/updateCanUse', 'GET','json',obj, function (data) {
          if (data.result == 200) {
              console.log(data);
              $('#myModal').modal('hide');
              $.initTable('tableList', columnsArray, quaryObject, tableUrl);
              alert('操作成功')
          }else{
             alert('操作失败')
          }
        }, function (data) {
            console.log(data);
        });
    }

}

var openPrize = function(){
    $('#myModalLabel').html('新增奖品');
    var htmlStr = '<form id="edit-profile" class="form-horizontal"><div class="control-group" style="margin-top: 18px;">'+
   '<label class="control-label" style="width:50px">奖品名称</label><div class="controls" style="margin-left:60px;">'+
   '<input type="text" class="span4" id="prizeName" /></div><br>'+
   '<label class="control-label" style="width:50px">奖品积分</label><div class="controls" style="margin-left:60px;">'+
   '<input type="text" class="span4" id="prizePoints"  /></div><br>'+
   '<label class="control-label" style="width:50px">奖品图片</label><div class="controls" style="margin-left:60px;">'+
   '<input type="file" class="span4"  name="上传新图" /></div><br>'+
   '</div><br>';
    $('#modalBody').html(htmlStr);
    var footerHtml = '<button class="btn btn-primary" onclick="savePrize()">保存</button>';
    $('#modalFooter').html(footerHtml);
    $('#myModal').modal('show');
}

var savePrize = function(){
    var obj = {
        name:$('#prizeName').val(),
        points:$('#prizePoints').val()
    }
    $.danmuAjax('/v1/api/admin/prize/save', 'POST','json',obj, function (data) {
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


var openSelectPrize = function(){
        $('#modalBody').html('<table id="addressTableList" class="table table-striped" table-height="360"></table>');
        var buttonHtml = '<button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>';
        $('#modalFooter').html(buttonHtml);
        $('#myModal').modal('show');
        var prizeTableUrl = '/v1/api/admin/prize/page';
        var prizeQueryObject = {
            pageSize: 6
        }
        var prizeColumnsArray =[
            {
                field: 'name',
                title: '名称',
                align: 'center'
            },
            {
               field: '', title: '操作',
               align: 'center',
               formatter: function (value, row, index) {
                    return '<a class="btn" onclick="openUpdatePrize(\''+row.id+'\',\''+row.name+'\')">修改</a><a class="btn" onclick="delPrize(\''+row.id+'\',\''+row.name+'\')">删除</a><a class="btn" onclick="selectPrize(\''+row.id+'\',\''+row.name+'\')">选择</a>';
               }
            }
        ];
        var tableSuccess = function(){
            $('#modalBody').find('.pull-left').remove();
        }
        $.initTable('addressTableList', prizeColumnsArray, prizeQueryObject, prizeTableUrl,tableSuccess);

}



var openUpdatePrize = function(id){
    var obj = {
        id:id
    }
    $.danmuAjax('/v1/api/admin/prize/get', 'GET','json',obj, function (data) {
          if (data.result == 200) {
              $('#myModalLabel').html('修改奖品信息');
                  var htmlStr = '<form id="edit-profile" class="form-horizontal"><div class="control-group" style="margin-top: 18px;">'+
                 '<label class="control-label" style="width:50px">奖品名称</label><div class="controls" style="margin-left:60px;">'+
                 '<input type="text" class="span4" id="prizeName" value="'+data.data.name+'" /></div><br>'+
                 '<label class="control-label" style="width:50px">奖品积分</label><div class="controls" style="margin-left:60px;">'+
                 '<input type="text" class="span4" id="prizePoints"  value="'+data.data.points+'"/></div><br>'+
                 '<label class="control-label" style="width:50px">奖品图片</label><div class="controls" style="margin-left:60px;">'+
                 '<input type="file" class="span4"  name="上传新图" /></div><br>'+
                 '</div><br>';
                  $('#modalBody').html(htmlStr);
                  var footerHtml = '<button class="btn btn-primary" onclick="updatePrize('+data.data.id+')">修改</button>';
                  $('#modalFooter').html(footerHtml);
                  $('#myModal').modal('show');
          }else{
             alert('查询失败')
          }
    }, function (data) {
        console.log(data);
    });
}

var updatePrize = function(id){
    var obj = {
        id:id,
        name:$('#prizeName').val(),
        points:$('#prizePoints').val()
    }
    $.danmuAjax('/v1/api/admin/prize/update', 'POST','json',obj, function (data) {
          if (data.result == 200) {
              console.log(data);
              openSelectPrize();
              alert('更新成功')
          }else{
             alert('更新失败')
          }
        }, function (data) {
            console.log(data);
        });
}

var delPrize = function(id){
    var obj = {
        id:id
    }
    $.danmuAjax('/v1/api/admin/prize/del', 'GET','json',obj, function (data) {
          if (data.result == 200) {
              console.log(data);
              openSelectPrize();
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