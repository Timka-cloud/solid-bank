package com.timka.solid.bank.controllers;

import com.timka.solid.bank.dto.JwtRequest;
import com.timka.solid.bank.dto.JwtResponse;
import com.timka.solid.bank.response.BodyResponse;
import com.timka.solid.bank.response.ResponseMessage;
import com.timka.solid.bank.services.UserService;
import com.timka.solid.bank.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new ResponseMessage( "Incorrect username or password",HttpStatus.UNAUTHORIZED.value()), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/register")
    public BodyResponse register(@RequestBody JwtRequest jwtRequest) {
        return userService.register(jwtRequest);
    }
}
