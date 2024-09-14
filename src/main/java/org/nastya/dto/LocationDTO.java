package org.nastya.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nastya.model.User;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDTO {

    private String name;
    private User user;
    private BigDecimal latitude;
    private BigDecimal longitude;
}
