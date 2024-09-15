package org.nastya.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

@WebServlet(urlPatterns = "/search")
public class SearchServlet extends BaseServlet {
    private final WeatherAPIService weatherAPIService = new WeatherAPIService();
    private final AuthenticationService authenticationService = new AuthenticationService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Session session = authenticationService.checkLogin(req.getCookies());
        context.setVariable("user", session.getUser());

        engine.process("main", context, resp.getWriter());
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Session session = authenticationService.checkLogin(req.getCookies());
        req.setAttribute("user", session.getUser());

        try {
            String location = req.getParameter("location");
            if (location.isBlank()) {
                throw new MissingFormFieldException();
            }

            LocationResponseApiDTO locationResponseApiDTO = weatherAPIService.findLocation(location);
            context.setVariable("locationResponseApiDTO", locationResponseApiDTO);

            engine.process("main", context, resp.getWriter());

        } catch (MissingFormFieldException | LocationNotFoundException e) {
            ResponseUtil.create(req, resp, e, HttpServletResponse.SC_BAD_REQUEST, "/main");

        } catch (InternalServerError e) {
            ResponseUtil.create(req, resp, e, HttpServletResponse.SC_BAD_GATEWAY, "/main");
        }
    }
}
