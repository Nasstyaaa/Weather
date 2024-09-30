package org.nastya.servlet;


import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.nastya.dto.UserDTORequest;
import org.nastya.exception.IncorrectLoginInformationException;
import org.nastya.exception.MissingFormFieldException;
import org.nastya.model.Session;
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
                ResponseUtil.create(req, resp, new MissingFormFieldException(),
                        HttpServletResponse.SC_BAD_REQUEST, "/login");
            }
            Session session = authenticationService.login(new UserDTORequest(login, password));
            Cookie cookie = new Cookie("SessionId", session.getId().toString());
            cookie.setMaxAge(30 * 60);
            cookie.setPath("/");
            cookie.setHttpOnly(true);

            resp.addCookie(cookie);
            resp.sendRedirect(req.getContextPath() +"/home");
        }catch (IncorrectLoginInformationException e) {
            ResponseUtil.create(req, resp, e, HttpServletResponse.SC_NOT_FOUND, "/login");
        }
    }
}
