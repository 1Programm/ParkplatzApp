package com.gfi.parkplatzapp.backend.facade.dto;

import com.gfi.parkplatzapp.backend.persistence.entities.DBImage;
import com.gfi.parkplatzapp.backend.persistence.entities.Parkflaeche;
import com.gfi.parkplatzapp.backend.persistence.entities.Parkhaus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParkhausParkflaecheDto {
    String bezeichnung;
    Long parkhausID;
    String strasse;
    int hausnummer;
    int plz;
    String ort;
    ParkflaecheDto[] parkflaecheList;

    @Getter
    @Setter
    public static class ParkflaecheDto {
        Long parkflaecheID;
        String bezeichnung;
        DBImage image;


        public static Parkflaeche createFromParkflaecheDto(ParkhausParkflaecheDto.ParkflaecheDto dto) {
            Parkflaeche parkflaeche = new Parkflaeche();
            parkflaeche.setParkflaecheID(dto.getParkflaecheID());
            parkflaeche.setImage(dto.getImage());
            parkflaeche.setBezeichnung(dto.getBezeichnung());
            parkflaeche.setParkplatzList(null);
            return parkflaeche;
        }

        public static ParkhausParkflaecheDto.ParkflaecheDto createFromParkflaeche(Parkflaeche parkflaeche) {
            ParkhausParkflaecheDto.ParkflaecheDto parkflaecheDto = new ParkhausParkflaecheDto.ParkflaecheDto();
            parkflaecheDto.setParkflaecheID(parkflaeche.getParkflaecheID());
            parkflaecheDto.setBezeichnung(parkflaeche.getBezeichnung());
            parkflaecheDto.setImage(parkflaeche.getImage());
            return parkflaecheDto;
        }
    }

    public static ParkhausParkflaecheDto createFromParkhaus(Parkhaus parkhaus) {
        ParkhausParkflaecheDto dto = new ParkhausParkflaecheDto();
        dto.setBezeichnung(parkhaus.getBezeichnung());
        dto.setParkhausID(parkhaus.getParkhausID());
        dto.setStrasse(parkhaus.getStrasse());
        dto.setHausnummer(parkhaus.getHausnummer());
        dto.setPlz(parkhaus.getPlz());
        dto.setOrt(parkhaus.getOrt());
        dto.setParkflaecheList(new ParkflaecheDto[parkhaus.getParkflaecheList().size()]);
        for (int i = 0; i < parkhaus.getParkflaecheList().size(); i++) {
            dto.getParkflaecheList()[i] = new ParkflaecheDto();
            dto.getParkflaecheList()[i].setParkflaecheID(parkhaus.getParkflaecheList().get(i).getParkflaecheID());
            dto.getParkflaecheList()[i].setBezeichnung(parkhaus.getParkflaecheList().get(i).getBezeichnung());
            dto.getParkflaecheList()[i].setImage(parkhaus.getParkflaecheList().get(i).getImage());
        }
        return dto;
    }

    public static Parkhaus createFromDto(ParkhausParkflaecheDto dto) {
        Parkhaus parkhaus = new Parkhaus();
        parkhaus.setBezeichnung(dto.getBezeichnung());
        parkhaus.setParkhausID(dto.getParkhausID());
        parkhaus.setStrasse(dto.getStrasse());
        parkhaus.setHausnummer(dto.getHausnummer());
        parkhaus.setPlz(dto.getPlz());
        parkhaus.setOrt(dto.getOrt());
        return parkhaus;
    }
}


