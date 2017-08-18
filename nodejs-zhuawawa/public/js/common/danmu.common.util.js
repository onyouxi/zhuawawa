/*!
 * danmu.ajax.plug v1.0.0
 */
(function ($) {
    $.objectCovertJson = function (object) {
        return JSON.stringify(object);
    }
    $.jsonConvertToObject = function (object) {
        return JSON.parse(object);
    }
})(jQuery);