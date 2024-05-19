package EcommerceBackend.Ecommerce.Controller;

import EcommerceBackend.Ecommerce.Repository.UserRepository;
import EcommerceBackend.Ecommerce.Service.Auth.AuthService;
import EcommerceBackend.Ecommerce.Utils.JwtUtil;
import EcommerceBackend.Ecommerce.dto.AuthenticationRequest;
import EcommerceBackend.Ecommerce.dto.SignUpRequest;
import EcommerceBackend.Ecommerce.dto.UserDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final AuthService authService;

    public static final String TOKEN_PREFIX = "Bearer";
    public static final String HEADER_STRING = "Authentication";

    @PostMapping("/authenticate")
    public void createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,
                                          HttpServletResponse response) throws IOException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        Optional<EcommerceBackend.Ecommerce.Entity.User> optionalUser = userRepository.findByEmail(userDetails.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        if (optionalUser.isPresent()) {
            response.getWriter().write(new JSONObject()
                    .put("userId", optionalUser.get().getId())
                    .put("role", optionalUser.get().getUserRole())
                    .put("token", jwt)
                    .toString()
            );
            response.addHeader(HEADER_STRING, TOKEN_PREFIX + jwt);
        }
    }

    @PostMapping("/signup")
public ResponseEntity<?> signupUser(@RequestBody SignUpRequest signUpRequest) {

        if(authService.hasUserWithEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>("User already exists", HttpStatus.NOT_ACCEPTABLE);
        }

        UserDto userDto = authService.createUser(signUpRequest);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
}

}
