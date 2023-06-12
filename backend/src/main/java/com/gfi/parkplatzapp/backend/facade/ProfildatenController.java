package com.gfi.parkplatzapp.backend.facade;

import com.gfi.parkplatzapp.backend.persistence.entities.Kennzeichen;
import com.gfi.parkplatzapp.backend.persistence.entities.Mitarbeiter;
import com.gfi.parkplatzapp.backend.service.ProfildatenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Der ProfildatenController ist für die Behandlung von Profildaten zuständig.
 * Er stellt Endpunkte bereit, um Mitarbeiterprofile zu verwalten.
 */
@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/profil")
public class ProfildatenController {

    private ProfildatenService profilService;

    /**
     * Erzeugt eine neue Instanz des ProfildatenControllers.
     *
     * @param profilService der ProfildatenService, der für die Geschäftslogik zuständig ist.
     */
    public ProfildatenController(final ProfildatenService profilService){
        this.profilService = profilService;
    }

    /**
     * Gibt einen Mitarbeiter anhand der Mitarbeiter-ID zurück.
     *
     * @param mitarbeiterID die ID des Mitarbeiters
     * @return der Mitarbeiter mit der entsprechenden ID
     */
    @GetMapping("/{mitarbeiterID}")
    public Mitarbeiter getMitarbeiter(@PathVariable("mitarbeiterID") Long mitarbeiterID)
    {
        return this.profilService.getMitarbeiter(mitarbeiterID);
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
        return profilService.deleteKennzeichenFromMitarbeiter(mitarbeiterID, kennzeichenID);
    }

    /**
     * Erstellt ein neues Kennzeichen für einen Mitarbeiter anhand der Mitarbeiter-ID und des Kennzeichens.
     *
     * @param mitarbeiterID die ID des Mitarbeiters
     * @param kennzeichen das Kennzeichen, das erstellt werden soll
     * @return der aktualisierte Mitarbeiter nach dem Hinzufügen des Kennzeichens
     */
    @PostMapping(path = "/{mitarbeiterID}", consumes = "application/json")
    public Mitarbeiter createKennzeichenForMitarbeiter(@PathVariable long mitarbeiterID, @RequestBody Kennzeichen kennzeichen)
    {
        return profilService.createKennzeichenForMitarbeiter(mitarbeiterID, kennzeichen.getKennzeichen());
    }
}