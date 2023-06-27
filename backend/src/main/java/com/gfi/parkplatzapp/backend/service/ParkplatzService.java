package com.gfi.parkplatzapp.backend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.gfi.parkplatzapp.backend.persistence.entities.*;
import com.gfi.parkplatzapp.backend.persistence.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ParkplatzService {

    @Autowired
    BuchungService buchenService;
    @Autowired
    PreiskategorieRepo preiskategorieRepo;
    @Autowired
    ParkplatztypRepo parkplatztypRepo;

    @Autowired
    ParkplatzRepo parkplatzRepo;

    @Autowired
    ParkflaecheRepo parkflaecheRepo;

    @Autowired
    BuchungRepo buchungRepo;
    public List<Parkplatz> saveParkplatz(Parkplatz parkplatz, Long parkflaecheID) {
        List<Parkplatz> parkplaetze = new ArrayList<>();
        boolean id = parkplatz.getParkplatzID() == null;
        parkplatz.setPreiskategorie(preiskategorieRepo.findById(parkplatz.getPreiskategorie().getKategorieID()).get());
        parkplatz.setParkplatztyp(parkplatztypRepo.findById(parkplatz.getParkplatztyp().getParkplatztypID()).get());
        parkplatzRepo.save(parkplatz);
        if(id == true) {
            Parkflaeche parkflaeche = parkflaecheRepo.findById(parkflaecheID).get();
            parkflaeche.getParkplatzList().add(parkplatz);
            parkflaecheRepo.save(parkflaeche);
        }

        return buchenService.getParkplaetzeOfParkflaeche(parkflaecheID);
    }

    public Parkplatz deleteParkplatz(Long parkplatzID) {
        Optional<Parkplatz> parkplatzOptional = parkplatzRepo.findById(parkplatzID);

        if (parkplatzOptional.isPresent()) {
            Parkplatz parkplatz = parkplatzOptional.get();

            // Setze den Parkplatz aller Buchungen auf "null"
            List<Buchung> buchungen = buchungRepo.findByParkplatz(parkplatz);
            for (Buchung buchung : buchungen) {
                buchung.setParkplatz(null);
                buchungRepo.save(buchung);
            }

           Parkflaeche parkflaeche = parkflaecheRepo.findByParkplatzList_parkplatzID(parkplatzID);
            List<Parkplatz> parkplatzList = parkflaeche.getParkplatzList();
            boolean found = false;
            int i = 0;
            while (i < parkplatzList.size()) {
                Parkplatz park = parkplatzList.get(i);
                if (park.equals(parkplatz)) {
                    parkplatzList.remove(i);
                    found = true;
                } else {
                    i++;
                }
            }

                parkflaeche.setParkplatzList(parkplatzList);

                 parkflaecheRepo.save(parkflaeche);
            // Lösche den Parkplatz
            parkplatzRepo.delete(parkplatz);

        }
       return parkplatzOptional.get();

    }

}
