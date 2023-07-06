package com.gfi.parkplatzapp.backend.service;

import com.gfi.parkplatzapp.backend.facade.dto.ParkhausEditierenDto;
import com.gfi.parkplatzapp.backend.facade.dto.ParkhausParkflaecheDto;
import com.gfi.parkplatzapp.backend.persistence.entities.Parkhaus;
import com.gfi.parkplatzapp.backend.persistence.repos.ParkflaecheRepo;
import com.gfi.parkplatzapp.backend.persistence.repos.ParkhausRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkhausService {
    @Autowired
    ParkhausRepo parkhausRepo;

    @Autowired
    ParkflaecheRepo parkflaecheRepo;

    public List<ParkhausParkflaecheDto> getParkhaeuser() {
        List<ParkhausParkflaecheDto> parkhausDtoList = new ArrayList<>();

        parkhausRepo.findAll().forEach(parkhaus -> {
            ParkhausParkflaecheDto parkhausDto = ParkhausParkflaecheDto.createFromParkhaus(parkhaus);
            parkhausDtoList.add(parkhausDto);
        });

        return parkhausDtoList;
    }

    public ParkhausEditierenDto getParkhaus(long parkhausID) {
        Parkhaus parkhaus = parkhausRepo.findById(parkhausID).orElseThrow(() -> new IllegalStateException("Could not find the Parkhaus with id [" + parkhausID + "]!"));
        return ParkhausEditierenDto.convertToDto(parkhaus);
    }

    public ParkhausEditierenDto saveParkhaus(ParkhausEditierenDto parkhausEditierenDto) {
       Parkhaus parkhaus = ParkhausEditierenDto.convertToParkhaus(parkhausEditierenDto);
       if(parkhausEditierenDto.getParkhausID() != null) {
           Parkhaus old = parkhausRepo.findById(parkhausEditierenDto.getParkhausID()).get();
           parkhaus.setParkflaecheList(old.getParkflaecheList());
       }
        Parkhaus res = parkhausRepo.save(parkhaus);
        return ParkhausEditierenDto.convertToDto(res);
    }

    public void deleteParkhaus(long parkhausID) {
        parkhausRepo.deleteById(parkhausID);
    }




}
