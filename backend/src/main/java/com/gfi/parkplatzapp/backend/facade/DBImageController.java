package com.gfi.parkplatzapp.backend.facade;

import com.gfi.parkplatzapp.backend.persistence.entities.DBImage;
import com.gfi.parkplatzapp.backend.persistence.repos.DBImageRepo;
import com.gfi.parkplatzapp.backend.utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/image")
public class DBImageController {

    @Autowired
    private DBImageRepo imageRepo;

    /**
     * Endpunkt zum Hochladen eines Bildes in die Datenbank.
     *
     * @param file Das hochzuladende Bild.
     * @return ResponseEntity mit einer Erfolgsmeldung, wenn das Bild erfolgreich hochgeladen wurde.
     * @throws IOException Falls ein Fehler beim Lesen oder Speichern des Bildes auftritt.
     */
    @PostMapping("/upload")
    public ResponseEntity<String> uplaodImage(@RequestParam("image") MultipartFile file) throws IOException {
        DBImage image = new DBImage(null, file.getOriginalFilename(), file.getContentType(), ImageUtils.compressImage(file.getBytes()));
        imageRepo.save(image);

        return ResponseEntity.status(HttpStatus.OK)
                .body("Bild erfolgreich hochgeladen: " + file.getOriginalFilename());
    }

    /**
     * Endpunkt zum Abrufen der Details eines Bildes anhand des Namens.
     *
     * @param name Der Name des gesuchten Bildes.
     * @return Das DBImage-Objekt mit den Details des Bildes.
     * @throws IOException Falls ein Fehler beim Lesen des Bildes auftritt oder das Bild nicht gefunden werden kann.
     */
    @GetMapping("/{name}/details")
    public DBImage getImageDetails(@PathVariable("name") String name) throws IOException {
        final Optional<DBImage> oImage = imageRepo.findByName(name);
        DBImage image = oImage.orElseThrow(() -> new IllegalStateException("Bild mit Namen [" + name + "] konnte nicht gefunden werden!"));

        return new DBImage(image.getId(), image.getName(), image.getType(), null);
    }

    /**
     * Endpunkt zum Abrufen eines Bildes anhand des Namens.
     *
     * @param name Der Name des gesuchten Bildes.
     * @return ResponseEntity mit dem Bild als Byte-Array und dem entsprechenden Medientyp.
     * @throws IOException Falls ein Fehler beim Lesen des Bildes auftritt oder das Bild nicht gefunden werden kann.
     */
    @GetMapping(path = {"/name/{name}"})
    public ResponseEntity<byte[]> getImage(@PathVariable("name") String name) throws IOException {
        final Optional<DBImage> oImage = imageRepo.findByName(name);
        DBImage image = oImage.orElseThrow(() -> new IllegalStateException("Bild mit Namen [" + name + "] konnte nicht gefunden werden!"));

        return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf(image.getType()))
                .body(ImageUtils.decompressImage(image.getImage()));
    }

    /**
     * Endpunkt zum Abrufen eines Bildes anhand der ID.
     *
     * @param id Die ID des gesuchten Bildes.
     * @return ResponseEntity mit dem Bild als Byte-Array und dem entsprechenden Medientyp.
     * @throws IOException Falls ein Fehler beim Lesen des Bildes auftritt oder das Bild nicht gefunden werden kann.
     */
    @GetMapping(path = {"/{id}"})
    public ResponseEntity<byte[]> getImage(@PathVariable("id") long id) throws IOException {
        final Optional<DBImage> oImage = imageRepo.findById(id);
        DBImage image = oImage.orElseThrow(() -> new IllegalStateException("Bild mit ID [" + id + "] konnte nicht gefunden werden!"));

        return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf(image.getType()))
                .body(ImageUtils.decompressImage(image.getImage()));
    }
}
