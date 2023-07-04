package com.gfi.parkplatzapp.backend.service;

import com.gfi.parkplatzapp.backend.facade.dto.ParkhausParkflaecheDto;
import com.gfi.parkplatzapp.backend.persistence.entities.DBImage;
import com.gfi.parkplatzapp.backend.persistence.entities.Parkflaeche;
import com.gfi.parkplatzapp.backend.persistence.entities.Parkhaus;
import com.gfi.parkplatzapp.backend.persistence.repos.DBImageRepo;
import com.gfi.parkplatzapp.backend.persistence.repos.ParkflaecheRepo;
import com.gfi.parkplatzapp.backend.persistence.repos.ParkhausRepo;
import com.gfi.parkplatzapp.backend.utils.ImageUtils;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    public void deleteParkflaeche(long parkflaecheID, long parkhausID) {

        Parkflaeche parkflaeche = getParkflaecheById(parkflaecheID);
        Parkhaus parkhaus = parkhausRepo.findById(parkhausID)
                .orElseThrow(() -> new IllegalStateException("Could not find the Parkhaus with id [" + parkhausID + "]!"));
        parkhaus.getParkflaecheList().remove(parkflaeche);
        parkhausRepo.save(parkhaus);
        parkflaecheRepo.delete(parkflaeche);
    }

    public ParkhausParkflaecheDto.ParkflaecheDto saveParkflaeche(long parkhausID, ParkhausParkflaecheDto.ParkflaecheDto _parkflaeche) {

        Parkflaeche parkflaeche = parkflaecheRepo.save(createFromParkflaecheDto(_parkflaeche));
        Parkhaus parkhaus = parkhausRepo.findById(parkhausID)
                .orElseThrow(() -> new IllegalStateException("Could not find the Parkhaus with id [" + parkhausID + "]!"));
        parkhaus.getParkflaecheList().add(parkflaeche);
        parkhausRepo.save(parkhaus);
        return createFromParkflaeche(parkflaeche);
    }

    private Parkflaeche createFromParkflaecheDto(ParkhausParkflaecheDto.ParkflaecheDto dto) {
        Parkflaeche parkflaeche = new Parkflaeche();
        parkflaeche.setImage(dto.getImage());
        parkflaeche.setBezeichnung(dto.getBezeichnung());
        parkflaeche.setParkplatzList(null);
        return parkflaeche;
    }

    private ParkhausParkflaecheDto.ParkflaecheDto createFromParkflaeche(Parkflaeche parkflaeche) {
        ParkhausParkflaecheDto.ParkflaecheDto parkflaecheDto = new ParkhausParkflaecheDto.ParkflaecheDto();
        parkflaecheDto.setParkflaecheID(parkflaeche.getParkflaecheID());
        parkflaecheDto.setBezeichnung(parkflaeche.getBezeichnung());
        parkflaecheDto.setImage(parkflaeche.getImage());
        return parkflaecheDto;
    }


}
