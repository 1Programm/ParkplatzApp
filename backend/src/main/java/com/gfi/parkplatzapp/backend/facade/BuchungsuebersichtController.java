package com.gfi.parkplatzapp.backend.facade;


import com.gfi.parkplatzapp.backend.facade.dto.BuchungDetailsDto;
import com.gfi.parkplatzapp.backend.facade.dto.BuchungUebersichtDto;
import com.gfi.parkplatzapp.backend.facade.dto.BuchungUebersichtMappedDto;
import com.gfi.parkplatzapp.backend.persistence.entities.Kennzeichen;
import com.gfi.parkplatzapp.backend.service.BuchungService;
import com.gfi.parkplatzapp.backend.service.MitarbeiterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/buchungen")
public class BuchungsuebersichtController {

    @Autowired
    private BuchungService buchungService;

    @Autowired
    private MitarbeiterService mitarbeiterService;

    /**
     * Gibt eine Liste von Buchung-DTOs für einen bestimmten Mitarbeiter zurück.
     *
     * @param mitarbeiterID Die ID des Mitarbeiters.
     * @return Die Liste von Buchung-DTOs als List<BuchungDetailsDto>.
     */
    @GetMapping("/{mitarbeiterID}")
    public List<BuchungDetailsDto> getBuchungenForMitarbeiter(@PathVariable("mitarbeiterID") Long mitarbeiterID) {
        return this.buchungService.getBuchungenForMitarbeiter(mitarbeiterID);
    }

    /**
     * Gibt eine Liste von Buchung-Uebersicht-DTOs für alle Buchungen zurück.
     *
     * @return Die Liste von Buchung-Uebersicht-DTOs als List<BuchungUebersichtDto>.
     */
    @GetMapping("/all")
    public List<BuchungUebersichtDto> getAllBuchungen() {
        return this.buchungService.getAllBuchungen();
    }

    /**
     * Gibt eine Liste von Buchung-Uebersicht-Mapped-DTOs zurück, gruppiert nach dem Buchungsdatum.
     *
     * @return Die Liste von Buchung-Uebersicht-Mapped-DTOs als List<BuchungUebersichtMappedDto<Date>>.
     */
    @GetMapping("/all/datum")
    public List<BuchungUebersichtMappedDto<Date>> getAllBuchungenMappedByDate() {
        return this.buchungService.getAllBuchungenMappedByDate();
    }

    /**
     * Gibt eine Liste von Buchung-Uebersicht-Mapped-DTOs zurück, gruppiert nach dem Mitarbeiternamen.
     *
     * @return Die Liste von Buchung-Uebersicht-Mapped-DTOs als List<BuchungUebersichtMappedDto<String>>.
     */
    @GetMapping("/all/mitarbeiter")
    public List<BuchungUebersichtMappedDto<String>> getAllBuchungenMappedByMitarbeiter() {
        return this.buchungService.getAllBuchungenMappedByMitarbeiter();
    }

    /**
     * Gibt eine Liste von Kennzeichen für einen bestimmten Mitarbeiter zurück.
     *
     * @param mitarbeiterID Die ID des Mitarbeiters.
     * @return Die Liste von Kennzeichen als List<Kennzeichen>.
     */
    @GetMapping("/{mitarbeiterID}/kennzeichen")
    public List<Kennzeichen> getKennzeichenForMitarbeiter(@PathVariable("mitarbeiterID") Long mitarbeiterID) {
        return this.mitarbeiterService.getKennzeichenForMitarbeiter(mitarbeiterID);
    }

    /**
     * Speichert ein Kennzeichen für eine Buchung.
     *
     * @param buchungID     Die ID der Buchung.
     * @param kennzeichenID Die ID des Kennzeichens.
     * @return Die Liste von aktualisierten Buchung-DTOs als List<BuchungDetailsDto>.
     */
    @PostMapping("/{buchungID}/kennzeichen/{kennzeichenID}")
    public List<BuchungDetailsDto> saveKennzeichenForBuchung(@PathVariable("buchungID") Long buchungID, @PathVariable("kennzeichenID") Long kennzeichenID) {
        return this.buchungService.updateKennzeichenForBuchung(buchungID, kennzeichenID);
    }

    /**
     * Löscht eine Buchung für einen bestimmten Mitarbeiter.
     *
     * @param mitarbeiterID Die ID des Mitarbeiters.
     * @param buchungID     Die ID der zu löschenden Buchung.
     * @return Die Liste von Buchung-DTOs nach der Löschung als List<BuchungDetailsDto>.
     */
    @DeleteMapping(path = "/{mitarbeiterID}/buchung/{buchungID}")
    public List<BuchungDetailsDto> deleteBuchungFromMitarbeiter(@PathVariable Long mitarbeiterID, @PathVariable Long buchungID) {
        return buchungService.deleteBuchungFromMitarbeiter(mitarbeiterID, buchungID);
    }
}
