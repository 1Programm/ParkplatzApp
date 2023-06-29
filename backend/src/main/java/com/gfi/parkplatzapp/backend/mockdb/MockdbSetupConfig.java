package com.gfi.parkplatzapp.backend.mockdb;

import com.gfi.parkplatzapp.backend.persistence.entities.DBImage;
import com.gfi.parkplatzapp.backend.persistence.entities.Parkflaeche;
import com.gfi.parkplatzapp.backend.persistence.repos.DBImageRepo;
import com.gfi.parkplatzapp.backend.persistence.repos.ParkflaecheRepo;
import com.gfi.parkplatzapp.backend.utils.ImageUtils;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import java.io.IOException;
import java.io.InputStream;

@Service
@Slf4j
public class MockdbSetupConfig {

    @Autowired
    private DBImageRepo imageRepo;

    @Autowired
    private ParkflaecheRepo parkflaecheRepo;

    @PostConstruct
    public void setup() throws IOException {
        log.info("Mock DB Setup...");

        saveImage("/static/parkflaechen", "parkplatz.jpg", MimeTypeUtils.IMAGE_JPEG_VALUE);
        saveImage("/static/parkflaechen", "parkplatz2.jpg", MimeTypeUtils.IMAGE_JPEG_VALUE);


        log.info("Mock DB Setup Done!");
    }

    @EventListener(ApplicationReadyEvent.class)
    public void afterDatabasePopulated() {
        log.info("Database finished populizing!");

        // Set the images here because otherwise the "data.sql" script has not been executed yet!

        Parkflaeche p1 = parkflaecheRepo.findById(1L).get();
        DBImage img1 = imageRepo.findById(1L).get();
        p1.setImage(img1);
        parkflaecheRepo.save(p1);

        Parkflaeche p2 = parkflaecheRepo.findById(2L).get();
        DBImage img2 = imageRepo.findById(2L).get();
        p2.setImage(img2);
        parkflaecheRepo.save(p2);

    }

    private DBImage saveImage(String path, String name, String contentType) throws IOException {
        String fullPath = path + "/" + name;
        InputStream is = MockdbSetupConfig.class.getResourceAsStream(fullPath);
        if(is == null) throw new IllegalStateException("Failed to find image [" + fullPath + "]!");
        byte[] bytes = is.readAllBytes();

        DBImage image = new DBImage(null, name, contentType, ImageUtils.compressImage(bytes));
        imageRepo.save(image);

        return image;
    }

}
