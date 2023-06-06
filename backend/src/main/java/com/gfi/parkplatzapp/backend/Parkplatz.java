package com.gfi.parkplatzapp.backend;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Parkplatz {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int parkplatzID;

    public String number;
    public String koordinate;

    public Parkplatztyp parkplatztyp;
    public Buchung buchung;
    public Preiskategorie preiskategorie;
    public Parkflaeche parkflaeche;

    public Parkplatz(int parkplatzID) {
        this.parkplatzID = parkplatzID;
    }

    public Parkplatz(int parkplatzID, String number, String koordinate, Parkplatztyp parkplatztyp, Buchung buchung, Preiskategorie preiskategorie, Parkflaeche parkflaeche) {
        this.parkplatzID = parkplatzID;
        this.number = number;
        this.koordinate = koordinate;
        this.parkplatztyp = parkplatztyp;
        this.buchung = buchung;
        this.preiskategorie = preiskategorie;
        this.parkflaeche = parkflaeche;
    }

    public int getParkplatzID() {
        return parkplatzID;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getKoordinate() {
        return koordinate;
    }

    public void setKoordinate(String koordinate) {
        this.koordinate = koordinate;
    }

    public Parkplatztyp getParkplatztyp() {
        return parkplatztyp;
    }

    public void setParkplatztyp(Parkplatztyp parkplatztyp) {
        this.parkplatztyp = parkplatztyp;
    }

    public Buchung getBuchung() {
        return buchung;
    }

    public void setBuchung(Buchung buchung) {
        this.buchung = buchung;
    }

    public Preiskategorie getPreiskategorie() {
        return preiskategorie;
    }

    public void setPreiskategorie(Preiskategorie preiskategorie) {
        this.preiskategorie = preiskategorie;
    }

    public Parkflaeche getParkflaeche() {
        return parkflaeche;
    }

    public void setParkflaeche(Parkflaeche parkflaeche) {
        this.parkflaeche = parkflaeche;
    }
}
