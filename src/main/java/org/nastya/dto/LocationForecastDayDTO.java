package org.nastya.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationForecastDayDTO {
    private LocationResponseApiDTO locationResponseApiDTO;
    private List<DayDTO> dayDTOList;
}
