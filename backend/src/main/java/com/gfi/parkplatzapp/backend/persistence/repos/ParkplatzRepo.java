package com.gfi.parkplatzapp.backend.persistence.repos;

import com.gfi.parkplatzapp.backend.persistence.entities.Parkplatz;
import org.springframework.data.repository.CrudRepository;

public interface ParkplatzRepo extends CrudRepository<Parkplatz, Long> {
}
