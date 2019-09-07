$(function () {  //jquery里的,是当文档载入完毕就执行的意思

    $('#xadd_btn').click(function () {//只有在页面加载的时候才会有效触发
        methods.xaddHandle()
    })
    $('#xxadd_btn').click(function () {//只有在页面加载的时候才会有效触发
        methods.xxaddHandle()
    })

    /**
     * 取消预定
     */
    $('#show_tbody').on('click', '.edit', function () {
        reserveId = this.id
        var aTitle = this.title
        var reserveTime = new Date(aTitle).getTime();
        var nowTime = new Date().getTime();
        if (nowTime - reserveTime > 0) {
            bootbox.alert({
                title: "来自智能会议室的提示",
                message: "会议已开始，禁止取消",
                closeButton: false
            })
            return
        }
        trTag = $(this).parents('tr')
        $('#xrenyuan').modal('show');
    })
    /**
     * 签到
     */
    $('#show_tbody').on('click', '.sign', function () {
        reserveId = this.id
        var aTitle = this.title
        var reserveTime = new Date(aTitle).getTime();
        var nowTime = new Date().getTime();
        if (nowTime - reserveTime < 0) {
            bootbox.alert({
                title: "来自智能会议室的提示",
                message: "会议尚未开始，禁止签到",
                closeButton: false
            })
            return
        } else if (nowTime - reserveTime > 300000) {
            bootbox.alert({
                title: "来自智能会议室的提示",
                message: "签到已过期，禁止签到",
                closeButton: false
            })
            return
        } else {
            //签到
            $.ajax({
                type: "POST",
                url: "/meeting/reserve/sign",
                data: {
                    'reserveId': reserveId,
                },
                dataType: "text", //return dataType: text or json
                success: function (json) {
                    if (json == "success") {
                        bootbox.alert({
                            title: "来自智能会议室的提示",
                            message: "签到成功",
                            closeButton: false
                        })
                        $('#show_tbody tr').eq(trIndex).empty();
                        $('#xrenyuan').modal('hide');
                    } else if ("exceed" == json) {
                        bootbox.alert({
                            title: "来自智能会议室的提示",
                            message: "签到时间有误，请检查",
                            closeButton: false
                        })
                        return
                    }
                    else {
                        bootbox.alert({
                            title: "来自智能会议室的提示",
                            message: "签到失败，请检查网络",
                            closeButton: false
                        })
                        return
                    }
                },
                error: function (json) {
                    bootbox.alert({
                        title: "来自智能会议室的提示",
                        message: "签到失败，请检查网络",
                        closeButton: false
                    })
                    return
                }
            });
        }

    })
    /**
     * 结束会议
     */
    $('#show_tbody').on('click', '.end', function () {
        reserveId = this.id
        var startDate = document.getElementById(reserveId).title;
        var endDate = this.title
        var startTime = new Date(startDate).getTime();
        var endTime = new Date(endDate).getTime();
        var nowTime = new Date().getTime();

        if (nowTime - startTime > 300000 && nowTime < endTime) {
            trTag = $(this).parents('tr')
            $('#xxrenyuan').modal('show');
        } else {
            bootbox.alert({
                title: "来自智能会议室的提示",
                message: "预定至少使用5分钟方可提前结束！",
                closeButton: false
            })
            return
        }

    })

    $('#back_btn').click(function () {
        $('#Ktext').val(' ');
        methods.resectList();
    })
})

var trTag,
    reserveId,
    addEnter = true,
    trIndex,
    hasNullMes = false;
var methods = {

    xaddHandle: function (the_index) {
        hasNullMes = false;
        if (hasNullMes) {
            return;
        }
        if (addEnter) {
            // ajax 取消预定
            $.ajax({
                type: "POST",
                url: "/meeting/reserve/cancel",
                data: {
                    'reserveId': reserveId,
                },
                dataType: "text", //return dataType: text or json
                success: function (json) {
                    if (json == "success") {
                        bootbox.alert({
                            title: "来自智能会议室的提示",
                            message: "取消成功",
                            closeButton: false
                        })
                        // $('#show_tbody tr').eq(trIndex).empty();
                        trTag.remove()
                        $('#xrenyuan').modal('hide');
                    }else if(json == "started"){
                        bootbox.alert({
                            title: "来自智能会议室的提示",
                            message: "会议已开始，禁止取消",
                            closeButton: false
                        })
                    } else {
                        bootbox.alert({
                            title: "来自智能会议室的提示",
                            message: "取消失败，请检查网络",
                            closeButton: false
                        })
                        return
                    }
                },
                error: function (json) {
                    bootbox.alert({
                        title: "来自智能会议室的提示",
                        message: "取消失败，请检查网络",
                        closeButton: false
                    })
                    return
                }
            });
        }
    },
    xxaddHandle: function (the_index) {
        hasNullMes = false;
        if (hasNullMes) {
            return;
        }
        if (addEnter) {
            //符合使用5分钟后结束会议
            $.ajax({
                type: "POST",
                url: "/meeting/reserve/over",
                data: {
                    'reserveId': reserveId,
                },
                dataType: "text", //return dataType: text or json
                success: function (json) {
                    if (json == "success") {
                        bootbox.alert({
                            title: "来自智能会议室的提示",
                            message: "结束会议成功",
                            closeButton: false
                        })
                        trTag.remove()
                        $('#xxrenyuan').modal('hide');
                    }else if("exceed" == json){
                        bootbox.alert({
                            title: "来自智能会议室的提示",
                            message: "结束有误，请检查",
                            closeButton: false
                        })
                        return
                    }
                    else {
                        bootbox.alert({
                            title: "来自智能会议室的提示",
                            message: "结束失败，请检查网络",
                            closeButton: false
                        })
                        return
                    }
                },
                error: function (json) {
                    bootbox.alert({
                        title: "来自智能会议室的提示",
                        message: "结束失败，请检查网络",
                        closeButton: false
                    })
                    return
                }
            });
        }
    },
    resectList: function () {
        $('#show_tbody tr').show();
    },
}