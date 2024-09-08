package org.nastya.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.nastya.config.ThymeleafConfig;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import java.io.IOException;

@WebServlet(urlPatterns = "/main")
public class MainPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        TemplateEngine engine = ThymeleafConfig.buildTemplateEngine(req.getServletContext());
        WebContext context = ThymeleafConfig.buildWebContext(req, resp, req.getServletContext());

        Cookie[] cookies = req.getCookies();
        System.out.println(cookies[0].getValue());
        //TODO запретить доступ не авторизовавшимся
        engine.process("main", context, resp.getWriter());
    }
}
