package org.nastya.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.nastya.dto.LocationResponseDTO;
import org.nastya.exception.LocationNotFoundException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherAPIService {

    public LocationResponseDTO findLocation(String location) throws IOException, URISyntaxException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://api.weatherapi.com/v1/current.json?" +
                        "q=" + location + "&key=bc2c6e79b7594e5eab475758241009"))
                .GET()
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        String json = response.body();
        LocationResponseDTO locationResponseDTO = null;
        if (response.statusCode() == 200) {
            JsonNode jsonNode = new ObjectMapper().readTree(json);

            locationResponseDTO = new LocationResponseDTO(
                    jsonNode.get("location").get("name").asText(),
                    jsonNode.get("location").get("lat").asText(),
                    jsonNode.get("location").get("lon").asText(),
                    jsonNode.get("current").get("temp_c").asText());

        } else if (response.statusCode() == 400) {
            throw new LocationNotFoundException();
        }

        return locationResponseDTO;
    }
}
