package com.gfi.parkplatzapp.backend.service;

import com.gfi.parkplatzapp.backend.Application;
import com.gfi.parkplatzapp.backend.facade.dto.*;

import com.gfi.parkplatzapp.backend.persistence.entities.*;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.List;


import static com.gfi.parkplatzapp.backend.utils.StatusEnum.BELEGT;
import static com.gfi.parkplatzapp.backend.utils.StatusEnum.FREI;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Application.class)
@ActiveProfiles("test")
@Transactional
class BuchungServiceTest {
    public Long id = 1L;
    @Autowired
    private BuchungService buchungService;
    @Autowired
    BuchungAbschlussDto[] dto;
    //@Autowired
    //private Parkplatz p1;

    @Test
    public void getBuchungenForMitarbeiter_Test() throws Exception {
        List<BuchungDetailsDto> buchungen = buchungService.getBuchungenForMitarbeiter(id);
        assertFalse(buchungen.isEmpty());
        assertEquals(3, buchungen.size());
    }

    @Test
    public void getAllBuchungen_Test() throws Exception {
        List<BuchungUebersichtDto> buchungen = buchungService.getAllBuchungen();
        assertFalse(buchungen.isEmpty());
        assertTrue(buchungen.get(1).getDatum().toString().equals("2023-08-30"));
    }

    @Test
    public void getAllBuchungenMappedByDate_Test() throws Exception {
        List<BuchungUebersichtMappedDto<Date>> buchungen = buchungService.getAllBuchungenMappedByDate();
        assertFalse(buchungen.isEmpty());
        assertEquals(3, buchungen.size());
        assertTrue(buchungen.get(1).toString().equals("2023-06-01"));
        // getDatum().toString().equals("2023-06-01")
        //n.f
    }

    @Test
    public void getAllBuchungenMappedByMitarbeiter_Test() throws Exception {
        List<BuchungUebersichtMappedDto<String>> buchungen = buchungService.getAllBuchungenMappedByMitarbeiter();
        assertFalse(buchungen.isEmpty());
        //assertEquals(buchungen.get(0));
        // n.f

    }

    @Test
    public void updateKennzeichenForBuchung_Test() throws Exception {
        List <BuchungDetailsDto> buchungen = buchungService.updateKennzeichenForBuchung(1L, 2L);
        assertEquals("DO-JB1998", buchungen.get(1).getKennzeichen().getKennzeichen().toString());
        //Expected :DO-JB1998
        //Actual   :DO-JB1999
    }

    @Test
    public void deleteBuchungFromMitarbeiter_Test() throws Exception {
        List<BuchungDetailsDto> buchungen = buchungService.deleteBuchungFromMitarbeiter(1l, 1l);
        assertEquals(2, buchungen.size());
        assertEquals(5, buchungen.get(0).getBuchungID());
        assertEquals(4, buchungen.get(1).getBuchungID());
    }

    @Test
    public void getParkflaechen_Test() throws Exception {
        List <ParkflaecheAuswahlDto> parkf = buchungService.getParkflaechen();
        assertEquals(2, parkf.size());

    }

    @Test
    public void getParkplaetzeOfParkflaecheAndDate_Test() throws Exception {
        List<ParkplatzMitStatusDto> parkp = buchungService.getParkplaetzeOfParkflaecheAndDate(1L, "07.07.2023");
        assertEquals(FREI, parkp.get(0).getStatus());
        //assertEquals(BELEGT, parkp.get(1).getStatus()); muss ich noch machen

        assertThrows(ParseException.class, () -> {
            buchungService.getParkplaetzeOfParkflaecheAndDate(1L, "07.2023");
        });

    }

    @Test
    public void getParkplaetzeOfParkflaeche_Test() throws Exception {
        List<Parkplatz> parkf = buchungService.getParkplaetzeOfParkflaeche(1L);
        assertEquals(2, parkf.size());
        assertEquals("P1", parkf.get(0).getNummer());
    }

    @Test
    public void getParkplatztyp_Test() throws Exception {
        List<Parkplatztyp> typ = buchungService.getParkplatztyp();
        assertEquals("Standard", typ.get(0).getBezeichnung());

    }

    @Test
    public void getPreiskategorien_Test() throws Exception {
        List<Preiskategorie> preis = buchungService.getPreiskategorien();
        assertEquals(10.00, preis.get(0).getPreis());
    }

    @Test
    public void isAnyKennzeichenForBuchung_Test() throws Exception {
        List<Buchung> buchung = buchungService.isAnyKennzeichenForBuchung(1L, 1L);
        assertEquals(2, buchung.size());
        assertEquals(1, buchung.get(0).getBuchungID());
        assertEquals(5, buchung.get(1).getBuchungID());
    }

    @Test
    public void updateBuchungen_Test() throws Exception {

    }

    @Test
    public void schliesseBuchungAb_Test() throws Exception {
        buchungService.schliesseBuchungAb(dto);

    }

    @Test
    public void calculateTagespreis_Test() throws Exception {
        // List buchungen = buchungService.getBuchungenForMitarbeiter(id);
        // System.out.println(p1.getNummer());
        // buchungService.calculateTagespreis(P1)
        // assertNull( buchungen.isEmpty());
    }

}