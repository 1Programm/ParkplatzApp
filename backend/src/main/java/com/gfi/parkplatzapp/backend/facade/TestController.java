package com.gfi.parkplatzapp.backend.facade;

import com.gfi.parkplatzapp.backend.persistence.entities.Parkflaeche;
import com.gfi.parkplatzapp.backend.persistence.entities.Parkhaus;
import com.gfi.parkplatzapp.backend.persistence.entities.Parkplatz;
import com.gfi.parkplatzapp.backend.persistence.repos.ParkhausRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@CrossOrigin
@Slf4j
@RestController
public class TestController {

    @Autowired
    private ParkhausRepo parkhausRepo;

    @GetMapping("/test")
    public void test2(){
        getParkhausAndLog(1);
        getParkhausAndLog(2);
    }

    private void getParkhausAndLog(long id){
        Optional<Parkhaus> op = parkhausRepo.findById(id);
        if(op.isEmpty()) {
            log.error("No such Parkhaus with id [{}]!", id);
            return;
        }

        Parkhaus p = op.get();

        log.info("Found Parkhaus with id [{}]: {}", id, p.getBezeichnung());
        log.info("Parkhaus has [{}] Parkflaechen.", p.getParkflaecheList().size());

        for(Parkflaeche pf : p.getParkflaecheList()){
            logParkflaeche(pf);
        }
    }

    private void logParkflaeche(Parkflaeche pf){
        log.info("# Parkflaeche with id: [{}]: {}", pf.getParkflaecheID(), pf.getBezeichnung());
        log.info("Parkflaeche has [{}] Parkplaetze.", pf.getParkplatzList().size());

        for(Parkplatz pp : pf.getParkplatzList()){
            logParkplatz(pp);
        }
    }

    private void logParkplatz(Parkplatz pp) {
        log.info("# - Parkplatz with id: [{}]: {} ({}, {})", pp.getParkplatzID(), pp.getNummer(), pp.getParkplatztyp(), pp.getPreiskategorie());
    }

    }
