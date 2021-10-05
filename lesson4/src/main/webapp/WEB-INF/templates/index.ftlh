<#import "./common.ftlh" as c>

<@c.page>
    <div class="articles">
        <ul>
            <#list posts?reverse as p>
                <li>
                    <@c.article p true "all"/>
                </li>
            </#list>
        </ul>
    </div>
</@c.page>
