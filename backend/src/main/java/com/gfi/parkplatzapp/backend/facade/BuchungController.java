package com.gfi.parkplatzapp.backend.facade;

import com.gfi.parkplatzapp.backend.facade.dto.*;
import com.gfi.parkplatzapp.backend.persistence.entities.Kennzeichen;
import com.gfi.parkplatzapp.backend.persistence.entities.Parkplatz;
import com.gfi.parkplatzapp.backend.persistence.entities.Parkplatztyp;
import com.gfi.parkplatzapp.backend.persistence.entities.Preiskategorie;
import com.gfi.parkplatzapp.backend.service.BuchungService;
import com.gfi.parkplatzapp.backend.service.MitarbeiterService;
import com.gfi.parkplatzapp.backend.service.ParkplatzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/buchung")
public class BuchungController {

    @Autowired
    private BuchungService buchungService;

    @Autowired
    private ParkplatzService parkplatzService;

    @Autowired
    private MitarbeiterService mitarbeiterService;

    /**
     * Gibt eine Liste von Parkflächen-Auswahl-DTOs zurück.
     *
     * @return Die Liste von Parkflächen (Parkplatz + Parkhaus) als ParkflaecheAuswahlDto.
     */
    @GetMapping("/parkflaechen")
    public List<ParkflaecheAuswahlDto> getParkflaechen() {
        return this.buchungService.getParkflaechen();
    }

    /**
     * Gibt die Adresse eines Parkhauses anhand seiner ID zurück.
     *
     * @param parkhausID Die ID des Parkhauses.
     * @return Die ParkhausAdresseDto, das die Adresse des Parkhauses enthält.
     */
    @GetMapping("/parkhaus/{parkhausID}/adresse")
    public ParkhausAdresseDto getParkhausAdresse(@PathVariable("parkhausID") long id){
        return this.buchungService.getParkhausAdresse(id);
    }

    /**
     * Gibt eine Liste von Parkplatz-mit-Status-DTOs für eine bestimmte Parkfläche und Datum zurück.
     *
     * @param parkflaecheID Die ID der Parkfläche.
     * @param date          Das Datum.
     * @return Die Liste von Parkplatz-mit-Status-DTOs als ParkplatzMitStatusDto.
     */
    @GetMapping("/parkplatz/{parkflaecheID}/{date}")
    public List<ParkplatzMitStatusDto> getParkplaetzeOfParkflaecheAndDate(@PathVariable("parkflaecheID") Long parkflaecheID, @PathVariable("date") String date) {
        return this.buchungService.getParkplaetzeOfParkflaecheAndDate(parkflaecheID, date);
    }

    /**
     * Gibt eine Liste von Parkplätzen für eine bestimmte Parkfläche zurück.
     *
     * @param parkflaecheID Die ID der Parkfläche.
     * @return Die Liste von Parkplätzen als List<Parkplatz>.
     */
    @GetMapping("/parkplatz/{parkflaecheID}")
    public List<Parkplatz> getParkplaetzeOfParkflaeche(@PathVariable("parkflaecheID") Long parkflaecheID) {
        return this.buchungService.getParkplaetzeOfParkflaeche(parkflaecheID);
    }

    /**
     * Gibt eine Liste von Parkplatztypen zurück.
     *
     * @return Die Liste von Parkplatztypen als List<Parkplatztyp>.
     */
    @GetMapping("/parkplatztypen")
    public List<Parkplatztyp> getParkplatztypen() {
        return this.buchungService.getParkplatztyp();
    }

    /**
     * Gibt eine Liste von Preiskategorien zurück.
     *
     * @return Die Liste von Preiskategorien als List<Preiskategorie>.
     */
    @GetMapping("/preiskategorien")
    public List<Preiskategorie> getPreiskategoiren() {
        return this.buchungService.getPreiskategorien();
    }

    /**
     * Gibt eine Liste von BuchungDetailsDtos zurück, die den Buchungsverlauf eines Mitarbeiters darstellen.
     *
     * @param mitarbeiterID Die ID des Mitarbeiters.
     * @return Die Liste von BuchungDetailsDtos als List<BuchungDetailsDto>.
     */
    @GetMapping("/{mitarbeiterID}")
    public List<BuchungDetailsDto> getBuchungenForMitarbeiter(@PathVariable("mitarbeiterID") Long mitarbeiterID)
    {
        return this.buchungService.getBuchungenForMitarbeiter(mitarbeiterID);
    }

    /**
     * Gibt eine Liste von Kennzeichen zurück, die einem bestimmten Mitarbeiter zugeordnet sind.
     *
     * @param mitarbeiterID Die ID des Mitarbeiters.
     * @return Die Liste von Kennzeichen als List<Kennzeichen>.
     */
    @GetMapping("/{mitarbeiterID}/kennzeichen")
    public List<Kennzeichen> getKennzeichenForMitarbeiter(@PathVariable("mitarbeiterID") Long mitarbeiterID) {
        return this.mitarbeiterService.getKennzeichenForMitarbeiter(mitarbeiterID);
    }

    /**
     * Aktualisiert die Buchungsdetails mit den übergebenen Werten.
     *
     * @param buchung Die BuchungDetailsDto, die die zu aktualisierenden Buchungsinformationen enthält.
     */
    @PostMapping(path = "/test", consumes = "application/json")
    public void updateBuchungen(@RequestBody BuchungDetailsDto buchung) {
        buchungService.updateBuchungen(buchung);
    }

    /**
     * Schließt die übergebenen Buchungen ab.
     *
     * @param buchungen Die Array von BuchungAbschlussDto, das die Buchungen enthält, die abgeschlossen werden sollen.
     */
    @PostMapping("/abschliessen")
    public void schliesseBuchungAb(@RequestBody BuchungAbschlussDto[] buchungen){
        buchungService.schliesseBuchungAb(buchungen);
    }
}
