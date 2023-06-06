package com.gfi.parkplatzapp.backend;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.ArrayList;

public class Parkflaeche {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int parkflacheID;

    public String bezeichnung;
    public ArrayList<Parkplatz> mitarbeiterList = new ArrayList<Parkplatz>();
    public Parkhaus parkhaus;

    public Parkflaeche(int parkpflacheId) {
        this.parkflacheID = parkflacheID;
    }

    public Parkflaeche(int parkflacheID, String bezeichnung, ArrayList<Parkplatz> mitarbeiterList, Parkhaus parkhaus) {
        this.parkflacheID = parkflacheID;
        this.bezeichnung = bezeichnung;
        this.mitarbeiterList = mitarbeiterList;
        this.parkhaus = parkhaus;
    }

    public int getParkflacheID() {
        return parkflacheID;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public ArrayList<Parkplatz> getMitarbeiterList() {
        return mitarbeiterList;
    }

    public void setMitarbeiterList(ArrayList<Parkplatz> mitarbeiterList) {
        this.mitarbeiterList = mitarbeiterList;
    }

    public Parkhaus getParkhaus() {
        return parkhaus;
    }

    public void setParkhaus(Parkhaus parkhaus) {
        this.parkhaus = parkhaus;
    }
}
