package com.gfi.parkplatzapp.backend.persistence.repos;

import com.gfi.parkplatzapp.backend.persistence.entities.DBImage;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DBImageRepo extends CrudRepository<DBImage, Long> {
    Optional<DBImage> findByName(String name);
}
