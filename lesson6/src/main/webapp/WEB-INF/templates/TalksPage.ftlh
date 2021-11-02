<#-- @ftlvariable name="talks" type="ru.itmo.wp.model.domain.Talk[]" -->
<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#-- @ftlvariable name="error" type="java.lang.String" -->
<#-- @ftlvariable name="text" type="java.lang.String" -->
<#-- @ftlvariable name="userService" type="ru.itmo.wp.model.service.UserService" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="message-box">
        <form method="post" action="">
            <input type="hidden" name="action" value="send"/>
            <div class="field">
                <div class="name">
                    <label for="to">To user</label>
                </div>
                <label>
                    <select class="select" name="targetUserId">
                        <#list users as user>
                            <option value=${user.id}>
                                ${user.login}
                            </option>
                        </#list>
                    </select>
                </label>
            </div>
            <div class="field">
                <div class="name">
                    <label for="text">Text</label>
                </div>
                <div class="value">
                    <textarea id="text" name="text">${text!}</textarea>
                </div>
            </div>
            <#if error??>
                <div class="error">
                    ${error!}
                </div>
            </#if>
            <div class="button-field">
                <input type="submit" value="Send">
            </div>
        </form>
    </div>
    <div class="datatable">
        <div class="caption">Talk</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>From</th>
                <th>To</th>
                <th>Text</th>
                <th>Created</th>
            </tr>
            </thead>
            <tbody>
            <#list talks as talk>
            <tr>
                <#assign source = "">
                <#assign target = "">
                <#list users as user>
                    <#if user.id == talk.sourceUserId>
                        <#assign source = user.login>
                    </#if>
                    <#if user.id == talk.targetUserId>
                        <#assign target = user.login>
                    </#if>
                </#list>

                <td>${talk.id}</td>
                <td>${source}</td>
                <td>${target}</td>
                <td>${talk.text}</td>
                <td>${talk.creationTime}</td>
            </tr>
            </#list>
            </tbody>
        </table>
    </div>
</@c.page>
