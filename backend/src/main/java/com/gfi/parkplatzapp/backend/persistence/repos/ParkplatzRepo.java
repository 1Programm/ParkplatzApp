package com.gfi.parkplatzapp.backend.persistence.repos;

import com.gfi.parkplatzapp.backend.persistence.entities.Parkplatz;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ParkplatzRepo extends CrudRepository<Parkplatz, Long> {
}
