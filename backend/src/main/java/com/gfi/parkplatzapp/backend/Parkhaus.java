package com.gfi.parkplatzapp.backend;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.ArrayList;

@Entity
public class Parkhaus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int parkhausID;

    public String bezeichnung;
    public String straße;
    public int hausnummer;
    public int plz;
    public String ort;

    public ArrayList<Parkflaeche> parkflaecheList = new ArrayList<Parkflaeche>();

    public Parkhaus(int parkhausID) {
        this.parkhausID = parkhausID;
    }

    public Parkhaus(int parkhausID, String bezeichnung, String straße, int hausnummer, int plz, String ort, ArrayList<Parkflaeche> parkflaecheList) {
        this.parkhausID = parkhausID;
        this.bezeichnung = bezeichnung;
        this.straße = straße;
        this.hausnummer = hausnummer;
        this.plz = plz;
        this.ort = ort;
        this.parkflaecheList = parkflaecheList;
    }

    public int getParkhausID() {
        return parkhausID;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public String getStraße() {
        return straße;
    }

    public void setStraße(String straße) {
        this.straße = straße;
    }

    public int getHausnummer() {
        return hausnummer;
    }

    public void setHausnummer(int hausnummer) {
        this.hausnummer = hausnummer;
    }

    public int getPlz() {
        return plz;
    }

    public void setPlz(int plz) {
        this.plz = plz;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public ArrayList<Parkflaeche> getParkflaecheList() {
        return parkflaecheList;
    }

    public void setParkflaecheList(ArrayList<Parkflaeche> parkflaecheList) {
        this.parkflaecheList = parkflaecheList;
    }
}
