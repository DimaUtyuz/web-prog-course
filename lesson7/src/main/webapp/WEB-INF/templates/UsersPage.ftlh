<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#-- @ftlvariable name="user" type="ru.itmo.wp.model.domain.User" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <#if user?? && user.admin>
                    <th>Role</th>
                    <th>Action</th>
                </#if>
            </tr>
            </thead>
            <tbody>
            <template class="tmpl-tr">
                <tr>
                    <td class="user-id"></td>
                    <td class="user-login"></td>
                    <#if user?? && user.admin>
                        <td class="user-admin"></td>
                        <td class="user-action">
                            <button></button>
                        </td>
                    </#if>
                </tr>
            </template>
            </tbody>
        </table>
    </div>

    <script>
        $(function () {
            const success = function(response) {
                response["users"].forEach(function(user) {
                    const $tr = $($(".users template").prop("content")).clone();
                    $tr.find(".user-id").text(user["id"]);
                    $tr.find(".user-login").text(user["login"]);
                    $tr.find("tr").attr("data-userId", user["id"]);

                    const $user_admin = $tr.find(".user-admin")
                    $user_admin.text(user["admin"] ? "Admin" : "User");
                    const $user_button = $tr.find(".user-action button");
                    $user_button.text(user["admin"] ? "Disable" : "Enable");

                    $(".users tbody").append($tr);
                });
                $(".users button").click(function() {
                    const context = $(this).parent().parent();
                    const admin = context.find("button").text();
                    const success = function (response) {
                        context.find(".user-admin").text(admin === "Disable" ? "User" : "Admin");
                        context.find("button").text(admin === "Disable" ? "Enable" : "Disable");
                    }
                    ajax("setAdmin", {id: context.attr("data-userId"), admin}, this, success)
                });
                $(".users tr").click(function() {
                    const success = function (response) {
                        notify(response["user"].creationTime);
                    }
                    ajax("findUser", {userId: $(this).attr("data-userId")}, this, success)
                });
            }
            ajax("findAll", {}, this, success);
        })
    </script>
</@c.page>
