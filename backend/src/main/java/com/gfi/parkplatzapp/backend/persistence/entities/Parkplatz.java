package com.gfi.parkplatzapp.backend.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Parkplatz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long parkplatzID;

    private String nummer;
    private int xKoordinate;
    private int yKoordinate;

    @ManyToOne
    private Parkplatztyp parkplatztyp;
    @ManyToOne
    private Preiskategorie preiskategorie;
}
