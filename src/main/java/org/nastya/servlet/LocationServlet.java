package org.nastya.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.nastya.dto.DayDTO;
import org.nastya.dto.LocationDTO;
import org.nastya.dto.LocationForecastDayDTO;
import org.nastya.model.Session;
import org.nastya.service.AuthenticationService;
import org.nastya.service.LocationService;
import org.nastya.service.WeatherAPIService;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet(urlPatterns = "/location")
public class LocationServlet extends BaseServlet{
    private final LocationService locationService = new LocationService();
    private final AuthenticationService authenticationService = new AuthenticationService();
    private final WeatherAPIService weatherAPIService = new WeatherAPIService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Session session = authenticationService.checkLogin(req.getCookies());

        String name = req.getParameter("name");

        LocationForecastDayDTO locationForecastDayDTO = weatherAPIService.findForecastDay(name);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Session session = authenticationService.checkLogin(req.getCookies());

        String name = req.getParameter("name");
        BigDecimal latitude = new BigDecimal(req.getParameter("latitude"));
        BigDecimal longitude = new BigDecimal(req.getParameter("longitude"));

        LocationDTO locationDTO = new LocationDTO(name, session.getUser(), latitude, longitude);

        locationService.updateUserLocations(locationDTO);
        resp.sendRedirect(req.getContextPath() + "/home");
    }
}
