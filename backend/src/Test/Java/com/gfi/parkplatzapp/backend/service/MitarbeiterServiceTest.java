package com.gfi.parkplatzapp.backend.service;

import com.gfi.parkplatzapp.backend.Application;
import com.gfi.parkplatzapp.backend.persistence.entities.Kennzeichen;
import com.gfi.parkplatzapp.backend.persistence.entities.Mitarbeiter;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Application.class)
@ActiveProfiles("test")
@Transactional
class MitarbeiterServiceTest {

    @Autowired
    private MitarbeiterService mitarbeiterService;

    /**
     * Überprüfen ob der Getter Wert den zur Id zugehörigen Wert ausliefert
     * Exception überprüfen mit fehlerhafter ID
     * @throws Exception
     */

    @Test
    public void getMitarbeiter_Test () {
        Mitarbeiter mitarbeiter = mitarbeiterService.getMitarbeiter(1L);
        assertEquals("Max", mitarbeiter.getVorname() );

        assertThrows(IllegalArgumentException.class, () -> mitarbeiterService.getMitarbeiter(-5L));
    }

    /**
     * Testen ob 2 Kennzeichen für id 1 ausgegeben werden
     * @throws Exception
     */

    @Test
    public void getKennzeichenForMitarbeiter_Test() {
        List<Kennzeichen> kennzeichen = mitarbeiterService.getKennzeichenForMitarbeiter(1L);
        assertEquals(2, kennzeichen.size());
        assertEquals("DO-JB1999", kennzeichen.get(0).getKennzeichen());
        assertEquals("DO-JB1998", kennzeichen.get(1).getKennzeichen());
    }

    /**
     * Testen ob Kennzeichen geloescht wurde
     * Testen ob Exception bei fehlerhaften Angaben geworfen werdden
     * @throws Exception
     */

    @Test
    public void deleteKennzeichenFromMitarbeiter_Test() {
        Mitarbeiter mitarbeiter = mitarbeiterService.deleteKennzeichenFromMitarbeiter(1L, 2L);
        assertEquals(1, mitarbeiter.getKennzeichenList().size());
        assertThrows(IllegalArgumentException.class, () -> mitarbeiterService.deleteKennzeichenFromMitarbeiter(8L, 1L));
        assertThrows(IllegalArgumentException.class, () -> mitarbeiterService.deleteKennzeichenFromMitarbeiter(1L, 8L));
    }

    /**
     * Testen ob ein neues Kennzeichen in Liste eingefügt wurde
     * Testen ob bei falscher Mitarbeiter ID Exception geworfen wird
     * @throws Exception
     */

    @Test
    public void createKennzeichenForMitarbeiter_Tets() {
        assertThrows(IllegalArgumentException.class, () -> mitarbeiterService.createKennzeichenForMitarbeiter(8L, "MS-PD1848"));

        assertEquals(3, mitarbeiterService.createKennzeichenForMitarbeiter(1L, "MS-PD1848").getKennzeichenList().size());
    }


}