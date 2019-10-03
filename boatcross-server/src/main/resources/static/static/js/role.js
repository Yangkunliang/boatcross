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