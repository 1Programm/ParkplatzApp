package com.gfi.parkplatzapp.backend.facade;

import com.gfi.parkplatzapp.backend.facade.dto.VerstossDto;
import com.gfi.parkplatzapp.backend.facade.dto.VerstossStatusDto;
import com.gfi.parkplatzapp.backend.persistence.entities.Verstoss;
import com.gfi.parkplatzapp.backend.service.VerstossService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/verstoss")
public class VerstossController {
    @Autowired
    private VerstossService verstossService;

    @PostMapping(path = "/update/{mitarbeiterID}", consumes = "application/json")
    public Verstoss speichernVerstoss(@PathVariable("mitarbeiterID") Long mitarbeiterID, @RequestBody VerstossDto verstossDto) {
        return verstossService.speichernVerstoss(mitarbeiterID, verstossDto);
    }

    @GetMapping(path = "/verstoesse/{mitarbeiterID}")
    public List<VerstossDto> getVerstoesseForMitatbeiter(@PathVariable("mitarbeiterID") Long mitarbeiterID) {
        return this.verstossService.getVerstoesseForMitatbeiter(mitarbeiterID);
    }

    @GetMapping(path = "/verstoesse")
    public List<VerstossDto> getAllVerstoesse() {
        return this.verstossService.getAllVerstoesse();
    }

    @GetMapping(path = "/status")
    public List<VerstossStatusDto> getAllVerstossStatus() {
        return this.verstossService.getAllVerstossStatus();
    }

    @PostMapping(path = "/updateStatus", consumes = "application/json")
    public Verstoss changeStatusForVerstoss(@RequestBody VerstossDto verstossDto) {
        return verstossService.changeStatusForVerstoss(verstossDto);
    }
}
