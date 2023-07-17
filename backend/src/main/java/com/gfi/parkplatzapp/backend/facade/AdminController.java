package com.gfi.parkplatzapp.backend.facade;

import com.gfi.parkplatzapp.backend.facade.dto.ParkhausEditierenDto;
import com.gfi.parkplatzapp.backend.facade.dto.ParkhausParkflaecheDto;
import com.gfi.parkplatzapp.backend.persistence.entities.DBImage;
import com.gfi.parkplatzapp.backend.persistence.entities.Mitarbeiter;
import com.gfi.parkplatzapp.backend.persistence.entities.Parkflaeche;
import com.gfi.parkplatzapp.backend.persistence.entities.Parkplatz;
import com.gfi.parkplatzapp.backend.service.ParkflaecheService;
import com.gfi.parkplatzapp.backend.service.ParkhausService;
import com.gfi.parkplatzapp.backend.service.ParkplatzService;
import com.gfi.parkplatzapp.backend.utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    ParkhausService parkhausService;

    @Autowired
    private ParkflaecheService parkflaecheService;

    @Autowired
    private ParkplatzService parkplatzService;

    @GetMapping("/parkhaus")
    public List<ParkhausParkflaecheDto> getParkhaeuser()
    {
        return this.parkhausService.getParkhaeuser();
    }



    /**
     * Gibt ein Parkhaus anhand seiner ID zurück.
     *
     * @param parkhausID Die ID des Parkhauses.
     * @return Das Parkhaus als ParkhausEditierenDto.
     */
    @GetMapping("/parkhaus/{parkhausID}")
    public ParkhausEditierenDto getParkhaus(@PathVariable("parkhausID") long parkhausID){
        return parkhausService.getParkhaus(parkhausID);
    }


    /**
     * Speichert einen Parkplatz und gibt die aktualisierte Liste von Parkplätzen zurück.
     *
     * @param parkflaecheID Die ID der Parkfläche.
     * @param parkplatz     Der zu speichernde Parkplatz.
     * @return Die aktualisierte Liste von Parkplätzen.
     */
    @PostMapping(path = "/parkplatz/{parkflaecheID}")
    public List<Parkplatz> saveParkplatz(@PathVariable("parkflaecheID") Long parkflaecheID, @RequestBody Parkplatz parkplatz) {
        return parkplatzService.saveParkplatz(parkplatz, parkflaecheID);
    }

    /**
     * Löscht einen Parkplatz und gibt den gelöschten Parkplatz zurück.
     *
     * @param parkplatzID Die ID des zu löschenden Parkplatzes.
     * @return Der gelöschte Parkplatz.
     */
    @DeleteMapping(path = "/parkplatz/{parkplatzID}")
    public Parkplatz deleteParkplatz(@PathVariable Long parkplatzID) {

        return parkplatzService.deleteParkplatz(parkplatzID);
    }

    /**
     * Gibt ein Bild für eine Parkfläche zurück.
     *
     * @param parkflaecheID Die ID der Parkfläche.
     * @return Das Bild der Parkfläche als ResponseEntity.
     * @throws IOException Falls ein Fehler beim Lesen des Bildes auftritt.
     */
    @GetMapping("/parkflaeche/{parkflaecheID}")
    public ResponseEntity<byte[]> getImageForParkflaeche(@PathVariable("parkflaecheID") long parkflaecheID) throws IOException {
        Parkflaeche parkflaeche = parkflaecheService.getParkflaecheById(parkflaecheID);
        DBImage image = parkflaeche.getImage();

        return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf(image.getType()))
                .body(ImageUtils.decompressImage(image.getImage()));
    }

    /**
     * Lädt ein Bild für eine Parkfläche hoch.
     *
     * @param parkflaecheID Die ID der Parkfläche.
     * @param file Das hochzuladende Bild als MultipartFile.
     * @throws IOException Falls ein Fehler beim Hochladen des Bildes auftritt.
     */
    @PostMapping("/parkflaeche/{parkflaecheID}/upload")
    public void uploadImageForParkflaeche(@PathVariable("parkflaecheID") long parkflaecheID, @RequestParam("image") MultipartFile file) throws IOException {
        parkflaecheService.updateImageForParkflaeche(parkflaecheID, file);
    }

    /**
     * Löscht eine Parkfläche in einem bestimmten Parkhaus.
     *
     * @param parkflaecheID Die ID der Parkfläche.
     * @param parkhausID Die ID des Parkhauses.
     */
    @DeleteMapping("/parkflaeche/{parkflaecheID}")
    public void deleteParkflaeche(@PathVariable("parkflaecheID") long parkflaecheID){
        parkflaecheService.deleteParkflaeche(parkflaecheID);
    }

    /**
     * Speichert eine neue Parkfläche in einem Parkhaus.
     *
     * @param parkhausID Die ID des Parkhauses.
     * @param parkflaeche Die zu speichernde Parkfläche als ParkflaecheDto.
     * @return Die gespeicherte Parkfläche als ParkflaecheDto.
     */
    @PostMapping("/parkflaeche/{parkhausID}")
    public ParkhausParkflaecheDto.ParkflaecheDto saveParkflaeche(@PathVariable("parkhausID") long parkhausID, @RequestBody ParkhausParkflaecheDto.ParkflaecheDto parkflaeche){
       return parkflaecheService.saveParkflaeche(parkhausID, parkflaeche);
    }

    /**
     * Speichert ein neues Parkhaus.
     *
     * @param parkhaus Das zu speichernde Parkhaus als ParkhausEditierenDto.
     * @return Das gespeicherte Parkhaus als ParkhausEditierenDto.
     */
    @PostMapping("/parkhaus")
    public ParkhausEditierenDto saveParkhaus(@RequestBody ParkhausEditierenDto parkhaus){
        return parkhausService.saveParkhaus(parkhaus);
    }


    /**
     * Löscht ein Parkhaus anhand seiner ID.
     *
     * @param parkhausID Die ID des Parkhauses.
     */
    @DeleteMapping("/parkhaus/{parkhausID}")
    public void deleteParkhaus(@PathVariable("parkhausID") long parkhausID){
        parkhausService.deleteParkhaus(parkhausID);
    }

}
