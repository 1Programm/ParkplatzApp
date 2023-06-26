package com.gfi.parkplatzapp.backend.facade;

import com.gfi.parkplatzapp.backend.persistence.entities.Mitarbeiter;
import com.gfi.parkplatzapp.backend.persistence.repos.MitarbeiterRepo;
import com.gfi.parkplatzapp.backend.service.MitarbeiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @Autowired
    private MitarbeiterService mitarbeiterService;

    @GetMapping("/mitarbeiter/id-by-technicalkey/{key}")
    public Long getMitarbeiterIdByTechnicalKey(@PathVariable("key") String technicalKey){
        System.out.println(technicalKey);

        Mitarbeiter mitarbeiter = mitarbeiterService.getMitarbeiterByTechnicalKey(technicalKey);
//        if(mitarbeiter == null){
//
//        }

        Long id = mitarbeiter.getMitarbeiterID();

        return id;
    }


}
