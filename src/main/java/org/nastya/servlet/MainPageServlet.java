package org.nastya.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.nastya.config.ThymeleafConfig;
import org.nastya.dto.LocationResponseDTO;
import org.nastya.exception.InternalServerError;
import org.nastya.exception.LocationNotFoundException;
import org.nastya.exception.MissingFormFieldException;
import org.nastya.exception.UserNotFoundException;
import org.nastya.model.Session;
import org.nastya.service.AuthenticationService;
import org.nastya.service.WeatherAPIService;
import org.nastya.util.ResponseUtil;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import java.io.IOException;

@WebServlet(urlPatterns = "/main")
public class MainPageServlet extends HttpServlet {
    private final WeatherAPIService weatherAPIService = new WeatherAPIService();
    private final AuthenticationService authenticationService = new AuthenticationService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        TemplateEngine engine = ThymeleafConfig.buildTemplateEngine(req.getServletContext());
        WebContext context = ThymeleafConfig.buildWebContext(req, resp, req.getServletContext());

        try {
            Session session = authenticationService.checkLogin(req.getCookies());
            context.setVariable("user", session.getUser());
            engine.process("main", context, resp.getWriter());
        } catch (UserNotFoundException e) {
            resp.sendRedirect("/");
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        TemplateEngine engine = ThymeleafConfig.buildTemplateEngine(req.getServletContext());
        WebContext context = ThymeleafConfig.buildWebContext(req, resp, req.getServletContext());

        Session session = authenticationService.checkLogin(req.getCookies());
        req.setAttribute("user", session.getUser());

        String location = req.getParameter("location");

        try {
            if (location.isBlank()) {
                throw new MissingFormFieldException();
            }

            LocationResponseDTO locationResponseDTO = weatherAPIService.findLocation(location);
            context.setVariable("locationResponseDTO", locationResponseDTO);

            engine.process("main", context, resp.getWriter());

        } catch (MissingFormFieldException | LocationNotFoundException e) {
            ResponseUtil.create(req, resp, e, HttpServletResponse.SC_BAD_REQUEST, "/main");
        } catch (InternalServerError e) {
            ResponseUtil.create(req, resp, e, HttpServletResponse.SC_BAD_GATEWAY, "/main");
        }
    }
}
