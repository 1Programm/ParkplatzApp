package com.gfi.parkplatzapp.backend.service;

import com.gfi.parkplatzapp.backend.facade.dto.*;
import com.gfi.parkplatzapp.backend.persistence.entities.*;
import com.gfi.parkplatzapp.backend.persistence.repos.*;
import com.gfi.parkplatzapp.backend.utils.AktivitaetEnum;
import com.gfi.parkplatzapp.backend.utils.StatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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
    public List<BuchungDetailsDto> getBuchungenForMitarbeiter(Long mitarbeiterID) {
        Mitarbeiter mitarbeiter = mitarbeiterRepo.findById(mitarbeiterID).get();
        List<Buchung> buchungen = buchungRepo.findByMitarbeiter(mitarbeiter, Sort.by("Datum").descending());
        List<BuchungDetailsDto> buchungDtos = new ArrayList<>();

        for (Buchung buchung : buchungen) {
            buchungDtos.add(createFromBuchung(buchung));
        }
        return buchungDtos;
    }

    /**
     * Liefert alle Buchungen (absteigend nach dem Datum sortiert) zurück.
     * @return Liste aller Buchungen als ÜbersichtDto
     */
    public List<BuchungUebersichtDto> getAllBuchungen(){
        return buchungRepo.findAll(Sort.by("datum").descending())
                .stream()
                .map(this::createUebersichtFromBuchung)
                .collect(Collectors.toList());
    }

    /**
     * Erstellt ein Überischt Dto zu einer Buchung
     * @return ein Buchungs-Übersicht-Dto
     */
    private BuchungUebersichtDto createUebersichtFromBuchung(Buchung buchung){
        Mitarbeiter mitarbeiter = buchung.getMitarbeiter();
        String name = mitarbeiter.getVorname() + " " + mitarbeiter.getNachname();

        Parkflaeche parkflaeche = parkflaecheRepo.findByParkplatzList_parkplatzID(buchung.getParkplatz().getParkplatzID());
        Parkhaus parkhaus = parkhausRepo.findByParkflaecheList_parkflaecheID(parkflaeche.getParkflaecheID());
        String bezeichnung = parkhaus.getBezeichnung() + "-" + parkflaeche.getBezeichnung() + "-" + buchung.getParkplatz().getNummer();

        return new BuchungUebersichtDto(buchung.getDatum(), name, bezeichnung, buchung.getTagespreis(), buchung.getKennzeichen());
    }

    /**
     * Liefert alle Buchungen, gemappt zu ihren Buchungstagen.
     * @return Liste an gemappten Buchungen
     */
    public List<BuchungUebersichtMappedDto<Date>> getAllBuchungenMappedByDate(){
        List<BuchungUebersichtDto> buchungen = getAllBuchungen();

        Map<Date, List<BuchungUebersichtDto>> map = new HashMap<>();
        for(BuchungUebersichtDto dto : buchungen){
            map.computeIfAbsent(dto.getDatum(), d -> new ArrayList<>()).add(dto);
        }

        return map.entrySet()
                .stream()
                .map(e -> new BuchungUebersichtMappedDto<>(e.getKey(), e.getValue()))
                .sorted((b1, b2) -> b2.getKey().compareTo(b1.getKey()))
                .collect(Collectors.toList());
    }

    /**
     * Liefert alle Buchungen, gemappt zu ihren Mitarbeitern.
     * @return Liste an gemappten Buchungen
     */
    public List<BuchungUebersichtMappedDto<String>> getAllBuchungenMappedByMitarbeiter(){
        List<BuchungUebersichtDto> buchungen = getAllBuchungen();

        Map<String, List<BuchungUebersichtDto>> map = new HashMap<>();
        for(BuchungUebersichtDto dto : buchungen){
            map.computeIfAbsent(dto.getMitarbeiterName(), d -> new ArrayList<>()).add(dto);
        }

        return map.entrySet()
                .stream()
                .map(e -> new BuchungUebersichtMappedDto<>(e.getKey(), e.getValue()))
                .sorted(Comparator.comparing(BuchungUebersichtMappedDto::getKey))
                .collect(Collectors.toList());
    }

    /**
     * Erstellt ein BuchungDto-Objekt aus einer Buchung.
     *
     * @param buchung die Buchung
     * @return das BuchungDto-Objekt
     */
    private BuchungDetailsDto createFromBuchung(Buchung buchung) {
        Parkflaeche parkflaeche = parkflaecheRepo.findByParkplatzList_parkplatzID(buchung.getParkplatz().getParkplatzID());
        Parkhaus parkhaus = parkhausRepo.findByParkflaecheList_parkflaecheID(parkflaeche.getParkflaecheID());
        String bezeichnung = parkhaus.getBezeichnung() + "-" + parkflaeche.getBezeichnung() + "-" + buchung.getParkplatz().getNummer();
        return new BuchungDetailsDto(buchung.getBuchungID(), buchung.getDatum(), buchung.getTagespreis(), bezeichnung, buchung.getKennzeichen());
    }

    /**
     * Speichert das Kennzeichen für die angegebene Buchung und liefert eine Liste von BuchungDto-Objekten für den zugehörigen Mitarbeiter zurück.
     *
     * @param buchungID     ID der Buchung
     * @param kennzeichenID ID des Kennzeichens
     * @return Liste von BuchungDto-Objekten
     */
    public List<BuchungDetailsDto> updateKennzeichenForBuchung(Long buchungID, Long kennzeichenID) {
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
    public List<BuchungDetailsDto> deleteBuchungFromMitarbeiter(Long mitarbeiterID, Long buchungID) {
        buchungRepo.deleteById(buchungID);
        return getBuchungenForMitarbeiter(mitarbeiterID);
    }

    /**
     * Gibt alle Parkflaechen als ParkflaecheAswahlDto zurück.
     * @return Liste an Parkflächen-Auswahl-Dto
     */
    public List<ParkflaecheAuswahlDto> getParkflaechen() {
        List<ParkflaecheAuswahlDto> resultList = new ArrayList<>();
        Iterable<Parkhaus> parkhausIterable = parkhausRepo.findAllByAktivitaet(AktivitaetEnum.AKTIV);

        for (Parkhaus parkhaus : parkhausIterable) {

            for (Parkflaeche parkflaeche : parkhaus.getParkflaecheList()) {
                if(parkflaeche.getAktivitaet() == AktivitaetEnum.INAKTIV){
                    continue;
                }
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

    /**
     * Gibt die Adresse zu einem Parkhaus als Dto zurück.
     * @param id die ID des Parkhauses
     * @return ein Parkhaus-Adresse-Dto.
     */
    public ParkhausAdresseDto getParkhausAdresse(long id){
        Parkhaus parkhaus = parkhausRepo.findById(id)
                .orElseThrow(() -> new IllegalStateException("Parkhaus [" + id + "] should be found!"));

        return ParkhausAdresseDto.createFromParkhaus(parkhaus);
    }

    /**
     * Gibt Parkplätze zu einer Parkfläche zurück, die je nachdem ob sie an dem übergebenen Datum bereits belegt sind
     * als 'BELEGT' oder 'FREI' gekennzeichnet werden.
     * @param parkflaecheID die Parkfläche, dessen Parkplätze zurück gegeben werden sollen
     * @param p_date das Datum (in dem Format 'dd.MM.yyyy'), an hand welchem geguckt wird, ob ein Parkplatz belegt ist.
     * @return Liste an Parkplätzen mit Status, ob sie belegt sind oder nicht.
     */
    public List<ParkplatzMitStatusDto> getParkplaetzeOfParkflaecheAndDate(Long parkflaecheID, String p_date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date();
        Parkflaeche flaeche = parkflaecheRepo.findByAktivitaetAndParkflaecheID(AktivitaetEnum.AKTIV, parkflaecheID);
        List<Parkplatz> parkplaetze = flaeche.getParkplatzList();
        List<ParkplatzMitStatusDto> res = new ArrayList<>();
        try {
            date = dateFormat.parse(p_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (Parkplatz parkplatz : parkplaetze) {
            if(parkplatz.getAktivitaet() == AktivitaetEnum.INAKTIV){
                continue;
            }
            StatusEnum status = isBuchungVorhanden(parkplatz, date) ? StatusEnum.BELEGT : StatusEnum.FREI;
            res.add(new ParkplatzMitStatusDto(status, parkplatz));

        }
        return res;
    }

    /**
     * Testet, ob eine Buchung an einem Datum vorhanden ist.
     */
    private boolean isBuchungVorhanden(Parkplatz parkplatz, Date date) {
        List<Buchung> buchungen = new ArrayList<>();
        this.buchungRepo.findAll().iterator().forEachRemaining(buchungen::add);
        for (Buchung b : buchungen) {
            if (b.getDatum().compareTo(date) == 0 && b.getParkplatz().getParkplatzID() == parkplatz.getParkplatzID())
                return true;
        }
        return false;
    }

    /**
     * Gibt die Parkplätze einer Parkfläche zurück.
     * @param parkflaecheID die Parkflächen ID
     * @return Liste an Parkplätze
     */
    public List<Parkplatz> getParkplaetzeOfParkflaeche(Long parkflaecheID) {
        Parkflaeche flaeche = parkflaecheRepo.findById(parkflaecheID).get();
        if(flaeche.getAktivitaet() == AktivitaetEnum.INAKTIV){
            return new ArrayList<>();
        }
        return flaeche.getParkplatzList()
                .stream()
                .filter(parkplatz -> parkplatz.getAktivitaet() == AktivitaetEnum.AKTIV)
                .collect(Collectors.toList());
    }

    /**
     * Gibt alle Parkplatztypen zurück.
     * @return Liste an Parkplatztypen.
     */
    public List<Parkplatztyp> getParkplatztyp() {
        List<Parkplatztyp> typen = new ArrayList<>();
        parkplatztypRepo.findAll().iterator().forEachRemaining(typen::add);
        return typen;
    }

    /**
     * Gibt alle Preiskategorien zurück.
     * @return Liste an Preiskategorien.
     */
    public List<Preiskategorie> getPreiskategorien() {
        List<Preiskategorie> kategorien = new ArrayList<>();
        preiskategorieRepo.findAll().iterator().forEachRemaining(kategorien::add);
        return kategorien;
    }

    /**
     * Schließt alle übergebenen Buchungen ab und speichert sie zu einem Mitarbeiter ab.
     * @param buchungen Liste aller neuen Buchungen.
     */
    public void schliesseBuchungAb(BuchungAbschlussDto[] buchungen){
        if(buchungen == null || buchungen.length == 0) return;

        Long mitarbeiterID = buchungen[0].getMitarbeiterID();
        Mitarbeiter mitarbeiter = mitarbeiterRepo.findById(mitarbeiterID)
                .orElseThrow(() -> new IllegalStateException("Mitarbeiter [" + mitarbeiterID + "] should be found!"));

        for(BuchungAbschlussDto abschlussDto : buchungen){
            Buchung newBuchung = new Buchung();
            newBuchung.setDatum(abschlussDto.getDatum());

            Long kennzeichenID = abschlussDto.getKennzeichen().getKennzeichenID();
            Kennzeichen kennzeichen = kennzeichenRepo.findById(kennzeichenID)
                    .orElseThrow(() -> new IllegalStateException("Kennzeichen [" + kennzeichenID + "] should be found!"));

            Long parkplatzID = abschlussDto.getParkplatz().getParkplatzID();
            Parkplatz parkplatz = parkplatzRepo.findById(parkplatzID)
                    .orElseThrow(() -> new IllegalStateException("Parkplatz [" + parkplatzID + "] should be found!"));


            double tagespreis = calculateTagespreis(abschlussDto.getParkplatz());
            newBuchung.setTagespreis(tagespreis);
            newBuchung.setKennzeichen(kennzeichen);
            newBuchung.setMitarbeiter(mitarbeiter);
            newBuchung.setParkplatz(parkplatz);

            buchungRepo.save(newBuchung);
        }

        log.info("{}", (Object) buchungen);
    }

    /**
     * Platzhaltermethode, falls der Preis in Zukunft anders berechnet werden soll.
     * Gibt den aktuellen Tagespreis zu einem Parkplatz zurück.
     * @param p der Parkplatz.
     * @return gibt einen Tagespreis als Dezimalzahl zurück.
     */
    public double calculateTagespreis(Parkplatz p){
        return p.getPreiskategorie().getPreis();
    }

}
