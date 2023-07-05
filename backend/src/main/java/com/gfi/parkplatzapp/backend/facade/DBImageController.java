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

    @PostMapping("/upload")
    public ResponseEntity<String> uplaodImage(@RequestParam("image") MultipartFile file) throws IOException {
        DBImage image = new DBImage(null, file.getOriginalFilename(), file.getContentType(), ImageUtils.compressImage(file.getBytes()));
        imageRepo.save(image);

        return ResponseEntity.status(HttpStatus.OK)
                .body("Image uploaded successfully: " + file.getOriginalFilename());
    }

    @GetMapping("/{name}/details")
    public DBImage getImageDetails(@PathVariable("name") String name) throws IOException {
        final Optional<DBImage> oImage = imageRepo.findByName(name);
        DBImage image = oImage.orElseThrow(() -> new IllegalStateException("Could not find image with name [" + name + "]!"));

        return new DBImage(image.getId(), image.getName(), image.getType(), null);
    }

    @GetMapping(path = {"/name/{name}"})
    public ResponseEntity<byte[]> getImage(@PathVariable("name") String name) throws IOException {
        final Optional<DBImage> oImage = imageRepo.findByName(name);
        DBImage image = oImage.orElseThrow(() -> new IllegalStateException("Could not find image with name [" + name + "]!"));

        return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf(image.getType()))
                .body(ImageUtils.decompressImage(image.getImage()));
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity<byte[]> getImage(@PathVariable("id") long id) throws IOException {
        final Optional<DBImage> oImage = imageRepo.findById(id);
        DBImage image = oImage.orElseThrow(() -> new IllegalStateException("Could not find image with id [" + id + "]!"));

        return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf(image.getType()))
                .body(ImageUtils.decompressImage(image.getImage()));
    }
}
