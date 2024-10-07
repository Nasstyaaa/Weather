import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.nastya.dto.LocationResponseApiDTO;
import org.nastya.exception.LocationNotFoundException;
import org.nastya.service.WeatherAPIService;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class WeatherAPIServiceTest {
    private HttpClient httpClient;
    private WeatherAPIService weatherAPIService;
    private HttpResponse<String> mockResponse;

    @BeforeEach
    public void initMocks(){
        httpClient = Mockito.mock(HttpClient.class);
        weatherAPIService = new WeatherAPIService(httpClient);
        mockResponse = mock(HttpResponse.class);
    }

    @Test
    public void findLocation_nameOfExistingLocation_shouldFindLocation() throws IOException, InterruptedException {
        String location = "Paris";
        String jsonResponse = "{ \"location\": { \"name\": \"Paris\" } }";

        when(mockResponse.body()).thenReturn(jsonResponse);
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);

        LocationResponseApiDTO locationResponseApiDTO = weatherAPIService.findLocation(location);

        assertEquals(location, locationResponseApiDTO.getLocationApiDTO().getName());
    }

    @Test
    public void findLocation_coordinatesOfExistingLocation_shouldFindLocation() throws IOException, InterruptedException {
        String location = "48.8567,2.3508";
        String jsonResponse = "{ \"location\": { \"name\": \"Paris\" } }";

        when(mockResponse.body()).thenReturn(jsonResponse);
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);

        LocationResponseApiDTO locationResponseApiDTO = weatherAPIService.findLocation(location);

        assertEquals("Paris", locationResponseApiDTO.getLocationApiDTO().getName());
    }

    @Test
    public void findLocation_NameOfNonExistingLocation_shouldThrowLocationNotFoundException() throws IOException, InterruptedException {
        String location = "Paruis";
        String jsonResponse = "[]";

        when(mockResponse.body()).thenReturn(jsonResponse);
        when(mockResponse.statusCode()).thenReturn(400);
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);

        assertThrows(LocationNotFoundException.class, () -> weatherAPIService.findLocation(location));
    }
}
