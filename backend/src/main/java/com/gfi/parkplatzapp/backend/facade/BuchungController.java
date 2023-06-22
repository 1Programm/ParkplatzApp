package com.gfi.parkplatzapp.backend.facade;

import com.gfi.parkplatzapp.backend.facade.dto.BuchungDto;
import com.gfi.parkplatzapp.backend.facade.dto.ParkflaecheAuswahlDto;
import com.gfi.parkplatzapp.backend.persistence.entities.Kennzeichen;
import com.gfi.parkplatzapp.backend.persistence.entities.Parkplatz;
import com.gfi.parkplatzapp.backend.persistence.entities.Parkplatztyp;
import com.gfi.parkplatzapp.backend.persistence.entities.Preiskategorie;
import com.gfi.parkplatzapp.backend.service.BuchungService;
import com.gfi.parkplatzapp.backend.service.MitarbeiterService;
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
    private MitarbeiterService mitarbeiterService;

    @GetMapping("/parkanlagen")
    public List<ParkflaecheAuswahlDto> getParkflaechen() {
        return this.buchungService.getParkflaechen();
    }

    @GetMapping("/parkplatz/{parkflaecheID}")
    public List<Parkplatz> getParkplaetzeOfParkflaeche(@PathVariable("parkflaecheID") Long parkflaecheID) {
        return this.buchungService.getParkplaetzeOfParkflaeche(parkflaecheID);
    }

    @GetMapping("/parkplatztypen")
    public List<Parkplatztyp> getParkplatztypen() {
        return this.buchungService.getParkplatztyp();
    }


    @GetMapping("/preiskategorien")
    public List<Preiskategorie> getPreiskategoiren() {
        return this.buchungService.getPreiskategorien();
    }

    @PostMapping(path = "/parkplatz", consumes = "application/json")
    public List<Parkplatz> createKennzeichenForMitarbeiter(@RequestBody Parkplatz parkplatz) {
        return buchungService.createParkplatz(parkplatz);
    }

    @GetMapping("/{mitarbeiterID}")
    public List<BuchungDto> getBuchungenForMitarbeiter(@PathVariable("mitarbeiterID") Long mitarbeiterID)
    {
        return this.buchungService.getBuchungenForMitarbeiter(mitarbeiterID);
    }

    @GetMapping("/{mitarbeiterID}/kennzeichen")
    public List<Kennzeichen> getKennzeichenForMitarbeiter(@PathVariable("mitarbeiterID") Long mitarbeiterID) {
        return this.mitarbeiterService.getKennzeichenForMitarbeiter(mitarbeiterID);
    }

    @PostMapping(path = "/buchen", consumes = "application/json")
    public void createKennzeichenForMitarbeiter(@RequestBody List<BuchungDto> buchungDtoList) {
        buchungService.updateBuchungen(buchungDtoList);
    }
}
