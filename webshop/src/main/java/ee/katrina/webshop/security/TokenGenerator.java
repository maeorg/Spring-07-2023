package ee.katrina.webshop.security;

import ee.katrina.webshop.dto.security.AuthToken;
import ee.katrina.webshop.entity.Person;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenGenerator {

    // hiljem kindlasti application.properties failis !Ei tohi githubi panna
    private String securityKey = "oAV1CCJSvHQmOfx2hkkpeH8zcXRPkroStk0+hy29Kg7LKHiPFkgCJFR4QzubCbweD5cmd0jYR5Q9" +
            "Uvsiw79gfHkpGFu6GOME/W1adSP5HPMqUpWn8DFGjC43ii5KSkr/oTgu3g==";

    public AuthToken getToken(Person person) {
        AuthToken authToken = new AuthToken();

        Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * 60);
        authToken.setExpiration(expiration);

        String jwtToken = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(securityKey)),
                        SignatureAlgorithm.HS512)
                .setIssuer("Katrina's webshop")
                .setExpiration(expiration)
                .setSubject(person.getId().toString())
                .setAudience(String.valueOf(person.isAdmin()))
                .compact();

        authToken.setToken(jwtToken);

        return authToken;
    }
}
