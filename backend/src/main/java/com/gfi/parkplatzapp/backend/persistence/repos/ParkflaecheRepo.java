package com.gfi.parkplatzapp.backend.persistence.repos;

import com.gfi.parkplatzapp.backend.persistence.entities.Parkflaeche;
import com.gfi.parkplatzapp.backend.utils.AktivitaetEnum;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Map;

public interface ParkflaecheRepo extends CrudRepository<Parkflaeche, Long> {

    Parkflaeche findByParkplatzList_parkplatzID(Long parkplatzID);

    Parkflaeche findByAktivitaetAndParkflaecheID(AktivitaetEnum aktivitaet, Long parkflaecheID);
}
