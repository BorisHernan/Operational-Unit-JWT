package com.gestion.unidadesoperativas.security.service;

import com.gestion.unidadesoperativas.security.Repository.UserRepository;
import com.gestion.unidadesoperativas.security.dto.CreatedUserDto;
import com.gestion.unidadesoperativas.security.dto.LoginDto;
import com.gestion.unidadesoperativas.security.dto.TokenDto;
import com.gestion.unidadesoperativas.security.entity.User;
import com.gestion.unidadesoperativas.security.enums.Role;
import com.gestion.unidadesoperativas.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public Mono<TokenDto> login(LoginDto dto){
        return userRepository.findByUsernameOrEmail(dto.getUsername(), dto.getUsername())
                .filter(user -> passwordEncoder.matches(dto.getPassword(), user.getPassword()))
                .map(user -> new TokenDto(jwtProvider.generateToken(user)))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "bad credentials")));

    }

    public Mono<User> create(CreatedUserDto dto) {
        User user = User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .roles(Role.ROLE_ADMIN.name() + ","+ Role.ROLE_USER.name())
                .build();

        Mono<Boolean> userExists = userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail()).hasElement();
        return userExists
                .flatMap( exists ->exists ?
                        Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username o email already implements"))
                        : userRepository.save(user));
    }

}
