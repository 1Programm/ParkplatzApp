package com.gfi.parkplatzapp.backend.facade.dto;

import com.gfi.parkplatzapp.backend.persistence.entities.DBImage;
import com.gfi.parkplatzapp.backend.persistence.entities.Parkflaeche;
import com.gfi.parkplatzapp.backend.persistence.entities.Parkhaus;
import com.gfi.parkplatzapp.backend.utils.AktivitaetEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Ein ParkhausParkflaecheDto ist ein Datenübertragungsobjekt, das die Daten eines Parkhauses und seiner Parkflächen enthält.
 * Dabei werden die Parkplätze nicht mit übertragen. Dies dient der Listenansicht für den Admin
 */
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
            parkflaeche.setAktivitaet(AktivitaetEnum.AKTIV);
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
        List<ParkflaecheDto> parkflaecheDtoList = new ArrayList<>();
        for (int i = 0; i < parkhaus.getParkflaecheList().size(); i++) {
            if(parkhaus.getParkflaecheList().get(i).getAktivitaet() == AktivitaetEnum.INAKTIV) {
                continue;
            }
            ParkflaecheDto parkflaecheDto = new ParkflaecheDto();
            parkflaecheDto.setParkflaecheID(parkhaus.getParkflaecheList().get(i).getParkflaecheID());
            parkflaecheDto.setBezeichnung(parkhaus.getParkflaecheList().get(i).getBezeichnung());
            parkflaecheDto.setImage(parkhaus.getParkflaecheList().get(i).getImage());
            parkflaecheDtoList.add(parkflaecheDto);

        }
        dto.setParkflaecheList(parkflaecheDtoList.toArray(new ParkflaecheDto[0]));
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


