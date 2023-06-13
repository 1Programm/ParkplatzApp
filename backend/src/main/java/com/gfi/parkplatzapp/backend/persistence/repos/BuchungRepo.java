package com.gfi.parkplatzapp.backend.persistence.repos;

import com.gfi.parkplatzapp.backend.persistence.entities.Buchung;
import com.gfi.parkplatzapp.backend.persistence.entities.Mitarbeiter;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BuchungRepo extends CrudRepository<Buchung, Long> {

    List<Buchung> findByMitarbeiter(Mitarbeiter mitarbeiter );
}
