package com.gfi.parkplatzapp.backend.persistence.repos;

import com.gfi.parkplatzapp.backend.persistence.entities.Parkflaeche;
import com.gfi.parkplatzapp.backend.persistence.entities.Parkhaus;
import com.gfi.parkplatzapp.backend.utils.AktivitaetEnum;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ParkhausRepo extends CrudRepository<Parkhaus, Long> {

    Parkhaus findByParkflaecheList_parkflaecheID(Long parkflaecheID);

    List<Parkhaus> findAllByAktivitaet(AktivitaetEnum aktivitaet);

}