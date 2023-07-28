package com.gfi.parkplatzapp.backend.service;

import com.gfi.parkplatzapp.backend.Application;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Application.class)
@ActiveProfiles("test")
@Transactional
class AccountServiceTest {
    @Autowired
    public AccountService accountService;
    public JwtAuthenticationToken jwtAuthenticationToken;

    /**
     * JwtAuthenticationToken ist unbekannt, weswegen die Klasse AccountService nicht getetsted wird
     * @throws Exception
     */


    @Test
    public void getAccount_Test () throws Exception {
        /*
       AccountDto accountS = accountService.getAccount(jwtAuthenticationToken);
       assertEquals("Checking Account for uid: [{}]...", accountS.getName());
         */
    }

}