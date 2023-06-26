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
public class Mitarbeiter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long mitarbeiterID;
    private String uid;

    private String vorname;
    private String nachname;
    private String mail;
    @OneToMany
    private List<Verstoss> verstossList;
    @OneToMany
    private List<Kennzeichen> kennzeichenList;

}
