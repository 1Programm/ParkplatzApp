package com.gfi.parkplatzapp.backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gfi.parkplatzapp.backend.Application;
import com.gfi.parkplatzapp.backend.persistence.entities.Kennzeichen;
import com.gfi.parkplatzapp.backend.persistence.entities.Mitarbeiter;
import com.gfi.parkplatzapp.backend.persistence.repos.MitarbeiterRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.RequestEntity.post;

//@WebMvcTest(controllers = MitarbeiterService.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Application.class)
@ActiveProfiles("test")
@Transactional
class MitarbeiterServiceTest {

    @Autowired
    private MockMvc mockMvc;
//    @MockBean
    @Autowired
    private MitarbeiterService mitarbeiterService;
    @Autowired
    private ObjectMapper objectMapper;

    Long id = 1L;

    @Test
    public void getMitarbeiter_Test () throws Exception {
        Mitarbeiter mitarbeiter = mitarbeiterService.getMitarbeiter(id);
        assertEquals("Max", mitarbeiter.getVorname() );

        assertThrows(IllegalArgumentException.class, () -> {
            mitarbeiterService.getMitarbeiter(-5L);
        });
    }

    @Test
    public void getKennzeichenForMitarbeiter_Test() throws Exception {
        List<Kennzeichen> kennzeichen = mitarbeiterService.getKennzeichenForMitarbeiter(id);
        assertEquals(2, kennzeichen.size());
        assertEquals("DO-JB1999", kennzeichen.get(0).getKennzeichen());
        assertEquals("DO-JB1998", kennzeichen.get(1).getKennzeichen());
    }

    @Test
    public void deleteKennzeichenFromMitarbeiter_Test() throws Exception {

        assertThrows(IllegalArgumentException.class, () -> {
            mitarbeiterService.deleteKennzeichenFromMitarbeiter(8L, 1L);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            mitarbeiterService.deleteKennzeichenFromMitarbeiter(1L, 8L);
        });

        assertEquals(1, mitarbeiterService.deleteKennzeichenFromMitarbeiter(1L,1L).getKennzeichenList().size());

    }

    @Test
    public void createKennzeichenForMitarbeiter_Tets() throws Exception {

        assertThrows(IllegalArgumentException.class, () -> {
            mitarbeiterService.createKennzeichenForMitarbeiter(8L, "MS-PD1848");
        });

        assertEquals(3, mitarbeiterService.createKennzeichenForMitarbeiter(1L, "MS-PD1848").getKennzeichenList().size());

    }


}