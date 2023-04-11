package ru.botanica.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.botanica.dto.AppStatus;
import ru.botanica.dto.JwtRequest;
import ru.botanica.dto.JwtResponse;
import ru.botanica.dto.UserDto;
import ru.botanica.services.UserService;
import ru.botanica.utils.JwtTokenUtil;



@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowCredentials = "false")
public class AuthController {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AppStatus(HttpStatus.UNAUTHORIZED.value(), "Некорректный логин или пароль"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
//        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
//        int size = authorities.size();
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) {
        if (userService.findByUsername(userDto.getUsername()).isPresent()) {
            return new ResponseEntity<>(new AppStatus(HttpStatus.BAD_REQUEST.value(), "Пользователь с таким именем уже существует"), HttpStatus.BAD_REQUEST);
        }
        userService.createNewUser(userDto);

        UserDetails userDetails = userService.loadUserByUsername(userDto.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }


    @PostMapping("/active")
    public ResponseEntity<?> active(@RequestBody UserDto userDto) {
        if (userService.findByUsername(userDto.getUsername()).isEmpty()) {
            return new ResponseEntity<>(new AppStatus(HttpStatus.BAD_REQUEST.value(), "Пользователь с таким именем не существует"), HttpStatus.BAD_REQUEST);
        }
        userService.activeUser(userDto);

        return new ResponseEntity<>(new AppStatus(HttpStatus.OK.value(), userDto.getUsername() + ": isActive обновлен"), HttpStatus.OK);
    }

    @PostMapping("/banned")
    public ResponseEntity<?> banned(@RequestBody UserDto userDto) {
        if (userService.findByUsername(userDto.getUsername()).isEmpty()) {
            return new ResponseEntity<>(new AppStatus(HttpStatus.BAD_REQUEST.value(), "Пользователь с таким именем не существует"), HttpStatus.BAD_REQUEST);
        }
        userService.bannedUser(userDto);

        return new ResponseEntity<>(new AppStatus(HttpStatus.OK.value(), userDto.getUsername() + ": isBanned обновлен"), HttpStatus.OK);
    }
    @PostMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody UserDto userDto) {
        if (userService.findByUsername(userDto.getUsername()).isEmpty()) {
            return new ResponseEntity<>(new AppStatus(HttpStatus.BAD_REQUEST.value(), "Пользователь с таким именем не существует"), HttpStatus.BAD_REQUEST);
        }
        userService.deleteUser(userDto);

        return new ResponseEntity<>(new AppStatus(HttpStatus.OK.value(), userDto.getUsername() + ": удален"), HttpStatus.OK);
    }




}
