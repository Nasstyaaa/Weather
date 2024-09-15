package org.nastya.servlet;


import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.nastya.dto.UserDTORequest;
import org.nastya.exception.IncorrectLoginInformation;
import org.nastya.exception.MissingFormFieldException;
import org.nastya.service.AuthenticationService;
import org.nastya.util.ResponseUtil;

import java.io.IOException;

@WebServlet(urlPatterns = "/")
public class LogInServlet extends BaseServlet {
    private final AuthenticationService authenticationService = new AuthenticationService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
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

            resp.addCookie(cookie);
            resp.sendRedirect(req.getContextPath() +"main");

        } catch (MissingFormFieldException e) {
            ResponseUtil.create(req, resp, e, HttpServletResponse.SC_BAD_REQUEST, "/login");

        } catch (IncorrectLoginInformation e) {
            ResponseUtil.create(req, resp, e, HttpServletResponse.SC_NOT_FOUND, "/login");
        }
    }
}
