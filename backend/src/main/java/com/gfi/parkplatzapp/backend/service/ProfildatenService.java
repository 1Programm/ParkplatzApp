package com.gfi.parkplatzapp.backend.service;

import com.gfi.parkplatzapp.backend.persistence.entities.Kennzeichen;
import com.gfi.parkplatzapp.backend.persistence.entities.Mitarbeiter;
import com.gfi.parkplatzapp.backend.persistence.repos.KennzeichenRepo;
import com.gfi.parkplatzapp.backend.persistence.repos.MitarbeiterRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProfildatenService {

    @Autowired
    private MitarbeiterRepo mitarbeiterRepository;

    @Autowired
    private KennzeichenRepo kennzeichenRepository;

    /**
     * Ruft einen Mitarbeiter anhand seiner ID ab.
     *
     * @param id die ID des Mitarbeiters
     * @return der gefundene Mitarbeiter
     * @throws IllegalArgumentException wenn der Mitarbeiter nicht gefunden wird
     */
    public Mitarbeiter getMitarbeiter(Long id) {
        return mitarbeiterRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Mitarbeiter mit ID " + id + " wurde nicht gefunden."));
    }

    /**
     * Entfernt ein Kennzeichen von einem Mitarbeiter.
     *
     * @param mitarbeiterID   die ID des Mitarbeiters
     * @param kennzeichenID   die ID des Kennzeichens, das entfernt werden soll
     * @return der aktualisierte Mitarbeiter nach dem Entfernen des Kennzeichens
     * @throws IllegalArgumentException wenn der Mitarbeiter oder das Kennzeichen nicht gefunden wird
     */
    public Mitarbeiter deleteKennzeichenFromMitarbeiter(Long mitarbeiterID, Long kennzeichenID) {
        Mitarbeiter mitarbeiter = mitarbeiterRepository.findById(mitarbeiterID)
                .orElseThrow(() -> new IllegalArgumentException("Mitarbeiter mit ID " + mitarbeiterID + " wurde nicht gefunden."));

        List<Kennzeichen> kennzeichenList = mitarbeiter.getKennzeichenList();
        boolean found = false;
        int i = 0;
        while (i < kennzeichenList.size()) {
            Kennzeichen kennzeichen = kennzeichenList.get(i);
            if (kennzeichen.getKennzeichenID().equals(kennzeichenID)) {
                kennzeichenList.remove(i);
                found = true;
            } else {
                i++;
            }
        }

        if (found) {
            mitarbeiter.setKennzeichenList(kennzeichenList);
            return mitarbeiterRepository.save(mitarbeiter);
        } else {
            throw new IllegalArgumentException("Das angegebene Kennzeichen existiert nicht für den Mitarbeiter.");
        }
    }

    /**
     * Erstellt ein neues Kennzeichen für einen Mitarbeiter.
     *
     * @param mitarbeiterID   die ID des Mitarbeiters
     * @param pKennzeichen    das Kennzeichen, das erstellt werden soll
     * @return der aktualisierte Mitarbeiter nach dem Hinzufügen des Kennzeichens
     * @throws IllegalArgumentException wenn der Mitarbeiter nicht gefunden wird
     */
    public Mitarbeiter createKennzeichenForMitarbeiter(long mitarbeiterID, String pKennzeichen) {
        Mitarbeiter mitarbeiter = mitarbeiterRepository.findById(mitarbeiterID)
                .orElseThrow(() -> new IllegalArgumentException("Mitarbeiter mit ID " + mitarbeiterID + " wurde nicht gefunden."));

        Kennzeichen kennzeichen = kennzeichenRepository.save(new Kennzeichen(null, pKennzeichen));

        mitarbeiter.getKennzeichenList().add(kennzeichen);
        return mitarbeiterRepository.save(mitarbeiter);
    }
}
