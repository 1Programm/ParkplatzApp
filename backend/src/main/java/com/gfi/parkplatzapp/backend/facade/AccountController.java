package com.gfi.parkplatzapp.backend.facade;

import com.gfi.parkplatzapp.backend.facade.dto.AccountDto;
import com.gfi.parkplatzapp.backend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {

    /**
     * Der AccountService, der für das Abrufen von Kontoinformationen verantwortlich ist.
     */
    @Autowired
    private AccountService accountService;

    /**
     * Ruft die Kontoinformationen für den authentifizierten Benutzer ab.
     *
     * @param auth Das JwtAuthenticationToken, das den authentifizierten Benutzer repräsentiert.
     * @return Das AccountDto, das die Kontoinformationen des authentifizierten Benutzers enthält.
     */
    @GetMapping
    public AccountDto getAccount(JwtAuthenticationToken auth){
        return accountService.getAccount(auth);
    }

}
