package com.gfi.parkplatzapp.backend.facade.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParkhausParkflaecheDto {
    String bezeichnung;
    ParkflaecheDto[] parkflaecheList;

    @Getter
    @Setter
    public class ParkflaecheDto {
        Long parkflaecheID;
        String bezeichnung;
    }
}


