package com.gfi.parkplatzapp.backend.facade.dto;

import com.gfi.parkplatzapp.backend.utils.StatusEnum;
import com.gfi.parkplatzapp.backend.persistence.entities.Parkplatz;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Dieses Dto wird für das Darstellen eines Markers benutzt,
 * der je nachdem ob ein Parkplatz bereits belegt ist anders angezeigt werden soll.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParkplatzMitStatusDto {
    StatusEnum status;
    Parkplatz parkplatz;
}
