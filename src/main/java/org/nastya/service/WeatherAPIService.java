package org.nastya.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import org.nastya.dto.DayDTO;
import org.nastya.dto.LocationForecastDayDTO;
import org.nastya.dto.LocationResponseApiDTO;
import org.nastya.exception.InternalServerException;
import org.nastya.exception.LocationNotFoundException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WeatherAPIService {

    public LocationResponseApiDTO findLocation(String location) {
        String locationName = location.replace(" ", "%20");
        Map<String, String> env = System.getenv();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://api.weatherapi.com/v1/current.json?" +
                            "q=" + locationName + "&key=" + env.get("API_KEY")))
                    .GET()
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            String json = response.body();
            LocationResponseApiDTO locationResponseApiDTO = null;
            if (response.statusCode() == 200) {
                JsonNode jsonNode = new ObjectMapper().readTree(json);

                locationResponseApiDTO = new LocationResponseApiDTO(
                        jsonNode.get("location").get("name").asText(),
                        jsonNode.get("location").get("lat").asText(),
                        jsonNode.get("location").get("lon").asText(),
                        jsonNode.get("current").get("temp_c").asText(),
                        jsonNode.get("current").get("condition").get("icon").asText());

            } else if (response.statusCode() == 400) {
                throw new LocationNotFoundException();
            }

            return locationResponseApiDTO;

        } catch (IOException | URISyntaxException | InterruptedException e) {
            throw new InternalServerException();
        }
    }

    public LocationForecastDayDTO findForecastDay(String location){
        String locationName = location.replace(" ", "%20");
        Map<String, String> env = System.getenv();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://api.weatherapi.com/v1/forecast.json?" +
                            "q=" + locationName + "&key=" + env.get("API_KEY") + "&days=5"))
                    .GET()
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            String json = response.body();
            LocationResponseApiDTO locationResponseApiDTO = null;
            List<DayDTO> dayDTOList = new ArrayList<>();
            if (response.statusCode() == 200) {
                JsonNode jsonNode = new ObjectMapper().readTree(json);

                locationResponseApiDTO = new LocationResponseApiDTO(
                        jsonNode.get("location").get("name").asText(),
                        jsonNode.get("location").get("lat").asText(),
                        jsonNode.get("location").get("lon").asText(),
                        jsonNode.get("current").get("temp_c").asText(),
                        jsonNode.get("current").get("condition").get("icon").asText());

                for (JsonNode dayNode : jsonNode.get("forecast").get("forecastday")) {
                    DayDTO dayDTO = new DayDTO(
                            dayNode.get("date").asText(),
                            dayNode.get("day").get("maxtemp_c").asText(),
                            dayNode.get("day").get("mintemp_c").asText(),
                            dayNode.get("day").get("condition").get("icon").asText()
                    );
                    dayDTOList.add(dayDTO);
                }

            } else if (response.statusCode() == 400) {
                throw new LocationNotFoundException();
            }

            return new LocationForecastDayDTO(locationResponseApiDTO, dayDTOList);

        } catch (IOException | URISyntaxException | InterruptedException e) {
            throw new InternalServerException();
        }
    }
}
