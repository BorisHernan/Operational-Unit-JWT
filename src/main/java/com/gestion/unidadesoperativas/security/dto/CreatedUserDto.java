package com.gestion.unidadesoperativas.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreatedUserDto {
    private String username;
    private String email;
    private String password;
}
