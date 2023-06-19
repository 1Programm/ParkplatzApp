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

    @ManyToOne(cascade = CascadeType.ALL)
    private Parkplatztyp parkplatztyp;
    @ManyToOne(cascade = CascadeType.ALL)
    private Preiskategorie preiskategorie;
}
