package com.gfi.parkplatzapp.backend.service;

import com.gfi.parkplatzapp.backend.facade.dto.BuchungDto;
import com.gfi.parkplatzapp.backend.persistence.entities.*;
import com.gfi.parkplatzapp.backend.persistence.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Der BuchungsuebersichtService bietet verschiedene Methoden zur Verwaltung von Buchungen.
 */
@Service
public class BuchungsuebersichtService {

    @Autowired
    private BuchungRepo buchungRepo;

    @Autowired
    private ParkflaecheRepo parkflaecheRepo;

    @Autowired
    private KennzeichenRepo kennzeichenRepo;

    @Autowired
    private ParkhausRepo parkhausRepo;

    @Autowired
    private MitarbeiterRepo mitarbeiterRepo;

    /**
     * Liefert eine Liste von BuchungDto-Objekten für den angegebenen Mitarbeiter.
     *
     * @param mitarbeiterID ID des Mitarbeiters
     * @return Liste von BuchungDto-Objekten
     */
    public List<BuchungDto> getBuchungenForMitarbeiter(Long mitarbeiterID) {
        Mitarbeiter mitarbeiter = mitarbeiterRepo.findById(mitarbeiterID).get();
        List<Buchung> buchungen = buchungRepo.findByMitarbeiter(mitarbeiter, Sort.by("Datum").descending());
        List<BuchungDto> buchungDtos = new ArrayList<>();

        for (Buchung buchung : buchungen) {
            buchungDtos.add(createFromBuchung(buchung));
        }
        return buchungDtos;
    }

    /**
     * Liefert eine Liste von Kennzeichen-Objekten für den angegebenen Mitarbeiter.
     *
     * @param mitarbeiterID ID des Mitarbeiters
     * @return Liste von Kennzeichen-Objekten
     */
    public List<Kennzeichen> getKennzeichenForMitarbeiter(Long mitarbeiterID) {
        return mitarbeiterRepo.findById(mitarbeiterID).get().getKennzeichenList();
    }

    /**
     * Speichert das Kennzeichen für die angegebene Buchung und liefert eine Liste von BuchungDto-Objekten für den zugehörigen Mitarbeiter zurück.
     *
     * @param buchungID     ID der Buchung
     * @param kennzeichenID ID des Kennzeichens
     * @return Liste von BuchungDto-Objekten
     */
    public List<BuchungDto> saveKennzeichenForBuchung(Long buchungID, Long kennzeichenID) {
        Kennzeichen kennzeichen = kennzeichenRepo.findById(kennzeichenID).get();
        Buchung buchung = buchungRepo.findById(buchungID).get();
        buchung.setKennzeichen(kennzeichen);
        buchungRepo.save(buchung);
        return getBuchungenForMitarbeiter(buchung.getMitarbeiter().getMitarbeiterID());
    }

    /**
     * Löscht die angegebene Buchung für den angegebenen Mitarbeiter und liefert eine Liste von BuchungDto-Objekten zurück.
     *
     * @param mitarbeiterID ID des Mitarbeiters
     * @param buchungID     ID der zu löschenden Buchung
     * @return Liste von BuchungDto-Objekten
     */
    public List<BuchungDto> deleteBuchungFromMitarbeiter(Long mitarbeiterID, Long buchungID) {
        buchungRepo.deleteById(buchungID);
        return getBuchungenForMitarbeiter(mitarbeiterID);
    }

    /**
     * Erstellt ein BuchungDto-Objekt aus einer Buchung.
     *
     * @param buchung die Buchung
     * @return das BuchungDto-Objekt
     */
    private BuchungDto createFromBuchung(Buchung buchung) {
        Parkflaeche parkflaeche = parkflaecheRepo.findByParkplatzList_parkplatzID(buchung.getParkplatz().getParkplatzID());
        Parkhaus parkhaus = parkhausRepo.findByParkflaecheList_parkflaecheID(parkflaeche.getParkflaecheID());
        String bezeichnung = parkhaus.getBezeichnung() + "-" + parkflaeche.getBezeichnung() + "-" + buchung.getParkplatz().getNummer();
        return new BuchungDto(buchung.getBuchungID(), buchung.getDatum(), buchung.getTagespreis(), bezeichnung, buchung.getKennzeichen());
    }
}
