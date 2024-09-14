package org.nastya.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.nastya.config.ThymeleafConfig;
import org.nastya.dto.LocationResponseApiDTO;
import org.nastya.exception.UserNotFoundException;
import org.nastya.model.Session;
import org.nastya.service.AuthenticationService;
import org.nastya.service.LocationService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import java.io.IOException;
import java.util.List;

@WebServlet (urlPatterns = "/home")
public class UserPageServlet extends HttpServlet {
    private final AuthenticationService authenticationService = new AuthenticationService();
    private final LocationService locationService = new LocationService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = ThymeleafConfig.buildTemplateEngine(req.getServletContext());
        WebContext context = ThymeleafConfig.buildWebContext(req, resp, req.getServletContext());

        try {
            Session session = authenticationService.checkLogin(req.getCookies());
            context.setVariable("user", session.getUser());

            List<LocationResponseApiDTO> locations = locationService.findUserLocations(session.getUser());
            System.out.println(locations.size());
            context.setVariable("locations", locations);

            engine.process("home", context, resp.getWriter());
        } catch (UserNotFoundException e){
            resp.sendRedirect(req.getContextPath() + "/home");
        }
    }
}
