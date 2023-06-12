package com.gfi.parkplatzapp.backend.persistence.repos;

import com.gfi.parkplatzapp.backend.persistence.entities.Buchung;
import org.springframework.data.repository.CrudRepository;

public interface BuchungRepo extends CrudRepository<Buchung, Long> {
}
