package Util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Objects;

@Singleton
public class KeyJWT {

   private String secret = "web4";
    Algorithm algorithm = Algorithm.HMAC256(secret);

    public String getKey(String login){

        try {
            String token = JWT.create()
                    .withIssuer("Token")
                    .withClaim("login", login)
                    .sign(algorithm);
            System.out.println(token+" "+login);
            return token;
        } catch (JWTCreationException exception){
            System.out.println("Ошибка создания ключа");
            return "";
        }
    }

    public boolean checkJWT(String jws){
        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("Token")
                    .build();

            DecodedJWT decodedJWT = verifier.verify(jws);

            System.out.println("Claims: " + decodedJWT.getClaim("login").asString()+" "+decodedJWT.getIssuer());

            return true;
        } catch (JWTVerificationException ex) {
            System.out.println("Verify JWT token fail: " + ex.getMessage());
            return false;
        }
    }

    public String getLogin(String jwt){
        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("Token")
                    .build();

            DecodedJWT decodedJWT = verifier.verify(jwt);

            System.out.println("Claims: " + decodedJWT.getClaim("login").asString()+" "+decodedJWT.getIssuer());

            return decodedJWT.getClaim("login").asString();
        } catch (JWTVerificationException ex) {
            System.out.println("Verify JWT token fail: " + ex.getMessage());
            return "";
        }
    }
}
