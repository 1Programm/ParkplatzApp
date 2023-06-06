package com.gfi.parkplatzapp.backend;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.ArrayList;

@Entity
public class Mitarbeiter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int mitarbeiterID;

    public String name;
    public String mail;
    public Buchung buchung;
    public ArrayList<Verstoss> verstossList = new ArrayList<Verstoss>();
    public ArrayList<Kennzeichen> kennzeichenList = new ArrayList<Kennzeichen>();

    public Mitarbeiter(int mitarbeiterID) {
        this.mitarbeiterID = mitarbeiterID;
    }

    public Mitarbeiter(int mitarbeiterID, String name, String mail, Buchung buchung, ArrayList<Verstoss> verstossList, ArrayList<Kennzeichen> kennzeichenList) {
        this.mitarbeiterID = mitarbeiterID;
        this.name = name;
        this.mail = mail;
        this.buchung = buchung;
        this.verstossList = verstossList;
        this.kennzeichenList = kennzeichenList;
    }

    public int getMitarbeiterID() {
        return mitarbeiterID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Buchung getBuchung() {
        return buchung;
    }

    public void setBuchung(Buchung buchung) {
        this.buchung = buchung;
    }

    public ArrayList<Verstoss> getVerstossList() {
        return verstossList;
    }

    public void setVerstossList(ArrayList<Verstoss> verstossList) {
        this.verstossList = verstossList;
    }

    public ArrayList<Kennzeichen> getKennzeichenList() {
        return kennzeichenList;
    }

    public void setKennzeichenList(ArrayList<Kennzeichen> kennzeichenList) {
        this.kennzeichenList = kennzeichenList;
    }
}
