package com.gfi.parkplatzapp.backend;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collection;
import java.util.Map;

@RestController
public class AccountFacade {

    @GetMapping("/account")
    public Account getAccount(Principal principal){
        if(principal == null) return null;
        String uuid = principal.getName();

        //1. Get Account from backend



        //2. If Account != null return Account


        if(!(principal instanceof OAuth2AuthenticationToken)) throw new IllegalStateException("Principal should be an instance of [" + OAuth2AuthenticationToken.class + "]!");
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) principal;

        //3. Get OidcUserAuthority
        OidcUserAuthority oidcUserAuthority = null;

        Collection<GrantedAuthority> authorities = oauthToken.getAuthorities();
        if(authorities != null){
            for(GrantedAuthority authority : authorities){
                if(authority instanceof OidcUserAuthority){
                    oidcUserAuthority = (OidcUserAuthority) authority;
                    break;
                }
            }
        }

        if(oidcUserAuthority == null) throw new IllegalStateException("No OidcUserAuthority found in [" + (authorities == null ? "null" : authorities.size()) + "] granted authorities!");

        OidcUserInfo userInfo = oidcUserAuthority.getUserInfo();
        if(userInfo == null) throw new IllegalStateException("No OidcUserInfo found! Should not happen...");

        Map<String, Object> claims = userInfo.getClaims();
        String fullName = (String) claims.get("name");
        String givenName = (String) claims.get("given_name");
        String familyName = (String) claims.get("family_name");




        return new Account("", "");
    }

}
