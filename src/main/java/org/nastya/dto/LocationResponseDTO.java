package org.nastya.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationResponseDTO {

    private String name;
    private String latitude;
    private String longitude;
    private String temp_c;
}
