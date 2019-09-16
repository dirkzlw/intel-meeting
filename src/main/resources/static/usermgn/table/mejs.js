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
            // var password = $('.password').val().trim();
            var email = $('.email').val().trim();
            var role = $("#role option:selected").val();
            // ajax 新增用户
            $.ajax({
                type: "POST",
                url: "/usermgn/user/save",
                data: {'username': username, 'email': email, 'roleName': role},
                dataType: "text", //return dataType: text or json
                success: function (json) {
                    json = eval('(' + json + ')');
                    var userId=json.userId;
                    var username=json.username;
                    // var password=json.password;
                    var email=json.email;
                    var role=json.role;
                    var status = json.rtn;
                    // var userId = json.rtnId;
                    var userIdTr = userId * 1 + 1;
                    if (status == "save") {
                        bootbox.alert({
                            title: "来自智能会议室的提示",
                            message: "用户添加成功",
                            closeButton: false
                        })
                        tdStr = "      \n" +
                            "                <td>"+username+"</td>\n" +
                            "                <td>"+email+"</td>\n" +
                            "                <td>"+role+"</td>\n" +
                            "                <td>\n" +
                            "                    <a id='" + userId + "' href='#' class='edit'>编辑</a> <a id='" + userId + "' href='#' class='del' onclick='delUser(this.id)'>删除</a>" +
                            "                    <a id='" + userId + "' href='#'  class='del' onclick='resetPsw(email)'>重置密码</a>"+
                            "               </td>" +
                            "           ";
                        // $('#show_tbody tr').eq(trIndex).empty().append(xtdStr);
                        // methods.setStr();
                        //  tdStr += "<td><a id='" + userId + "' href='#' class='edit'>编辑</a> <a id='" + userId + "' href='#' class='del' onclick='delUser(this.id)'>删除</a></td>";
                        $('#show_tbody').append('<tr id=' + userIdTr + '>' + tdStr + '</tr>');
                        $('#renyuan').modal('hide');
                    } else if (status=="userNameExist") {
                        bootbox.alert({
                            title: "来自智能会议室的提示",
                            message: "用户名已存在，请检查",
                            closeButton: false
                        })
                        return
                    }else if(status=="emailExist"){
                        bootbox.alert({
                            title: "来自智能会议室的提示",
                            message: "邮箱已存在，请检查",
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
            // var xpassword = $('.xpassword').val().trim();
            var xemail = $('.xemail').val().trim();
            var xrole = $("#xrole option:selected").val();
            // ajax 修改会议
            $.ajax({
                type: "POST",
                url: "/usermgn/user/save",
                data: {
                    'userId': editUserId,
                    'username': xusername,
                    'email': xemail,
                    'roleName': xrole
                },
                dataType: "text", //return dataType: text or json
                success: function (json) {
                    json = eval('(' + json + ')');
                    var userId=json.userId;
                    var username=json.username;
                    var email=json.email;
                    var role=json.role;
                    var status = json.rtn;
                    if (status == "save") {
                        bootbox.alert({
                            title: "来自智能会议室的提示",
                            message: "用户信息修改成功",
                            closeButton: false
                        })
                        xtdStr = "      \n" +
                            "                <td>"+username+"</td>\n" +
                            "                <td>"+email+"</td>\n" +
                            "                <td>"+role+"</td>\n" +
                            "                <td>\n" +
                            "                    <a id='" + userId + "' href='#' class='edit'>编辑</a> <a id='" + userId + "' href='#' class='del' onclick='delUser(this.id)'>删除</a>" +
                            "                    <a id='" + userId + "' href='#' class='${title:this.email}' onclick='resetPwd(title)'>重置密码</a>"+
                            "               </td>" +
                            "           ";
                        // methods.xsetStr();
                        // xtdStr += "<td><a id='" + userId + "' href='#' class='edit'>编辑</a> <a id='" + userId + "' href='#' class='del' onclick='delUser(this.id)'>删除</a></td>";
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
        }else{
                var emreg = /^[a-zA-Z0-9]([a-zA-Z0-9_]{2,20})@([a-z0-9]{1,10})([.]{1})([a-z]{2,4})$/;
                var email = $('.xusername').val();
            if (emreg.test(email) == true) {
                bootbox.alert({
                    title: "来自智能会议室的提示",
                    message: "用户名格式不可与邮箱格式相同，请重新添加",
                    closeButton: false
                })
                hasNullMes = true;
                return
            }
        }
        // if ($('.xpassword').val().trim() === '') {
        //     bootbox.alert({
        //         title: "来自智能会议室的提示",
        //         message: "密码为必选项，请填写",
        //         closeButton: false
        //     })
        //     hasNullMes = true;
        //     return
        // }
        if ($('.xemail').val().trim() == '') {
            bootbox.alert({
                title: "来自智能会议室的提示",
                message: "邮箱为必选项，请填写",
                closeButton: false
            })
            hasNullMes = true;
            return
        }else{
            var emreg = /^[a-zA-Z0-9]([a-zA-Z0-9_]{2,20})@([a-z0-9]{1,10})([.]{1})([a-z]{2,4})$/;
            var email = $('.xemail').val();
            if (emreg.test(email) == false) {
                bootbox.alert({
                    title: "来自智能会议室的提示",
                    message: "邮箱格式不正确，请重新填写",
                    closeButton: false
                })
                hasNullMes = true;
                return
            }
        }
    },
    checkMustMes: function () {

        if ($('.username').val().trim() == '') {
            bootbox.alert({
                title: "来自智能会议室的提示",
                message: "用户名为必选项，请填写",
                closeButton: false
            })
            hasNullMes = true;
            return
        }else{
            var emreg = /^[a-zA-Z0-9]([a-zA-Z0-9_]{2,20})@([a-z0-9]{1,10})([.]{1})([a-z]{2,4})$/;
            var email = $('.username').val();
            if (emreg.test(email) == true) {
                bootbox.alert({
                    title: "来自智能会议室的提示",
                    message: "用户名格式不正确，请重新填写",
                    closeButton: false
                })
                hasNullMes = true;
                return
            }
        }
        // if ($('.password').val().trim() === '') {
        //     bootbox.alert({
        //         title: "来自智能会议室的提示",
        //         message: "密码为必选项，请填写",
        //         closeButton: false
        //     })
        //     hasNullMes = true;
        //     return
        // }
        if ($('.email').val().trim() === '') {
            bootbox.alert({
                title: "来自智能会议室的提示",
                message: "邮箱为必选项，请填写",
                closeButton: false
            })
            hasNullMes = true;
            return
        }else{
            var emreg = /^[a-zA-Z0-9]([a-zA-Z0-9_]{2,20})@([a-z0-9]{1,10})([.]{1})([a-z]{2,4})$/;
            var email = $('.email').val();
            if (emreg.test(email) == false) {
                bootbox.alert({
                    title: "来自智能会议室的提示",
                    message: "邮箱格式不正确，请重新填写",
                    closeButton: false
                })
                hasNullMes = true;
                return
            }
        }
    },
}