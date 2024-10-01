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
public class DayApiDTO {
    @JsonProperty("maxtemp_c")
    private String maxT;

    @JsonProperty("mintemp_c")
    private String minT;

    @JsonProperty("condition")
    private ConditionApiDTO conditionApiDTO;
}
