package org.nastya.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.nastya.config.ThymeleafConfig;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import java.io.IOException;

public class ResponseUtil {

    public static void create(HttpServletRequest req, HttpServletResponse resp, Exception e, int status, String page)
            throws IOException {
        resp.setStatus(status);
        TemplateEngine engine = ThymeleafConfig.buildTemplateEngine(req.getServletContext());
        WebContext context = ThymeleafConfig.buildWebContext(req, resp, req.getServletContext());
        context.setVariable("message", e.getMessage());
        engine.process(page, context, resp.getWriter());
    }

}
