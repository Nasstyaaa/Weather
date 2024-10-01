//import org.junit.jupiter.api.Test;
//import org.nastya.dto.LocationResponseApiDTO;
//import org.nastya.exception.InternalServerException;
//import org.nastya.exception.LocationNotFoundException;
//import org.nastya.service.WeatherAPIService;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class WeatherAPIServiceTest {
//    private final WeatherAPIService weatherAPIService = new WeatherAPIService();
//
//    @Test
//    public void findLocation_nameOfExistingLocation_shouldFindLocation() {
//        String location = "Paris";
//
//        LocationResponseApiDTO locationResponseApiDTO = weatherAPIService.findLocation(location);
//
//        assertEquals(location, locationResponseApiDTO.getLocationApiDTO().getName());
//    }
//
//    @Test
//    public void findLocation_coordinatesOfExistingLocation_shouldFindLocation() {
//        String location = "48.8567,2.3508";
//
//        LocationResponseApiDTO locationResponseApiDTO = weatherAPIService.findLocation(location);
//
//        assertEquals("Paris", locationResponseApiDTO.getLocationApiDTO().getName());
//    }
//
//    @Test
//    public void findLocation_NameOfNonExistingLocation_shouldThrowLocationNotFoundException() {
//        String location = "Paruis";
//
//        assertThrows(LocationNotFoundException.class, () -> weatherAPIService.findLocation(location));
//    }
//
//    @Test
//    public void findLocation_incorrectCoordinatesFormat_shouldThrowInternalServerError() {
//        String location = "48.8567 2.3508";
//
//        assertThrows(InternalServerException.class, () -> weatherAPIService.findLocation(location));
//    }
//}
