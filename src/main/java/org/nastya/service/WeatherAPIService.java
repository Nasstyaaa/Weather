package org.nastya.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.nastya.dto.LocationForecastDayDTO;
import org.nastya.dto.LocationResponseApiDTO;
import org.nastya.exception.InternalServerException;
import org.nastya.exception.LocationNotFoundException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class WeatherAPIService {

    public LocationResponseApiDTO findLocation(String location) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://api.weatherapi.com/v1/current.json?"
                            + "q=" + URLEncoder.encode(location, StandardCharsets.UTF_8)
                            + "&key=bc2c6e79b7594e5eab475758241009" ))//+ System.getenv().get("API_KEY")))
                    .GET()
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 400) {
                throw new LocationNotFoundException();
            }
            return new ObjectMapper().readValue(response.body(), LocationResponseApiDTO.class);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            throw new InternalServerException();
        }
    }

    public LocationForecastDayDTO findForecastDay(String location) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://api.weatherapi.com/v1/forecast.json?"
                            + "q=" + URLEncoder.encode(location, StandardCharsets.UTF_8)
                            + "&key=bc2c6e79b7594e5eab475758241009" + "&days=3"))
                    .GET()
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 400) {
                throw new LocationNotFoundException();
            }

            return new ObjectMapper().readValue(response.body(), LocationForecastDayDTO.class);

        } catch (IOException | URISyntaxException | InterruptedException e) {
            throw new InternalServerException();
        }
    }
}
