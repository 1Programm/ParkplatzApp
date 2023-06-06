package com.gfi.parkplatzapp.backend;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Kennzeichen {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int kennzeichenID;
    public String kennzeichen;
    public Mitarbeiter mitarbeiter;

    public Kennzeichen(int kennzeichenID) {
        this.kennzeichenID = kennzeichenID;
    }

    public Kennzeichen(int kennzeichenID, String kennzeichen, Mitarbeiter mitarbeiter) {
        this.kennzeichenID = kennzeichenID;
        this.kennzeichen = kennzeichen;
        this.mitarbeiter = mitarbeiter;
    }

    public int getKennzeichenID() {
        return kennzeichenID;
    }

    public String getKennzeichen() {
        return kennzeichen;
    }

    public void setKennzeichen(String kennzeichen) {
        this.kennzeichen = kennzeichen;
    }

    public Mitarbeiter getMitarbeiter() {
        return mitarbeiter;
    }

    public void setMitarbeiter(Mitarbeiter mitarbeiter) {
        this.mitarbeiter = mitarbeiter;
    }
}
