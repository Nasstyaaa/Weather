package org.nastya.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationResponseApiDTO {

    @JsonProperty("location")
    private LocationApiDTO locationApiDTO;

    @JsonProperty("current")
    private CurrentApiDTO currentApiDTO;
}
