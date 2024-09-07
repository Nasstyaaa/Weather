package org.nastya.servlet;

import jakarta.servlet.ServletException;
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

@WebServlet(urlPatterns = "/registration")
public class RegistrationServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        TemplateEngine engine = ThymeleafConfig.buildTemplateEngine(req.getServletContext());
        WebContext context = ThymeleafConfig.buildWebContext(req, resp, req.getServletContext());

        engine.process("registration", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (login.isBlank() || password.isBlank()) {
            //TODO exception
        }

        if (userDAO.find(login).isPresent()){
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            //TODO exception
        }


    }
}
