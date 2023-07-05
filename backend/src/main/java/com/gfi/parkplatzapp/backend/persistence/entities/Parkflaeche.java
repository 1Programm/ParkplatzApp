package com.gfi.parkplatzapp.backend.persistence.entities;

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

    @ManyToOne
    private DBImage image;

    @OneToMany
    private List<Parkplatz> parkplatzList;

}
