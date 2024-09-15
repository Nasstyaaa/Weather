package org.nastya.service;

import org.nastya.dao.LocationDAO;
import org.nastya.dto.LocationDTO;
import org.nastya.dto.LocationResponseApiDTO;
import org.nastya.exception.LocationAlreadyAddedException;
import org.nastya.exception.LocationNotFoundException;
import org.nastya.model.Location;
import org.nastya.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LocationService {
    private final LocationDAO locationDAO = new LocationDAO();
    private final WeatherAPIService weatherAPIService = new WeatherAPIService();

    public void add(LocationDTO locationDTO) {
        Optional<Location> location = locationDAO.findByNameAndUser(locationDTO.getName(), locationDTO.getUser());
        if (location.isEmpty()) {
            locationDAO.save(new Location(
                    locationDTO.getName(),
                    locationDTO.getUser(),
                    locationDTO.getLatitude(),
                    locationDTO.getLongitude()));
        } else {
            throw new LocationAlreadyAddedException();
        }
    }

    public void delete(LocationDTO locationDTO) {
        Optional<Location> location = locationDAO.findByNameAndUser(locationDTO.getName(), locationDTO.getUser());
        location.ifPresent(locationDAO::delete);
    }

    public List<LocationResponseApiDTO> findUserLocations(User user) {
        List<LocationResponseApiDTO> locations = new ArrayList<>();

        locationDAO.findAllByUser(user).forEach(location -> {
            LocationResponseApiDTO locationDto = weatherAPIService.findLocation(location.getName());
            locations.add(locationDto);
        });

        return locations;
    }
}
