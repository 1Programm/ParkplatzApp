package com.gfi.parkplatzapp.backend.service;

import com.gfi.parkplatzapp.backend.facade.dto.AccountDto;
import com.gfi.parkplatzapp.backend.persistence.entities.Mitarbeiter;
import com.gfi.parkplatzapp.backend.persistence.repos.MitarbeiterRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class AccountService {

    @Autowired
    private MitarbeiterRepo mitarbeiterRepo;

    @SuppressWarnings("unchecked")
    public AccountDto getAccount(JwtAuthenticationToken auth){
        String uid = auth.getName();
        log.info("Checking Account for uid: [{}]...", uid);

        //Get Mitarbeiter by technical key (uid from KeyCloak Server)
        Mitarbeiter mitarbeiter = mitarbeiterRepo.findByUid(uid);
        Long mitarbeiterId = null;

        Jwt jwt = (Jwt)auth.getPrincipal();
        Map<String, Object> claims = jwt.getClaims();

        //If Mitarbeiter does not exist in the Database yet we create one with the information from the KeyCloak server!
        if(mitarbeiter == null){
            log.info("No mapped user found in database!");
            String firstName = (String) claims.get("given_name");
            String lastName = (String) claims.get("family_name");
            String email = (String) claims.get("email");

            log.info("Creating new mapping to Mitarbeiter with {uid: '{}', given_name: '{}', family_name: '{}', email: '{}'}", uid, firstName, lastName, email);

            Mitarbeiter newMitarbeiter = new Mitarbeiter();
            newMitarbeiter.setUid(uid);
            newMitarbeiter.setVorname(firstName);
            newMitarbeiter.setNachname(lastName);
            newMitarbeiter.setMail(email);

            newMitarbeiter = mitarbeiterRepo.save(newMitarbeiter);

            mitarbeiterId = newMitarbeiter.getMitarbeiterID();
        }
        else {
            mitarbeiterId = mitarbeiter.getMitarbeiterID();
            log.info("Found mapped Mitarbeiter [{}] in database!", mitarbeiterId);
        }

        String name = (String) claims.get("name");

        //Get all roles for this Mitarbeiter
        Map<String, Object> realmAccess = (Map<String, Object>) claims.get("realm_access");
        List<String> roles = (List<String>) realmAccess.get("roles");

        return new AccountDto(uid, mitarbeiterId, name, roles);
    }


}
