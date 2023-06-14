package com.gfi.parkplatzapp.backend.service;

import com.gfi.parkplatzapp.backend.facade.dto.BuchungDto;
import com.gfi.parkplatzapp.backend.facade.dto.ParkflaecheAuswahlDto;
import com.gfi.parkplatzapp.backend.persistence.entities.Buchung;
import com.gfi.parkplatzapp.backend.persistence.entities.Mitarbeiter;
import com.gfi.parkplatzapp.backend.persistence.entities.Parkflaeche;
import com.gfi.parkplatzapp.backend.persistence.entities.Parkhaus;
import com.gfi.parkplatzapp.backend.persistence.repos.BuchungRepo;
import com.gfi.parkplatzapp.backend.persistence.repos.MitarbeiterRepo;
import com.gfi.parkplatzapp.backend.persistence.repos.ParkflaecheRepo;
import com.gfi.parkplatzapp.backend.persistence.repos.ParkhausRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class BuchungService {

    @Autowired
    private ParkhausRepo parkhausRepository;
    @Autowired
    private ParkflaecheRepo parkflaecheRepository;
    @Autowired
    private BuchungRepo buchungRepository;
    @Autowired
    private MitarbeiterRepo mitarbeiterRepository;

    public List<ParkflaecheAuswahlDto> getParkflaechen() {
        List<ParkflaecheAuswahlDto> resultList = new ArrayList<>();
        Iterable<Parkhaus> parkhausIterable = parkhausRepository.findAll();

        for(Parkhaus parkhaus : parkhausIterable) {
            for(Parkflaeche parkflaeche : parkhaus.getParkflaecheList()) {
                ParkflaecheAuswahlDto parkflaecheAuswahlDto = new ParkflaecheAuswahlDto();
                parkflaecheAuswahlDto.setParkhausID(parkhaus.getParkhausID());
                parkflaecheAuswahlDto.setParkhausBezeichnung(parkhaus.getBezeichnung());
                parkflaecheAuswahlDto.setParkflaecheID(parkflaeche.getParkflaecheID());
                parkflaecheAuswahlDto.setParkflaecheBezeichnung(parkflaeche.getBezeichnung());
                resultList.add(parkflaecheAuswahlDto);
            }
        }

        return resultList;
    }


}
