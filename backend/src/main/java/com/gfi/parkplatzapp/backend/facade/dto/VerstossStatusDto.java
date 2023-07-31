package com.gfi.parkplatzapp.backend.facade.dto;

import com.gfi.parkplatzapp.backend.utils.VerstossStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Ein Json Wrapper - Dto für das VertossStatus - Enum.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VerstossStatusDto {
    private String key;
    private String label;

    public static VerstossStatusDto parseFromVerstossStatus(VerstossStatus verstossStatus) {
        return new VerstossStatusDto(verstossStatus.toString(), verstossStatus.getLabel());
    }
}
