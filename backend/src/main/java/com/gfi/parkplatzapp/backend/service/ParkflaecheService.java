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

    /**
     * Gibt die Parkfläche mit einer bestimmten id zurück.
     * @param id die ID.
     * @return gibt eine Parkfläche zurück.
     */
    public Parkflaeche getParkflaecheById(long id){
        return parkflaecheRepo.findById(id)
                .orElseThrow(() -> new IllegalStateException("Could not find the Parkflaeche with id [" + id + "]!"));
    }

    /**
     * Speichert ein Bild ab und weist dieses einer Parkfläche zu.
     * @param parkflaecheID die Parkfläche.
     * @param file das Bild.
     */
    public void updateImageForParkflaeche(long parkflaecheID, MultipartFile file) throws IOException {
        DBImage image = new DBImage(null, file.getOriginalFilename(), file.getContentType(), ImageUtils.compressImage(file.getBytes()));
        image = imageRepo.save(image);

        Parkflaeche parkflaeche = getParkflaecheById(parkflaecheID);
        parkflaeche.setImage(image);
        parkflaecheRepo.save(parkflaeche);
    }

    /**
     * Löscht eine Parkfläche.
     * @param parkflaecheID die ID.
     */
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

    /**
     * Speichert eine Parkfläche (eine neue oder alte) zu einem Parkhaus ab.
     * @param parkhausID die ParkhausID.
     * @param parkflaecheDto Die Parkfläche, die gespeichert werden soll.
     * @return die abgespeicherte Parkfläche (besitzt nun eine ID, falls sie voher keine hatte).
     */
    public ParkhausParkflaecheDto.ParkflaecheDto saveParkflaeche(long parkhausID, ParkhausParkflaecheDto.ParkflaecheDto parkflaecheDto) {
        Parkflaeche toSave;
        if(parkflaecheDto.getParkflaecheID() != null) {
            toSave = parkflaecheRepo.findById(parkflaecheDto.getParkflaecheID()).get();
            toSave.setBezeichnung(parkflaecheDto.getBezeichnung());
            toSave.setImage(parkflaecheDto.getImage());
            toSave.setAktivitaet(AktivitaetEnum.AKTIV);
            parkflaecheRepo.save(toSave);
        } else {
            toSave = ParkhausParkflaecheDto.ParkflaecheDto.createFromParkflaecheDto(parkflaecheDto);
            parkflaecheRepo.save(toSave);
            Parkhaus parkhaus = parkhausRepo.findById(parkhausID)
                    .orElseThrow(() -> new IllegalStateException("Could not find the Parkhaus with id [" + parkhausID + "]!"));
            parkhaus.getParkflaecheList().add(toSave);
            parkhausRepo.save(parkhaus);
        }

        return ParkhausParkflaecheDto.ParkflaecheDto.createFromParkflaeche(toSave);
    }




}
