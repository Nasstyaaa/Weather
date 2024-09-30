package org.nastya.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.nastya.dto.LocationDTO;
import org.nastya.dto.LocationResponseApiDTO;
import org.nastya.exception.InternalServerException;
import org.nastya.exception.LocationAlreadyExistsException;
import org.nastya.exception.LocationNotFoundException;
import org.nastya.exception.MissingFormFieldException;
import org.nastya.model.Session;
import org.nastya.service.AuthenticationService;
import org.nastya.service.LocationService;
import org.nastya.service.WeatherAPIService;
import org.nastya.util.ResponseUtil;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet(urlPatterns = "/search")
public class SearchServlet extends BaseServlet {
    private final WeatherAPIService weatherAPIService = new WeatherAPIService();
    private final LocationService locationService = new LocationService();
    private final AuthenticationService authenticationService = new AuthenticationService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Session session = authenticationService.checkLogin(req.getCookies());
        context.setVariable("user", session.getUser());

        String location = req.getParameter("location");
        if (location == null){
            resp.sendRedirect(req.getContextPath() +"/home");
            return;

        } else if (location.isBlank()) {
            ResponseUtil.create(req, resp, new MissingFormFieldException(),
                    HttpServletResponse.SC_BAD_REQUEST, "/search");
        }

        try {
            LocationResponseApiDTO locationResponseApiDTO = weatherAPIService.findLocation(location);
            context.setVariable("locationResponseApiDTO", locationResponseApiDTO);

            engine.process("search", context, resp.getWriter());

        } catch (LocationNotFoundException e) {
            ResponseUtil.create(req, resp, e, HttpServletResponse.SC_BAD_REQUEST, "/search");

        } catch (InternalServerException e) {
            ResponseUtil.create(req, resp, e, HttpServletResponse.SC_BAD_GATEWAY, "/search");
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Session session = authenticationService.checkLogin(req.getCookies());
        context.setVariable("user", session.getUser());

        String name = req.getParameter("name");
        BigDecimal latitude = new BigDecimal(req.getParameter("latitude"));
        BigDecimal longitude = new BigDecimal(req.getParameter("longitude"));

        try {
            LocationDTO locationDTO = new LocationDTO(name, session.getUser(), latitude, longitude);
            locationService.add(locationDTO);
            resp.sendRedirect(req.getContextPath() + "/home");

        } catch (LocationAlreadyExistsException e) {
            ResponseUtil.create(req, resp, e, HttpServletResponse.SC_BAD_REQUEST, "/search");
        }
    }
}
