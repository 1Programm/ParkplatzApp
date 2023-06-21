package com.gfi.parkplatzapp.backend.facade;


import com.gfi.parkplatzapp.backend.facade.dto.BuchungDto;
import com.gfi.parkplatzapp.backend.persistence.entities.Kennzeichen;
import com.gfi.parkplatzapp.backend.service.BuchungService;
import com.gfi.parkplatzapp.backend.service.MitarbeiterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{mitarbeiterID}")
    public List<BuchungDto> getBuchungenForMitarbeiter(@PathVariable("mitarbeiterID") Long mitarbeiterID)
    {
        return this.buchungService.getBuchungenForMitarbeiter(mitarbeiterID);
    }

    @GetMapping("/{mitarbeiterID}/kennzeichen")
    public List<Kennzeichen> getKennzeichenForMitarbeiter(@PathVariable("mitarbeiterID") Long mitarbeiterID) {
        return this.mitarbeiterService.getKennzeichenForMitarbeiter(mitarbeiterID);
    }

    @PostMapping("/{buchungID}/kennzeichen/{kennzeichenID}")
    public List<BuchungDto> saveKennzeichenForBuchung(@PathVariable("buchungID") Long buchungID, @PathVariable("kennzeichenID") Long kennzeichenID) {
        return this.buchungService.updateKennzeichenForBuchung(buchungID, kennzeichenID);
    }

    @DeleteMapping(path = "/{mitarbeiterID}/buchung/{buchungID}")
    public List<BuchungDto> deleteBuchungFromMitarbeiter(@PathVariable Long mitarbeiterID, @PathVariable Long buchungID) {
        return buchungService.deleteBuchungFromMitarbeiter(mitarbeiterID, buchungID);
    }


}
