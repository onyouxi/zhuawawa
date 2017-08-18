(function ($) {
    $.initSwitch = function (id) {
        $('#' + id).bootstrapSwitch({
            onText: "启动",
            offText: "关闭",
            onColor: "success",
            offColor: "danger",
            size: "small",
            inverse:true,
            animate:true,
            onSwitchChange:function () {
                return;
            }
        });
    }
})(jQuery);