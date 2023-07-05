package com.gfi.parkplatzapp.backend.persistence.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Parkplatztyp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long parkplatztypID;

    private String bezeichnung;
    private String beschreibung;

}
