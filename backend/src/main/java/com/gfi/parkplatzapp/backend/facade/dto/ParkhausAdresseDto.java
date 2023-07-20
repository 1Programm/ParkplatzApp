package com.gfi.parkplatzapp.backend.facade.dto;


import com.gfi.parkplatzapp.backend.persistence.entities.Parkhaus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParkhausAdresseDto {

    private String strasse;
    private int hausnummer;
    private int plz;
    private String ort;

    public static ParkhausAdresseDto createFromParkhaus(Parkhaus parkhaus){
        return new ParkhausAdresseDto(parkhaus.getStrasse(), parkhaus.getHausnummer(), parkhaus.getPlz(), parkhaus.getOrt());
    }

}
