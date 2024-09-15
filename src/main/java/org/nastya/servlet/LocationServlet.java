package org.nastya.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.nastya.dto.DayDTO;
import org.nastya.dto.LocationDTO;
import org.nastya.dto.LocationForecastDayDTO;
import org.nastya.dto.LocationResponseApiDTO;
import org.nastya.exception.InternalServerError;
import org.nastya.exception.LocationNotFoundException;
import org.nastya.exception.MissingFormFieldException;
import org.nastya.model.Session;
import org.nastya.service.AuthenticationService;
import org.nastya.service.LocationService;
import org.nastya.service.WeatherAPIService;
import org.nastya.util.ResponseUtil;

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
        context.setVariable("user", session.getUser());

        try {
            String name = req.getParameter("name");
            LocationForecastDayDTO locationForecastDayDTO = weatherAPIService.findForecastDay(name);
            context.setVariable("locationForecastDayDTO", locationForecastDayDTO);

            engine.process("forecastday", context, resp.getWriter());

        } catch (MissingFormFieldException | LocationNotFoundException e) {
            ResponseUtil.create(req, resp, e, HttpServletResponse.SC_BAD_REQUEST, "/forecastday");

        } catch (InternalServerError e) {
            ResponseUtil.create(req, resp, e, HttpServletResponse.SC_BAD_GATEWAY, "/forecastday");
        }

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
