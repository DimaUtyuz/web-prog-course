<#import "commons.ftlh" as c>
<#import "/spring.ftl" as spring>

<@c.page>
    <div class="user-page">
        <div class="user">
            <div class="info">
                <#if userPage??>
                    <ul>
                        <li>id: ${userPage.id}</li>
                        <li>login: ${userPage.login}</li>
                        <li>creation time: ${userPage.creationTime}</li>
                    </ul>
                <#else>
                    No such User.
                </#if>
            </div>
            <img src="<@spring.url '/img/user-icon.jpg'/>" alt="user-icon">
        </div>
    </div>
</@c.page>
