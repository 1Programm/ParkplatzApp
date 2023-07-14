package com.gfi.parkplatzapp.backend.service;

import com.gfi.parkplatzapp.backend.facade.dto.VerstossDto;
import com.gfi.parkplatzapp.backend.persistence.entities.Mitarbeiter;
import com.gfi.parkplatzapp.backend.persistence.entities.Verstoss;
import com.gfi.parkplatzapp.backend.persistence.repos.MitarbeiterRepo;
import com.gfi.parkplatzapp.backend.persistence.repos.VerstossRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
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

    public List<Verstoss> getVerstoesseForMitatbeiter(Long mitarbeiterID) {
        Mitarbeiter mitarbeiter = mitarbeiterRepo.findById(mitarbeiterID)
                .orElseThrow(() -> new IllegalArgumentException("Mitarbeiter mit ID " + mitarbeiterID + " wurde nicht gefunden."));

        mitarbeiter.getVerstossList().sort(Comparator.comparing(Verstoss::getDatum));

        return mitarbeiter.getVerstossList();
    }

    public List<Verstoss> getAllVerstoesse() {
        List<Verstoss> verstossList = new ArrayList<>();
        Iterable<Mitarbeiter> mitarbeiterIterable = mitarbeiterRepo.findAll();

        for(Mitarbeiter mitarbeiter : mitarbeiterIterable) {
            List<Verstoss> verstosse = mitarbeiter.getVerstossList();
            verstossList.addAll(verstosse);
        }

        verstossList.sort(Comparator
                .comparing((Verstoss verstoss) -> verstoss.getStatus().equals("Abgeschlossen"))
                .reversed()
                .thenComparing(Verstoss::getDatum));

        return verstossList;
    }
    public Verstoss changeStatusForVerstoss(VerstossDto verstossDto) {
        Verstoss verstoss = verstossRepo.findById(verstossDto.getMeldeID())
                .orElseThrow(() -> new IllegalArgumentException("Verstoss mit ID " + verstossDto.getMeldeID() + " wurde nicht gefunden."));

        Mitarbeiter mitarbeiter = mitarbeiterRepo.findByVerstossListContaining(verstoss);

        mitarbeiter.getVerstossList().forEach(itemVerstoss -> {
            if(itemVerstoss.getMeldeID() == verstoss.getMeldeID()) {
                verstoss.setStatus(verstossDto.getStatus().getEnumValue());
            }
        });
        mitarbeiterRepo.save(mitarbeiter);
        return verstoss;
    }
}
