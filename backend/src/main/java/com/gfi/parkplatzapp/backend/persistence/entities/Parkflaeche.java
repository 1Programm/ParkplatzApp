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
public class Parkflaeche {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long parkflacheID;

    private String bezeichnung;
    @OneToMany
    private List<Parkplatz> parkplatzList;

}
