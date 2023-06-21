package com.gfi.parkplatzapp.backend.facade.dto;

import Utils.StatusEnum;
import com.gfi.parkplatzapp.backend.persistence.entities.Parkplatz;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParkplatzMitStatusDto {
    StatusEnum status;
    Parkplatz parkplatz;

}
