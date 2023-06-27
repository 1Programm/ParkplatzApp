package com.gfi.parkplatzapp.backend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.gfi.parkplatzapp.backend.persistence.entities.*;
import com.gfi.parkplatzapp.backend.persistence.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ParkplatzService {

    @Autowired
    private BuchungService buchenService;

    @Autowired
    private PreiskategorieRepo preiskategorieRepo;

    @Autowired
    private ParkplatztypRepo parkplatztypRepo;

    @Autowired
    private ParkplatzRepo parkplatzRepo;

    @Autowired
    private ParkflaecheRepo parkflaecheRepo;

    @Autowired
    private BuchungRepo buchungRepo;

    /**
     * Speichert einen Parkplatz und verknüpft ihn mit einer Parkfläche, falls die Parkplatz-ID null ist.
     *
     * @param parkplatz     Der zu speichernde Parkplatz.
     * @param parkflaecheID Die ID der Parkfläche, mit der der Parkplatz verknüpft werden soll.
     * @return Die aktualisierte Liste der Parkplätze für die Parkfläche.
     * @throws NoSuchElementException, wenn die Preiskategorie oder der Parkplatztyp mit den angegebenen IDs nicht gefunden wurden.
     */
    public List<Parkplatz> saveParkplatz(Parkplatz parkplatz, Long parkflaecheID) throws NoSuchElementException {

        parkplatz.setPreiskategorie(preiskategorieRepo.findById(parkplatz.getPreiskategorie().getKategorieID())
                .orElseThrow(NoSuchElementException::new));

        parkplatz.setParkplatztyp(parkplatztypRepo.findById(parkplatz.getParkplatztyp().getParkplatztypID())
                .orElseThrow(NoSuchElementException::new));

        parkplatzRepo.save(parkplatz);

        if (parkplatz.getParkplatzID() == null) {
            Parkflaeche parkflaeche = parkflaecheRepo.findById(parkflaecheID)
                    .orElseThrow(NoSuchElementException::new);
            parkflaeche.getParkplatzList().add(parkplatz);

            parkflaecheRepo.save(parkflaeche);
        }
        return buchenService.getParkplaetzeOfParkflaeche(parkflaecheID);
    }

    /**
     * Löscht einen Parkplatz anhand seiner ID.
     *
     * @param parkplatzID Die ID des zu löschenden Parkplatzes.
     * @return Der gelöschte Parkplatz.
     */
    public Parkplatz deleteParkplatz(Long parkplatzID) {
        Optional<Parkplatz> parkplatzOptional = parkplatzRepo.findById(parkplatzID);

        if (parkplatzOptional.isPresent()) {
            Parkplatz parkplatz = parkplatzOptional.get();

            // Setze den Parkplatz aller zugehörigen Buchungen auf null
            List<Buchung> buchungen = buchungRepo.findByParkplatz(parkplatz);
            for (Buchung buchung : buchungen) {
                buchung.setParkplatz(null);
            }
            buchungRepo.saveAll(buchungen);

            Parkflaeche parkflaeche = parkflaecheRepo.findByParkplatzList_parkplatzID(parkplatzID);
            List<Parkplatz> parkplatzList = parkflaeche.getParkplatzList();
            parkplatzList.remove(parkplatz);

            parkflaecheRepo.save(parkflaeche);

            // Lösche den Parkplatz
            parkplatzRepo.delete(parkplatz);
        }

        return parkplatzOptional.get();
    }
}
