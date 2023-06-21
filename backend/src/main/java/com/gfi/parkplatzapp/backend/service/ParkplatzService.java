package com.gfi.parkplatzapp.backend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.gfi.parkplatzapp.backend.persistence.entities.Mitarbeiter;
import com.gfi.parkplatzapp.backend.persistence.entities.Parkflaeche;
import com.gfi.parkplatzapp.backend.persistence.entities.Parkplatz;
import com.gfi.parkplatzapp.backend.persistence.repos.ParkflaecheRepo;
import com.gfi.parkplatzapp.backend.persistence.repos.ParkplatzRepo;
import com.gfi.parkplatzapp.backend.persistence.repos.ParkplatztypRepo;
import com.gfi.parkplatzapp.backend.persistence.repos.PreiskategorieRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

        Parkplatz parkplatz = parkplatzRepo.findById(parkplatzID)
                .orElseThrow(() -> new IllegalArgumentException("Mitarbeiter mit ID " + parkplatzID + " wurde nicht gefunden."));

        parkplatzRepo.delete(parkplatz);
        return parkplatz;
    }
}
