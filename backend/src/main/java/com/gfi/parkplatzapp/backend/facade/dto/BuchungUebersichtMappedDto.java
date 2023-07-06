package com.gfi.parkplatzapp.backend.facade.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BuchungUebersichtMappedDto<K> {

    private K key;
    private List<BuchungUebersichtDto> value;

}
