package org.nastya.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.nastya.config.ThymeleafConfig;
import org.nastya.dto.UserDTORequest;
import org.nastya.exception.MissingFormFieldException;
import org.nastya.exception.UserAlreadyExistsException;
import org.nastya.service.AuthenticationService;
import org.nastya.util.ResponseUtil;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import java.io.IOException;

@WebServlet(urlPatterns = "/registration")
public class RegistrationServlet extends HttpServlet {
    private final AuthenticationService authenticationService = new AuthenticationService();

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

        try {
            if (login.isBlank() || password.isBlank()) {
                throw new MissingFormFieldException();
            }
            authenticationService.register(new UserDTORequest(login, password));
            resp.setStatus(HttpServletResponse.SC_OK); //TODO нужно ли здесь это?
            resp.sendRedirect(req.getContextPath() + "/");

        } catch (UserAlreadyExistsException e) {
            ResponseUtil.create(req, resp, e, HttpServletResponse.SC_CONFLICT, "/registration");
        } catch (MissingFormFieldException e) {
            ResponseUtil.create(req, resp, e, HttpServletResponse.SC_BAD_REQUEST, "/registration");
        }
    }
}
