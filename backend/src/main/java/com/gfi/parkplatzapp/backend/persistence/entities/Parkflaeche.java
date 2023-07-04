package com.gfi.parkplatzapp.backend.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Parkflaeche {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long parkflaecheID;

    private String bezeichnung;

    @ManyToOne
    private DBImage image;

    @OneToMany
    private List<Parkplatz> parkplatzList;

}
