$(function () {
    $("#saveRoleInfoBtn").click(function () {
        var flag = checkParam();
        if (!flag) {
            return;
        }
        var data = $('#roleForm').serialize();
        $.post(
            "/role/save",
            data,
            function (json) {
                var code = json.code;
                if (code == 0) {
                    $('#addModal').modal('hide');
                   refresh();
                } else {
                    alert("fail" + json.message)
                }
            }
        )
    });

    /**
     * 删除确认弹框
     */
    $("#deleteRoleBtn").click(function () {
        var roleIds = $('input[name="roleIds"]:checked');
        if (roleIds.length == 0) {
            alert("请至少选择一项");
            return;
        }
        $('#deleteModal').modal('show');
    });

    /**
     * 确认删除
     */
    $("#deleteBtn").click(function () {
        var deleteRoleIds = [];
        $('input[name="roleIds"]:checked').each(function() {//遍历每一个名字为nodes的复选框，其中选中的执行函数
            deleteRoleIds.push($(this).val());//将选中的值添加到数组chk_value中
        });
        console.log(deleteRoleIds)
        $.post(
            "/role/delete",
            {
                roleIds: deleteRoleIds
            },
            function (json) {
                var code = json.code;
                if (code == 0) {
                    $('#deleteModal').modal('hide');
                    refresh();
                } else {
                    alert("fail" + json.message)
                }
            }
        )
    });
});

function checkParam() {
    var name = $("#name").val().trim();
    if (name == '') {
        alert("角色名称不能为空");
        return false;
    }
    var role = $("#role").val().trim();
    if (role == '') {
        alert("角色字符不能为空");
        return false;
    }
    return true;
}

function refresh(){
    location.reload();
}