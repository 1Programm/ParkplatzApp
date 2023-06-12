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
public class Parkplatztyp {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long parkplatztypID;

    private String bezeichnung;
    private String beschreibung;

}
