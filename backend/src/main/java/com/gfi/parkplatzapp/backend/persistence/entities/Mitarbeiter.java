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
public class Mitarbeiter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long mitarbeiterID;

    private String name;
    private String mail;
    @OneToMany
    private ArrayList<Verstoss> verstossList;
    @OneToMany
    private ArrayList<Kennzeichen> kennzeichenList;

}
