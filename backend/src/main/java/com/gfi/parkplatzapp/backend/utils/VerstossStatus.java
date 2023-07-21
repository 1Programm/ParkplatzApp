package com.gfi.parkplatzapp.backend.utils;

public enum VerstossStatus {
    IN_BEARBEITUNG("In Bearbeitung"),
    ABGESCHLOSSEN("Abgeschlossen");

    private String label;

    VerstossStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
