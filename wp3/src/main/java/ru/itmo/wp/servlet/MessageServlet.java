package ru.itmo.wp.servlet;

import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class MessageServlet extends HttpServlet {
    List<Map<String, String>> messages = new ArrayList<>();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Object objectToConvert = null;
        String uri = request.getRequestURI();
        if (uri.endsWith("/message/auth")) {
            String user = (String) request.getSession().getAttribute("user");
            if (Objects.isNull(user)) {
                user = request.getParameter("user");
                if (Objects.isNull(user) || user.isBlank()) {
                    user = "";
                } else {
                    user = user.trim();
                    request.getSession().setAttribute("user", user);
                }
            }
            objectToConvert = user;
        } else if (uri.endsWith("/message/add")) {
            String text = request.getParameter("text").trim();
            if (!text.isEmpty()) {
                String user = (String) request.getSession().getAttribute("user");
                HashMap<String, String> tmp = new HashMap<>();
                tmp.put("user", user);
                tmp.put("text", text);
                messages.add(tmp);
            }
        } else if (uri.endsWith("/message/findAll")) {
            if (!Objects.isNull(request.getSession().getAttribute("user"))) {
                objectToConvert = messages;
            }
        }
        response.setContentType("application/json;charset=windows-1251");
        String json = new Gson().toJson(objectToConvert);
        response.getWriter().print(json);
        response.getWriter().flush();
    }
}
