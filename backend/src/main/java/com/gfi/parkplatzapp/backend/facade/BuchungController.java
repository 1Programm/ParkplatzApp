package com.gfi.parkplatzapp.backend.facade;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gfi.parkplatzapp.backend.facade.dto.ParkflaecheAuswahlDto;
import com.gfi.parkplatzapp.backend.facade.dto.ParkplatzMitStatusDto;
import com.gfi.parkplatzapp.backend.persistence.entities.*;
import com.gfi.parkplatzapp.backend.service.BuchungService;
import com.gfi.parkplatzapp.backend.service.ParkplatzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
    @GetMapping("/parkanlagen")
    public List<ParkflaecheAuswahlDto> getParkflaechen() {
        return this.buchungService.getParkflaechen();
    }

    @GetMapping("/parkplatz/{parkflaecheID}/{date}")
    public List<ParkplatzMitStatusDto> getParkplaetzeOfParkflaecheAndDate(@PathVariable("parkflaecheID") Long parkflaecheID, @PathVariable("date") String date) {
        return this.buchungService.getParkplaetzeOfParkflaecheAndDate(parkflaecheID, date);
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

    @PostMapping(path = "/parkplatz/{parkflaecheID}")
    public List<Parkplatz> saveParkplatz(@PathVariable("parkflaecheID") Long parkflaecheID, @RequestBody Parkplatz parkplatz) {
        return parkplatzService.saveParkplatz(parkplatz, parkflaecheID);
    }
    @DeleteMapping(path = "/parkplatz/{parkplatzID}")
    public Parkplatz deleteParkplatz(@PathVariable Long parkplatzID) {
        return parkplatzService.deleteParkplatz(parkplatzID);
    }



}
