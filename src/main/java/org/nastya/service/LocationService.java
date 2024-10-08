package org.nastya.service;

import org.hibernate.exception.ConstraintViolationException;
import org.nastya.dao.LocationDAO;
import org.nastya.dto.LocationDTO;
import org.nastya.dto.LocationResponseApiDTO;
import org.nastya.exception.LocationAlreadyExistsException;
import org.nastya.model.Location;
import org.nastya.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LocationService {
    private final LocationDAO locationDAO = new LocationDAO();
    private final WeatherAPIService weatherAPIService = new WeatherAPIService();

    public void add(LocationDTO locationDTO) {
        try{
            locationDAO.save(new Location(
                    locationDTO.getName(),
                    locationDTO.getUser(),
                    locationDTO.getLatitude(),
                    locationDTO.getLongitude()));
        } catch (ConstraintViolationException e){
            throw new LocationAlreadyExistsException();
        }
    }

    public void delete(LocationDTO locationDTO) {
        Optional<Location> location = locationDAO.findByNameAndUser(locationDTO.getName(), locationDTO.getUser());
        location.ifPresent(locationDAO::delete);
    }

    public List<LocationResponseApiDTO> findUserLocations(User user) {
        return locationDAO.findAllByUser(user).stream()
                .map(location -> weatherAPIService.findLocation(location.getName()))
                .toList();
    }
}
