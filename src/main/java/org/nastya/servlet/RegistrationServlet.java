package org.nastya.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.nastya.dto.UserDTORequest;
import org.nastya.exception.MissingFormFieldException;
import org.nastya.exception.UserAlreadyExistsException;
import org.nastya.service.AuthenticationService;
import org.nastya.util.ResponseUtil;

import java.io.IOException;

@WebServlet(urlPatterns = "/registration")
public class RegistrationServlet extends BaseServlet {
    private final AuthenticationService authenticationService = new AuthenticationService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        engine.process("registration", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        try {
            if (login.isBlank() || password.isBlank()) {
                ResponseUtil.create(req, resp, new MissingFormFieldException(),
                        HttpServletResponse.SC_BAD_REQUEST, "/registration");
            }
            authenticationService.register(new UserDTORequest(login, password));
            resp.sendRedirect(req.getContextPath() + "/");
        } catch (UserAlreadyExistsException e) {
            ResponseUtil.create(req, resp, e, HttpServletResponse.SC_CONFLICT, "/registration");

        }
    }
}
