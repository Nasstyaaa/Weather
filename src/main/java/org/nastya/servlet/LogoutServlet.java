package org.nastya.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.nastya.model.Session;
import org.nastya.service.AuthenticationService;

import java.io.IOException;

@WebServlet(urlPatterns = "/logout")
public class LogoutServlet extends BaseServlet{
    private final AuthenticationService authenticationService = new AuthenticationService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Session session = authenticationService.checkLogin(req.getCookies());

        authenticationService.logout(session);
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
