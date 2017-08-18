var tableUrl = '/v1/api/admin/wxmessage/page';
var blockwordIndex = 0;
var columnsArray = [
    {
        field: 'words',
        title: '关键词',
        align: 'center'
    },
    {
        title: '自动回复内容',
        align: 'center',
        width:'60%',
        formatter: function (value, row, index) {
            if( null == row.mediaId){
                return row.message;
            }else{
                return "已经关联音频："+row.mediaName;
            }
        }
    },
    {
        field: '', title: '操作',
        align: 'center',
        formatter: function (value, row, index) {
            return '<a class="btn" onclick="openReplyWords(\''+row.id+'\',\''+row.words+'\',\''+row.message+'\',\''+row.mediaId+'\',\''+row.mediaName+'\')">修改</a><a class="btn" onclick="delReplyWords(\''+row.id+'\',\''+row.words+'\')">删除</a>';
        },
        events: 'operateEvents'
    }
];
var quaryObject = {
    pageSize: 20
};

var addWechatReply = function(){
    $('#addReplyWords').val($('#replyWords').val());
    $('#myModal').modal('show');
}

var findByWordsLike = function(){
    quaryObject = {
        words:$('#replyWords').val(),
        pageSize: 20
    }
    $.initTable('tableList', columnsArray, quaryObject, tableUrl,tableSuccess);
}

var tableSuccess = function(data){
    if(data.total == 0){
        $('#findReplyBtn').attr('disabled',false);
    }else{
        $('#findReplyBtn').attr('disabled',true);
    }
}

var findByWords = function(){
    var obj = {
        words:$('#addReplyWords').val()
    }
    var test = $.danmuAjax('/v1/api/admin/wxmessage/findByWords', 'GET','json',obj, function (data) {
        if(data.result == 200) {
            if(data.data > 0){
                if(!$('#addReplyWords').parent().children('.help-block').html()){
                    $('#addReplyWords').after('<p class="help-block" style="color:red">关键词不能重复</p>');
                }

            }else{
                 $('#addReplyWords').parent().children('.help-block').remove();

            }
         }
    }, function (data) {
        console.log(data);
    });
}

var saveReplyWords = function(){

    if(''==$('#addReplyWords').val()){
        $('#addReplyWords').after('<p class="help-block" style="color:red">关键词不能为空</p>');
        return;
    }else{
        if($('#addReplyWords').parent().children('.help-block').html()){
         $('#addReplyWords').parent().children('.help-block').remove();
        }
    }
    if('' == g_replyWordsId){
        findByWords();
        if($('#addReplyWords').parent().children('.help-block').html()){
            return;
        }
    }

    if(''==$('#message').val()){
        if(!$('#message').parent().children('.help-block').html()){
            $('#message').after('<p class="help-block" style="color:red">自动回复不能为空</p>');
         }
        return;
    }else{
        if($('#message').parent().children('.help-block').html()){
         $('#message').parent().children('.help-block').remove();
        }
    }
    var postUrl = '';
     var obj = null;
    if( '' == g_replyWordsId){
        postUrl = '/v1/api/admin/wxmessage/save';
        obj = {
                words:$('#addReplyWords').val(),
                message:$('#message').val()
            }
    }else{
        postUrl = '/v1/api/admin/wxmessage/update';
        obj = {
            id:g_replyWordsId,
            words:$('#addReplyWords').val(),
            message:$('#message').val()
        }
    }

    $.danmuAjax(postUrl, 'POST','json',obj, function (data) {
        if(data.result == 200) {
          console.log(data);
          $.initTable('tableList', columnsArray, quaryObject, tableUrl,tableSuccess);
          $('#myModal').modal('hide');
          g_replyWordsId='';
          $('#addReplyWords').val('');
          $('#message').val('');
         }else{
            alert('添加失败');
         }
    }, function (data) {
        console.log(data);
    });
}

var delReplyWords = function(id,name){
    if(confirm('确定要删除'+name+'吗？')){
        var obj = {
            id:id
        }
        $.danmuAjax('/v1/api/admin/wxmessage/del', 'GET','json',obj, function (data) {
            if(data.result == 200) {
              console.log(data);
              $.initTable('tableList', columnsArray, quaryObject, tableUrl,tableSuccess);
             }else{
                alert('删除失败');
             }
        }, function (data) {
            console.log(data);
        });
     }
}

var g_replyWordsId = ''

var openReplyWords = function(id,words,message,mediaId,mediaName){
   g_replyWordsId = id;
   $('#voiceControl').html('');
   $('#addReplyWords').val(words);
   if( null == mediaId || 'null' == mediaId){
        $('#message').val(message);
   }else{
        $('#messageControl').html('<label class="control-label" for="message">已经关联语音</label><div class="controls">'+mediaName+'<a onclick="cancelVoice(\''+id+'\')">取消</a></div>');
        $('#voiceControl').html('');
   }
   $('#selectVoiceBtn').click(function(){
            openVoice(g_replyWordsId);
   });
   $('#myModal').modal('show');

}


var openVoice = function(msgId){
    $('#voiceControl').html('<table id="voiceTableList" class="table table-striped" table-height="360"></table>');
    var voiceUrl = '/v1/api/admin/wxmessage/findVoice';
    var voiceColumnsArray = [
        {
            field: 'name',
            title: '名称',
            align: 'center'
        },
        {
            field: 'media_id',
            title: '音频id',
            align: 'center'
        },
        {
            title: '操作',
            align: 'center',
            formatter: function (value, row, index) {
                return '<a class="btn" onclick="selectVoice(\''+msgId+'\',\''+row.name+'\',\''+row.media_id+'\')">选择</a>';
            },
            events: 'operateEvents'
        }
    ];
    var voiceQuaryObject = {
        page:1,
        size:20
    };
    $.initTable('voiceTableList', voiceColumnsArray, voiceQuaryObject, voiceUrl,tableSuccess);
}

var selectVoice = function(msgId,name,media_id){
    var obj = {
        id:msgId,
        mediaId:media_id,
        mediaName:name
    }
    $.danmuAjax('/v1/api/admin/wxmessage/selectVoice', 'GET','json',obj, function (data) {
        if(data.result == 200) {
           console.log(data);
           $('#messageControl').html('<label class="control-label" for="message">已经关联语音</label><div class="controls">'+name+'<a onclick="cancelVoice()">取消</a></div>');
           $('#voiceControl').html('');
         }else{
            alert('删除失败');
         }
    }, function (data) {
        console.log(data);
    });
}

var cancelVoice = function(msgId){
    if(confirm('确定要取消关联音频文件吗？')){
        var obj = {
            id:msgId
        }
        $.danmuAjax('/v1/api/admin/wxmessage/cancelVoice', 'GET','json',obj, function (data) {
            if(data.result == 200) {
              console.log(data);
              $('#messageControl').html('<label class="control-label" for="message" id="messageControl">自动回复内容</label><textarea style="width:300px;height:50px" id="message"></textarea></div>');
             }else{
                alert('删除失败');
             }
        }, function (data) {
            console.log(data);
        });
     }
}


//加载表格数据
$.initTable('tableList', columnsArray, quaryObject, tableUrl,tableSuccess);