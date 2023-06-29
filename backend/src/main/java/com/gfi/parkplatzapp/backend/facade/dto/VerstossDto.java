package com.gfi.parkplatzapp.backend.facade.dto;

import com.gfi.parkplatzapp.backend.persistence.entities.Kennzeichen;
import com.gfi.parkplatzapp.backend.util.VerstossStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VerstossDto {
    private Long verstossID;
    private Long mitarbeiterID;
    private Date datum;
    private String bemerkung;
    private VerstossStatus status;
}
