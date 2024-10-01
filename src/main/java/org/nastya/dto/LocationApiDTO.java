package org.nastya.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationApiDTO {
    @JsonProperty("name")
    private String name;

    @JsonProperty("lat")
    private String latitude;

    @JsonProperty("lon")
    private String longitude;
}
