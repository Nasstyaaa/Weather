package org.nastya.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.nastya.dto.LocationResponseDTO;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherAPIService {

    public LocationResponseDTO createLocation() throws IOException, InterruptedException, URISyntaxException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://api.weatherapi.com/v1/current.json?" +
                        "q=48.8567,2.3508&key=bc2c6e79b7594e5eab475758241009"))
                .GET()
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        String json = response.body();
        JsonNode jsonNode = new ObjectMapper().readTree(json);

        LocationResponseDTO locationResponseDTO = new LocationResponseDTO(
                jsonNode.get("location").get("name").asText(),
                jsonNode.get("location").get("lat").asText(),
                jsonNode.get("location").get("lon").asText(),
                jsonNode.get("current").get("temp_c").asText());

        return locationResponseDTO;
    }
}
