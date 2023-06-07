package com.gfi.parkplatzapp.backend.service;

import com.gfi.parkplatzapp.backend.persistence.entities.Mitarbeiter;
import com.gfi.parkplatzapp.backend.persistence.repos.MitarbeiterRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProfildatenService {


    @Autowired
    private MitarbeiterRepo mitarbeiterRepository;

    public Mitarbeiter getMitarbeiter(Long id) {
        log.info("bla", mitarbeiterRepository.findById(id).get());

        return mitarbeiterRepository.findById(id).get();
    }


}
