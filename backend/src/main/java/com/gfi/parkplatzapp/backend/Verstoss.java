package com.gfi.parkplatzapp.backend;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Verstoss {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int meldeID;

    public Date datum;
    public String bemerkung;
    public Mitarbeiter mitarbeiter;

    public Verstoss(int modelID) {
        this.meldeID = modelID;
    }

    public Verstoss(int modelID, Date datum, String bemerkung, Mitarbeiter mitarbeiter) {
        this.meldeID = modelID;
        this.datum = datum;
        this.bemerkung = bemerkung;
        this.mitarbeiter = mitarbeiter;
    }

    public int getMeldeID() {
        return meldeID;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getBemerkung() {
        return bemerkung;
    }

    public void setBemerkung(String bemerkung) {
        this.bemerkung = bemerkung;
    }

    public Mitarbeiter getMitarbeiter() {
        return mitarbeiter;
    }

    public void setMitarbeiter(Mitarbeiter mitarbeiter) {
        this.mitarbeiter = mitarbeiter;
    }
}
