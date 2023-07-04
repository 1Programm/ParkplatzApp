package com.gfi.parkplatzapp.backend.facade.dto;

import com.gfi.parkplatzapp.backend.persistence.entities.Parkhaus;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ParkhausEditierenDto {
    Long parkhausID;
    String bezeichnung;
    String strasse;
    int hausnummer;
    int plz;
    String ort;

    public static ParkhausEditierenDto convertToDto(Parkhaus parkhaus){
        ParkhausEditierenDto parkhausEditierenDto = new ParkhausEditierenDto();
        parkhausEditierenDto.setParkhausID(parkhaus.getParkhausID());
        parkhausEditierenDto.setBezeichnung(parkhaus.getBezeichnung());
        parkhausEditierenDto.setStrasse(parkhaus.getStrasse());
        parkhausEditierenDto.setHausnummer(parkhaus.getHausnummer());
        parkhausEditierenDto.setPlz(parkhaus.getPlz());
        parkhausEditierenDto.setOrt(parkhaus.getOrt());
        return parkhausEditierenDto;
    }

    public static Parkhaus convertToParkhaus(ParkhausEditierenDto parkhausEditierenDto){
        Parkhaus parkhaus = new Parkhaus();
        parkhaus.setParkhausID(parkhausEditierenDto.getParkhausID());
        parkhaus.setBezeichnung(parkhausEditierenDto.getBezeichnung());
        parkhaus.setStrasse(parkhausEditierenDto.getStrasse());
        parkhaus.setHausnummer(parkhausEditierenDto.getHausnummer());
        parkhaus.setPlz(parkhausEditierenDto.getPlz());
        parkhaus.setOrt(parkhausEditierenDto.getOrt());
        return parkhaus;
    }
}
