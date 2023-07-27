package com.gfi.parkplatzapp.backend.facade.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Die ParkflaecheAuswahlDto-Klasse repräsentiert die Datenübertragungsobjekte für die Parkflächenauswahlinformationen.
 * Auf der Home Seite wird dieses Dto zur Auswahl der Parkfläche verwendet.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParkflaecheAuswahlDto {
    private Long parkhausID;
    private String parkhausBezeichnung;
    private Long parkflaecheID;
    private String parkflaecheBezeichnung;
}
