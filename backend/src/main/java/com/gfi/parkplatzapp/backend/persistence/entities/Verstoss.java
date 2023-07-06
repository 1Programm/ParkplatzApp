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
public class Verstoss {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long meldeID;

    @Temporal(TemporalType.TIMESTAMP)
    private Date datum;
    private String bemerkung;
    private String status;
}
