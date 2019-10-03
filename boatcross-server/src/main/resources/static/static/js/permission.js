$(function () {
    $("#savePermissionInfoBtn").click(function () {
        var flag = checkParam();
        if (!flag) {
            return;
        }
        var data = $('#permissionForm').serialize();
        $.post(
            "/permission/save",
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
        alert("权限名称不能为空");
        return false;
    }
    var permission = $("#permission").val().trim();
    if (permission == '') {
        alert("权限标识不能为空");
        return false;
    }
    return true;
}

function refresh(){
    location.reload();
}