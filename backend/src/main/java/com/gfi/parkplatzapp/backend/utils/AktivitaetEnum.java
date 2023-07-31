package com.gfi.parkplatzapp.backend.utils;

/**
 * Wird benutzt, um ein Datenbankeintrag als 'gelöscht' zu kennzeichnen.
 * Es kann zum beispiel passieren, dass ein Parkplatz gelöscht wird,
 * aber um die Historie der Buchungen noch anzeigen zu können weiterhin benötigt wird.
 * Dann wird der Status einfach auf 'INAKTIV' gesetzt, um das Objekt als 'gelöscht' zu kennzeichnen.
 */
public enum AktivitaetEnum {
    AKTIV, INAKTIV
}
