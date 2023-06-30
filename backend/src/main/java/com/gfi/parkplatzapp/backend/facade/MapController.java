package com.gfi.parkplatzapp.backend.facade;

import com.gfi.parkplatzapp.backend.persistence.entities.DBImage;
import com.gfi.parkplatzapp.backend.persistence.entities.Parkflaeche;
import com.gfi.parkplatzapp.backend.service.ParkflaecheService;
import com.gfi.parkplatzapp.backend.utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/map")
public class MapController {

    @Autowired
    private ParkflaecheService parkflaecheService;

    @GetMapping("/parkflaeche/{parkflaecheID}")
    public ResponseEntity<byte[]> getImageForParkflaeche(@PathVariable("parkflaecheID") long parkflaecheID) throws IOException {
        Parkflaeche parkflaeche = parkflaecheService.getParkflaecheById(parkflaecheID);
        DBImage image = parkflaeche.getImage();

        return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf(image.getType()))
                .body(ImageUtils.decompressImage(image.getImage()));
    }

    @PostMapping("/parkflaeche/{parkflaecheID}/upload")
    public void uploadImageForParkflaeche(@PathVariable("parkflaecheID") long parkflaecheID, @RequestParam("image") MultipartFile file) throws IOException {
        parkflaecheService.updateImageForParkflaeche(parkflaecheID, file);
    }

}
