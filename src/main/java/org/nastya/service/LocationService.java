package org.nastya.service;

import org.nastya.dao.LocationDAO;
import org.nastya.dto.LocationDTO;
import org.nastya.model.Location;

import java.util.Optional;

public class LocationService {
    private final LocationDAO locationDAO = new LocationDAO();

    public void process(LocationDTO locationDTO) {
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
}
