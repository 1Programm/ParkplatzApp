package com.gfi.parkplatzapp.backend.service;

import com.gfi.parkplatzapp.backend.Application;
import com.gfi.parkplatzapp.backend.facade.dto.ParkhausParkflaecheDto;
import com.gfi.parkplatzapp.backend.persistence.entities.Parkflaeche;
import com.gfi.parkplatzapp.backend.persistence.entities.Parkhaus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
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

import static org.junit.jupiter.api.Assertions.*;

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
    public void getParkhaeuser_Test() {
        List<ParkhausParkflaecheDto> parkhaus = parkhausService.getParkhaeuser();
        assertEquals(2, parkhaus.size());
        assertEquals("Parkhaus 1", parkhaus.get(0).getBezeichnung());
        assertEquals("Parkhaus 2", parkhaus.get(1).getBezeichnung());
    }

    @Test
    public void createFromParkhaus_Test () throws Exception {
        List<Parkflaeche> parkflaeche = new ArrayList<>();
        Parkhaus parkhaus = new Parkhaus(1L, "Parkhaus 3", "Nebenstraße", 3, 44999, "Gelsenkirchen", parkflaeche);
        ParkhausParkflaecheDto parkhausflaeche = parkhausService.createFromParkhaus(parkhaus);
        assertEquals(1, parkhausflaeche.getParkhausID());
        assertEquals(0, parkhausflaeche.getParkflaecheList().length);
    }

}