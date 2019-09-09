$(function () {  //jquery里的,是当文档载入完毕就执行的意思

    $('#xadd_btn').click(function () {//只有在页面加载的时候才会有效触发
        methods.xaddHandle()
    })

    $('#show_tbody').on('click', '.edit', function () {
        editMeetingId = this.id
        var aTitle = document.getElementById(editMeetingId).title
        if ("会议室故障" == aTitle) {
            bootbox.alert({
                title: "来自智能会议室的提示",
                message: "会议室故障，暂停服务",
                closeButton: false
            })
            return
        }
        trIndex = $('.edit', '#show_tbody').index($(this));
        addEnter = true;
        $(this).parents('tr').addClass('has_case');
        methods.editHandle(trIndex);
    })

    $('#back_btn').click(function () {
        $('#Ktext').val(' ');
        methods.resectList();
    })

    $('#xrenyuan').on('hide.bs.modal', function () {
        addEnter = true;
        $('#show_tbody tr').removeClass('has_case');
        $('#xxztb input').val(' ');
        $('#xxztb select').find('option:first').prop('selected', true)
    });

})

var editMeetingId,
    addEnter = true,
    xtdStr = '',
    trIndex,
    hasNullMes = false,
    xtarInp = $('#xxztb input'),
    xtarSel = $('#xxztb select');

var methods = {

    xaddHandle: function (the_index) {
        hasNullMes = false;
        methods.xcheckMustMes();
        if (hasNullMes) {
            return;
        }
        if (addEnter) {
            var reserveDay = $('.reserveDay').val().trim();
            var startTime = $('.startTime').val().trim();
            var endTime = $('.endTime').val().trim();
            // ajax 修改会议
            $.ajax({
                type: "POST",
                url: "/meeting/reserve",
                data: {
                    'meetingId': editMeetingId,
                    'reserveDay': reserveDay,
                    'startTime': startTime,
                    'endTime': endTime
                },
                dataType: "text", //return dataType: text or json
                success: function (json) {
                    json = eval('(' + json + ')');
                    var meetingId = json.meetingId;
                    var meetingName = json.meetingName;
                    var containNum = json.containNum;
                    var reserveStatus = json.reserveStatus;
                    var reserveTime = json.reserveTime;
                    var rtn = json.rtn;
                    if (rtn == "reserve") {
                        bootbox.alert({
                            title: "来自智能会议室的提示",
                            message: "会议室预定成功，请在会议开始五分钟内进行签到！",
                            closeButton: false
                        })
                        xtdStr = "      \n" +
                            "                <td>" + meetingName + "</td>\n" +
                            "                <td>" + containNum + "</td>\n" +
                            "                <td>" + reserveStatus + "</td>\n" +
                            "                <td>" + reserveTime + "</td>\n" +
                            "                <td>\n" +
                            "                    <a id=" + meetingId + " href='#' class='edit'>预约</a>" +
                            "               </td>" +
                            "           ";
                        $('#show_tbody tr').eq(trIndex).empty().append(xtdStr);
                        $('#xrenyuan').modal('hide');
                    } else if (rtn == "timeError") {
                        bootbox.alert({
                            title: "来自智能会议室的提示",
                            message: "会议室时间存在冲突，请检查",
                            closeButton: false
                        })
                        return
                    }
                },
                error: function (json) {
                    bootbox.alert({
                        title: "来自智能会议室的提示",
                        message: "添加失败，请检查网络",
                        closeButton: false
                    })
                    return
                }
            });
        }
    },
    editHandle: function (the_index) {

        var tar = $('#show_tbody tr').eq(the_index);
        var nowConArr = [];
        for (var i = 0; i < tar.find('td').length - 1; i++) {
            var a = tar.children('td').eq(i).html();
            nowConArr.push(a);
        }

        $('#xrenyuan').modal('show');

        for (var j = 0; j < xtarInp.length; j++) {
            xtarInp.eq(j).val(nowConArr[j])
        }
        for (var p = 0; p < xtarSel.length; p++) {
            var the_p = p + xtarInp.length;
            xtarSel.eq(p).val(nowConArr[the_p]);
        }

    },
    resectList: function () {
        $('#show_tbody tr').show();
    },
    xcheckMustMes: function () {

        //确定预约三日内会议室
        var reserveStr = $('.reserveDay').val().trim()
        var startStr = $('.startTime').val().trim()
        var endStr = $('.endTime').val().trim()
        var reserveArr = reserveStr.split("-")
        var startArr = startStr.split(":")
        var endArr = endStr.split(":")
        var startTime = new Date(reserveArr[0] * 1,
            reserveArr[1] - 1,
            reserveArr[2] * 1,
            startArr[0] * 1,
            startArr[1] * 1).getTime()
        var endTime = new Date(reserveArr[0] * 1,
            reserveArr[1] - 1,
            reserveArr[2] * 1,
            endArr[0] * 1,
            endArr[1] * 1).getTime()
        var nowTime = new Date().getTime()
        if (startTime - nowTime > 219600000 || startTime - nowTime < 0) {
            bootbox.alert({
                title: "来自智能会议室的提示",
                message: "以今天开始，请预约在三日内",
                closeButton: false
            })
            hasNullMes = true;
            return
        }

        if (reserveStr === '') {
            bootbox.alert({
                title: "来自智能会议室的提示",
                message: "预定日期为必选项，请填写",
                closeButton: false
            })
            hasNullMes = true;
            return
        }

        //确定预约时间在8:00--21:00
        var hour1 = startArr[0]
        var hour2 = endArr[0]
        var min2 = endArr[1]
        if (hour1 < 8 || hour2 > 21 || hour2 - hour1 < 0) {
            bootbox.alert({
                title: "来自智能会议室的提示",
                message: "预约时间为08:00--21:00",
                closeButton: false
            })
            hasNullMes = true;
            return
        }
        //预约结束时间不能超过21:00
        if (hour2 == 21 && min2 > 0) {
            bootbox.alert({
                title: "来自智能会议室的提示",
                message: "预约时间不能超过21:00",
                closeButton: false
            })
            hasNullMes = true;
            return
        }
        //至少预约半小时
        if(endTime - startTime <1800000){
            bootbox.alert({
                title: "来自智能会议室的提示",
                message: "预约时长至少半小时",
                closeButton: false
            })
            hasNullMes = true;
            return
        }
        if (startStr === '') {
            bootbox.alert({
                title: "来自智能会议室的提示",
                message: "开始时间为必选项，请填写",
                closeButton: false
            })
            hasNullMes = true;
            return
        }
        if (endStr === '') {
            bootbox.alert({
                title: "来自智能会议室的提示",
                message: "结束为必选项，请填写",
                closeButton: false
            })
            hasNullMes = true;
            return
        }
    },
}