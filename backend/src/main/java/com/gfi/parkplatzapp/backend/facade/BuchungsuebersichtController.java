package com.gfi.parkplatzapp.backend.facade;


import com.gfi.parkplatzapp.backend.facade.dto.BuchungDto;
import com.gfi.parkplatzapp.backend.persistence.entities.Buchung;
import com.gfi.parkplatzapp.backend.persistence.entities.Kennzeichen;
import com.gfi.parkplatzapp.backend.persistence.entities.Mitarbeiter;
import com.gfi.parkplatzapp.backend.service.BuchungsuebersichtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/buchungen")
public class BuchungsuebersichtController {

    BuchungsuebersichtService buchungenService;

    public BuchungsuebersichtController(final BuchungsuebersichtService buchungenService) {
        this.buchungenService = buchungenService;
    }

    @GetMapping("/{mitarbeiterID}")
    public List<BuchungDto> getBuchungenForMitarbeiter(@PathVariable("mitarbeiterID") Long mitarbeiterID)
    {
        return this.buchungenService.getBuchungenForMitarbeiter(mitarbeiterID);
    }

    @GetMapping("/{mitarbeiterID}/kennzeichen")
    public List<Kennzeichen> getKennzeichenForMitarbeiter(@PathVariable("mitarbeiterID") Long mitarbeiterID) {
        return this.buchungenService.getKennzeichenForMitarbeiter(mitarbeiterID);
    }

    @PostMapping("/{buchungID}/kennzeichen/{kennzeichenID}")
    public List<BuchungDto> saveKennzeichenForBuchung(@PathVariable("buchungID") Long buchungID, @PathVariable("kennzeichenID") Long kennzeichenID) {
        return this.buchungenService.saveKennzeichenForBuchung(buchungID, kennzeichenID);
    }

    @DeleteMapping(path = "/{mitarbeiterID}/buchung/{buchungID}")
    public List<BuchungDto> deleteBuchungFromMitarbeiter(@PathVariable long mitarbeiterID, @PathVariable long buchungID) {
        return buchungenService.deleteBuchungFromMitarbeiter(mitarbeiterID, buchungID);
    }
}
