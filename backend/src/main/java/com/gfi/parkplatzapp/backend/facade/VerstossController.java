package com.gfi.parkplatzapp.backend.facade;

import com.gfi.parkplatzapp.backend.facade.dto.VerstossDto;
import com.gfi.parkplatzapp.backend.persistence.entities.Verstoss;
import com.gfi.parkplatzapp.backend.service.VerstossService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/verstoss")
public class VerstossController {
    @Autowired
    private VerstossService verstossService;

    @PostMapping(path = "/update", consumes = "application/json")
    public Verstoss speichernVerstoss(@RequestBody VerstossDto verstossDto) {
        return verstossService.speichernVerstoss(verstossDto);
    }
}
