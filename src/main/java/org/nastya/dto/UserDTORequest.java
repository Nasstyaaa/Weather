package org.nastya.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTORequest {
    private String login;
    private String password;
}
