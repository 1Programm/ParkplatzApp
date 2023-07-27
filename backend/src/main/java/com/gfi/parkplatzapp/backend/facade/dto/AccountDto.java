package com.gfi.parkplatzapp.backend.facade.dto;

import lombok.*;

import java.util.List;

/**
 * Die AccountDto-Klasse repräsentiert die Datenübertragungsobjekte für Benutzerkontoinformationen in der Parkplatzapp-Backend-Fassade.
 * Sie enthält Attribute wie die eindeutige Benutzer-ID, die Mitarbeiter-ID, den Namen des Benutzers und die Liste der Rollen.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AccountDto {

    private String uid;
    private Long mitarbeiterId;
    private String name;
    private List<String> roles;

}
