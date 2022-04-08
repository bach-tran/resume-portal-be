package com.bach.resumeportal.controller;

import com.bach.resumeportal.dto.LoginDTO;
import com.bach.resumeportal.dto.UserJwtDto;
import com.bach.resumeportal.exception.BadParameterException;
import com.bach.resumeportal.model.User;
import com.bach.resumeportal.service.AuthenticationService;
import com.bach.resumeportal.service.JwtService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.FailedLoginException;

@RestController
@CrossOrigin(originPatterns = "*", allowedHeaders = "*", exposedHeaders = "*")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO dto) {
        try {
            User user = authService.login(dto.getUsername(), dto.getPassword());

            // Utilize JwtService to create a JSON web token with user information inside
            String jwt = jwtService.createJwt(user);

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("token", jwt);

            return ResponseEntity.ok().headers(responseHeaders).body(user);
        } catch (FailedLoginException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        } catch (BadParameterException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("/test")
    public ResponseEntity<?> test(@RequestHeader("Authorization") String authHeader) {

        try {
            String jwt = authHeader.split(" ")[1];

            UserJwtDto dto = jwtService.parseJwt(jwt);

            return ResponseEntity.ok(dto);
        } catch (JsonProcessingException e) {

            return ResponseEntity.status(500).build();
        }

    }

}
