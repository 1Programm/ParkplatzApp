


-- Mitarbeiter
INSERT INTO Mitarbeiter (mitarbeiterID, mail, vorname, nachname)
VALUES(1, 'max@example.com', 'Max', 'Mustermann');

INSERT INTO Mitarbeiter (mitarbeiterID, mail, vorname, nachname)
VALUES(2, 'sabine@example.com', 'Sabine', 'Schmidt');

INSERT INTO Mitarbeiter (mitarbeiterID, mail, vorname, nachname)
VALUES(3, 'thomas@example.com', 'Thomas', 'Müller');



-- Parkflaeche
INSERT INTO Parkflaeche (parkflaecheID, bezeichnung)
VALUES(1, 'Fläche A');

INSERT INTO Parkflaeche (parkflaecheID, bezeichnung)
VALUES(2, 'Fläche B');

-- Parkhaus
INSERT INTO Parkhaus (parkhausID, bezeichnung, hausnummer, ort, plz, strasse)
VALUES(1, 'Parkhaus 1', '1', 'Berlin', '12345', 'Hauptstraße');

INSERT INTO Parkhaus (parkhausID, bezeichnung, hausnummer, ort, plz, strasse)
VALUES(2, 'Parkhaus 2', '2', 'München', '67890', 'Nebenstraße');

-- Parkplatztyp
INSERT INTO Parkplatztyp (parkplatztypID, beschreibung, bezeichnung)
VALUES(1, 'Standard-Parkplatz', 'Standard');

INSERT INTO Parkplatztyp (parkplatztypID, beschreibung, bezeichnung)
VALUES(2, 'Parkplatz für Behinderte', 'Behindertenparkplatz');

INSERT INTO Parkplatztyp (parkplatztypID, beschreibung, bezeichnung)
VALUES(3, 'VIP-Parkplatz', 'VIP');

-- Preiskategorie
INSERT INTO Preiskategorie (kategorieID, bezeichnung, preis)
VALUES(1, 'Normal', 10.00);

INSERT INTO Preiskategorie (kategorieID, bezeichnung, preis)
VALUES(2, 'Premium', 15.00);

-- Verstoss
INSERT INTO Verstoss (meldeID, bemerkung, datum)
VALUES(1, 'Falschparken', '2023-06-01');

INSERT INTO Verstoss (meldeID, bemerkung, datum)
VALUES(2, 'Geschwindigkeitsüberschreitung', '2023-06-02');

INSERT INTO Verstoss (meldeID, bemerkung, datum)
VALUES(3, 'Parken auf Behindertenparkplatz', '2023-06-03');

--Kennzeichen
INSERT INTO Kennzeichen (kennzeichenID, kennzeichen)
VALUES(1, 'DO-JB1999');

INSERT INTO Kennzeichen (kennzeichenID, kennzeichen)
VALUES(2, 'DO-JB1998');

INSERT INTO Kennzeichen (kennzeichenID, kennzeichen)
VALUES(3, 'DO-JB1997');

INSERT INTO Kennzeichen (kennzeichenID, kennzeichen)
VALUES(4, 'DO-KE1200');

-- Parkplatz
INSERT INTO Parkplatz (parkplatzID, nummer, x_Koordinate, y_Koordinate, parkplatztyp_parkplatztypID, preiskategorie_kategorieID)
VALUES(1, 'P1', 185, 132, 1, 1);

INSERT INTO Parkplatz (parkplatzID, nummer, x_Koordinate, y_Koordinate, parkplatztyp_parkplatztypID, preiskategorie_kategorieID)
VALUES(2, 'P2', 135, 132, 2, 2);

INSERT INTO Parkplatz (parkplatzID, nummer, x_Koordinate, y_Koordinate, parkplatztyp_parkplatztypID, preiskategorie_kategorieID)
VALUES(3, 'P3', 2, 300, 1, 1);

INSERT INTO Parkplatz (parkplatzID, nummer, x_Koordinate, y_Koordinate, parkplatztyp_parkplatztypID, preiskategorie_kategorieID)
VALUES(4, 'P4', 1, 10, 3, 2);

-- Buchung
INSERT INTO Buchung (buchungID, datum, tagespreis, mitarbeiter_mitarbeiterID, parkplatz_parkplatzID, kennzeichen_kennzeichenID)
VALUES(1, '2023-06-01', 10.50, 1, 1, 1);

INSERT INTO Buchung (buchungID, datum, tagespreis, mitarbeiter_mitarbeiterID, parkplatz_parkplatzID, kennzeichen_kennzeichenID)
VALUES(2, '2023-06-02', 15.00, 2, 3, 3);

INSERT INTO Buchung (buchungID, datum, tagespreis, mitarbeiter_mitarbeiterID, parkplatz_parkplatzID, kennzeichen_kennzeichenID)
VALUES(5, '2023-08-30', 15.00, 1, 3, 1);

INSERT INTO Buchung (buchungID, datum, tagespreis, mitarbeiter_mitarbeiterID, parkplatz_parkplatzID, kennzeichen_kennzeichenID)
VALUES(3, '2023-06-03', 12.75, 3, 2, 4);

INSERT INTO Buchung (buchungID, datum, tagespreis, mitarbeiter_mitarbeiterID, parkplatz_parkplatzID, kennzeichen_kennzeichenID)
VALUES(4, '2023-06-04', 9.99, 1, 4, 2);

-- Mitarbeiter_verstoss_list
INSERT INTO Mitarbeiter_verstoss_list (mitarbeiter_mitarbeiterID, verstoss_list_meldeID)
VALUES(1, 1);

INSERT INTO Mitarbeiter_verstoss_list (mitarbeiter_mitarbeiterID, verstoss_list_meldeID)
VALUES(2, 2);

INSERT INTO Mitarbeiter_verstoss_list (mitarbeiter_mitarbeiterID, verstoss_list_meldeID)
VALUES(2, 3);

--Mitarbeiter_kennzeichen_list
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

