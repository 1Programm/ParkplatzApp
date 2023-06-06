package com.gfi.parkplatzapp.backend;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.ArrayList;

@Entity
public class Parkplatztyp {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int parkplatztypID;

    public String bezeichnung;
    public String beschreibung;
    public ArrayList<Parkplatz> mitarbeiterList = new ArrayList<Parkplatz>();

    public Parkplatztyp(int parkplatztyp) {
        this.parkplatztypID = parkplatztyp;
    }

    public Parkplatztyp(int parkplatztyp, String bezeichnung, String beschreibung, ArrayList<Parkplatz> mitarbeiterList) {
        this.parkplatztypID = parkplatztyp;
        this.bezeichnung = bezeichnung;
        this.beschreibung = beschreibung;
        this.mitarbeiterList = mitarbeiterList;
    }

    public int getParkplatztypID() {
        return parkplatztypID;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public ArrayList<Parkplatz> getMitarbeiterList() {
        return mitarbeiterList;
    }

    public void setMitarbeiterList(ArrayList<Parkplatz> mitarbeiterList) {
        this.mitarbeiterList = mitarbeiterList;
    }
}
