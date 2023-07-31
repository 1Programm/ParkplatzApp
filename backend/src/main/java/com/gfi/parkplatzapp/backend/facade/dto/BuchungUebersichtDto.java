package com.gfi.parkplatzapp.backend.facade.dto;

import com.gfi.parkplatzapp.backend.persistence.entities.Kennzeichen;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Die BuchungUebersichtDto-Klasse repräsentiert die Datenübertragungsobjekte für die Buchungsübersichtsinformationen.
 * Diese Anzeige wird auf der Buchungsübersichtseite angezeigt
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BuchungUebersichtDto {

    private Date datum;
    private String mitarbeiterName;
    private String parkplatzKennung;
    private Double tagespreis;
    private Kennzeichen kennzeichen;

}


