package com.gfi.parkplatzapp.backend.service;

import com.gfi.parkplatzapp.backend.facade.dto.BuchungDto;
import com.gfi.parkplatzapp.backend.facade.dto.ParkflaecheAuswahlDto;
import com.gfi.parkplatzapp.backend.persistence.entities.*;
import com.gfi.parkplatzapp.backend.persistence.repos.*;
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
    @Autowired
    private ParkplatzRepo parkplatzRepo;
    @Autowired
    private ParkplatztypRepo parkplatztypRepo;

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

    public List<Parkplatz> getParkplaetzeOfParkflaeche(Long parkflaecheID) {
        Parkflaeche flaeche = parkflaecheRepository.findById(parkflaecheID).get();
        return flaeche.getParkplatzList();
    }

    public List<Parkplatztyp> getParkplatztyp() {
        List<Parkplatztyp> typen = new ArrayList<>();
        parkplatztypRepo.findAll().iterator().forEachRemaining(typen::add);
        return typen;
    }


}
