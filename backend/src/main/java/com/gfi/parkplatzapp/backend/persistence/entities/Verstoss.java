package com.gfi.parkplatzapp.backend.persistence.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Verstoss {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long meldeID;

    private Date datum;
    private String bemerkung;
}
