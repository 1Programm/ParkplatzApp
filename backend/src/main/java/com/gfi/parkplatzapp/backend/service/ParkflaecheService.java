package com.gfi.parkplatzapp.backend.service;

import com.gfi.parkplatzapp.backend.facade.dto.ParkhausParkflaecheDto;
import com.gfi.parkplatzapp.backend.persistence.entities.DBImage;
import com.gfi.parkplatzapp.backend.persistence.entities.Parkflaeche;
import com.gfi.parkplatzapp.backend.persistence.entities.Parkhaus;
import com.gfi.parkplatzapp.backend.persistence.repos.DBImageRepo;
import com.gfi.parkplatzapp.backend.persistence.repos.ParkflaecheRepo;
import com.gfi.parkplatzapp.backend.persistence.repos.ParkhausRepo;
import com.gfi.parkplatzapp.backend.utils.AktivitaetEnum;
import com.gfi.parkplatzapp.backend.utils.ImageUtils;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
public class ParkflaecheService {

    @Autowired
    private ParkflaecheRepo parkflaecheRepo;

    @Autowired
    private ParkhausRepo parkhausRepo;

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

    public void deleteParkflaeche(long parkflaecheID) {
        log.info("Deleting Parkflaeche with id [" + parkflaecheID + "]!");
        parkflaecheRepo.findById(parkflaecheID)
                .ifPresentOrElse(parkflaeche -> {
                    parkflaeche.setAktivitaet(AktivitaetEnum.INAKTIV);
                    parkflaecheRepo.save(parkflaeche);
                }, () -> {
                    throw new IllegalStateException("Could not find the Parkflaeche with id [" + parkflaecheID + "]!");
                });
    }

    public ParkhausParkflaecheDto.ParkflaecheDto saveParkflaeche(long parkhausID, ParkhausParkflaecheDto.ParkflaecheDto _parkflaeche) {
        Parkflaeche toSave;
        if(_parkflaeche.getParkflaecheID() != null) {
            toSave = parkflaecheRepo.findById(_parkflaeche.getParkflaecheID()).get();
            toSave.setBezeichnung(_parkflaeche.getBezeichnung());
            toSave.setImage(_parkflaeche.getImage());
            toSave.setAktivitaet(AktivitaetEnum.AKTIV);
            parkflaecheRepo.save(toSave);
        } else {
            toSave = ParkhausParkflaecheDto.ParkflaecheDto.createFromParkflaecheDto(_parkflaeche);
            parkflaecheRepo.save(toSave);
            Parkhaus parkhaus = parkhausRepo.findById(parkhausID)
                    .orElseThrow(() -> new IllegalStateException("Could not find the Parkhaus with id [" + parkhausID + "]!"));
            parkhaus.getParkflaecheList().add(toSave);
            parkhausRepo.save(parkhaus);
        }



        return ParkhausParkflaecheDto.ParkflaecheDto.createFromParkflaeche(toSave);
    }




}
