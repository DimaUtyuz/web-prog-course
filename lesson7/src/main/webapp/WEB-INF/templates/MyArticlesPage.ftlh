<#-- @ftlvariable name="myArticles" type="ru.itmo.wp.model.domain.Article[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="articles datatable">
        <div class="caption">Article</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Title</th>
                <th>Hidden</th>
            </tr>
            </thead>
            <tbody>
            <#list myArticles as article>
                <tr>
                    <td class="article-id">${article.id}</td>
                    <td class="article-title">${article.title}</td>
                    <td class="articles-hidden">
                        <#if article.hidden>
                            <button value=${article.id}>Show</button>
                        <#else>
                            <button value=${article.id}>Hide</button>
                        </#if>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>

    <script>
        $(".articles button").click(function() {
            const context = $(this);
            const hidden = context.text();
            const success = function (response) {
                context.text(hidden === "Hide" ? "Show" : "Hide");
            }
            ajax("setHidden", {id: $(this).val(), hidden}, this, success)
        });
    </script>
</@c.page>
