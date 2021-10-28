package ru.itmo.wp.web.page;

import com.google.common.base.Strings;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class IndexPage extends BasePage {
    @Override
    protected void action(HttpServletRequest request, Map<String, Object> view) {
        putMessage(view);
    }
}
