package com.gestion.unidadesoperativas.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import org.springframework.data.relational.core.mapping.Column;
import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class OperationalUnitRequestDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 8222253670338491507L;

    @Column
    private String name;
    @Column
    private String director;
    @Column
    private String telephone;
    @Column
    private String address;
    @Column
    private String department;
    @Column
    private String active;


}
