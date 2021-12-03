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
                <#list users as u>
                <tr>
                    <td class="id">${u.id}</td>
                    <td class="login">${u.login}</td>
                    <td class="creationTime">${u.creationTime}</td>
                    <td class="status">
                        <#if user?? && user.id != u.id>
                            <form method="post" action="">
                                <input type="hidden" name="action" value="update"/>
                                <input type="hidden" name="id" value="${u.id?c}"/>
                                <input type="hidden" name="status" value="${u.disabled ? then("Enable", "Disable")}"/>
                                <input type="submit" value="${u.disabled ? then("Enable", "Disable")}">
                            </form>
                        <#else>
                            <#if user??>
                                This is you
                           <#else>
                                Unknown
                           </#if>
                        </#if>
                    </td>
                </tr>
                </#list>
            </#if>
            </tbody>
        </table>
    </div>
</@c.page>
