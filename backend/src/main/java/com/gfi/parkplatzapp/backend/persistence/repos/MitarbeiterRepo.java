package com.gfi.parkplatzapp.backend.persistence.repos;

import com.gfi.parkplatzapp.backend.persistence.entities.Mitarbeiter;
import com.gfi.parkplatzapp.backend.persistence.entities.Verstoss;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MitarbeiterRepo extends CrudRepository<Mitarbeiter, Long> {
    Mitarbeiter findByUid(String uid);
    Mitarbeiter findByVerstossListContaining(Verstoss verstoss);
}
