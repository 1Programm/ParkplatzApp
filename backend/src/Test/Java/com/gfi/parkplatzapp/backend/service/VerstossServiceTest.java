package com.gfi.parkplatzapp.backend.service;

import com.gfi.parkplatzapp.backend.Application;
import com.gfi.parkplatzapp.backend.facade.dto.VerstossDto;
import com.gfi.parkplatzapp.backend.persistence.entities.Verstoss;
import com.gfi.parkplatzapp.backend.util.VerstossStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.gfi.parkplatzapp.backend.util.VerstossStatus.IN_BEARBEITUNG;
import static org.junit.jupiter.api.Assertions.*;


@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Application.class)
@ActiveProfiles("test")
@Transactional

class VerstossServiceTest {

    @Autowired
    VerstossService verstossService;

    @Test
    public void speichernVerstoss_Test () throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2023);
        calendar.set(Calendar.MONTH, 07);
        calendar.set(Calendar.DATE, 21);
        Date datum = calendar.getTime();

        VerstossDto verstossDto = new VerstossDto(1L, 1L, datum, "Test",IN_BEARBEITUNG);
        Verstoss verstoss = verstossService.speichernVerstoss(verstossDto);
        assertEquals("Test", verstoss.getBemerkung());
        assertEquals("In Bearbeitung", verstoss.getStatus());

        assertThrows(IllegalArgumentException.class, () -> {
            VerstossDto verstossDtoError = new VerstossDto(1L, 8L, datum, "Test",IN_BEARBEITUNG);
            verstossService.speichernVerstoss(verstossDtoError);
        });
    }

    @Test
    public void getVerstoesse_Test () throws Exception {
        List<Verstoss> verstoss = verstossService.getVerstoesse(1L);
        assertEquals("In Bearbeitung", verstoss.get(0).getStatus());

        assertThrows(IllegalArgumentException.class, () -> {
            verstossService.getVerstoesse(19L);
        });

    }

}