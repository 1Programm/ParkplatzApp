package com.gfi.parkplatzapp.backend.service;

import com.gfi.parkplatzapp.backend.facade.dto.VerstossDto;
import com.gfi.parkplatzapp.backend.facade.dto.VerstossStatusDto;
import com.gfi.parkplatzapp.backend.persistence.entities.Mitarbeiter;
import com.gfi.parkplatzapp.backend.persistence.entities.Verstoss;
import com.gfi.parkplatzapp.backend.persistence.repos.MitarbeiterRepo;
import com.gfi.parkplatzapp.backend.persistence.repos.VerstossRepo;
import com.gfi.parkplatzapp.backend.utils.VerstossStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VerstossService {
    @Autowired
    private MitarbeiterRepo mitarbeiterRepo;
    @Autowired
    private VerstossRepo verstossRepo;

    /**
     * Speichert einen Verstoß zu einem Mitarbeiter ab.
     * @param mitarbeiterID die MitarbeiterID.
     * @param verstossDto das Verstoß-Dto
     * @return ein Vertstoß.
     */
    public Verstoss speichernVerstoss(long mitarbeiterID, VerstossDto verstossDto) {
        Verstoss verstoss = new Verstoss();
        verstoss.setBemerkung(verstossDto.getBemerkung());
        verstoss.setStatus(VerstossStatus.IN_BEARBEITUNG.toString());
        verstoss.setDatum(verstossDto.getDatum());
        verstossRepo.save(verstoss);

        Mitarbeiter mitarbeiter = mitarbeiterRepo.findById(mitarbeiterID)
                .orElseThrow(() -> new IllegalArgumentException("Mitarbeiter mit ID " + mitarbeiterID + " wurde nicht gefunden."));

        mitarbeiter.getVerstossList().add(verstoss);
        mitarbeiterRepo.save(mitarbeiter);

        return verstoss;
    }

    /**
     * Gibt alle Verstöße zurück, die ein Mitarbeiter insgesamt erstellt hat.
     * @param mitarbeiterID die MitarbeiterID.
     * @return Liste mit Verstoss-Dtos.
     */
    public List<VerstossDto> getVerstoesseForMitatbeiter(Long mitarbeiterID) {
        Mitarbeiter mitarbeiter = mitarbeiterRepo.findById(mitarbeiterID)
                .orElseThrow(() -> new IllegalArgumentException("Mitarbeiter mit ID " + mitarbeiterID + " wurde nicht gefunden."));

        return mitarbeiter.getVerstossList().stream().map(verstoss -> VerstossDto.parseFromVerstoss(verstoss, mitarbeiter.getMail()))
                .sorted(Comparator.comparing(VerstossDto::getDatum)).collect(Collectors.toList());
    }

    /**
     * Gibt alle Verstöße zurück, die es gibt.
     * @return Liste mit Verstoss-Dtos.
     */
    public List<VerstossDto> getAllVerstoesse() {
        List<VerstossDto> verstossList = new ArrayList<>();
        Iterable<Mitarbeiter> mitarbeiterIterable = mitarbeiterRepo.findAll();

        for(Mitarbeiter mitarbeiter : mitarbeiterIterable) {
            List<Verstoss> verstosse = mitarbeiter.getVerstossList();
            verstossList.addAll(verstosse.stream().map(verstoss -> VerstossDto.parseFromVerstoss(verstoss, mitarbeiter.getMail())).toList());

        }

        verstossList.sort(Comparator
                .comparing((VerstossDto verstoss) -> verstoss.getStatus().getKey().equals(VerstossStatus.ABGESCHLOSSEN.toString()))
                .reversed()
                .thenComparing(VerstossDto::getDatum));

        return verstossList;
    }

    /**
     * Speichert den geänderten Status eines Verstoßes ab.
     * @param verstossDto das Verstoß Dto.
     * @return ein Verstoß.
     */
    public Verstoss changeStatusForVerstoss(VerstossDto verstossDto) {
        Verstoss verstoss = verstossRepo.findById(verstossDto.getMeldeID())
                .orElseThrow(() -> new IllegalArgumentException("Verstoss mit ID " + verstossDto.getMeldeID() + " wurde nicht gefunden."));

        Mitarbeiter mitarbeiter = mitarbeiterRepo.findByVerstossListContaining(verstoss);

        mitarbeiter.getVerstossList().forEach(itemVerstoss -> {
            if(itemVerstoss.getMeldeID() == verstoss.getMeldeID()) {
                verstoss.setStatus(verstossDto.getStatus().getKey());
            }
        });
        mitarbeiterRepo.save(mitarbeiter);
        return verstoss;
    }

    /**
     * Gibt alle möglichen Verstoß Status zurück.
     * @return Liste mit Verstoß-Status-Dtos.
     */
    public List<VerstossStatusDto> getAllVerstossStatus() {
        List<VerstossStatusDto> verstossStatusDtoList = new ArrayList<>();
        for(VerstossStatus status : VerstossStatus.values()) {
            verstossStatusDtoList.add(new VerstossStatusDto(status.toString(), status.getLabel()));
        }
        return verstossStatusDtoList;
    }
}
