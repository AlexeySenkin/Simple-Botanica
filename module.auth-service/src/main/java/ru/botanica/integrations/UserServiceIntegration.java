package ru.botanica.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class UserServiceIntegration {
    private final WebClient webClient;

    public ResponseEntity<?> registerUser(String userName, String email) {
        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                                .path("/user/register")
                                .queryParam("username", userName)
                                .queryParam("email", email)
                                .build()
                        )
                .retrieve()
                .toEntity(String.class)
                .block();
    }
}
