<#import "./common.ftlh" as c>

<@c.page>
    <#assign u = c.findBy(users, "id", user_id)!>
    <#if u?has_content>
        <div class="articles">
            <ul>
                <#list posts?reverse as p>
                    <li>
                        <@c.article p true u.id/>
                    </li>
                </#list>
            </ul>
        </div>
    <#else>
        No such user
    </#if>
</@c.page>
