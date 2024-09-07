package org.nastya.servlet;


import jakarta.persistence.NoResultException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.nastya.config.ThymeleafConfig;
import org.nastya.dao.UserDAO;
import org.nastya.model.User;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import java.io.IOException;

@WebServlet(urlPatterns = "/")
public class LogInServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        TemplateEngine engine = ThymeleafConfig.buildTemplateEngine(req.getServletContext());
        WebContext context = ThymeleafConfig.buildWebContext(req, resp, req.getServletContext());

        engine.process("login", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (login.isBlank() || password.isBlank()) {
            //TODO exception
        }


        if (userDAO.find(login).isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            //TODO exception
        }

        //TODO Sessions
        resp.sendRedirect("/main");
    }
}
