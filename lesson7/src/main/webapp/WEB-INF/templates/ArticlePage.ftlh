
<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#-- @ftlvariable name="error" type="java.lang.String" -->
<#-- @ftlvariable name="text" type="java.lang.String" -->
<#-- @ftlvariable name="userService" type="ru.itmo.wp.model.service.UserService" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="create article-box">
        <form method="post" action="">
            <input type="hidden" name="action" value="create"/>
            <div class="field">
                <div class="name">
                    <label for="title">Title</label>
                </div>
                <div class="value">
                    <input id="title" name="title"/>
                </div>
            </div>
            <div class="field">
                <div class="name">
                    <label for="text">Text</label>
                </div>
                <div class="text-value">
                    <textarea id="text" name="text"></textarea>
                </div>
            </div>
             <div class="error"></div>
            <div class="button-field">
                <input type="submit" value="Create">
            </div>
        </form>
    </div>
    <script>
        $(function () {
            $(".create form").submit(function () {
                const title = $(this).find("input[name='title']").val();
                const text = $(this).find("textarea[name='text']").val();

                ajax("create", {title, text}, this);

                return false;
            });
        })
    </script>
</@c.page>
