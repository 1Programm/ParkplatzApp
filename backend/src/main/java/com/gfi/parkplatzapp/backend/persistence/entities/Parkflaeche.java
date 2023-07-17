package com.gfi.parkplatzapp.backend.persistence.entities;

import com.gfi.parkplatzapp.backend.utils.AktivitaetEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Parkflaeche {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long parkflaecheID;

    private String bezeichnung;

    @Enumerated(EnumType.STRING)
    private AktivitaetEnum aktivitaet;

    @ManyToOne
    private DBImage image;

    @ManyToOne
    private Parkhaus parkhaus;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Parkplatz> parkplatzList;

}
