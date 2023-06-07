package com.gfi.parkplatzapp.backend.persistence.repos;

import com.gfi.parkplatzapp.backend.persistence.entities.Kennzeichen;
import org.springframework.data.repository.CrudRepository;

public interface KennzeichenRepo extends CrudRepository<Kennzeichen, Long> {
}
