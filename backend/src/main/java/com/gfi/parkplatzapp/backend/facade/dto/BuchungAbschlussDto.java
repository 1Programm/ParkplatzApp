package com.gfi.parkplatzapp.backend.facade.dto;

import com.gfi.parkplatzapp.backend.persistence.entities.Kennzeichen;
import com.gfi.parkplatzapp.backend.persistence.entities.Parkplatz;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BuchungAbschlussDto {

    private Date datum;
    private Long mitarbeiterID;
    private Kennzeichen kennzeichen;
    private Parkplatz parkplatz;

}
