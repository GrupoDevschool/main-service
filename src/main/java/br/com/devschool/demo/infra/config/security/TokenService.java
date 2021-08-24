package br.com.devschool.demo.infra.config.security;

import br.com.devschool.demo.infra.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final UserRepository userRepository;

    @Value("${mobicare.jwt.secret}")
    private String secret;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public UserDetails createUserDetails(String email) {
            return new org.springframework.security.core.userdetails.User(email, "password",
                    new ArrayList<>());
    }

    public void setAuthentication(String token, HttpServletRequest request) {
        if (this.validateToken(token)) {
            String username = getUsernameFromToken(token);

            UserDetails userDetails = createUserDetails(username);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Boolean validateToken(String token) {
        final String username = getUsernameFromToken(token);
        return (!Objects.isNull(username) && !isTokenExpired(token));
    }


    public void authenticateJWT(String token, HttpServletRequest request) {
        try {
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                this.setAuthentication(token, request);
            }
        } catch (Exception ignored) {

        }
    }
}
