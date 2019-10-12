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

$("#editRolePermissionBtn").click(function () {
    var roleIds = $('input[name="roleIds"]:checked');
    if (roleIds.length == 0) {
        alert("请选择一项");
        return;
    }
    if(roleIds.length > 1) {
        alert("最多选择一项");
        return;
    }
    var roleId = $(roleIds[0]).val();
    $.get(
        "/permission/editByRoleId?roleId=" + roleId,
        function (json) {
            var code = json.code;
            if (code == 0) {
                var data = json.data;
                var _html = '<table class="table table-striped table-bordered table-hover">';
                _html += '<tr><th style="width: 64px"><label>#</label></th>'
                    + ' <th style="width: 240px">id</th>'
                    + ' <th style="width: 240px">权限名称</th>'
                    + ' <th style="width: 240px">权限标识</th>'
                    + ' <th style="width: 240px">描述</th>'
                    + ' <th style="width: 240px">创建人</th>'
                    + ' <th style="width: 240px">创建时间</th></tr>'
                $.each(data, function (index, val) {
                   _html += '<tr><td><input type="checkbox" name="roleIds" value="' + val.id + '"/></td>'
                        + '<td>' + val.id + '</td>'
                        + '<td>' + val.name + '</td>'
                        + '<td>' + val.permission + '</td>'
                        + '<td>' + val.description + '</td>'
                        + '<td>' + val.createBy + '</td>'
                        + '<td>' + val.createTime + '</td></tr>';
                });
                _html += '</table>';
                $("#rolePermissionModal").html(_html);
                $('#editRolePermissionModal').modal('show');
            } else {
                alert("fail" + json.message)
            }
        }
    )
});
