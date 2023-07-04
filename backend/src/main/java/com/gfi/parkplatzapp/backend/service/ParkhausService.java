package com.gfi.parkplatzapp.backend.service;

import com.gfi.parkplatzapp.backend.facade.dto.ParkhausParkflaecheDto;
import com.gfi.parkplatzapp.backend.persistence.entities.Parkhaus;
import com.gfi.parkplatzapp.backend.persistence.repos.ParkhausRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkhausService {
    @Autowired
    ParkhausRepo parkhausRepo;
    public List<ParkhausParkflaecheDto> getParkhaeuser() {
        List<ParkhausParkflaecheDto> parkhausDtoList = new ArrayList<>();

        parkhausRepo.findAll().forEach(parkhaus -> {
            ParkhausParkflaecheDto parkhausDto = createFromParkhaus(parkhaus);
            parkhausDtoList.add(parkhausDto);
        });

        return parkhausDtoList;
    }

   ParkhausParkflaecheDto createFromParkhaus(Parkhaus parkhaus) {
        ParkhausParkflaecheDto res = new ParkhausParkflaecheDto();

        List<ParkhausParkflaecheDto.ParkflaecheDto> parkflaecheDtoList = new ArrayList<>();
        res.setBezeichnung(parkhaus.getBezeichnung());
        res.setParkhausID(parkhaus.getParkhausID());
        parkhaus.getParkflaecheList().forEach(parkflaeche -> {

            ParkhausParkflaecheDto.ParkflaecheDto parkflaecheDto = new ParkhausParkflaecheDto.ParkflaecheDto();
            parkflaecheDto.setParkflaecheID(parkflaeche.getParkflaecheID());
            parkflaecheDto.setBezeichnung(parkflaeche.getBezeichnung());
            parkflaecheDto.setImage(parkflaeche.getImage());
            parkflaecheDtoList.add(parkflaecheDto);

        });

        res.setParkflaecheList(parkflaecheDtoList.toArray(new ParkhausParkflaecheDto.ParkflaecheDto[0]));
        return res;
    }
}
