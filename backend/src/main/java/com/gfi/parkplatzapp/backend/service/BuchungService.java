package com.gfi.parkplatzapp.backend.service;

import com.gfi.parkplatzapp.backend.facade.dto.BuchungDto;
import com.gfi.parkplatzapp.backend.facade.dto.ParkflaecheAuswahlDto;
import com.gfi.parkplatzapp.backend.persistence.entities.*;
import com.gfi.parkplatzapp.backend.persistence.repos.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class BuchungService {

    @Autowired
    private ParkhausRepo parkhausRepo;

    @Autowired
    private ParkflaecheRepo parkflaecheRepo;

    @Autowired
    private BuchungRepo buchungRepo;

    @Autowired
    private MitarbeiterRepo mitarbeiterRepo;

    @Autowired
    private ParkplatzRepo parkplatzRepo;

    @Autowired
    private ParkplatztypRepo parkplatztypRepo;

    @Autowired
    private PreiskategorieRepo preiskategorieRepo;

    @Autowired
    private KennzeichenRepo kennzeichenRepo;


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

    /**
     * Speichert das Kennzeichen für die angegebene Buchung und liefert eine Liste von BuchungDto-Objekten für den zugehörigen Mitarbeiter zurück.
     *
     * @param buchungID     ID der Buchung
     * @param kennzeichenID ID des Kennzeichens
     * @return Liste von BuchungDto-Objekten
     */
    public List<BuchungDto> updateKennzeichenForBuchung(Long buchungID, Long kennzeichenID) {
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

    public List<ParkflaecheAuswahlDto> getParkflaechen() {
        List<ParkflaecheAuswahlDto> resultList = new ArrayList<>();
        Iterable<Parkhaus> parkhausIterable = parkhausRepo.findAll();

        for(Parkhaus parkhaus : parkhausIterable) {
            for(Parkflaeche parkflaeche : parkhaus.getParkflaecheList()) {
                ParkflaecheAuswahlDto parkflaecheAuswahlDto = new ParkflaecheAuswahlDto();
                parkflaecheAuswahlDto.setParkhausID(parkhaus.getParkhausID());
                parkflaecheAuswahlDto.setParkhausBezeichnung(parkhaus.getBezeichnung());
                parkflaecheAuswahlDto.setParkflaecheID(parkflaeche.getParkflaecheID());
                parkflaecheAuswahlDto.setParkflaecheBezeichnung(parkflaeche.getBezeichnung());
                resultList.add(parkflaecheAuswahlDto);
            }
        }

        return resultList;
    }

    public List<Parkplatz> getParkplaetzeOfParkflaeche(Long parkflaecheID) {
        Parkflaeche flaeche = parkflaecheRepo.findById(parkflaecheID).get();
        return flaeche.getParkplatzList();
    }

    public List<Parkplatztyp> getParkplatztyp() {
        List<Parkplatztyp> typen = new ArrayList<>();
        parkplatztypRepo.findAll().iterator().forEachRemaining(typen::add);
        return typen;
    }

    public List<Preiskategorie> getPreiskategorien() {
        List<Preiskategorie> kategorien = new ArrayList<>();
        preiskategorieRepo.findAll().iterator().forEachRemaining(kategorien::add);
        return kategorien;
    }

    public List<Parkplatz> createParkplatz(Parkplatz parkplatz) {
        List<Parkplatz> parkplaetze = new ArrayList<>();
        parkplatz.setPreiskategorie(preiskategorieRepo.findById(parkplatz.getPreiskategorie().getKategorieID()).get());
        parkplatz.setParkplatztyp(parkplatztypRepo.findById(parkplatz.getParkplatztyp().getParkplatztypID()).get());
        parkplatzRepo.save(parkplatz);
        parkplatzRepo.findAll().iterator().forEachRemaining(parkplaetze::add);
        return parkplaetze;
    }

    public void updateBuchungen(List<BuchungDto> buchungDtoList) {
        log.info("Test", buchungDtoList);
        /*buchungsList.forEach(buchungDto -> {
            Buchung buchung = new Buchung();
            buchung.setKennzeichen(buchungDto.getKennzeichen());
            buchung.setDatum(buchungDto.getDatum());
            buchung.setTagespreis(buchungDto.getTagespreis());
        });*/
    }

}
