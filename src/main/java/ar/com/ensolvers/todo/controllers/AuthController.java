package ar.com.ensolvers.todo.controllers;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ensolvers.todo.entities.User;
import ar.com.ensolvers.todo.model.request.*;
import ar.com.ensolvers.todo.model.response.*;
import ar.com.ensolvers.todo.security.jwt.JWTTokenUtil;
import ar.com.ensolvers.todo.services.JWTUserDetailsService;
import ar.com.ensolvers.todo.services.UserService;



@RestController
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    private JWTTokenUtil jwtTokenUtil;

    @Autowired
    private JWTUserDetailsService userDetailsService;

 
    @PostMapping("auth/register")
    public ResponseEntity<RegistrationResponse> postRegisterUser(@RequestBody RegistrationRequest req,
            BindingResult results) {
        RegistrationResponse r = new RegistrationResponse();


       User user = userService.create(req.email,req.password);

        r.isOk = true;
        r.message = "Your user was created succesfully.";
        r.userId = user.getUserId();

        return ResponseEntity.ok(r);

    }

    @PostMapping("auth/login") 
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest authenticationRequest,
            BindingResult results) throws Exception {

        User loggedUser = userService.login(authenticationRequest.username, authenticationRequest.password);

        UserDetails userDetails = userService.getUserAsUserDetail(loggedUser);
        Map<String, Object> claims = userService.getUserClaims(loggedUser);


        String token = jwtTokenUtil.generateToken(userDetails, claims);

        User u = userService.findByUsername(authenticationRequest.username);

        LoginResponse r = new LoginResponse();
        r.username = authenticationRequest.username;
        r.email = u.getEmail();
        r.token = token;

        return ResponseEntity.ok(r);
    }
}