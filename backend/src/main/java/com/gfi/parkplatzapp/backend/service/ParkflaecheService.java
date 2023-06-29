package com.gfi.parkplatzapp.backend.service;

import com.gfi.parkplatzapp.backend.persistence.entities.DBImage;
import com.gfi.parkplatzapp.backend.persistence.entities.Parkflaeche;
import com.gfi.parkplatzapp.backend.persistence.repos.DBImageRepo;
import com.gfi.parkplatzapp.backend.persistence.repos.ParkflaecheRepo;
import com.gfi.parkplatzapp.backend.utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ParkflaecheService {

    @Autowired
    private ParkflaecheRepo parkflaecheRepo;

    @Autowired
    private DBImageRepo imageRepo;

    public Parkflaeche getParkflaecheById(long id){
        return parkflaecheRepo.findById(id)
                .orElseThrow(() -> new IllegalStateException("Could not find the Parkflaeche with id [" + id + "]!"));
    }

    public void updateImageForParkflaeche(long parkflaecheID, MultipartFile file) throws IOException {
        DBImage image = new DBImage(null, file.getOriginalFilename(), file.getContentType(), ImageUtils.compressImage(file.getBytes()));
        image = imageRepo.save(image);

        Parkflaeche parkflaeche = getParkflaecheById(parkflaecheID);
        parkflaeche.setImage(image);
        parkflaecheRepo.save(parkflaeche);
    }

}
