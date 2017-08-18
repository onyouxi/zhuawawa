/*!
 * danmu.table.plug v1.0.0
 */
(function ($) {
        $.initTable = function (id, columnsArray, queryParamObject, url,onLoadSuccessFunc,formatNoMatches) {
            var tableHeight = $('#'+id).attr('table-height');
            $('#' + id).bootstrapTable('destroy').bootstrapTable({
                url: url,
                columns: columnsArray,
                height: (tableHeight!=null)?tableHeight:550,
                striped: true,  //表格显示条纹
                pagination: true, //启动分页
                pageSize: (queryParamObject.pageSize!=null)?queryParamObject.pageSize:10,  //每页显示的记录数
                pageNumber: (queryParamObject.pageNumber!=null)?queryParamObject.pageNumber:1, //当前第几页
                pageList: [5, 10, 15, 20, 25],  //记录数可选列表
                search: false,  //是否启用查询
                //showColumns: true,  //显示下拉框勾选要显示的列
                //showRefresh: true,  //显示刷新按钮
                sidePagination: "server", //表示服务端请求
                queryParamsType: "undefined",
                smartDisplay:true,
                queryParams: function queryParams(params) {   //设置查询参数
                    queryParamObject.pageNumber = params.pageNumber;
                    queryParamObject.pageSize = params.pageSize;
                    return queryParamObject;
                },
                onLoadSuccess:function(data) {
                    if(data.total>0 && data.rows==null){
                        if(queryParamObject.pageNumber>1){
                            queryParamObject.pageNumber = queryParamObject.pageNumber-1;
                            $.initTable(id, columnsArray, queryParamObject, url,onLoadSuccessFunc);
                        }
                    }else{
                        if(onLoadSuccessFunc){
                            onLoadSuccessFunc(data);
                        }
                    }
                },
                formatNoMatches:function(){
                    if( null != formatNoMatches && ''!=formatNoMatches){
                        return formatNoMatches;
                    }else{
                        return '没有找到匹配的记录';
                    }

                },
                onLoadError:function(status){
                    if (status == 444) {
                        window.location = '/login';
                        return;
                    }
                    if (status == 443) {
                        window.location = '/film/danmuCheck';
                        return;
                    }
                }

            });
        }
})(jQuery);