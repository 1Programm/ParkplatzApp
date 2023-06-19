package com.gfi.parkplatzapp.backend.facade;

import com.gfi.parkplatzapp.backend.facade.dto.ParkflaecheAuswahlDto;
import com.gfi.parkplatzapp.backend.persistence.entities.*;
import com.gfi.parkplatzapp.backend.service.BuchungService;
import com.gfi.parkplatzapp.backend.service.ProfildatenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/buchung")
public class BuchungController {

    private BuchungService buchungService;

    public BuchungController(final BuchungService buchungService){
        this.buchungService = buchungService;
    }


    @GetMapping("/parkanlagen")
    public List<ParkflaecheAuswahlDto> getParkflaechen()
    {
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
    public List<Parkplatz> createKennzeichenForMitarbeiter(@RequestBody Parkplatz parkplatz)
    {
        return buchungService.createParkplatz(parkplatz);
    }
}
