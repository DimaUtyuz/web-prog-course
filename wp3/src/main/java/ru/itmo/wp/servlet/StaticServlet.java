package ru.itmo.wp.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StaticServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] uris = request.getRequestURI().split("\\+");
        String basicPath = "./src/main/webapp/static/";
        for (int i = 0; i < uris.length; i++) {
            if (Files.isRegularFile(Paths.get(basicPath + uris[i]))) {
                uris[i] = basicPath + uris[i];
            } else if (Files.isRegularFile(Paths.get(getServletContext().getRealPath("/static" + uris[i])))) {
                uris[i] = getServletContext().getRealPath("/static" + uris[i]);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
        }
        response.setContentType(getContentTypeFromName(uris[0]));
        OutputStream outputStream = response.getOutputStream();
        for (String uri : uris) {
            Files.copy(Paths.get(uri), outputStream);
        }
        outputStream.flush();
    }

    private String getContentTypeFromName(String name) {
        name = name.toLowerCase();

        if (name.endsWith(".png")) {
            return "image/png";
        }

        if (name.endsWith(".jpg")) {
            return "image/jpeg";
        }

        if (name.endsWith(".html")) {
            return "text/html";
        }

        if (name.endsWith(".css")) {
            return "text/css";
        }

        if (name.endsWith(".js")) {
            return "application/javascript";
        }

        throw new IllegalArgumentException("Can't find content type for '" + name + "'.");
    }
}
