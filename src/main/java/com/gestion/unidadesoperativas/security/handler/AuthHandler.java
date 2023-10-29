package com.gestion.unidadesoperativas.security.handler;

import com.gestion.unidadesoperativas.security.dto.CreatedUserDto;
import com.gestion.unidadesoperativas.security.dto.LoginDto;
import com.gestion.unidadesoperativas.security.dto.TokenDto;
import com.gestion.unidadesoperativas.security.entity.User;
import com.gestion.unidadesoperativas.security.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthHandler {
    private final UserService userService;

    public Mono<ServerResponse> login(ServerRequest request){
        Mono<LoginDto> dtoMono = request.bodyToMono(LoginDto.class);
        return  dtoMono
                .flatMap((dto -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(userService.login(dto), TokenDto.class)));
    }

    public Mono<ServerResponse> create(ServerRequest request){
        Mono<CreatedUserDto> dtoMono = request.bodyToMono(CreatedUserDto.class);
        return  dtoMono
                .flatMap((dto -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(userService.create(dto), User.class)));
    }

}
