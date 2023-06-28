package com.gfi.parkplatzapp.backend.util;

public enum VerstossStatus {
    IN_BEARBEITUNG("In Bearbeitung"),
    ABGESCHLOSSEN("Abgeschlossen");

    private String enumValue;

    private VerstossStatus(String enumValue) {
        this.enumValue = enumValue;
    }

    public String getEnumValue() {
        return enumValue;
    }
}
