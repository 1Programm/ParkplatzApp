package com.gfi.parkplatzapp.backend.service;

import com.gfi.parkplatzapp.backend.Application;
import com.gfi.parkplatzapp.backend.persistence.entities.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.parameters.P;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static com.gfi.parkplatzapp.backend.utils.AktivitaetEnum.AKTIV;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Application.class)
@ActiveProfiles("test")

@Transactional
class ParkplatzServiceTest {

    @Autowired
    ParkplatzService parkplatzService;

    @Test
    public void saveParkplatz_Test () throws Exception {
        Parkplatztyp parkplatztyp = new Parkplatztyp(1L, "Test", "probieren");
        Preiskategorie preiskategorie = new Preiskategorie(1L, "Test", 19.00);
        Parkplatz parkplatz = new Parkplatz(1L, "P4", 345, 789, AKTIV, parkplatztyp, preiskategorie, null);
        List<Parkplatz> parkplatzS = parkplatzService.saveParkplatz(parkplatz, 1L);
        assertEquals("P4", parkplatzS.get(0).getNummer());

        assertThrows(NoSuchElementException.class, () -> {
            Preiskategorie preiskategorieError = new Preiskategorie(-20L, "Test", 19.00);
            Parkplatz parkplatzError = new Parkplatz(1L, "P4", 345, 789, AKTIV, parkplatztyp, preiskategorieError, null);
            parkplatzService.saveParkplatz(parkplatzError, 1L);

        });

        assertThrows(NoSuchElementException.class, () -> {
            Parkplatztyp parkplatztypError = new Parkplatztyp(-20L, "Test", "probieren");
            Parkplatz parkplatzError = new Parkplatz(1L, "P4", 345, 789, AKTIV, parkplatztypError, preiskategorie, null);
            parkplatzService.saveParkplatz(parkplatzError, 1L);
        });

    }

    @Test
    public void deleteParkplatz_Test () throws Exception {
        Parkplatz delPl = parkplatzService.deleteParkplatz(1L);
        assertEquals("P1", delPl.getNummer());
    }


}