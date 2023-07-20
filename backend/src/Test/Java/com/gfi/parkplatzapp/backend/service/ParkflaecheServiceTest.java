package com.gfi.parkplatzapp.backend.service;

import com.gfi.parkplatzapp.backend.Application;
import com.gfi.parkplatzapp.backend.facade.dto.BuchungAbschlussDto;
import com.gfi.parkplatzapp.backend.facade.dto.ParkhausParkflaecheDto;
import com.gfi.parkplatzapp.backend.persistence.entities.Parkflaeche;
import com.gfi.parkplatzapp.backend.persistence.entities.Parkhaus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Application.class)
@ActiveProfiles("test")
@Transactional

class ParkflaecheServiceTest  {

    @Autowired
    private ParkflaecheService parkflaecheService;

    @Test
    public void getParkflaecheById_Test() throws Exception {

        assertThrows(IllegalStateException.class, () -> {
            parkflaecheService.getParkflaecheById(8L);
        });
        assertEquals("Fläche A", parkflaecheService.getParkflaecheById(1L).getBezeichnung());
    }

    @Test
    public void updateImageForParkflaeche_Test() throws Exception {
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes());
        parkflaecheService.updateImageForParkflaeche(1l, file);
    }

    @Test
    public void deleteParkflaeche_Test() throws Exception {

        ParkflaecheService mockInstance = mock(ParkflaecheService.class);
        mockInstance.deleteParkflaeche(1l);
        Mockito.verify(mockInstance).deleteParkflaeche(1L);

        assertThrows(IllegalStateException.class, () -> {
            parkflaecheService.deleteParkflaeche(-1L);
        });
    }

    @Test
    public void saveParkflaeche_Test() throws Exception {
        ParkhausParkflaecheDto.ParkflaecheDto parkflaeche = new ParkhausParkflaecheDto.ParkflaecheDto();



        assertThrows(IllegalStateException.class, () -> {
            parkflaecheService.saveParkflaeche(-1L, parkflaeche);
        });
    }



}