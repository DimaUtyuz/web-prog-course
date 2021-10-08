package ru.itmo.web.lesson4.util;

import ru.itmo.web.lesson4.model.Post;
import ru.itmo.web.lesson4.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataUtil {
    private static final String LONG_TEXT = "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Alias debitis dolore dolorum facere hic nisi perferendis rerum sed. Accusamus, delectus ducimus facere fuga illum quaerat ratione voluptatibus! Autem cum, cupiditate dolor dolore, earum excepturi.";
    private static final String SHORT_TEXT = "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Alias debitis dolore dolorum facere hic nisi perferendis rerum sed. Accusamus, delectus ducimus facere fuga illum quaerat ratione voluptatibus!";
    private static final List<User> USERS = Arrays.asList(
            new User(1, "MikeMirzayanov", "Mike Mirzayanov", User.UserColors.RED),
            new User(6, "pashka", "Pavel Mavrin", User.UserColors.GREEN),
            new User(9, "geranazarov555", "Georgiy Nazarov", User.UserColors.BLUE),
            new User(11, "tourist", "Gennady Korotkevich", User.UserColors.RED),
            new User(2, "user2", "User Userovich", User.UserColors.GREEN)
    );
    private static final Map<String, String> NAVS = new HashMap<>(Map.of("index", "Home",
            "help", "Help", "contests", "Contests", "users", "Users"));
    private static final List<Post> POSTS = Arrays.asList(
            new Post(2, "First post", SHORT_TEXT, 1),
            new Post(5, "Second post", SHORT_TEXT, 6),
            new Post(7, "Third post", LONG_TEXT, 9),
            new Post(10, "Fourth post", SHORT_TEXT, 11),
            new Post(12, "My post", LONG_TEXT, 11),
            new Post(100, "Lol post", SHORT_TEXT, 11)
    );

    public static void addData(HttpServletRequest request, Map<String, Object> data) {
        data.put("users", USERS);
        data.put("posts", POSTS);
        data.put("menu", "");
        for (String key : NAVS.keySet()) {
            if (request.getRequestURI().endsWith(key)) {
                data.put("menu", NAVS.get(key));
            }
        }
        for (User user : USERS) {
            if (Long.toString(user.getId()).equals(request.getParameter("logged_user_id"))) {
                data.put("user", user);
            }
        }
    }
}
