package com.gfi.parkplatzapp.backend.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Preiskategorie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long kategorieID;

    private String bezeichnung;
    private double preis;
}
