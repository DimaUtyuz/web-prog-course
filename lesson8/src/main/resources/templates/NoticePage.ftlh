<#import "commons.ftlh" as c>

<@c.page>
    <div class="notice-box">
        <form method="post" action="">
            <input type="hidden" name="action" value="create"/>
            <div class="field">
                <div class="name">
                    <label for="text">Text</label>
                </div>
                <div class="text-value">
                    <textarea id="content" name="content">${noticeForm.content!}</textarea>
                </div>
                <@c.error "noticeForm.content"/>
            </div>
            <div class="error"></div>
            <div class="button-field">
                <input type="submit" value="Add">
            </div>
        </form>
    </div>
</@c.page>
