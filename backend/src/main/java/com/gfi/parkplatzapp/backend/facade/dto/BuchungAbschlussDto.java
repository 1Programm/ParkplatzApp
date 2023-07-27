package com.gfi.parkplatzapp.backend.facade.dto;

import com.gfi.parkplatzapp.backend.persistence.entities.Kennzeichen;
import com.gfi.parkplatzapp.backend.persistence.entities.Parkplatz;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Die BuchungAbschlussDto-Klasse repräsentiert die Datenübertragungsobjekte für die Buchungsabschlussinformationen.
 * Das Frontend schickt eine Liste von Buchungsabschlüssen an das Backend.
 */
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
