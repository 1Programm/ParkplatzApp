package com.gfi.parkplatzapp.backend;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.ArrayList;
import java.util.Date;

@Entity
public class Buchung {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int buchungID;

    public Date datum;
    public Double tagespreis;
    public ArrayList<Mitarbeiter> mitarbeiterList = new ArrayList<Mitarbeiter>();
    public ArrayList<Parkplatz> parkplatzList = new ArrayList<Parkplatz>();


    public Buchung(int buchungID) {
        this.buchungID = buchungID;
    }

    public Buchung(int buchungID, Date datum, Double tagespreis, ArrayList<Mitarbeiter> mitarbeiterList, ArrayList<Parkplatz> parkplatzList) {
        this.buchungID = buchungID;
        this.datum = datum;
        this.tagespreis = tagespreis;
        this.mitarbeiterList = mitarbeiterList;
        this.parkplatzList = parkplatzList;
    }

    public ArrayList<Mitarbeiter> getMitarbeiterList() {
        return mitarbeiterList;
    }

    public void setMitarbeiterList(ArrayList<Mitarbeiter> mitarbeiterList) {
        this.mitarbeiterList = mitarbeiterList;
    }

    public ArrayList<Parkplatz> getParkplatzList() {
        return parkplatzList;
    }

    public void setParkplatzList(ArrayList<Parkplatz> parkplatzList) {
        this.parkplatzList = parkplatzList;
    }

    public int getBuchungID() {
        return buchungID;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }


    public Double getTagespreis() {
        return tagespreis;
    }

    public void setTagespreis(Double tagespreis) {
        this.tagespreis = tagespreis;
    }

}