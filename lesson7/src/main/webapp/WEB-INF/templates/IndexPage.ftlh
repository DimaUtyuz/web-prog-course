<#import "commons.ftlh" as c>

<@c.page>
    <div class="articles">
        <ul>
            <template>
                <li>
                    <article>
                        <div class="title"></div>
                        <div class="text"></div>
                    </article>
                </li>
            </template>
        </ul>
    </div>
    <script>
        $(function () {
            const success = function(response) {
                response["articles"].forEach(function(article) {
                    const $li = $($(".articles template").prop("content")).clone();
                    $li.find(".title").text(article["title"]);
                    $li.find(".text").text(article["text"]);
                    $(".articles ul").append($li);
                });
            }
            ajax("findArticles", {}, this, success);
        })
    </script>
</@c.page>
