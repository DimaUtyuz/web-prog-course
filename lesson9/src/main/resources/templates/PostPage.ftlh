<#import "commons.ftlh" as c>

<@c.page>
    <#if postPage??>
        <@c.post post=postPage/>
        <#if user??>
            <div class="comment-form-box">
                <form method="post" action="">
                    <input type="hidden" name="action" value="comment"/>
                    <div class="field">
                        <div class="value">
                            <label for="text"></label><textarea id="text" name="text">${comment.text!}</textarea>
                        </div>
                        <@c.error "comment.text"/>
                    </div>
                    <div class="error"></div>
                    <div class="button-field">
                        <input type="submit" value="Comment">
                    </div>
                </form>
            </div>
        </#if>
        <#list postPage.comments as comment>
            <div class="comment">
                <div class="information">By ${comment.user.login}, ${comment.creationTime?datetime}</div>
                <div class="text">${comment.text}</div>
            </div>
        </#list>
    <#else>
        No such Post.
    </#if>
</@c.page>
