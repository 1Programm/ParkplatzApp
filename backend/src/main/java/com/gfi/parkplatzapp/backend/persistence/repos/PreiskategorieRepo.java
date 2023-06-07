package com.gfi.parkplatzapp.backend.persistence.repos;

import com.gfi.parkplatzapp.backend.persistence.entities.Preiskategorie;
import org.springframework.data.repository.CrudRepository;

public interface PreiskategorieRepo extends CrudRepository<Preiskategorie, Long> {
}
