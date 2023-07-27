package com.gfi.parkplatzapp.backend.facade.dto;

import com.gfi.parkplatzapp.backend.persistence.entities.Kennzeichen;
import com.gfi.parkplatzapp.backend.persistence.entities.Mitarbeiter;
import com.gfi.parkplatzapp.backend.persistence.entities.Parkplatz;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Die BuchungDetailsDto-Klasse repräsentiert die Datenübertragungsobjekte für die Buchungsdetailsinformationen.
 * Das Dto dient der Repräsentation im Warenkorb
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BuchungDetailsDto {
    private Long buchungID;

    private Date datum;
    private Double tagespreis;
    private String parkplatzKennung;
    private Kennzeichen kennzeichen;
}


