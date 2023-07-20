package com.gfi.parkplatzapp.backend.persistence.entities;

import com.gfi.parkplatzapp.backend.utils.AktivitaetEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Parkhaus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long parkhausID;

    private String bezeichnung;
    private String strasse;
    private int hausnummer;
    private int plz;
    private String ort;

    @Enumerated(EnumType.STRING)
    private AktivitaetEnum aktivitaet;

    @OneToMany
    private List<Parkflaeche> parkflaecheList;
}
