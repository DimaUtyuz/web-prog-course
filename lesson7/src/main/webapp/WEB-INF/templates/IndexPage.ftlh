<#import "commons.ftlh" as c>

<@c.page>
    <div class="articles">
        <ul>
            <template>
                <li>
                    <article>
                        <div class="title"></div>
                        <div class="information"></div>
                        <div class="text">
                            <p class="template-indent" style="display: none"></p>
                        </div>
                    </article>
                </li>
            </template>
        </ul>
    </div>
    <script>
        $(function () {
            const success = function(response) {
                let users = new Map();
                response["users"].forEach(function (user) {
                    users.set(user["id"], user["login"]);
                });
                response["articles"].forEach(function(article) {
                    if (!article["hidden"]) {
                        const $li = $($(".articles template").prop("content")).clone();
                        $li.find(".title").html(article["title"]);
                        $li.find(".information").text("By " + users.get(article["userId"]));
                        $li.find(".text").html(article["text"].replaceAll("\n", "<br>"));
                        $(".articles ul").append($li);
                    }
                });
            }
            ajax("findArticles", {}, this, success);
        })
    </script>
</@c.page>
