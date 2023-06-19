package com.gfi.parkplatzapp.backend.service;

import com.gfi.parkplatzapp.backend.facade.dto.BuchungDto;
import com.gfi.parkplatzapp.backend.facade.dto.ParkflaecheAuswahlDto;
import com.gfi.parkplatzapp.backend.persistence.entities.*;
import com.gfi.parkplatzapp.backend.persistence.repos.*;
import jakarta.transaction.Transactional;
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

    @Autowired
    private PreiskategorieRepo preiskategorieRepo;

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

    public List<Preiskategorie> getPreiskategorien() {
        List<Preiskategorie> kategorien = new ArrayList<>();
        preiskategorieRepo.findAll().iterator().forEachRemaining(kategorien::add);
        return kategorien;
    }

    public List<Parkplatz> createParkplatz(Parkplatz parkplatz) {
        List<Parkplatz> parkplaetze = new ArrayList<>();
        parkplatz.setPreiskategorie(preiskategorieRepo.findById(parkplatz.getPreiskategorie().getKategorieID()).get());
        parkplatz.setParkplatztyp(parkplatztypRepo.findById(parkplatz.getParkplatztyp().getParkplatztypID()).get());
        parkplatzRepo.save(parkplatz);
        parkplatzRepo.findAll().iterator().forEachRemaining(parkplaetze::add);
        return parkplaetze;
    }

}
