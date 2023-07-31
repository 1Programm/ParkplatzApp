package com.gfi.parkplatzapp.backend.facade.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 *
 * Ein Dto, was dazu benutzt wird Daten zu einem bestimmten Werte typ zu mappen.
 * Dieses Dto wird für das Anzeigen der getätigten Buchungen benutzt.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BuchungUebersichtMappedDto<K> {

    private K key;
    private List<BuchungUebersichtDto> value;

}
