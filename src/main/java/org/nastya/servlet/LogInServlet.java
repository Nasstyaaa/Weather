package org.nastya.servlet;


import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.nastya.config.ThymeleafConfig;
import org.nastya.dto.UserDTORequest;
import org.nastya.exception.InvalidPasswordException;
import org.nastya.exception.MissingFormFieldException;
import org.nastya.exception.UserNotFoundException;
import org.nastya.service.AuthenticationService;
import org.nastya.util.ResponseUtil;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import java.io.IOException;

@WebServlet(urlPatterns = "/")
public class LogInServlet extends HttpServlet {
    private final AuthenticationService authenticationService = new AuthenticationService();

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

        try {
            if (login.isBlank() || password.isBlank()) {
                throw new MissingFormFieldException();
            }
            Cookie cookie = authenticationService.login(new UserDTORequest(login, password));

            resp.setStatus(HttpServletResponse.SC_OK);//TODO нужно ли здесь это?
            resp.addCookie(cookie);
            resp.sendRedirect(req.getContextPath() +"main");

        } catch (MissingFormFieldException | InvalidPasswordException e) {
            ResponseUtil.create(req, resp, e, HttpServletResponse.SC_BAD_REQUEST, "/login");
        } catch (UserNotFoundException e) {
            ResponseUtil.create(req, resp, e, HttpServletResponse.SC_NOT_FOUND, "/login");
        }
    }
}
