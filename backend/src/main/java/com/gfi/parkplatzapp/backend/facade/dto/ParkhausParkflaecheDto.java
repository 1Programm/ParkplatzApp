package com.gfi.parkplatzapp.backend.facade.dto;

import com.gfi.parkplatzapp.backend.persistence.entities.DBImage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParkhausParkflaecheDto {
    String bezeichnung;
    Long parkhausID;
    ParkflaecheDto[] parkflaecheList;

    @Getter
    @Setter
    public static class ParkflaecheDto {
        Long parkflaecheID;
        String bezeichnung;
        DBImage image;
    }
}


