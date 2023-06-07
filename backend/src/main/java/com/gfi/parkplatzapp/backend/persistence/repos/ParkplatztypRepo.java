package com.gfi.parkplatzapp.backend.persistence.repos;

import com.gfi.parkplatzapp.backend.persistence.entities.Parkplatztyp;
import org.springframework.data.repository.CrudRepository;

public interface ParkplatztypRepo extends CrudRepository<Parkplatztyp, Long> {
}
