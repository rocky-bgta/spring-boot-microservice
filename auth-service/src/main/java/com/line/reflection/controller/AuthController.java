package  com.line.reflection.controller;


import com.line.reflection.dto.AuthRequest;
import com.line.reflection.entity.UserCredential;
import com.line.reflection.service.AzureTokenService;
import com.line.reflection.service.JwtAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private JwtAuthService service;

    @Autowired
    private AzureTokenService azureTokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public String addNewUser(@RequestBody UserCredential user) {
        return service.saveUser(user);
    }

    @PostMapping("/token")
    public ResponseEntity<String> getToken(@RequestBody AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
            ResponseEntity<String> responseEntity = azureTokenService.getAccessToken(authRequest.getUsername(), authRequest.getPassword());
            return responseEntity;
        } else {
            throw new RuntimeException("invalid access");
        }
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        return azureTokenService.validateToken(token);
    }
}
