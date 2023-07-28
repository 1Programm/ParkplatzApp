/**
 * Diese Klasse repräsentiert den Controller für Verstöße im Backend der Parkplatz-App.
 * Sie bietet Endpunkte zum Speichern und Abrufen von Verstößen sowie zum Ändern des Verstoßstatus.
 */
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

    /**
     * Endpunkt zum Speichern eines neuen Verstoßes für einen Mitarbeiter.
     *
     * @param mitarbeiterID Die ID des betroffenen Mitarbeiters.
     * @param verstossDto   Das VerstossDto-Objekt, das die Informationen zum Verstoß enthält.
     * @return Das Verstoss-Objekt, das nach dem Speichern zurückgegeben wird.
     */
    @PostMapping(path = "/update/{mitarbeiterID}", consumes = "application/json")
    public Verstoss speichernVerstoss(@PathVariable("mitarbeiterID") Long mitarbeiterID, @RequestBody VerstossDto verstossDto) {
        return verstossService.speichernVerstoss(mitarbeiterID, verstossDto);
    }

    /**
     * Endpunkt zum Abrufen aller Verstöße für einen bestimmten Mitarbeiter.
     *
     * @param mitarbeiterID Die ID des Mitarbeiters, für den die Verstöße abgerufen werden sollen.
     * @return Eine Liste von VerstossDto-Objekten, die die Verstöße des Mitarbeiters enthalten.
     */
    @GetMapping(path = "/verstoesse/{mitarbeiterID}")
    public List<VerstossDto> getVerstoesseForMitatbeiter(@PathVariable("mitarbeiterID") Long mitarbeiterID) {
        return this.verstossService.getVerstoesseForMitatbeiter(mitarbeiterID);
    }

    /**
     * Endpunkt zum Abrufen aller Verstöße.
     *
     * @return Eine Liste von VerstossDto-Objekten, die alle Verstöße enthalten.
     */
    @GetMapping(path = "/verstoesse")
    public List<VerstossDto> getAllVerstoesse() {
        return this.verstossService.getAllVerstoesse();
    }

    /**
     * Endpunkt zum Abrufen aller Verstoßstatus.
     *
     * @return Eine Liste von VerstossStatusDto-Objekten, die alle Verstoßstatus enthalten.
     */
    @GetMapping(path = "/status")
    public List<VerstossStatusDto> getAllVerstossStatus() {
        return this.verstossService.getAllVerstossStatus();
    }

    /**
     * Endpunkt zum Ändern des Status für einen Verstoß.
     *
     * @param verstossDto Das VerstossDto-Objekt, das den zu ändernden Verstoß und den neuen Status enthält.
     * @return Das Verstoss-Objekt mit dem aktualisierten Status, das nach der Änderung zurückgegeben wird.
     */
    @PostMapping(path = "/updateStatus", consumes = "application/json")
    public Verstoss changeStatusForVerstoss(@RequestBody VerstossDto verstossDto) {
        return verstossService.changeStatusForVerstoss(verstossDto);
    }
}
