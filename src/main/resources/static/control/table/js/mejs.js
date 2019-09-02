$(function () {

    $('#add_btn').click(function () {
        methods.addHandle()
    })
    $('#xadd_btn').click(function () {
        methods.xaddHandle()
    })

    $('#show_tbody').on('click', '.edit', function () {
        editMeetingId = this.id
        trIndex = $('.edit', '#show_tbody').index($(this));
        addEnter = true;
        $(this).parents('tr').addClass('has_case');
        methods.editHandle(trIndex);
    })

    $('#back_btn').click(function () {
        $('#Ktext').val(' ');
        methods.resectList();
    })

    $('#renyuan').on('hide.bs.modal', function () {
        addEnter = true;
        $('#show_tbody tr').removeClass('has_case');
        $('#xztb input').val(' ');
        $('#xztb select').find('option:first').prop('selected', true)
        $('#xxztb input').val(' ');
        $('#xxztb select').find('option:first').prop('selected', true)
    });

})

var editMeetingId,
    addEnter = true,
    noRepeat = false,
    tdStr = '',
    xtdStr = '',
    trIndex,
    hasNullMes = false,
    tarInp = $('#xztb input'),
    tarSel = $('#xztb select'),
    xtarInp = $('#xxztb input'),
    xtarSel = $('#xxztb select');

var methods = {

    addHandle: function (the_index) {
        hasNullMes = false;
        methods.checkMustMes();
        if (hasNullMes) {
            return;
        }
        if (addEnter) {
            var meetingName = $('.meetingName').val().trim();
            var containNum = $('.containNum').val().trim();
            var meetingStatus = $("#meetingStatus option:selected").val();
            // ajax 新增会议
            $.ajax({
                type: "POST",
                url: "/control/meeting/save",
                data: {'meetingName': meetingName, 'containNum': containNum, 'enableStatus': meetingStatus},
                dataType: "text", //return dataType: text or json
                success: function (json) {
                    json = eval('(' + json + ')');
                    var status = json.status;
                    var meetingId = json.meetingId;
                    var meetingIdTr = meetingId * 1 + 1;
                    if (status == "save") {
                        bootbox.alert({
                            title: "来自智能会议室的提示",
                            message: "会议室添加成功",
                            closeButton: false
                        })
                        methods.setStr();
                        tdStr += "<td><a id='" + meetingId + "' href='#' class='edit'>编辑</a> <a id='" + meetingId + "' href='#' class='del' onclick='delMeeting(this.id)'>删除</a></td>";
                        $('#show_tbody').append('<tr id=' + meetingIdTr + '>' + tdStr + '</tr>');
                        $('#renyuan').modal('hide');
                    } else if (status == "exist") {
                        bootbox.alert({
                            title: "来自智能会议室的提示",
                            message: "会议室已存在，请检查",
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
    xaddHandle: function (the_index) {
        hasNullMes = false;
        methods.xcheckMustMes();
        if (hasNullMes) {
            return;
        }
        if (addEnter) {
            var xmeetingName = $('.xmeetingName').val().trim();
            var xcontainNum = $('.xcontainNum').val().trim();
            var xmeetingStatus = $("#xmeetingStatus option:selected").val();
            // ajax 修改会议
            $.ajax({
                type: "POST",
                url: "/control/meeting/save",
                data: {'meetingId':editMeetingId,'meetingName': xmeetingName, 'containNum': xcontainNum, 'enableStatus': xmeetingStatus},
                dataType: "text", //return dataType: text or json
                success: function (json) {
                    json = eval('(' + json + ')');
                    var status = json.status;
                    var meetingId = json.meetingId;
                    // var meetingIdTr = meetingId * 1 + 1;
                    if (status == "save") {
                        bootbox.alert({
                            title: "来自智能会议室的提示",
                            message: "会议室修改成功",
                            closeButton: false
                        })
                        methods.xsetStr();
                        xtdStr += "<td><a id='" + meetingId + "' href='#' class='edit'>编辑</a> <a id='" + meetingId + "' href='#' class='del' onclick='delMeeting(this.id)'>删除</a></td>";
                        $('#show_tbody tr').eq(trIndex).empty().append(xtdStr);
                        $('#xrenyuan').modal('hide');
                    } else if (status == "exist") {
                        bootbox.alert({
                            title: "来自智能会议室的提示",
                            message: "会议室已存在，请检查",
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
    setStr: function () {

        tdStr = '';
        for (var a = 0; a < tarInp.length; a++) {
            tdStr += '<td>' + tarInp.eq(a).val() + '</td>'
        }
        for (var b = 0; b < tarSel.length; b++) {
            tdStr += '<td>' + tarSel.eq(b).val() + '</td>'
        }

    },
    xsetStr: function () {

        xtdStr = '';
        for (var a = 0; a < xtarInp.length; a++) {
            xtdStr += '<td>' + xtarInp.eq(a).val() + '</td>'
        }
        for (var b = 0; b < xtarSel.length; b++) {
            xtdStr += '<td>' + xtarSel.eq(b).val() + '</td>'
        }

    },
    resectList: function () {
        $('#show_tbody tr').show();
    },
    xcheckMustMes: function () {

        if ($('.xmeetingName').val().trim() === '') {
            bootbox.alert({
                title: "来自智能会议室的提示",
                message: "会议室名称为必选项，请填写",
                closeButton: false
            })
            hasNullMes = true;
            return
        }
        if ($('.xcontainNum').val().trim() === '') {
            bootbox.alert({
                title: "来自智能会议室的提示",
                message: "容纳人数为必选项，请填写",
                closeButton: false
            })
            hasNullMes = true;
            return
        }
    },
    checkMustMes: function () {

        if ($('.meetingName').val().trim() === '') {
            bootbox.alert({
                title: "来自智能会议室的提示",
                message: "会议室名称为必选项，请填写",
                closeButton: false
            })
            hasNullMes = true;
            return
        }
        if ($('.containNum').val().trim() === '') {
            bootbox.alert({
                title: "来自智能会议室的提示",
                message: "容纳人数为必选项，请填写",
                closeButton: false
            })
            hasNullMes = true;
            return
        }
    },
}