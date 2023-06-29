package com.gfi.parkplatzapp.backend.service;

import com.gfi.parkplatzapp.backend.facade.dto.VerstossDto;
import com.gfi.parkplatzapp.backend.persistence.entities.Mitarbeiter;
import com.gfi.parkplatzapp.backend.persistence.entities.Verstoss;
import com.gfi.parkplatzapp.backend.persistence.repos.MitarbeiterRepo;
import com.gfi.parkplatzapp.backend.persistence.repos.VerstossRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class VerstossService {

    @Autowired
    private MitarbeiterRepo mitarbeiterRepo;

    @Autowired
    private VerstossRepo verstossRepo;

    public Verstoss speichernVerstoss(VerstossDto verstossDto) {
        Verstoss verstoss = new Verstoss();
        verstoss.setBemerkung(verstossDto.getBemerkung());
        verstoss.setStatus(verstossDto.getStatus().getEnumValue());
        verstoss.setDatum(verstossDto.getDatum());
        verstossRepo.save(verstoss);

        Mitarbeiter mitarbeiter = mitarbeiterRepo.findById(verstossDto.getMitarbeiterID())
                .orElseThrow(() -> new IllegalArgumentException("Mitarbeiter mit ID " + verstossDto.getMitarbeiterID() + " wurde nicht gefunden."));

        mitarbeiter.getVerstossList().add(verstoss);
        mitarbeiterRepo.save(mitarbeiter);

        return verstoss;
    }

    public List<Verstoss> getVerstoesse(Long mitarbeiterID) {
        Mitarbeiter mitarbeiter = mitarbeiterRepo.findById(mitarbeiterID)
                .orElseThrow(() -> new IllegalArgumentException("Mitarbeiter mit ID " + mitarbeiterID + " wurde nicht gefunden."));

        return mitarbeiter.getVerstossList();
    }
}
