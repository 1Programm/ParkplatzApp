package com.gfi.parkplatzapp.backend;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.ArrayList;

@Entity
public class Preiskategorie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int kategorieID;

    public String bezeichnung;
    public double preis;
    public ArrayList<Parkplatz> mitarbeiterList = new ArrayList<Parkplatz>();

    public Preiskategorie(int kategorieID) {
        this.kategorieID = kategorieID;
    }

    public Preiskategorie(int kategorieID, String bezeichnung, double preis, ArrayList<Parkplatz> mitarbeiterList) {
        this.kategorieID = kategorieID;
        this.bezeichnung = bezeichnung;
        this.preis = preis;
        this.mitarbeiterList = mitarbeiterList;
    }

    public int getKategorieID() {
        return kategorieID;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public double getPreis() {
        return preis;
    }

    public void setPreis(double preis) {
        this.preis = preis;
    }

    public ArrayList<Parkplatz> getMitarbeiterList() {
        return mitarbeiterList;
    }

    public void setMitarbeiterList(ArrayList<Parkplatz> mitarbeiterList) {
        this.mitarbeiterList = mitarbeiterList;
    }
}
