package com.gfi.parkplatzapp.backend.persistence.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Preiskategorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long kategorieID;

    private String bezeichnung;
    private double preis;
}
