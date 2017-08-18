/*!
 * danmu.ajax.plug v1.0.0
 */
(function ($) {
    $.danmuAjax = function (url, method,dataType, contentType,data, success, error) {
        if(''==contentType || null == contentType){
            contentType = "application/json; charset=utf-8";
        }
        $.ajax({
            contentType: contentType,//"application/json; charset=utf-8"
            type: method,
            url: url,
            data: data,
            dataType: dataType,
            success: success,
            error: error
        });
    }
})(jQuery);