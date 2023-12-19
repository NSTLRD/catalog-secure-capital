/**
 * @author Starling Diaz on 12/19/2023.
 * @Academy StarAcademy
 * @version CatalogSecureCapital 1.0
 * @since 12/19/2023.
 */

package com.starlingdiaz.CatalogSecureCapital.provider;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.starlingdiaz.CatalogSecureCapital.model.UserPrincipal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenProvider {
    private static final String Get_Star_Technology_LLC = "STAR_TECHNOLOGY_LLC";
    private static final String CUSTOMER_MANAGEMENT_SERVICE = "COMPANY_STUDENT_MANAGEMENT_SERVICE";
    private static final String AUTHORITIES = "authorities";
    private static final long ACCESS_TOKEN_EXPIRATION_TIME = 345600000;
    //can you see the problem here? 864000000 is 10 days in milliseconds. That's a long time for a token to be valid.
    //can you put 4 days instead? 345600000


    @Value("${jwt.secret}")
    private String secret;

    public String createAccessToken(UserPrincipal userPrincipal) {
        //generate token
        String[] claims = getClaimsFromUser(userPrincipal);
        return JWT.create()
                .withIssuer(Get_Star_Technology_LLC)
                .withAudience(CUSTOMER_MANAGEMENT_SERVICE)
                .withIssuedAt(new Date()).withSubject(userPrincipal.getUsername())
                .withArrayClaim(AUTHORITIES, claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(secret.getBytes()));
    }

    private String[] getClaimsFromUser(UserPrincipal userPrincipal) {
        return userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new);
    }
}
