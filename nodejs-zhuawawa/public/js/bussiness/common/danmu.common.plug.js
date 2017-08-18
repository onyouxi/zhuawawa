
(function ($) {
    $.checkObject = {};

    /**1.审核节目 2：定时弹幕**/
    $.pageType;
    
    $.initTitle = function (object) {

        object.pageSize=12;
        if(object.pageNumber==undefined){
            object.pageNumber=1
        }

        $.danmuAjax('/v1/api/admin/findDanmuType', 'GET', 'json', {'pageNumber':object.pageNumber,'pageSize':object.pageSize}, function (data) {
            console.log(data);
            if (data.result == 200) {

                var array = data.data.content;
                var total = data.data.totalElements;
                var number = data.data.number;
                if(array!=null && array.length>0){
                    var divobject = $("#"+object.divId);
                    divobject.empty();
                    for(var i=0; i<array.length; i++){
                        //var tempObject = {};
                        var id = array[i].id;
                        var name=array[i].name;
                        var button = '<button class="btn" id="'+id+'"  style=" width: 100px; height:30px;margin-top: 1px; margin-right: 0.5em; vertical-align:middle;text-align:center;" value="'+id+'">'+name+'</button>';
                        divobject.append(button);
                        $('#'+id).click(function(){
                            $.createPlug($(this).val(),object.partyId);
                            $("#templateId").val($(this).val());
                            return false;
                        });
                    }


                    var totalPageNo =  parseInt((total  + object.pageSize -1) / object.pageSize);
                    if(object.pageNumber == 1 && totalPageNo > 1){
                        divobject.append("<br/><button  type='button' class='btn btn-info titlePre disabled'>&lt;</button> &nbsp;")
                        divobject.append("<button type='button' class='btn btn-info titleNext'>&gt;</button>")
                        $('.titleNext').click(function(){
                            object.pageNumber = object.pageNumber+1;
                            $.initTitle(object);
                            return;
                        });
                    }else if(object.pageNumber == totalPageNo && totalPageNo > 1){

                        divobject.append("<br/><button  type='button' class='btn btn-info titlePre'>&lt;</button> &nbsp;")
                        $('.titlePre').click(function(){
                            object.pageNumber = object.pageNumber-1;
                            $.initTitle(object);
                            return;
                        });
                        divobject.append("<button type='button' class='btn btn-info titleNext disabled'>&gt;</button>")
                    }else if(totalPageNo ==1 ){
                        divobject.append("<br/><button  type='button' class='btn btn-info titlePre disabled'>&lt;</button> &nbsp;")
                        divobject.append("<button type='button' class='btn btn-info titleNext disabled'>&gt;</button>")
                    }else{
                        divobject.append("<br/><button  type='button' class='btn btn-info titlePre'>&lt;</button> &nbsp;")
                        $('.titlePre').click(function(){
                            object.pageNumber = object.pageNumber-1;
                            $.initTitle(object);
                            return;
                        });
                        divobject.append("<button type='button' class='btn btn-info titleNext'>&gt;</button>")
                        $('.titleNext').click(function(){
                            object.pageNumber = object.pageNumber+1;
                            $.initTitle(object);
                            return;
                        });
                    }

                    $.setTitleListPlug(object);

                    var hiddenInput="<input type='hidden' id='templateId' name='templateId'/>"
                    divobject.append(hiddenInput);
                    $.createPlug(array[0].id,object.partyId);
                }
            }
        }, function (data) {
            console.log(data);
        });
    }


    /**
     *
     * @param divId 外部divId
     * @param widgetId 组件I
     * @param valueList 值
     * @param functionName 方法
     */
    $.setTitleListPlug =function (object) {
        var divId = object.divId;
        var valueList = object.valueList;
        var partyId = object.partyId;

        var divobject = $("#"+divId);
        if(object.partyId!=undefined){
            var partyIdInput="<input type='hidden' id='partyId' name='partyId' value='"+object.partyId+"'/>"
            divobject.append(partyIdInput);
        }

        if(object.addressId!=undefined){
            var addressIdInput="<input type='hidden' id='addressId' name='addressId' value='"+object.addressId+"'/>"
            divobject.append(addressIdInput);
        }


    }

    $.createPlug=function (id,partyId,defaultValueObject) {

        $("#templateId").val(id);

        $.danmuAjax('/v1/api/admin/findDanmuTemplateInfo/'+id, 'GET', 'json', {}, function (data) {
            console.log(data);
            if (data.result == 200) {
                var temObject = data.data;
                console.log(data);
                var cmdTempComponentDataList = temObject.cmdTempComponentDataList;
                /*if(this.globalObject!=undefined){
                    globalObject = cmdTempComponentDataList;
                }*/
                $.checkObject = temObject;
                var divId = "componentDivId";
                $("#"+divId).empty().append("<input type='hidden' id='danmuId' name='danmuId' />");
                createTitleComponent({'divId':divId,'name':temObject.name})
                if(cmdTempComponentDataList!=null){
                    for(var i=0; i<cmdTempComponentDataList.length; i++){
                        var componentObject = cmdTempComponentDataList[i];
                        var componentId  = componentObject.componentId;
                        var object = {
                            divId : divId,
                            widgetId : componentObject.id,
                            //defaultValue : componentObject.defaultValue,
                            defaultValue:(defaultValueObject==null?componentObject.defaultValue:defaultValueObject[componentObject.key]),
                            valueList : componentObject.cmdComponentValueList,
                            componentId : componentId,
                            componentType:componentObject.componentType,
                            key : componentObject.key,
                            check:componentObject.checkRule,
                            partyId:partyId
                        };

                        //创建标题组件
                        if(componentId>=0 && componentId<=3){
                            createCompentBycomponentId(object);
                        }else{
                            createCompentBycomponentType(object);
                        }
                    }
                }
            }
        },null,false);
    }
    
    function createTitleComponent(object) {
        var divId = object.divId;
        var divObject = $("#"+divId).append('<div id="'+divId+'Div"></div>');
        $('#'+divId+'Div').append('<div style="margin-bottom: 1px;margin-top: 1px;"><i class="icon-bookmark"></i><span style="margin-left: 3px;font-size: larger;">'+object.name+'</span></div>')

    }


    function findResource(object){
        var divId = object.divId;
        var widgetId=object.widgetId;
        var key = object.key;
        var partyId = object.partyId;
        var url  = '/v1/api/admin/initResource?partyId=' + partyId;

        //getVideoPage(1);
        $.danmuAjax(url, 'GET', 'json', {}, function (data) {
            if (data.result == 200) {
                var divObject = $("#"+divId).append('<div id="'+widgetId+'Div"></div>');
                var resourceArray = [];
                if(object.componentId==3){
                    //视频特效
                    resourceArray=data.data.specialVideos;
                    if(resourceArray!==null && resourceArray!=undefined){
                        for (var i = 0; i < resourceArray.length; i++) {
                            var specialVideo = resourceArray[i];
                            var buttonName = specialVideo.resourceName.substring(0,4);
                            var id=widgetId+i;
                            var html = '<button class="btn" id="'+id+'"  style=" width: 65px; height:30px;margin-top: 1px; margin-right: 0.5em; " title="' + specialVideo.resourceName + '" value="'+specialVideo.id+'">' + buttonName + '</button>';
                            divObject.append(html);

                            if(object.defaultValue==specialVideo.id){
                                $('#'+id).addClass("button-border-color");
                            }else{
                                $('#'+id).removeClass("button-border-color");
                            }
                            $('#'+id).click(function(){
                                $("#"+key).val($(this).val());
                                return false;
                            });
                        }
                    }

                }else if(object.componentId==2){
                    //图片特效
                    resourceArray=data.data.specialImages;
                    if(resourceArray!==null && resourceArray!=undefined){
                        for (var i = 0; i < resourceArray.length; i++) {
                            var image = resourceArray[i];
                            var id=widgetId+i;
                            var html = '<input type="image" id="'+id+'" src="' + _baseImageUrl + image.fileUrl + '" style="width: 50px; height: 50px;margin-left: 1em;" title="' + image.fileUrl + '" value="'+image.id+'"/>';
                            divObject.append(html);
                            if(object.defaultValue==image.id){
                                $('#'+id).addClass("button-border-color");
                            }else{
                                $('#'+id).removeClass("button-border-color");
                            }
                            $('#'+id).click(function(){
                                $("#"+key).val($(this).val());
                                $("input[type='image']").each(function () {
                                    $(this).removeClass("button-border-color");
                                })
                                $(this).addClass("button-border-color");
                                return false;
                            });

                        }
                    }
                }else if(object.componentId==1){
                    //表情特效
                    resourceArray=data.data.expressions;
                    if(resourceArray!=null && resourceArray!=undefined){
                        for (var i = 0; i < resourceArray.length; i++) {
                            var expression = resourceArray[i];
                            var id=widgetId+i;
                            var html = '<input type="image" id="'+id+'" src="' + _baseImageUrl + expression.smallFileUrl + '"  style="width: 50px; height: 50px;margin-left: 1em;"  title="' + expression.smallFileUrl + '" value="'+expression.id+'" />';
                            divObject.append(html);
                            if(object.defaultValue==expression.id){
                                $('#'+id).addClass("button-border-color");
                            }else{
                                $('#'+id).removeClass("button-border-color");
                            }
                            $('#'+id).click(function(){
                                $("#"+key).val($(this).val());
                                $("input[type='image']").each(function () {
                                    $(this).removeClass("button-border-color");
                                })
                                $(this).addClass("button-border-color");
                                return false;
                            });
                        }
                    }

                }


                var hiddenInput="<input type='hidden' id='"+key+"' name='"+key+"' value='"+object.defaultValue+"'/>"
                divObject.append(hiddenInput);


            } else {
                alert("资源加载失败")
            }
        },null,false);
    }

    var getVideoPage = function(divId,widgetId,fileType,pageNo,key){


        var obj={fileType:fileType,pageNo:pageNo, pageSize:10};

        //{'pageNumber':(object.pageNumber==undefined?0:object.pageNumber)

        $.danmuAjax('/v1/api/admin/resource/findResourcePage', 'GET','json',obj, function (data) {

            var divObject = $("#"+divId)

            var buttonDivId = key+'Div';
            divObject.append('<div id="'+buttonDivId+'"></div>');
            var buuttonDiv=$("#"+buttonDivId);
            buuttonDiv.empty();
            //divObject.empty();

            var array = data.content;
            var total = data.totalElements;
            var number = data.number;
            for(var i=0;i<array.length;i++){

                var id=widgetId+i;
                if(fileType==3){
                    var specialVideo = array[i];
                    var buttonName = specialVideo.resourceName.substring(0,4);
                    var html = '<button  class="btn"  id="'+id+'" style=" width: 65px; height:30px;margin-top: 1px; margin-right: 0.5em; " title="' + specialVideo.resourceName + '" value="'+array[i].id+'">'+buttonName+'</button>';
                    buuttonDiv.append(html);
                    $('#'+id).click(function(){
                        $("#"+key).val($(this).val());
                        return false;
                    });
                }else if(fileType==2){
                    var fileUrl = _baseImageUrl+array[i].fileUrl;
                    var html = '<input type="image" id="'+id+'" src="' + fileUrl + '" style="width: 50px; height: 50px;margin-left: 1em;" title="' + array[i].resourceName + '" value="'+array[i].id+'" />';
                    buuttonDiv.append(html);
                    $('#'+id).click(function(){
                        $("#"+key).val($(this).val());

                        $("input[type='image']").each(function () {
                            $(this).removeClass("button-border-color");
                        })
                        $(this).addClass("button-border-color");
                        return false;
                    });
                }else if(fileType==1){
                    var fileUrl = _baseImageUrl+array[i].smallFileUrl;
                    var html = '<input type="image" id="'+id+'" src="' + fileUrl + '" style="width: 50px; height: 50px;margin-left: 1em;" title="' + array[i].resourceName + '" value="'+array[i].id+'"/>';
                    buuttonDiv.append(html);
                    $('#'+id).click(function(){
                        $("#"+key).val($(this).val());
                        $("input[type='image']").each(function () {
                            $(this).removeClass("button-border-color");
                        })
                        $(this).addClass("button-border-color");
                        return false;
                    });
                }
            }

            var totalPageNo =  parseInt((total  + obj.pageSize -1) / obj.pageSize);
            if(obj.pageNo == 1 && totalPageNo > 1){
                buuttonDiv.append("<br/><button  type='button' class='btn btn-info "+key+"ButtonPre disabled'>&lt;</button> &nbsp;")
                buuttonDiv.append("<button type='button' class='btn btn-info "+key+"ButtonNext'>&gt;</button>")
                $('.'+key+'ButtonNext').click(function(){
                    getVideoPage(divId,widgetId,fileType,obj.pageNo+1,key);
                    return;
                });
            }else if(obj.pageNo == totalPageNo && totalPageNo > 1){

                buuttonDiv.append("<br/><button  type='button' class='btn btn-info "+key+"ButtonPre'>&lt;</button> &nbsp;")
                $('.'+key+'ButtonPre').click(function(){
                    getVideoPage(divId,widgetId,fileType,obj.pageNo-1,key)
                    return;
                });
                buuttonDiv.append("<button type='button' class='btn btn-info "+key+"ButtonNext disabled'>&gt;</button>")
            }else if(totalPageNo ==1 ){
                buuttonDiv.append("<br/><button  type='button' class='btn btn-info "+key+"ButtonPre disabled'>&lt;</button> &nbsp;")
                buuttonDiv.append("<button type='button' class='btn btn-info "+key+"ButtonNext disabled'>&gt;</button>")
            }else{
                buuttonDiv.append("<br/><button  type='button' class='btn btn-info "+key+"ButtonPre'>&lt;</button> &nbsp;")
                $('.'+key+'ButtonPre').click(function(){
                    getVideoPage(divId,widgetId,fileType,obj.pageNo-1,key)
                    return;
                });
                buuttonDiv.append("<button type='button' class='btn btn-info "+key+"ButtonNext'>&gt;</button>")
                $('.'+key+'ButtonNext').click(function(){
                    getVideoPage(divId,widgetId,fileType,obj.pageNo+1,key);
                    return;
                });
            }

            var hiddenInput="<input type='hidden' id='"+key+"' name='"+key+"'/>"
            divObject.append(hiddenInput);
        }, function (data) {
            console.log(data);
        });
    }


    function createCompentBycomponentId(object) {

        //object.partyId="58f5d90a0cf25cad536db129";
        var componentId = object.componentId;
        if(componentId==0){
            //不生成任何控件
           // alert('无组件');
            console.log('不生成任何组件');
        }else if(componentId==1){
            //alert('特效视频');
            if(object.partyId!=undefined){
                findResource(object);
            }else{
                getVideoPage(object.divId,object.widgetId,1,1,object.key);
            }

        }else if(componentId==2){
            if(object.partyId!=undefined){
                findResource(object);
            }else{
                getVideoPage(object.divId,object.widgetId,2,1,object.key);
            }

        }else if(componentId==3){
            //checkbox
            //alert('表情图片');
            if(object.partyId!=undefined){
                findResource(object);
            }else{
                getVideoPage(object.divId,object.widgetId,3,1,object.key);
            }

        }
    }
    function createCompentBycomponentType(object) {
        var componentType = object.componentType;
        if(componentType==0){
            //不生成任何控件
            //alert('text');
            $.setTextPlug(object);
        }else if(componentType==1){
            //生成textarea控件
            $.setTextAreaPlug(object);
        }else if(componentType==2){
            //生成slect控件
            $.setSelectPlug(object);
        }else if(componentType==3){
            //生成radiobutton
            $.setRadioButtonListPlug(object);
        }else if(componentType==4){
            //checkbox
            $.setCheckBoxListPlug(object);
        }
    }

    $.setRadioButtonListPlug = function (object) {
        var divId = object.divId;
        var valueList = object.valueList;
        var widgetId=object.widgetId;
        var key = object.key;

        var divObject = $("#"+divId).append('<div id="'+widgetId+'Div"></div>');
        if( null != valueList){
            for( var i=0;i<valueList.length;i++){
                var name = valueList[i].name;
                var value = valueList[i].value;
                var id= valueList[i].id;
                var radioButton = '<label style="display: inline-block; width: 80px;"><input id="'+widgetId+id+'" name="'+key+'" type="radio" value="'+value+'" style="display: inline-block;"/>'+name+'</label> ';
                divObject.append(radioButton);
                if(object.defaultValue==value){
                    $("#"+widgetId+id).attr("checked",'checked');
                }
            }
        }
    }
    $.setCheckBoxListPlug = function (object) {
        var divId = object.divId;
        var valueList = object.valueList;
        var widgetId=object.widgetId;
        var key = object.key;
        var divObject = $("#"+divId).append('<div id="'+widgetId+'Div"></div>');

        if( null != valueList){
            for( var i=0;i<valueList.length;i++){
                var name = valueList[i].name;
                var value = valueList[i].value;
                var id= valueList[i].id;
                var checkBox = '<label style="display: inline-block; width: 80px;"><input id="'+widgetId+id+'" name="'+key+'" type="checkbox" value="'+value+'" style="display: inline-block;"/>'+name+'</label> ';
                divObject.append(checkBox);
                if(object.defaultValue==value){
                    $("#"+widgetId+id).attr("checked",'true');
                }
            }
        }
    }

    $.setSelectPlug =function (object) {

        var divId = object.divId;
        var valueList = object.valueList;
        var widgetId=object.widgetId;
        var key = object.key;
        var divObject = $("#"+divId).append('<div id="'+widgetId+'Div"></div>');
        var select='<select style="width: 260px;" id="'+widgetId+'" name="'+key+'">';
        if( null != valueList){
            for( var i=0;i<valueList.length;i++){
                select += '<option value=' + valueList[i].value + '>' + valueList[i].name + '</option>';
            }
        }
        select += '</select>';
        divObject.append(select);
        //set default value
        $("#"+widgetId).val(object.defaultValue);
    }

    $.setTextAreaPlug =function (object) {
        var divId = object.divId;
        var widgetId = object.widgetId;
        var key = object.key;
        var keyUp= object.keyUp;
        var keyDown= object.keyDown;
        var defaultValue = object.defaultValue;
        var divObject = $("#"+divId).append('<div id="'+widgetId+'Div"></div>');
        //placeholder="'+defaultValue+'"
        var textArea = '<textarea id="'+widgetId+'" name="'+key+'" class="form-control" rows="3" style="width: 260px;height: 80px;outline:none;resize:none;"></textarea>';
        divObject.append(textArea);
        $("#"+widgetId).val(object.defaultValue);

        /*var textArea = '<div id="'+widgetId+'Div"><textarea id="'+widgetId+'" name="'+widgetId+'" class="form-control" rows="3" placeholder="'+defaultValue+'"style="width: 260px;height: 80px;outline:none;resize:none;"></textarea></div>';
        object.append(textArea);
        if(keyUp!=undefined){
            $("#"+widgetId).bind("keyup",function () {
                keyUp();
            })
        }
        if(keyDown!=undefined){
            $("#"+widgetId).bind("keydown",function () {
                keyDown();
            })
        }*/
    }

    $.setTextPlug =function (object) {
        var divId = object.divId;
        var widgetId = object.widgetId;
        var key = object.key;
        var keyUp= object.keyUp;
        var keyDown= object.keyDown;
        var defaultValue = object.defaultValue;
        var divObject = $("#"+divId).append('<div id="'+widgetId+'Div"></div>');
        //placeholder="'+defaultValue+'"
        var textArea = '<input id="'+widgetId+'" name="'+key+'" class="form-control"  style="width: 260px;"></input>';
        divObject.append(textArea);
        $("#"+widgetId).val(object.defaultValue);
    }


    $.executeCompontentCheck =function () {
        var cmdTempComponentDataList = $.checkObject.cmdTempComponentDataList;
        if(cmdTempComponentDataList==null){
            alert('组件不存在');
            return false;
        }
        var count = 0;
        for(var i=0; i<cmdTempComponentDataList.length; i++){
            var compontent = cmdTempComponentDataList[i];
            var boolean = $.checkAllCompontent(compontent);
            console.log('check boolean:'+boolean);
            if(!boolean){
                count++;
            }
        }
        if(count>0){
            return false;
        }
        return true;
    }
    
    $.checkAllCompontent =function (compontent) {
        //组件的id 0无组件 3特效视频 2特效图片 1表情图片
        //String  componentId;
        var componentId = compontent.componentId;
        console.log('componentId:'+componentId);

        if(componentId==2){
            var temp = $("#"+compontent.key).val();
            if(temp==null || temp==""){
                alert("请选择图片特效!");
                return false;
            }
        }else if(componentId==1){
            var temp = $("#"+compontent.key).val();
            if(temp==null || temp==""){
                alert("请选择表情特效!");
                return false;
            }
        }else{
            return $.checkCompontent(compontent);
        }

        return true;
    }

    $.checkCompontent=function (compontent) {
        var rule = compontent.checkRule;
        //组件的类型 0text 1textarea 2select  3radiobutton 4checkbox
        var componentType = compontent.componentType;
        //0数字 1布尔值 2字符串 3数组
        var type = compontent.type;
        if(componentType==0){
            var content = $("*[name='"+compontent.key+"']").val();
            return $.checContentIsOk(content,rule,type)
        }else if(componentType==1){
            //textArea
            var content = $("*[name='"+compontent.key+"']").val();
            if(type==3){
                var array = content.split(",");
                return $.checkLogicArray(array,rule);
            }else{
                //验证其他类型
                return $.checContentIsOk(content,rule,type)
            }
            
        }else if(componentType==2){
            //select
            var content = $("*[name='"+compontent.key+"']").val();
            if(type==3){
                alert('select type is array');
                return false;
            }
            return $.checContentIsOk(content,rule,type)
        }else if(componentType==3){
            //radioButton
            var content = $('input[name="'+compontent.key+'"]:checked').val();
            if(type==3){
                alert('radio type is array');
                return false;
            }
             return $.checContentIsOk(content,rule,type)
        }else if(componentType==4){
            //checkBox
            if(type!=3){
                alert('checkBox type is  not array');
                return false;
            }
            var array =[];
            $('input[name="'+compontent.key+'"]:checked').each(function(){
                array.push($(this).val());
            });
            return $.checkLogicArray(array,rule);
        }
        return true;
    }


    $.checContentIsOk =function (content, rule,type) {
        if(rule!=null && rule!==undefined){
            var isNull =  rule.split("-")[0];
            var max =  rule.split("-")[1];

            if($.checkisNotEmpty(content)){
                if(content.length>max){
                    alert('最大长度不能超过'+max);
                    return false;
                }else{
                    $.checkCompontentDType(type,content);
                }
            }else{
                if(isNull!=0){
                    alert("不能为空！");
                    return false;
                }
            }
        }else{
            return $.checkCompontentDType(type,content);
        }
        return true;
    }
    $.checkLogicArray =function (array,rule) {
        var specialBoolean  = $.checkSpecialArray(array);
        if(!specialBoolean){
            alert('非法数组!');
            return false;
        }
        if(!$.checkIsArray(array)){
            alert('数组格式不正确!');
            return false;
        }
        if(rule!=null && rule!==undefined){
            var isNull =  rule.split("-")[0];
            var max =  rule.split("-")[1];
            if(array.length==0){
                if(isNull!=0){
                    alert('不能输入空值!');
                    return false;
                }
            }
            if(array.length>5){
                alert('数组过长!');
                return false;
            }
        }

        return true;
    }
    
    $.chckIsEmpty =function (object) {
        if(object==null || object==undefined || object==""){
            return true;
        }
        return false;
    }

    $.checkisNotEmpty = function (object) {
        return !$.chckIsEmpty(object);
    }

    $.checkCompontentDType = function (type,content) {
        //0数字 1布尔值 2字符串 3数组
        if(type==0){
            var flg =$.checkNumber(content);
            if(!flg){
                alert('请输入数字！');
                return false;
            }
        }else if(type==1){
            var flg =$.checBoolean(content);
            if(!flg){
                alert('请输布尔类型！');
                return false;
            }
        }else if(type==2){

        }else if(type==3){
            var flg = $.checkIsArray(content);
            if(!flg){
                alert('不是数组!');
                return false;
            }
        }
        return true;
    }

    $.checkSpecialArray = function (array) {
        if(array!=null && array.length>0){
            for(var i=0; i<array.length; i++){
                if(array[i]==null || array[i]==""){
                    return false;
                }
            }
        }
        return true;
    }
    $.checkIsArray = function (object) {
        return object && typeof object==='object' &&
            Array == object.constructor;
    }


    $.checBoolean = function (object) {
        if(object == "true" || object == "false"){
            return true;
        }
        return false;
    }

    $.checkNumber =function(object) {
        var reg = /^[0-9]+.?[0-9]*$/;
        if (reg.test(object)) {
            return true;
        }
        return false;
    }
})(jQuery);