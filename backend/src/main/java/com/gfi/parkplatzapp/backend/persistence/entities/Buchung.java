package com.gfi.parkplatzapp.backend.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Buchung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long buchungID;

    @Temporal(TemporalType.DATE)
    private Date datum;

    private Double tagespreis;

    @ManyToOne
    private Kennzeichen kennzeichen;

    @ManyToOne
    private Mitarbeiter mitarbeiter;
    @ManyToOne
    private Parkplatz parkplatz;
    
}