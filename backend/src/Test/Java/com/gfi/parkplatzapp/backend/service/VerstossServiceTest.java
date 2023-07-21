package com.gfi.parkplatzapp.backend.service;

import com.gfi.parkplatzapp.backend.Application;
import com.gfi.parkplatzapp.backend.facade.dto.VerstossDto;
import com.gfi.parkplatzapp.backend.facade.dto.VerstossStatusDto;
import com.gfi.parkplatzapp.backend.persistence.entities.Verstoss;
import com.gfi.parkplatzapp.backend.utils.VerstossStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.gfi.parkplatzapp.backend.utils.VerstossStatus.ABGESCHLOSSEN;
import static com.gfi.parkplatzapp.backend.utils.VerstossStatus.IN_BEARBEITUNG;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


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

        VerstossDto verstossDto = new VerstossDto(1L, datum, "Test", VerstossStatusDto.parseFromVerstossStatus(IN_BEARBEITUNG));
        Verstoss verstoss = verstossService.speichernVerstoss(1, verstossDto);
        assertEquals("Test", verstoss.getBemerkung());
        assertEquals("IN_BEARBEITUNG", verstoss.getStatus());

        assertThrows(IllegalArgumentException.class, () -> {
            VerstossDto verstossDtoError = new VerstossDto(1L, datum, "Test", VerstossStatusDto.parseFromVerstossStatus(IN_BEARBEITUNG));
            verstossService.speichernVerstoss(8, verstossDtoError);
        });
    }

    @Test
    public void getVerstoesseForMitarbeiter_Test () throws Exception {
        List<VerstossDto> verstoss = verstossService.getVerstoesseForMitatbeiter(1L);
        assertEquals("IN_BEARBEITUNG", verstoss.get(0).getStatus().getKey());

        assertThrows(IllegalArgumentException.class, () -> {
            verstossService.getVerstoesseForMitatbeiter(19L);
        });

    }

    @Test
    public void getAllVerstoesse_Test() throws Exception {
        List<VerstossDto> verstoss = verstossService.getAllVerstoesse();
        assertEquals(3, verstoss.size());
    }

    @Test
    public void changeStatusForVerstoss_Test() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2023);
        calendar.set(Calendar.MONTH, 07);
        calendar.set(Calendar.DATE, 21);
        Date datum = calendar.getTime();
        VerstossDto verstossDto = new VerstossDto(10L, datum, "Test", VerstossStatusDto.parseFromVerstossStatus(ABGESCHLOSSEN));
        assertEquals(ABGESCHLOSSEN.toString(), verstossDto.getStatus().getKey());

        assertThrows(IllegalArgumentException.class, () -> {
            VerstossDto verstossDtoError = new VerstossDto(-10L, datum, "Test", VerstossStatusDto.parseFromVerstossStatus(ABGESCHLOSSEN));;
            verstossService.changeStatusForVerstoss(verstossDtoError);
        });
    }

}