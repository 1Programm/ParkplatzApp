package com.gfi.parkplatzapp.backend.service;

import com.gfi.parkplatzapp.backend.facade.dto.ParkhausEditierenDto;
import com.gfi.parkplatzapp.backend.facade.dto.ParkhausParkflaecheDto;
import com.gfi.parkplatzapp.backend.persistence.entities.Parkhaus;
import com.gfi.parkplatzapp.backend.persistence.repos.ParkflaecheRepo;
import com.gfi.parkplatzapp.backend.persistence.repos.ParkhausRepo;
import com.gfi.parkplatzapp.backend.utils.AktivitaetEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ParkhausService {
    @Autowired
    ParkhausRepo parkhausRepo;

    @Autowired
    ParkflaecheRepo parkflaecheRepo;

    public List<ParkhausParkflaecheDto> getParkhaeuser() {
        List<ParkhausParkflaecheDto> parkhausDtoList = new ArrayList<>();

        parkhausRepo.findAllByAktivitaet(AktivitaetEnum.AKTIV).forEach(parkhaus -> {
            ParkhausParkflaecheDto parkhausDto = ParkhausParkflaecheDto.createFromParkhaus(parkhaus);
            parkhausDtoList.add(parkhausDto);
        });

        return parkhausDtoList;
    }

    public ParkhausEditierenDto getParkhaus(long parkhausID) {
        Parkhaus parkhaus = parkhausRepo.findById(parkhausID).orElseThrow(() -> new IllegalStateException("Could not find the Parkhaus with id [" + parkhausID + "]!"));
        if(parkhaus.getAktivitaet() == AktivitaetEnum.AKTIV) {
            return ParkhausEditierenDto.convertToDto(parkhaus);
        } else {
            throw new IllegalStateException("Could not find active Parkhaus with id [" + parkhausID + "]!");
        }
    }

    public ParkhausEditierenDto saveParkhaus(ParkhausEditierenDto parkhausEditierenDto) {
       Parkhaus parkhaus = ParkhausEditierenDto.convertToParkhaus(parkhausEditierenDto);
       if(parkhausEditierenDto.getParkhausID() != null) {
           Parkhaus old = parkhausRepo.findById(parkhausEditierenDto.getParkhausID()).get();
           parkhaus.setParkflaecheList(old.getParkflaecheList());
           parkhaus.setAktivitaet(old.getAktivitaet());
       }
       else {
              parkhaus.setAktivitaet(AktivitaetEnum.AKTIV);
       }
        Parkhaus res = parkhausRepo.save(parkhaus);
        return ParkhausEditierenDto.convertToDto(res);
    }

    public void deleteParkhaus(long parkhausID) {
        Parkhaus parkhaus = parkhausRepo.findById(parkhausID).orElseThrow(() -> new IllegalStateException("Could not find the Parkhaus with id [" + parkhausID + "]!"));
        parkhaus.setAktivitaet(AktivitaetEnum.INAKTIV);
        parkhausRepo.save(parkhaus);
    }




}
