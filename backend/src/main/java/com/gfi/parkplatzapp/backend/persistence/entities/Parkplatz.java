package com.gfi.parkplatzapp.backend.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Parkplatz {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long parkplatzID;

    private String nummer;
    private String koordinate;

    @ManyToOne
    private Parkplatztyp parkplatztyp;
    @ManyToOne
    private Preiskategorie preiskategorie;
}
