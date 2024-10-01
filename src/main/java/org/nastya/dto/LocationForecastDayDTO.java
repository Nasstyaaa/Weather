package org.nastya.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationForecastDayDTO {
    @JsonProperty("location")
    private LocationApiDTO location;

    @JsonProperty("current")
    private CurrentApiDTO current;

    @JsonProperty("forecast")
    private ForecastApiDTO forecast;
}
