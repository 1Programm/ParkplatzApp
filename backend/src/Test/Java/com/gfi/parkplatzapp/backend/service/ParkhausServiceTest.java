package com.gfi.parkplatzapp.backend.service;

import com.gfi.parkplatzapp.backend.Application;
import com.gfi.parkplatzapp.backend.facade.dto.BuchungAbschlussDto;
import com.gfi.parkplatzapp.backend.facade.dto.ParkhausEditierenDto;
import com.gfi.parkplatzapp.backend.facade.dto.ParkhausParkflaecheDto;
import com.gfi.parkplatzapp.backend.persistence.entities.Parkflaeche;
import com.gfi.parkplatzapp.backend.persistence.entities.Parkhaus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.gfi.parkplatzapp.backend.utils.AktivitaetEnum.AKTIV;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Application.class)
@ActiveProfiles("test")

@Transactional
class ParkhausServiceTest {

    @Autowired
    private ParkhausService parkhausService;

    @Test
    public void getParkhaeuser_Test() throws Exception{
        List<ParkhausParkflaecheDto> parkhaus = parkhausService.getParkhaeuser();
        assertEquals(2, parkhaus.size());
        assertEquals("Parkhaus 1", parkhaus.get(0).getBezeichnung());
        assertEquals("Parkhaus 2", parkhaus.get(1).getBezeichnung());
    }

    @Test
    public void getParkhaus() throws Exception{
        ParkhausEditierenDto parkhaus = parkhausService.getParkhaus(1L);
        assertEquals("Parkhaus 1", parkhaus.getBezeichnung());

        assertThrows(IllegalStateException.class, () -> {
            parkhausService.getParkhaus(-1L);
        });

    }

    @Test
    public void saveParkhaus_Test() throws Exception {
        ParkhausEditierenDto parkhausEditierenDto = new ParkhausEditierenDto(1L, "Test", "Teststraße", 5, 9999, "Gelsenkirchen");
        ParkhausEditierenDto parkhaus = parkhausService.saveParkhaus(parkhausEditierenDto);
        assertEquals("Test", parkhaus.getBezeichnung());

        ParkhausEditierenDto parkhausEditierenDtoNull = new ParkhausEditierenDto(null, "Test", "Teststraße", 5, 9999, "Gelsenkirchen");
        Parkhaus parkhausEdit = ParkhausEditierenDto.convertToParkhaus(parkhausEditierenDtoNull);
        assertEquals(AKTIV, parkhausEdit.getAktivitaet());

    }

    @Test
    public void deleteParkhaus_Test() throws Exception {
        ParkhausService mockInstance = mock(ParkhausService.class);
        mockInstance.deleteParkhaus(1L);
        Mockito.verify(mockInstance).deleteParkhaus(1L);

        assertThrows(IllegalStateException.class, () -> {
            parkhausService.deleteParkhaus(-1L);
        });
    }

}