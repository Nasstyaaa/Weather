package org.nastya.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.nastya.config.ThymeleafConfig;
import org.nastya.exception.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import java.io.IOException;

public abstract class BaseServlet extends HttpServlet {
    protected TemplateEngine engine;
    protected WebContext context;

    @Override
    public void init(ServletConfig config) throws ServletException {
        engine = ThymeleafConfig.buildTemplateEngine(config.getServletContext());
        super.init(config);
    }

    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        context = ThymeleafConfig.buildWebContext(req, resp, req.getServletContext());
        try {
            super.service(req, resp);
            resp.setStatus(HttpServletResponse.SC_OK);

        } catch (UserNotFoundException e) {
            resp.sendRedirect(req.getContextPath() + "/");
        }
    }
}
