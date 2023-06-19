package com.gfi.parkplatzapp.backend.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Parkplatztyp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long parkplatztypID;

    private String bezeichnung;
    private String beschreibung;

}
