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
public class CurrentApiDTO {
    @JsonProperty("temp_c")
    private String tempC;

    @JsonProperty("condition")
    private ConditionApiDTO conditionApiDTO;
}
