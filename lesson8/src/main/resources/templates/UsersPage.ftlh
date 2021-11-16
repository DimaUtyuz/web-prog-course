<#import "commons.ftlh" as c>

<@c.page>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <th>Created</th>
                <th>Status</th>
            </tr>
            </thead>
            <tbody>
            <#if users?size==0>
                <tr class="noData">
                    <td colspan="4">
                        No data
                    </td>
                </tr>
            <#else>
                <#list users as user>
                <tr>
                    <td class="id">${user.id}</td>
                    <td class="login">${user.login}</td>
                    <td class="creationTime">${user.creationTime}</td>
                    <td class="status">
                        <form method="post" action="">
                            <input type="hidden" name="action" value="update"/>
                            <input type="hidden" name="id" value="${user.id?string}"/>
                            <input type="hidden" name="status" value="${user.disabled ? then("Enable", "Disable")}"/>
                            <input type="submit" value="${user.disabled ? then("Enable", "Disable")}">
                        </form>
                    </td>
                </tr>
                </#list>
            </#if>
            </tbody>
        </table>
    </div>
</@c.page>
