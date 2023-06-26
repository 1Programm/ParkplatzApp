package com.gfi.parkplatzapp.backend.service;

import com.gfi.parkplatzapp.backend.facade.Account;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AccountService {

    public Account getAccount(JwtAuthenticationToken auth){
        String uuid = auth.getName();

        //get mitarbeiter id
        long mitarbeiterId = 1;


        Jwt jwt = (Jwt)auth.getPrincipal();
        Map<String, Object> claims = jwt.getClaims();
        String name = (String) claims.get("name");
        Map<String, Object> realmAccess = (Map<String, Object>) claims.get("realm_access");
        List<String> roles = (List<String>) realmAccess.get("roles");



        return new Account(uuid, mitarbeiterId, name, roles.toArray(new String[0]));
    }


}
