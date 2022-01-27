package com.example.IdentityProvider.Services;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.IdentityProvider.POJO.User;
import identityprovider.example.com.authentication.AddUserRequest;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

public class IdentityProviderCreateJWT {

    public static String createJWT(String issuer, String subject, String role) {

        //define a secret key
        String SECRET_KEY = "generateJWT";

        //extract the uid
        UUID uid = UUID.randomUUID();

        //The JWT signature algorithm we will be using to sign the token
        Algorithm signatureAlgorithm = Algorithm.HMAC256(SECRET_KEY.getBytes());

        //Let's set the JWT Claims
        return JWT.create()
                .withJWTId(String.valueOf(uid))
                .withSubject(subject) //username
                .withAudience(role)
                .withIssuer(issuer)
                .withExpiresAt(new java.util.Date(System.currentTimeMillis() + 10 * 60 * 70000L))
                .sign(signatureAlgorithm);
    }

    public static String verifyJwt(String jwtToken) {

        //define a secret key
        String SECRET_KEY = "generateJWT";

        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY.getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();

            //decodez si verific jwt ul
            DecodedJWT decodedJWT = verifier.verify(jwtToken);

            return decodedJWT.getSubject() + " " + decodedJWT.getAudience().get(0);

        } catch(JWTVerificationException e) {
            if(e.getMessage().contains("expired"))
                return "Expired";
            else
                return "Invalid";
        }
    }

}
