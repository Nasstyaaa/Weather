package org.nastya.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DayDTO {
    private String date;
    private String max_temp;
    private String min_temp;
    private String icon;
}
