/**
 * @author Starling Diaz on 10/8/2023.
 * @Academy StarAcademy
 * @version CatalogSecureCapital 1.0
 * @since 10/8/2023.
 */

package com.starlingdiaz.CatalogSecureCapital.controller;

import com.starlingdiaz.CatalogSecureCapital.dto.UserDTO;
import com.starlingdiaz.CatalogSecureCapital.model.HttpResponse;
import com.starlingdiaz.CatalogSecureCapital.model.User;
import com.starlingdiaz.CatalogSecureCapital.request.LoginRequest;
import com.starlingdiaz.CatalogSecureCapital.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(path = "/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;


    @PostMapping("/login")
    public ResponseEntity<HttpResponse> login(@RequestBody @Valid LoginRequest loginRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        UserDTO user = userService.getUserByEmail(loginRequest.getEmail());
        return user.isUsingMfa() ? sendVerificationCode(user) : sendResponse(user);

    }

    @PostMapping("/register")
    public ResponseEntity<HttpResponse> saveUser(@RequestBody @Valid User user){
       UserDTO userDto = userService.createUser(user);
       return ResponseEntity.created(getUri()).body(
               HttpResponse.builder()
                       .timeStamp(now().toString())
                       .data(of("user", userDto))
                       .message("User created successfully")
                       .status(CREATED)
                       .statusCode(CREATED.value())
                       .build());
    }

    private URI getUri() {
        return URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/user/get/<userId>")
                .toUriString());
    }

    //is the user dont use mfa we send the response here
    private ResponseEntity<HttpResponse> sendResponse(UserDTO user) {
      return ResponseEntity.ok().body(
              HttpResponse.builder()
                      .timeStamp(now().toString())
                      .data(of("user", user))
                      .message("Login Successfully")
                      .status(OK)
                      .statusCode(OK.value())
                      .build());
    }

    private ResponseEntity<HttpResponse> sendVerificationCode(UserDTO user) {
        userService.sendVerificationCode(user);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("user", user))
                        .message("Verification code sent")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

}
