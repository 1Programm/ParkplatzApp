package com.gfi.parkplatzapp.backend.persistence.repos;

import com.gfi.parkplatzapp.backend.persistence.entities.Verstoss;
import org.springframework.data.repository.CrudRepository;

public interface VerstossRepo extends CrudRepository<Verstoss, Long> {
}
