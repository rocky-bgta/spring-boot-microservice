package  com.line.reflection.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Service
public class AzureTokenService {

    @Autowired
    private JwtDecoder jwtDecoder;

    @Value("${azure.client-id}")
    private String clientId;

    @Value("${azure.client-secret}")
    private String clientSecret;

    @Value("${azure.token-url}")
    private String tokenUrl;

    @Value("${azure.scope}")
    private String scope;

    public ResponseEntity<String> getAccessToken(String username, String password) {
        RestTemplate restTemplate = new RestTemplate();
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("username", username);
        body.add("password", password);
        body.add("grant_type", "password");
        body.add("scope", scope);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        return restTemplate.exchange(tokenUrl, HttpMethod.POST, request, String.class);
    }

    public ResponseEntity<String> refreshAccessToken(String refreshToken) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("refresh_token", refreshToken);
        body.add("grant_type", "refresh_token");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        return restTemplate.exchange(tokenUrl, HttpMethod.POST, request, String.class);
    }

    public String validateToken(String accessToken) {
        try {
            Jwt jwt = jwtDecoder.decode(accessToken);
            // Optionally, you can perform additional validation or extract claims from 'jwt'
            return ResponseEntity.ok("Token is valid").getBody();
        } catch (JwtException e) {
            return ResponseEntity.badRequest().body("Invalid token: " + e.getMessage()).getBody();
        }
    }
}
