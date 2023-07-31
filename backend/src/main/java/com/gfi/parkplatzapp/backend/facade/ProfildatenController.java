package com.gfi.parkplatzapp.backend.facade;

import com.gfi.parkplatzapp.backend.persistence.entities.Buchung;
import com.gfi.parkplatzapp.backend.persistence.entities.Mitarbeiter;
import com.gfi.parkplatzapp.backend.service.BuchungService;
import com.gfi.parkplatzapp.backend.service.MitarbeiterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Der ProfildatenController ist für die Behandlung von Profildaten zuständig.
 * Er stellt Endpunkte bereit, um Mitarbeiterprofile zu verwalten.
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/profil")
public class ProfildatenController {

    @Autowired
    private MitarbeiterService mitarbeiterService;

    @Autowired
    private BuchungService buchungService;

    /**
     * Gibt einen Mitarbeiter anhand der Mitarbeiter-ID zurück.
     *
     * @param mitarbeiterID die ID des Mitarbeiters
     * @return der Mitarbeiter mit der entsprechenden ID
     */
    @GetMapping("/{mitarbeiterID}")
    public Mitarbeiter getMitarbeiter(@PathVariable("mitarbeiterID") Long mitarbeiterID)
    {
        return this.mitarbeiterService.getMitarbeiter(mitarbeiterID);
    }

    /**
     * Entfernt ein Kennzeichen von einem Mitarbeiter anhand der Mitarbeiter-ID und der Kennzeichen-ID.
     *
     * @param mitarbeiterID die ID des Mitarbeiters
     * @param kennzeichenID die ID des Kennzeichens, das entfernt werden soll
     * @return der aktualisierte Mitarbeiter nach dem Entfernen des Kennzeichens
     */
    @DeleteMapping(path = "/{mitarbeiterID}/kennzeichen/{kennzeichenID}")
    public Mitarbeiter deleteKennzeichenFromMitarbeiter(@PathVariable long mitarbeiterID, @PathVariable long kennzeichenID) {
        return mitarbeiterService.deleteKennzeichenFromMitarbeiter(mitarbeiterID, kennzeichenID);
    }

    /**
     * Erstellt ein neues Kennzeichen für einen Mitarbeiter anhand der Mitarbeiter-ID und des Kennzeichens.
     *
     * @param mitarbeiterID die ID des Mitarbeiters
     * @param kennzeichen das Kennzeichen, das erstellt werden soll
     * @return der aktualisierte Mitarbeiter nach dem Hinzufügen des Kennzeichens
     */
    @PostMapping(path = "/{mitarbeiterID}")
    public Mitarbeiter createKennzeichenForMitarbeiter(@PathVariable long mitarbeiterID, @RequestBody String kennzeichen)
    {
        return mitarbeiterService.createKennzeichenForMitarbeiter(mitarbeiterID, kennzeichen);
    }


}