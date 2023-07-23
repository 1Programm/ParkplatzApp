package com.gfi.parkplatzapp.backend.facade.dto;

import com.gfi.parkplatzapp.backend.persistence.entities.Verstoss;
import com.gfi.parkplatzapp.backend.utils.VerstossStatus;
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
    private Long meldeID;
    private Date datum;
    private String mitarbeiterEmail;
    private String bemerkung;
    private VerstossStatusDto status;

    public static VerstossDto parseFromVerstoss(Verstoss verstoss, String mitarbeiterEmail) {
        VerstossDto verstossDto = new VerstossDto();
        verstossDto.setBemerkung(verstoss.getBemerkung());
        verstossDto.setStatus(VerstossStatusDto.parseFromVerstossStatus(VerstossStatus.valueOf(verstoss.getStatus())));
        verstossDto.setDatum(verstoss.getDatum());
        verstossDto.setMitarbeiterEmail(mitarbeiterEmail);
        verstossDto.setMeldeID(verstoss.getMeldeID());

        return verstossDto;
    }
}
