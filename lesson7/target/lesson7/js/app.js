window.notify = function (message) {
    $.notify(message, {
        position: "right bottom",
        className: "success"
    });
}

window.ajax = function ajax(action, data, context, success=function (){}, boba="sdads") {
    data["action"] = action;
    $.ajax({
        type: "POST",
        url: "",
        dataType: "json",
        data,
        success: function (response) {
            success(response);
            if (response["error"]) {
                $(context).find(".error").text(response["error"]);
            } else {
                if (response["redirect"]) {
                    location.href = response["redirect"];
                }
            }
        }
    });
    return false;
}
