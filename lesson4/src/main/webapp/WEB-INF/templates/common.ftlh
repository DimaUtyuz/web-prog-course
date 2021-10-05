<#-- @ftlvariable name="handle" type="java.lang.String" -->
<#macro page>
    <!DOCTYPE html>
    <html lang="ru">
    <head>
        <meta charset="UTF-8">
        <title>Codeforces</title>
        <link rel="stylesheet" href="/css/normalize.css">
        <link rel="stylesheet" href="/css/style.css">
        <link rel="stylesheet" href="/css/user.css">
        <link rel="stylesheet" href="/css/article.css">
        <link rel="stylesheet" href="/css/datatable.css">
        <link rel="stylesheet" href="/css/userlink.css">
        <link rel="icon" href="/favicon.ico">
    </head>
    <body>
    <@header/>
    <div class="middle">
        <@sidebar/>
        <main>
            <#nested/>
        </main>
    </div>
    <@footer/>
    </body>
    </html>
</#macro>

<#macro header>
    <header>
        <a href="#" class="logo"><img alt="Codeforces" title="Codeforces"
                                      src="/img/codeforces-logo-with-telegram.png"></a>
        <div class="languages">
            <a href="#"><img alt="In English" title="In English"
                             src="/img/gb.png"></a>
            <a href="#"><img alt="In Russian" title="In Russian"
                             src="/img/ru.png"></a>
        </div>
        <div class="enter-or-register-box">
            <#if user??>
                <@userlink user true/>
                |
                <a href="/logout">Logout</a>
            <#else>
                <a href="/enter">Enter</a>
                |
                <a href="/register">Register</a>
            </#if>
        </div>
        <nav>
            <#assign items=[
            {"href": "/index", "name": "Home"},
            {"href": "/misc/help", "name": "Help"},
            {"href": "/contests", "name": "Contests"},
            {"href": "/users", "name": "Users"}
            ]/>
            <ul>
                <#list items as item>
                    <li>
                        <#if menu == item.name>
                            <a class="join" href="${item.href}">${item.name}</a>
                        <#else>
                            <a href="${item.href}">${item.name}</a>
                        </#if>
                    </li>
                </#list>
            </ul>
        </nav>
    </header>
</#macro>

<#macro sidebar>
    <aside>
        <#list posts?reverse as p>
            <section>
                <div class="header">Post ${p.id}</div>
                <div class="body">
                    <#assign text = p.text>
                    <#if text?length gt 250>
                        ${text?substring(0, 250)}...
                    <#else>
                        ${text}
                    </#if>
                </div>
                <div class="footer">
                    <a href="/post?post_id=${p.id}">View all</a>
                </div>
            </section>
        </#list>
    </aside>
</#macro>

<#macro footer>
    <footer>
        2010-2021 &copy; by Mike Mirzayanov
    </footer>
</#macro>

<#macro article post short id>
    <#if id?is_string || id == post.user_id>
        <article>
            <div class="title">${post.title}</div>
            <div class="information">By
                <#assign u = findBy(users, "id", post.user_id)> <@userlink u false/></div>
            <div class="body">
                <#assign text = post.text>
                <#if text?length gt 250 && short>
                    ${text?substring(0, 250)}...
                <#else>
                    ${text}
                </#if>
            </div>
        </article>
    </#if>
</#macro>

<#macro userlink user nameOnly>
    <#if nameOnly>
        <a href="/user?handle=${user.handle}">${user.name}</a>
    <#else>
        <a class="userlink user-${user.color?lower_case}" href="/user?handle=${user.handle}">${user.handle}</a>
    </#if>
</#macro>

<#macro userpage user>
    <div class="user-page">
        <#assign prev = prev(users, "handle", user.handle)!/>
        <#assign next = next(users, "handle", user.handle)!/>
        <div class="user">
            <div class="info">
                <div class="name">
                    <@userlink user false/>
                </div>
                <ul>
                    <li>id: ${user.id}</li>
                    <li>handle: ${user.handle}</li>
                    <li>name: ${user.name}</li>
                    <li><a href="/posts?user_id=${user.id}">posts</a>: ${countBy(posts, "user_id", user.id)}</li>
                </ul>
            </div>
            <img src="/img/user-icon.jpg" alt="user-icon">
        </div>
        <div class="links">
            <#if prev?has_content>
                <a href="/user?handle=${prev.handle}">&#x2190</a>
            <#else>
                &#x2190
            </#if>
            <#if next?has_content>
                <a href="/user?handle=${next.handle}">&#x2192</a>
            <#else>
                &#x2192
            </#if>
        </div>
    </div>
</#macro>

<#function findBy items key value>
    <#list items as item>
        <#if item[key] == value>
            <#return item/>
        </#if>
    </#list>
</#function>

<#function countBy items key value>
    <#assign ct = 0>
    <#list items as item>
        <#if item[key] == value>
            <#assign ct++>
        </#if>
    </#list>
    <#return ct/>
</#function>

<#function next items key value>
    <#assign used = false>
    <#list items as item>
        <#if used>
            <#return item/>
        </#if>
        <#if item[key] == value>
            <#assign used = true/>
        </#if>
    </#list>
</#function>

<#function prev items key value>
    <#assign prev = 0>
    <#list items as item>
        <#if item[key] != value>
            <#assign prev = item>
        </#if>
        <#if item[key] == value>
            <#if prev?is_hash>
                <#return prev/>
            </#if>
        </#if>
    </#list>
</#function>
