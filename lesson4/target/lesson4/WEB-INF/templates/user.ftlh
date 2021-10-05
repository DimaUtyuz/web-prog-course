<#-- @ftlvariable name="handle" type="java.lang.String" -->
<#-- @ftlvariable name="user_id" type="java.lang.String" -->
<#import "./common.ftlh" as c>

<@c.page>
    <#if handle?has_content>
        <#assign u = c.findBy(users, "handle", handle)!/>
    <#elseif user_id?has_content>
        <#assign u = c.findBy(users, "id", user_id)!/>
    </#if>
    <#if u?has_content>
        <@c.userpage u/>
    <#else>
        No such user
    </#if>
</@c.page>
