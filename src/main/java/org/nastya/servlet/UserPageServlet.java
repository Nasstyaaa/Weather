package org.nastya.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.nastya.dto.LocationResponseApiDTO;
import org.nastya.exception.LocationNotFoundException;
import org.nastya.model.Location;
import org.nastya.model.Session;
import org.nastya.service.AuthenticationService;
import org.nastya.service.LocationService;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/home")
public class UserPageServlet extends BaseServlet {
    private final AuthenticationService authenticationService = new AuthenticationService();
    private final LocationService locationService = new LocationService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Session session = authenticationService.checkLogin(req.getCookies());
        context.setVariable("user", session.getUser());

        List<LocationResponseApiDTO> locations = locationService.findUserLocations(session.getUser());
        context.setVariable("locations", locations);

        engine.process("home", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Session session = authenticationService.checkLogin(req.getCookies());
        req.setAttribute("user", session.getUser());

        String action = req.getParameter("action");

        try {
            if (action.equals("find")) {
                String locationName = req.getParameter("location");
                Location location = locationService.findUserLocation(locationName, session.getUser());

                context.setVariable("locations", location);
                engine.process("home", context, resp.getWriter());
            }
        } catch (LocationNotFoundException e) {

        }

    }
}
