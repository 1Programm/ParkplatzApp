package com.gfi.parkplatzapp.backend.service;

import com.gfi.parkplatzapp.backend.persistence.entities.Kennzeichen;
import com.gfi.parkplatzapp.backend.persistence.entities.Mitarbeiter;
import com.gfi.parkplatzapp.backend.persistence.repos.KennzeichenRepo;
import com.gfi.parkplatzapp.backend.persistence.repos.MitarbeiterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class MitarbeiterService {

    @Autowired
    private MitarbeiterRepo mitarbeiterRepo;

    @Autowired
    private KennzeichenRepo kennzeichenRepo;

    /**
     * Ruft einen Mitarbeiter anhand seiner ID ab.
     *
     * @param id die ID des Mitarbeiters
     * @return der gefundene Mitarbeiter
     * @throws IllegalArgumentException wenn der Mitarbeiter nicht gefunden wird
     */
    public Mitarbeiter getMitarbeiter(Long id) {
        return mitarbeiterRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Mitarbeiter mit ID " + id + " wurde nicht gefunden."));
    }

    public Mitarbeiter getMitarbeiterByTechnicalKey(String technicalKey){
        return mitarbeiterRepo.findByUid(technicalKey);
    }

    /**
     * Liefert eine Liste von Kennzeichen-Objekten für den angegebenen Mitarbeiter.
     *
     * @param mitarbeiterID ID des Mitarbeiters
     * @return Liste von Kennzeichen-Objekten
     */
    public List<Kennzeichen> getKennzeichenForMitarbeiter(Long mitarbeiterID) {
        return mitarbeiterRepo.findById(mitarbeiterID).get().getKennzeichenList();
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
        Mitarbeiter mitarbeiter = mitarbeiterRepo.findById(mitarbeiterID)
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
            return mitarbeiterRepo.save(mitarbeiter);
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
        Mitarbeiter mitarbeiter = mitarbeiterRepo.findById(mitarbeiterID)
                .orElseThrow(() -> new IllegalArgumentException("Mitarbeiter mit ID " + mitarbeiterID + " wurde nicht gefunden."));

        Kennzeichen kennzeichen = kennzeichenRepo.save(new Kennzeichen(null, pKennzeichen));

        mitarbeiter.getKennzeichenList().add(kennzeichen);
        return mitarbeiterRepo.save(mitarbeiter);
    }

}
