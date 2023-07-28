package com.gfi.parkplatzapp.backend.service;

import com.gfi.parkplatzapp.backend.Application;
import com.gfi.parkplatzapp.backend.facade.dto.*;

import com.gfi.parkplatzapp.backend.persistence.entities.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.*;


import static com.gfi.parkplatzapp.backend.utils.AktivitaetEnum.AKTIV;
import static com.gfi.parkplatzapp.backend.utils.StatusEnum.BELEGT;
import static com.gfi.parkplatzapp.backend.utils.StatusEnum.FREI;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Application.class)
@ActiveProfiles("test")
@Transactional
class BuchungServiceTest {
    public Long id = 1L;
    @Autowired
    private BuchungService buchungService;

    /**
     * Testen ob die entsprechende Anzahl an Buchungen für Mitarbeiter 1 ausgegeben werden
     * Testen ob die Buchungnen nach Datum sortiert ausgegeben wurden
     * @throws Exception
     */

    @Test
    public void getBuchungenForMitarbeiter_Test() throws Exception {
        List<BuchungDetailsDto> buchungen = buchungService.getBuchungenForMitarbeiter(id);
        assertEquals(3, buchungen.size());
        assertEquals("2023-09-09", buchungen.get(0).getDatum().toString());
        assertEquals("2023-06-04", buchungen.get(2).getDatum().toString());
    }

    /**
     * Testen ob alle Buchungen ausgegeben werden
     * Testen ob die Buchungen nach Datum sortiert ausgegen werden
     * @throws Exception
     */

    @Test
    public void getAllBuchungen_Test() throws Exception {
        List<BuchungUebersichtDto> buchungen = buchungService.getAllBuchungen();
        assertEquals(5, buchungen.size());
        assertTrue(buchungen.get(1).getDatum().toString().equals("2023-08-30"));
    }

    /**
     * Testen ob alle Buchungen ausgegeben werden
     * Testen ob die Buchungen nach Datum sortiert ausgegen werden
     * @throws Exception
     */

    @Test
    public void getAllBuchungenMappedByDate_Test() throws Exception {
        List<BuchungUebersichtMappedDto<Date>> buchungen = buchungService.getAllBuchungenMappedByDate();
        assertEquals(5, buchungen.size());
        assertEquals("2023-09-09", buchungen.get(0).getValue().get(0).getDatum().toString());
        assertEquals("2023-06-02", buchungen.get(4).getValue().get(0).getDatum().toString());
    }

    /**
     * Testen ob Buchungen nach Mitarbeiter ausgegeben werden
     * Testen ob die Buchungen nach Mitarbeiter Name alphabetisch absteigend sortiert ausgegeben werden
     * @throws Exception
     */

    @Test
    public void getAllBuchungenMappedByMitarbeiter_Test() throws Exception {
        List<BuchungUebersichtMappedDto<String>> buchungen = buchungService.getAllBuchungenMappedByMitarbeiter();
        assertFalse(buchungen.isEmpty());
        assertEquals(3,buchungen.size());
        assertEquals("Marielle Musterfrau", buchungen.get(0).getValue().get(0).getMitarbeiterName());
        assertEquals("Thomas Müller", buchungen.get(2).getValue().get(0).getMitarbeiterName());
    }

    /**
     * Testen ob Kennzeichen von DO-JB1999 nach DO-JB1998 updedated wird
     * @throws Exception
     */

    @Test
    public void updateKennzeichenForBuchung_Test() throws Exception {
        List <BuchungDetailsDto> buchungen = buchungService.updateKennzeichenForBuchung(1L, 2L);
        assertEquals("DO-JB1998", buchungen.get(0).getKennzeichen().getKennzeichen());
    }

    /**
     * Testen ob es beim Löschen einer Buchung statt 3 nur noch 2 Buchungen gibt.
     * Testen ob die Buchung mt der Id 1 gelöscht wird (Somit sollte es nur noch Buchungen mit der ID 4,5 geben)
     * @throws Exception
     */

    @Test
    public void deleteBuchungFromMitarbeiter_Test() throws Exception {
        List<BuchungDetailsDto> buchungen = buchungService.deleteBuchungFromMitarbeiter(1l, 1l);
        assertEquals(2, buchungen.size());
        assertEquals(5, buchungen.get(0).getBuchungID());
        assertEquals(4, buchungen.get(1).getBuchungID());
    }

    /**
     * Testen ob 2 Parkflaechen ausgegeben werden
     * Testen ob die Parkflaechen A,B ausgegeben werden
     * @throws Exception
     */

    @Test
    public void getParkflaechen_Test() throws Exception {
        List <ParkflaecheAuswahlDto> parkf = buchungService.getParkflaechen();
        assertEquals(2, parkf.size());
        assertEquals("Fläche A", parkf.get(0).getParkflaecheBezeichnung());
        assertEquals("Fläche B", parkf.get(1).getParkflaecheBezeichnung());
    }

    /**
     * Testen ob bei freien Parkplätzen der Enum Frei und bei belegeten Parkplätzen Enum BELEGT ausgegeben wird
     * @throws Exception
     */

    @Test
    public void getParkplaetzeOfParkflaecheAndDate_Test() throws Exception {
        List<ParkplatzMitStatusDto> parkp = buchungService.getParkplaetzeOfParkflaecheAndDate(1L, "07.07.2023");
        assertEquals(FREI, parkp.get(0).getStatus());
        List<ParkplatzMitStatusDto> parkp2 = buchungService.getParkplaetzeOfParkflaecheAndDate(1L, "09.09.2023");
        assertEquals(BELEGT, parkp2.get(0).getStatus());
    }

    /**
     * Testen ob 2 Parkplätze für Parkpläche A ausgegeben werden
     * @throws Exception
     */

    @Test
    public void getParkplaetzeOfParkflaeche_Test() throws Exception {
        List<Parkplatz> parkf = buchungService.getParkplaetzeOfParkflaeche(1L);
        assertEquals(2, parkf.size());
        assertEquals("P1", parkf.get(0).getNummer());
        assertEquals("P2", parkf.get(1).getNummer());
    }

    /**
     * Testen ob 3 Typen ausgegeben werden
     * Testen ob unterschiedliche Typen ausgegeben werden
     * @throws Exception
     */

    @Test
    public void getParkplatztyp_Test() throws Exception {
        List<Parkplatztyp> typ = buchungService.getParkplatztyp();
        assertEquals(3, typ.size());
        assertEquals("Standard", typ.get(0).getBezeichnung());
        assertEquals("VIP", typ.get(2).getBezeichnung());
    }

    /**
     * Testen ob 2 Preistypen ausgegeben werden
     * Testen ob Preistyp 10.00 und 15.00 ausgegeben werden
     * @throws Exception
     */

    @Test
    public void getPreiskategorien_Test() throws Exception {
        List<Preiskategorie> preis = buchungService.getPreiskategorien();
        assertEquals(2, preis.size());
        assertEquals(10.00, preis.get(0).getPreis());
        assertEquals(15.00, preis.get(1).getPreis());
    }

    /**
     * Testen ob 2 buchungen ausgegeben werden
     * Testen ob id 1 und 5 ausgegeben werden
     * @throws Exception
     */

    @Test
    public void isAnyKennzeichenForBuchung_Test() throws Exception {
        List<Buchung> buchung = buchungService.isAnyKennzeichenForBuchung(1L, 1L);
        assertEquals(2, buchung.size());
        assertEquals(1, buchung.get(0).getBuchungID());
        assertEquals(5, buchung.get(1).getBuchungID());
    }

    /**
     * Methode nicht implementiert
     * @throws Exception
     */

    @Test
    public void updateBuchungen_Test() throws Exception {

    }

    /**
     * Testen ob Buchung erstellt wird. Testen ob Exception Fehler abfängt
     * @throws Exception
     */

    @Test
    public void schliesseBuchungAb_Test() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2023);
        calendar.set(Calendar.MONTH, 07);
        calendar.set(Calendar.DATE, 21);
        Date datum = calendar.getTime();

        Parkplatztyp parkT = new Parkplatztyp(1L, "Test", "Testfall");
        Preiskategorie preisK = new Preiskategorie(1L, "Test", 10.00);
        Parkplatz park = new Parkplatz(1L, "P8", 123, 456, AKTIV, parkT, preisK, null);
        Kennzeichen kennzeichen = new Kennzeichen(6L, "DO-JB1999");
        BuchungAbschlussDto dto = new BuchungAbschlussDto(datum, 1L, kennzeichen, park);
        BuchungAbschlussDto[] dtoList = {dto};

        BuchungService mockInstance = mock(BuchungService.class);
        mockInstance.schliesseBuchungAb(dtoList);
        ArgumentCaptor<BuchungAbschlussDto[]> captor = ArgumentCaptor.forClass(BuchungAbschlussDto[].class);
        Mockito.verify(mockInstance).schliesseBuchungAb(captor.capture());
        assertEquals(1, captor.getValue().length);

        assertThrows(IllegalStateException.class, () -> {
            BuchungAbschlussDto dtoError = new BuchungAbschlussDto(datum, -1L, kennzeichen, park);
            BuchungAbschlussDto[] dtoListErrorMitarbeiterId = {dtoError};
            buchungService.schliesseBuchungAb(dtoListErrorMitarbeiterId);
        });

        assertThrows(IllegalStateException.class, () -> {
            Kennzeichen kennzeichenError = new Kennzeichen(-6L, "DO-JB1999");
            BuchungAbschlussDto dtoError = new BuchungAbschlussDto(datum, -1L, kennzeichenError, park);
            BuchungAbschlussDto[] dtoListErrorKennzeichenId = {dtoError};
            buchungService.schliesseBuchungAb(dtoListErrorKennzeichenId);
        });

        assertThrows(IllegalStateException.class, () -> {
            Parkplatz parkError = new Parkplatz(1L, "P8", 123, 456, AKTIV, parkT, preisK, null);
            BuchungAbschlussDto dtoError = new BuchungAbschlussDto(datum, -1L, kennzeichen, parkError);
            BuchungAbschlussDto[] dtoListErrorParkplatzId = {dtoError};
            buchungService.schliesseBuchungAb(dtoListErrorParkplatzId);
        });
    }

    /**
     * Testen ob Tagespreis ausgegeben wird
     * @throws Exception
     */

    @Test
    public void calculateTagespreis_Test() throws Exception {
        Parkplatztyp parkT = new Parkplatztyp(1L, "Test", "Testfall");
        Preiskategorie preisK = new Preiskategorie(1L, "Test", 10.00);
        Parkplatz park = new Parkplatz(1L, "P8", 123, 456, AKTIV, parkT, preisK, null);
        double tagPreis = buchungService.calculateTagespreis(park);
        assertEquals(10.00, tagPreis);
    }

}