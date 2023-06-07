package com.gfi.parkplatzapp.backend.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Parkhaus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long parkhausID;

    private String bezeichnung;
    private String straße;
    private int hausnummer;
    private int plz;
    private String ort;

    @OneToMany
    private List<Parkflaeche> parkflaecheList;
}
