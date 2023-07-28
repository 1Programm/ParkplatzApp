package com.gfi.parkplatzapp.backend.service;

import com.gfi.parkplatzapp.backend.Application;
import com.gfi.parkplatzapp.backend.persistence.entities.Parkplatz;
import com.gfi.parkplatzapp.backend.persistence.entities.Parkplatztyp;
import com.gfi.parkplatzapp.backend.persistence.entities.Preiskategorie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static com.gfi.parkplatzapp.backend.utils.AktivitaetEnum.AKTIV;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Application.class)
@ActiveProfiles("test")

@Transactional
class ParkplatzServiceTest {

    @Autowired
    ParkplatzService parkplatzService;

    /**
     * Testen ob saveParkplatz  einen neuen Parkplatz anlegt
     * Testen ob falsche ID bei preisKategorie oder parkplatzTyp geworfen wird
     * @throws Exception
     */

    @Test
    public void saveParkplatz_Test () {
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

    /**
     *
     * @throws Exception
     */

    @Test
    public void deleteParkplatz_Test () {
        Parkplatz delPl = parkplatzService.deleteParkplatz(1L);
        assertEquals("P1", delPl.getNummer());
        //assertEquals(3, delPl.);
        //delPl.getParkflaeche().getParkplatzList()
    }

}