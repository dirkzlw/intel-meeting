$(function () {

    $('#add_btn').click(function () {
        methods.addHandle()
    })
    $('#xadd_btn').click(function () {
        methods.xaddHandle()
    })

    $('#show_tbody').on('click', '.edit', function () {
        editUserId = this.id
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

var editUserId,
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
            var username = $('.username').val().trim();
            var password = $('.password').val().trim();
            var email = $('.email').val().trim();
            var role = $("#role option:selected").val();
            // ajax 新增用户
            $.ajax({
                type: "POST",
                url: "/usermgn/user/save",
                data: {'username': username, 'password': password, 'email': email, 'roleName': role},
                dataType: "text", //return dataType: text or json
                success: function (json) {
                    json = eval('(' + json + ')');
                    var status = json.rtn;
                    var userId = json.rtnId;
                    var userIdTr = userId * 1 + 1;
                    if (status == "save") {
                        bootbox.alert({
                            title: "来自智能会议室的提示",
                            message: "用户添加成功",
                            closeButton: false
                        })
                        methods.setStr();
                        tdStr += "<td><a id='" + userId + "' href='#' class='edit'>编辑</a> <a id='" + userId + "' href='#' class='del' onclick='delUser(this.id)'>删除</a></td>";
                        $('#show_tbody').append('<tr id=' + userIdTr + '>' + tdStr + '</tr>');
                        $('#renyuan').modal('hide');
                    } else if (status == "exist") {
                        bootbox.alert({
                            title: "来自智能会议室的提示",
                            message: "用户名/邮箱已存在，请检查",
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
            var xusername = $('.xusername').val().trim();
            var xpassword = $('.xpassword').val().trim();
            var xemail = $('.xpassword').val().trim();
            var xrole = $("#xrole option:selected").val();
            // ajax 修改会议
            $.ajax({
                type: "POST",
                url: "/usermgn/user/save",
                data: {
                    'userId': editUserId,
                    'username': xusername,
                    'password': xpassword,
                    'email': xemail,
                    'role': xrole
                },
                dataType: "text", //return dataType: text or json
                success: function (json) {
                    json = eval('(' + json + ')');
                    var status = json.rtn;
                    var userId = json.rtnId;
                    if (status == "save") {
                        bootbox.alert({
                            title: "来自智能会议室的提示",
                            message: "用户信息修改成功",
                            closeButton: false
                        })
                        methods.xsetStr();
                        xtdStr += "<td><a id='" + userId + "' href='#' class='edit'>编辑</a> <a id='" + userId + "' href='#' class='del' onclick='delUser(this.id)'>删除</a></td>";
                        $('#show_tbody tr').eq(trIndex).empty().append(xtdStr);
                        $('#xrenyuan').modal('hide');
                    } else if (status == "exist") {
                        bootbox.alert({
                            title: "来自智能会议室的提示",
                            message: "用户已存在，请检查",
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

        if ($('.xusername').val().trim() === '') {
            bootbox.alert({
                title: "来自智能会议室的提示",
                message: "用户名为必选项，请填写",
                closeButton: false
            })
            hasNullMes = true;
            return
        }
        if ($('.xpassword').val().trim() === '') {
            bootbox.alert({
                title: "来自智能会议室的提示",
                message: "密码为必选项，请填写",
                closeButton: false
            })
            hasNullMes = true;
            return
        }
        if ($('.xemail').val().trim() === '') {
            bootbox.alert({
                title: "来自智能会议室的提示",
                message: "邮箱为必选项，请填写",
                closeButton: false
            })
            hasNullMes = true;
            return
        }
    },
    checkMustMes: function () {

        if ($('.username').val().trim() === '') {
            bootbox.alert({
                title: "来自智能会议室的提示",
                message: "用户名为必选项，请填写",
                closeButton: false
            })
            hasNullMes = true;
            return
        }
        if ($('.password').val().trim() === '') {
            bootbox.alert({
                title: "来自智能会议室的提示",
                message: "密码为必选项，请填写",
                closeButton: false
            })
            hasNullMes = true;
            return
        }
        if ($('.email').val().trim() === '') {
            bootbox.alert({
                title: "来自智能会议室的提示",
                message: "邮箱为必选项，请填写",
                closeButton: false
            })
            hasNullMes = true;
            return
        }
    },
}