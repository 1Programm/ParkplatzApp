package com.gfi.parkplatzapp.backend.persistence.repos;

import com.gfi.parkplatzapp.backend.persistence.entities.Parkflaeche;
import com.gfi.parkplatzapp.backend.persistence.entities.Parkplatz;
import org.springframework.data.repository.CrudRepository;

public interface ParkflaecheRepo extends CrudRepository<Parkflaeche, Long> {
    Parkflaeche findByParkplatzList_parkplatzID(Long parkplatzID);
}
