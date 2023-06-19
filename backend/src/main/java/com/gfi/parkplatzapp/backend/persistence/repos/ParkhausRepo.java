package com.gfi.parkplatzapp.backend.persistence.repos;

import com.gfi.parkplatzapp.backend.persistence.entities.Parkflaeche;
import com.gfi.parkplatzapp.backend.persistence.entities.Parkhaus;
import org.springframework.data.repository.CrudRepository;

public interface ParkhausRepo extends CrudRepository<Parkhaus, Long> {

    Parkhaus findByParkflaecheList_parkflaecheID(Long parkflaecheID);
}