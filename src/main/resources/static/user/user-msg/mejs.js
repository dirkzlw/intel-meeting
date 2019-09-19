$(function () {  //jquery里的,是当文档载入完毕就执行的意思

    $('#xadd_btn').click(function () {//只有在页面加载的时候才会有效触发
        methods.xaddHandle()
    })

    $('#show_tbody').on('click', '.usernameEdit', function () {
        userId = this.id
        addEnter = true;
        $('#xrenyuan').modal('show');
    })


    $('#xadd_btn2').click(function () {//只有在页面加载的时候才会有效触发
        methods.xaddHandle2()
    })

    $('#show_tbody').on('click', '.userpwdEdit', function () {
        userId = this.id
        addEnter = true;
        $('#xrenyuan2').modal('show');
    })


    $('#xadd_btn3').click(function () {
        methods.xaddHandle3()
    })

    $('#show_tbody').on('click','.emailEdit',function () {
        userId = this.id
        addEnter = true;
        $('#xrenyuan3').modal('show');
    })

    $('#xadd_btn4').click(function () {
        methods.xaddHandle4()
    })

    $('#show_tbody').on('click','.HeadUrlEdit',function () {
        userId = this.id
        addEnter = true;
        $('#xrenyuan4').modal('show');
    })
})

var userId,
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
            var newUsername = $('#newUsername').val().trim();
            // ajax 修改用户名
            $.ajax({
                type: "POST",
                url: "/user/username/reset",
                data: {
                    'userId': userId,
                    'newUsername': newUsername
                },
                dataType: "text", //return dataType: text or json
                success: function (json) {
                    if (json == "success") {
                        bootbox.alert({
                            title: "来自智能会议室的提示",
                            message: "修改用户名成功",
                            closeButton: false
                        })
                        $('#xrenyuan').modal('hide');
                        window.open("/to/user/msg?userId=" + userId, "_self")
                        return
                    }
                    else if (json == "userNameExist") {
                        bootbox.alert({
                            title: "来自智能会议室的提示",
                            message: "修改用户名失败，用户名已存在",
                            closeButton: false
                        })
                        return
                    }
                },
                error: function (json) {
                    bootbox.alert({
                        title: "来自智能会议室的提示",
                        message: "修改失败，请检查网络",
                        closeButton: false
                    })
                    return
                }
            });
        }
    },
    xcheckMustMes: function () {
        //确定新用户名是否为空
        debugger
        var newUsername = $('.newUsername').val().trim();
        if (newUsername === '') {
            bootbox.alert({
                title: "来自智能会议室的提示",
                message: "新用户名为必选项，请填写",
                closeButton: false
            })
            hasNullMes = true;
            return
        }
    },

    xaddHandle2: function (the_index) {
        hasNullMes = false;
        methods.xcheckMustMes2();
        if (hasNullMes) {
            return;
        }
        if (addEnter) {
            var oldUserpwd = $('.oldUserpwd').val().trim();
            var newUserpwd = $('.newUserpwd').val().trim();
            // ajax 修改密码
            $.ajax({
                type: "POST",
                url: "/user/userpwd/reset",
                data: {
                    'userId': userId,
                    'oldUserpwd': oldUserpwd,
                    'newUserpwd': newUserpwd
                },
                dataType: "text", //return dataType: text or json
                success: function (json) {
                    if (json == "success") {
                        bootbox.alert({
                            title: "来自智能会议室的提示",
                            message: "修改密码成功",
                            closeButton: false
                        })
                        $('#xrenyuan2').modal('hide');
                        window.open("/to/user/msg?userId=" + userId, "_self")
                        return
                    }
                    else if (json == "oldUserpwdFalse") {
                        bootbox.alert({
                            title: "来自智能会议室的提示",
                            message: "修改密码失败，原密码输入错误",
                            closeButton: false
                        })
                        return
                    }
                },
                error: function (json) {
                    bootbox.alert({
                        title: "来自智能会议室的提示",
                        message: "修改失败，请检查网络",
                        closeButton: false
                    })
                    return
                }
            });
        }
    },
    xcheckMustMes2: function () {
        //确定修改密码时填入的数据是否为空，两次密码是否一致
        var oldUserpwd = $('.oldUserpwd').val().trim()
        var newUserpwd = $('.newUserpwd').val().trim()
        var newUserpwd2 = $('.newUserpwd2').val().trim()
        if (oldUserpwd === '') {
            bootbox.alert({
                title: "来自智能会议室的提示",
                message: "旧密码为必选项，请填写",
                closeButton: false
            })
            hasNullMes = true;
            return
        }
        else if (newUserpwd === '') {
            bootbox.alert({
                title: "来自智能会议室的提示",
                message: "新密码为必选项，请填写",
                closeButton: false
            })
            hasNullMes = true;
            return
        }
        else if (newUserpwd2 === '') {
            bootbox.alert({
                title: "来自智能会议室的提示",
                message: "再次输入密码为必选项，请填写",
                closeButton: false
            })
            hasNullMes = true;
            return
        }
        else if (newUserpwd !== newUserpwd2) {
            bootbox.alert({
                title: "来自智能会议室的提示",
                message: "两次新密码不一致，请重新填写",
                closeButton: false
            })
            hasNullMes = true;
            return
        }
        else if (oldUserpwd === newUserpwd) {
            bootbox.alert({
                title: "来自智能会议室的提示",
                message: "新旧密码不能相等，请重新填写",
                closeButton: false
            })
            hasNullMes = true;
            return
        }
    },

    xaddHandle3 : function (the_index) {
        hasNullMes = false;
        methods.xcheckMustMe3();
        if(hasNullMes){
            return;
        }
        if(addEnter){
            var newEmail = $('.newEmail').val().trim();
            $.ajax({
                type : "POST",
                url : "/user/email/reset",
                data : {
                    'userId' : userId,
                    'newEmail' : newEmail
                },
                dataType : "text",
                success : function (json) {
                    if(json == "EmailExist"){
                        bootbox.alert({
                            title: "来自智能会议室的提示",
                            message: "邮箱已存在，请更换！",
                            closeButton: false
                        })
                    }else if (json == "success") {
                        bootbox.alert({
                            title: "来自智能会议室的提示",
                            message: "修改邮箱成功",
                            closeButton: false
                        })
                        $('#xrenyuan').modal('hide');
                        window.open("/to/user/msg?userId=" + userId, "_self")
                        return
                    }

                },
                error : function(json) {
                    bootbox.alert({
                        title : "来自智能会议室的提示",
                        message : "修改失败，请检查网络",
                        closeButton : false
                    })
                    return
                }
            })
        }
    },
    xcheckMustMe3 : function () {
        var newEmail = $('.newEmail').val().trim()
        if(newEmail === ''){
            bootbox.alert({
                title : "来自智能会议室的提示",
                message : "新邮箱为必填项，请填写",
                closeButton : false
            })
            return
        }
    },
    xaddHandle4 : function (the_index) {
        hasNullMes = false;
        if (hasNullMes) {
            return;
        }
        if (addEnter) {
            var newHeadUrl = document.getElementById("file1").files[0];
            var formData = new FormData();
            formData.append("userId", userId)
            formData.append("newHeadUrl", newHeadUrl)
            $.ajax({
                type: "POST",
                url: "/user/headurl/reset",
                data: formData,
                dataType: "text",
                processData: false,
                contentType: false,
                success: function (json) {
                    if (json == "success") {
                        bootbox.alert({
                            title: "来自智能会议室的提示",
                            message: "修改头像成功",
                            closeButton: false
                        })
                        $('#xrenyuan').modal('hide');
                        window.open("/to/user/msg?userId=" + userId, "_self")
                        return
                    }

                },
                error: function (json) {
                    bootbox.alert({
                        title: "来自智能会议室的提示",
                        message: "修改失败，请检查网络",
                        closeButton: false
                    })
                    return
                }
            });
        }
    }
}