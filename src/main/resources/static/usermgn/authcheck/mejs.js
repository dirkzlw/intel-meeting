$(function () {  //jquery里的,是当文档载入完毕就执行的意思

    $('#xadd_btn').click(function () {//只有在页面加载的时候才会有效触发
        methods.xaddHandle()
    })

    $('#show_tbody').on('click', '.nopass', function () {
        authId = this.id
        aTag = document.getElementById(authId * 1 + 1);
        $('#xrenyuan').modal('show');
    })

})

var authId,
    aTag,
    addEnter = true,
    hasNullMes = false;

var methods = {

    xaddHandle: function (the_index) {
        hasNullMes = false;
        methods.xcheckMustMes();
        if (hasNullMes) {
            return;
        }
        if (addEnter) {
            var noPassReason = $('.noPassReason').val().trim();
            $.ajax({
                type: "POST",
                url: "/user/auth/nopass",
                data: {
                    'authId': authId,
                    'noPassReason': noPassReason,
                },
                dataType: "text", //return dataType: text or json
                success: function (json) {
                    if (json == "success") {
                        bootbox.alert({
                            title: "来自智能会议室的提示",
                            message: "用户认证不予通过成功",
                            closeButton: false
                        })
                        aTag.remove();
                        $('#xrenyuan').modal('hide');
                    } else if (rtn == "timeError") {
                        bootbox.alert({
                            title: "来自智能会议室的提示",
                            message: "用户认证不通过失败，请检查网络",
                            closeButton: false
                        })
                        return
                    }
                },
                error: function (json) {
                    bootbox.alert({
                        title: "来自智能会议室的提示",
                        message: "用户认证不通过失败，请检查网络",
                        closeButton: false
                    })
                    return
                }
            });
        }
    },
    xcheckMustMes: function () {

        //理由不能为空
        var noPassReason = $('.noPassReason').val().trim()
        if (noPassReason === '') {
            bootbox.alert({
                title: "来自智能会议室的提示",
                message: "不予通过原因不能为空",
                closeButton: false
            })
            hasNullMes = true;
            return
        }
    },
}