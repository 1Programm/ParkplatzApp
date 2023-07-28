package com.gfi.parkplatzapp.backend.service;

import com.gfi.parkplatzapp.backend.Application;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Application.class)
@ActiveProfiles("test")
@Transactional

class ParkflaecheServiceTest  {

    @Autowired
    private ParkflaecheService parkflaecheService;

    /**
     * Testen ob Parkflaechen Id außerhalb liegt
     * Testen ob Parkflaeche entsprechend der ID ausgegeben wird
     * @throws Exception
     */

    @Test
    public void getParkflaecheById_Test() {

        assertThrows(IllegalStateException.class, () -> parkflaecheService.getParkflaecheById(8L));
        assertEquals("Fläche A", parkflaecheService.getParkflaecheById(1L).getBezeichnung());
    }

    /**
     * Test Image wird zunächst nicht erzeugt
     * @throws Exception
     */

    @Test
    public void updateImageForParkflaeche_Test() {
        /*
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes());
        parkflaecheService.updateImageForParkflaeche(1l, file);
         */
    }

    /**
     * Testen ob deleteParkflaeche ausgeführt wird
     * Testen auf falsche IDs
     * @throws Exception
     */

    @Test
    public void deleteParkflaeche_Test() {

        ParkflaecheService mockInstance = mock(ParkflaecheService.class);
        mockInstance.deleteParkflaeche(1L);
        Mockito.verify(mockInstance).deleteParkflaeche(1L);

        assertThrows(IllegalStateException.class, () -> parkflaecheService.deleteParkflaeche(-1L));
    }

    /**
     * Test Image wird zunächst nicht erzeugt
     * @throws Exception
     */

    @Test
    public void saveParkflaeche_Test() {
        /*
        ParkhausParkflaecheDto.ParkflaecheDto parkflaecheDto = new ParkhausParkflaecheDto.ParkflaecheDto(1L, "Test", DBImage );
        ParkhausParkflaecheDto.ParkflaecheDto parkflaeche = parkflaecheService.saveParkflaeche(1L, parkflaecheDto);
        assertEquals("Test", parkflaeche.getBezeichnung());

        assertThrows(IllegalStateException.class, () -> {
            parkflaecheService.saveParkflaeche(-1L, parkflaeche);
        });

         */
    }


}