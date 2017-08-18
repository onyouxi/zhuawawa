/*!
 * danmu.ajax.plug v1.0.0
 */
(function ($) {

    /**
     * 根据空间类型设置控件的状态
     * @param type
     * @param state
     */
    $.setControlDisabledStateByType = function (type, state) {
        if(state){
            $(type).addClass('disabled');
        }else{
            $(type).removeClass('disabled');
        }
        $(type).prop('disabled', state);
    }

    /**
     * 根据空间类型设置控件的状态
     * @param type
     * @param state
     */
    $.setControlDisabledStateById = function (id, state) {
        if(state){
            $('#'+id).addClass('disabled');
        }else{
            $('#'+id).removeClass('disabled');
        }
        $('#'+id).prop('disabled', state);
    }

    /**
     * 根据空间类型设置控件的状态
     * @param type
     * @param state
     */
    $.setControlDisabledStateByCss = function (css, state) {
        if(state){
            $('.'+css).addClass('disabled');
        }else{
            $('.'+css).removeClass('disabled');
        }
        $('.'+css).prop('disabled', state);
    }

})(jQuery);