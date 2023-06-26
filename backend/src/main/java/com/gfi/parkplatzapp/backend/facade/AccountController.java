package com.gfi.parkplatzapp.backend.facade;

import com.gfi.parkplatzapp.backend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public Account getAccount(JwtAuthenticationToken auth){
        System.out.println("auth: " + auth);

        return accountService.getAccount(auth);
    }

}
