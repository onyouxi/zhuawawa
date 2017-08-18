/*!
 * danmu.ajax.plug v1.0.0
 */
(function ($) {
    $.danmuAjax = function (url, method,dataType, data, success, error,async) {
        $.ajax({
            type: method,
            url: url,
            data: data,
            dataType: dataType,
            success: success,
            async:async==null?true:false,
            error: error
        });
    }
})(jQuery);