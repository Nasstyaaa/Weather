package org.nastya.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.nastya.dto.LocationForecastDayDTO;
import org.nastya.exception.InternalServerException;
import org.nastya.exception.LocationNotFoundException;
import org.nastya.exception.MissingFormFieldException;
import org.nastya.model.Session;
import org.nastya.service.AuthenticationService;
import org.nastya.service.WeatherAPIService;
import org.nastya.util.ResponseUtil;

import java.io.IOException;

@WebServlet(urlPatterns = "/forecast")
public class ForecastDayServlet extends BaseServlet {
    private final AuthenticationService authenticationService = new AuthenticationService();
    private final WeatherAPIService weatherAPIService = new WeatherAPIService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Session session = authenticationService.checkLogin(req.getCookies());
        context.setVariable("user", session.getUser());

        String name = req.getParameter("name");
        try {
            LocationForecastDayDTO locationForecastDayDTO = weatherAPIService.findForecastDay(name);
            context.setVariable("locationForecastDayDTO", locationForecastDayDTO);

            engine.process("forecastday", context, resp.getWriter());
        } catch (LocationNotFoundException e) {
            ResponseUtil.create(req, resp, e, HttpServletResponse.SC_BAD_REQUEST, "/forecastday");
        } catch (InternalServerException e) {
            ResponseUtil.create(req, resp, e, HttpServletResponse.SC_BAD_GATEWAY, "/forecastday");
        }

    }
}
