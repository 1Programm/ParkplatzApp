-- Mitarbeiter
INSERT INTO Mitarbeiter (uid, mail, vorname, nachname)
VALUES('4cd18d0c-fc5f-44c5-a12c-9ed1ef09c945', 'max-mustermann@web.de', 'Max', 'Mustermann');

INSERT INTO Mitarbeiter (uid, mail, vorname, nachname)
VALUES('a9ec3008-2295-47d4-8b8e-83ce3b4f6470', 'marielle-musterfrau@gmx.de', 'Marielle', 'Musterfrau');

INSERT INTO Mitarbeiter (uid, mail, vorname, nachname)
VALUES('2a7051ab-a5f4-462d-b011-440388fd5208', 'thomas@example.com', 'Thomas', 'Müller');



-- Parkflaeche
INSERT INTO Parkflaeche (bezeichnung, aktivitaet)
VALUES('Fläche A', 'AKTIV');

INSERT INTO Parkflaeche (bezeichnung, aktivitaet)
VALUES('Fläche B', 'AKTIV');



-- Parkhaus
INSERT INTO Parkhaus (bezeichnung, hausnummer, ort, plz, strasse, aktivitaet)
VALUES('Parkhaus 1', '1', 'Berlin', '12345', 'Hauptstraße', 'AKTIV');

INSERT INTO Parkhaus (bezeichnung, hausnummer, ort, plz, strasse, aktivitaet)
VALUES('Parkhaus 2', '2', 'München', '67890', 'Nebenstraße', 'AKTIV');



-- Parkplatztyp
INSERT INTO Parkplatztyp (beschreibung, bezeichnung)
VALUES('Standard-Parkplatz', 'Standard');

INSERT INTO Parkplatztyp (beschreibung, bezeichnung)
VALUES('Parkplatz für Behinderte', 'Behindertenparkplatz');

INSERT INTO Parkplatztyp (beschreibung, bezeichnung)
VALUES('VIP-Parkplatz', 'VIP');



-- Preiskategorie
INSERT INTO Preiskategorie (bezeichnung, preis)
VALUES('Normal', 10.00);

INSERT INTO Preiskategorie (bezeichnung, preis)
VALUES('Premium', 15.00);



-- Verstoss
INSERT INTO Verstoss (datum, bemerkung, status)
VALUES('2023-06-01', 'Falschparken', 'In_Bearbeitung');

INSERT INTO Verstoss (datum, bemerkung, status)
VALUES('2023-06-02', 'Geschwindigkeitsüberschreitung', 'In_Bearbeitung');

INSERT INTO Verstoss (datum, bemerkung, status)
VALUES('2023-06-03', 'Parken auf Behindertenparkplatz', 'In_Bearbeitung');


-- Kennzeichen
INSERT INTO Kennzeichen (kennzeichen)
VALUES('DO-JB1999');

INSERT INTO Kennzeichen (kennzeichen)
VALUES('DO-JB1998');

INSERT INTO Kennzeichen (kennzeichen)
VALUES('DO-JB1997');

INSERT INTO Kennzeichen (kennzeichen)
VALUES('DO-KE1200');



-- Parkplatz
INSERT INTO Parkplatz (nummer, x_Koordinate, y_Koordinate, parkplatztyp_parkplatztypID, preiskategorie_kategorieID, aktivitaet)
VALUES('P1', 185, 132, 1, 1, 'AKTIV');

INSERT INTO Parkplatz (nummer, x_Koordinate, y_Koordinate, parkplatztyp_parkplatztypID, preiskategorie_kategorieID, aktivitaet)
VALUES('P2', 135, 132, 2, 2, 'AKTIV');

INSERT INTO Parkplatz (nummer, x_Koordinate, y_Koordinate, parkplatztyp_parkplatztypID, preiskategorie_kategorieID, aktivitaet)
VALUES('P3', 2, 300, 1, 1, 'AKTIV');

INSERT INTO Parkplatz ( nummer, x_Koordinate, y_Koordinate, parkplatztyp_parkplatztypID, preiskategorie_kategorieID, aktivitaet)
VALUES('P4', 1, 10, 3, 2, 'AKTIV');



-- Buchung
INSERT INTO Buchung (datum, tagespreis, mitarbeiter_mitarbeiterID, parkplatz_parkplatzID, kennzeichen_kennzeichenID)
VALUES('2023-06-01', 10.50, 1, 1, 1);

INSERT INTO Buchung (datum, tagespreis, mitarbeiter_mitarbeiterID, parkplatz_parkplatzID, kennzeichen_kennzeichenID)
VALUES('2023-06-02', 15.00, 2, 3, 3);

INSERT INTO Buchung (datum, tagespreis, mitarbeiter_mitarbeiterID, parkplatz_parkplatzID, kennzeichen_kennzeichenID)
VALUES('2023-06-03', 12.75, 3, 2, 4);

INSERT INTO Buchung (datum, tagespreis, mitarbeiter_mitarbeiterID, parkplatz_parkplatzID, kennzeichen_kennzeichenID)
VALUES('2023-06-04', 9.99, 1, 4, 2);

INSERT INTO Buchung (datum, tagespreis, mitarbeiter_mitarbeiterID, parkplatz_parkplatzID, kennzeichen_kennzeichenID)
VALUES('2023-08-30', 15.00, 1, 3, 1);






-- BEZIEHUNGEN



-- Mitarbeiter_verstoss_list
INSERT INTO Mitarbeiter_verstoss_list (mitarbeiter_mitarbeiterID, verstoss_list_meldeID)
VALUES(1, 1);

INSERT INTO Mitarbeiter_verstoss_list (mitarbeiter_mitarbeiterID, verstoss_list_meldeID)
VALUES(2, 2);

INSERT INTO Mitarbeiter_verstoss_list (mitarbeiter_mitarbeiterID, verstoss_list_meldeID)
VALUES(2, 3);



-- Mitarbeiter_kennzeichen_list
INSERT INTO Mitarbeiter_kennzeichen_list(mitarbeiter_mitarbeiterID, kennzeichen_list_kennzeichenID)
VALUES(1,1);

INSERT INTO Mitarbeiter_kennzeichen_list(mitarbeiter_mitarbeiterID, kennzeichen_list_kennzeichenID)
VALUES(1,2);

INSERT INTO Mitarbeiter_kennzeichen_list(mitarbeiter_mitarbeiterID, kennzeichen_list_kennzeichenID)
VALUES(2,3);

INSERT INTO Mitarbeiter_kennzeichen_list(mitarbeiter_mitarbeiterID, kennzeichen_list_kennzeichenID)
VALUES(3,4);



-- parkflaeche_parkplatz_list
INSERT INTO parkflaeche_parkplatz_list (parkflaeche_parkflaecheID, parkplatz_list_parkplatzID)
VALUES(1, 1);

INSERT INTO parkflaeche_parkplatz_list (parkflaeche_parkflaecheID, parkplatz_list_parkplatzID)
VALUES(1, 2);

INSERT INTO parkflaeche_parkplatz_list (parkflaeche_parkflaecheID, parkplatz_list_parkplatzID)
VALUES(2, 3);

INSERT INTO parkflaeche_parkplatz_list (parkflaeche_parkflaecheID, parkplatz_list_parkplatzID)
VALUES(2, 4);



-- parkhaus_parkflaeche_list
INSERT INTO parkhaus_parkflaeche_list (parkhaus_parkhausID, parkflaeche_list_parkflaecheID)
VALUES(1, 1);

INSERT INTO parkhaus_parkflaeche_list (parkhaus_parkhausID, parkflaeche_list_parkflaecheID)
VALUES(1, 2);

