package com.gfi.parkplatzapp.backend.facade;

import com.gfi.parkplatzapp.backend.facade.dto.ParkflaecheAuswahlDto;
import com.gfi.parkplatzapp.backend.persistence.entities.Mitarbeiter;
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
}
