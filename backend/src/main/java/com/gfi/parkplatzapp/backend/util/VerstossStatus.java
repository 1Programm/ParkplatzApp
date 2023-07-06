package com.gfi.parkplatzapp.backend.util;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum VerstossStatus {
    IN_BEARBEITUNG("In_Bearbeitung"),
    ABGESCHLOSSEN("Abgeschlossen");

    private String enumValue;

    @JsonCreator
    public static VerstossStatus parseFromString(String value) {
        return VerstossStatus.valueOf(value.toUpperCase());
    }

    private VerstossStatus(String enumValue) {
        this.enumValue = enumValue;
    }

    public String getEnumValue() {
        return enumValue;
    }
}
