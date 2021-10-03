package ru.itmo.wp.servlet;

import ru.itmo.wp.util.ImageUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

public class CaptchaFilter extends HttpFilter {
    String captchaHtml = "<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <title>Codeforces</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "<div class=\"middle\">\n" +
                        "    <main>\n" +
                        "        <img src=\"data:image/png;base64,$$capthaImage$$\" alt=\"captcha\">\n" +
                        "        <form action=\"/captcha-ans\" method=\"post\">\n" +
                        "            <label>Enter number from the picture: </label>\n" +
                        "            <input name=\"answer\">\n" +
                        "            <input type=\"hidden\" value=\"$$requestURI$$\" name=\"requestURI\">\n" +
                        "        </form>\n" +
                        "    </main>\n" +
                        "</div>\n" +
                        "</body>\n" +
                        "</html>";

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession();
        if (!"done".equals(session.getAttribute("answer"))) {
            if ("GET".equals(request.getMethod())) {
                createCaptcha(request, response, false);
            } else if (request.getRequestURI().endsWith("/captcha-ans")) {
                if (request.getParameter("answer").equals(session.getAttribute("answer"))) {
                    session.setAttribute("answer", "done");
                    response.sendRedirect(request.getParameter("requestURI"));
                } else {
                    createCaptcha(request, response, true);
                }
            } else {
                chain.doFilter(request, response);
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    private void createCaptcha(HttpServletRequest request, HttpServletResponse response, boolean needNumber) throws IOException {
        HttpSession session = request.getSession();
        String number = (String) session.getAttribute("answer");
        if (Objects.isNull(number) || needNumber) {
            number = Integer.toString(100 + (int) (Math.random() * 900));
            session.setAttribute("answer", number);
        }
        byte[] img = ImageUtils.toPng(number);
        String resultCaptcha = captchaHtml.replace("$$requestURI$$", request.getRequestURI())
                                            .replace("$$capthaImage$$", Base64.getEncoder().encodeToString(img));
        response.getOutputStream().print(resultCaptcha);
        response.getOutputStream().flush();
    }
}
