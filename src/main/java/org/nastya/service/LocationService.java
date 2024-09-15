package org.nastya.service;

import org.nastya.dao.LocationDAO;
import org.nastya.dto.LocationDTO;
import org.nastya.dto.LocationResponseApiDTO;
import org.nastya.exception.LocationNotFoundException;
import org.nastya.model.Location;
import org.nastya.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LocationService {
    private final LocationDAO locationDAO = new LocationDAO();

    public void updateUserLocations(LocationDTO locationDTO) {
        Optional<Location> location = locationDAO.findByNameAndUser(locationDTO.getName(), locationDTO.getUser());
        if (location.isPresent()) {
            locationDAO.delete(location.get());
        } else {
            locationDAO.save(new Location(
                    locationDTO.getName(),
                    locationDTO.getUser(),
                    locationDTO.getLatitude(),
                    locationDTO.getLongitude()));
        }
    }

    public List<LocationResponseApiDTO> findUserLocations(User user) {
        WeatherAPIService weatherAPIService = new WeatherAPIService();
        List<LocationResponseApiDTO> locations = new ArrayList<>();

        locationDAO.findAllByUser(user).forEach(location -> {
            LocationResponseApiDTO locationDto = weatherAPIService.findLocation(location.getName());
            locations.add(locationDto);
        });

        return locations;
    }

    public Location findUserLocation (String location, User user){
        return locationDAO.findByNameAndUser(location, user).orElseThrow(LocationNotFoundException::new);
    }
}
