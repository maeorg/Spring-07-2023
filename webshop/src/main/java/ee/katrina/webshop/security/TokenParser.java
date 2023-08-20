package ee.katrina.webshop.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
public class TokenParser extends BasicAuthenticationFilter {

    // hiljem kindlasti application.properties failis !Ei tohi githubi panna
    private String securityKey = "oAV1CCJSvHQmOfx2hkkpeH8zcXRPkroStk0+hy29Kg7LKHiPFkgCJFR4QzubCbweD5cmd0jYR5Q9" +
            "Uvsiw79gfHkpGFu6GOME/W1adSP5HPMqUpWn8DFGjC43ii5KSkr/oTgu3g==";
    public TokenParser(@Lazy AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String requestToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (requestToken != null && requestToken.startsWith("Bearer ")) {

            requestToken = requestToken.replace("Bearer ", "");

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(securityKey)))
                    .build()
                    .parseClaimsJws(requestToken)
                    .getBody();

            String personId = claims.getSubject();

            boolean isAdmin = Boolean.parseBoolean(claims.getAudience());

            List<GrantedAuthority> authorities = new ArrayList<>();
            if (isAdmin) {
                System.out.println("LÄKSIN SISSE");
                GrantedAuthority authority = new SimpleGrantedAuthority("admin");
                authorities.add(authority);
            }

            Authentication authentication = new UsernamePasswordAuthenticationToken(personId, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            log.info(authentication);
        }

        super.doFilterInternal(request, response, chain);
    }
}
