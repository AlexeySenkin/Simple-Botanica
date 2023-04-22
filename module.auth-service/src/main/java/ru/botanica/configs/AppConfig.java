package ru.botanica.configs;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;
import ru.botanica.properties.UserServiceIntegrationProperties;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableConfigurationProperties(UserServiceIntegrationProperties.class)
@RequiredArgsConstructor
public class AppConfig {
    private final UserServiceIntegrationProperties userServiceIntegrationProperties;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public WebClient userServiceWebClient() {

        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, userServiceIntegrationProperties.getConnectionTimeout())
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(userServiceIntegrationProperties.getReadTimeout(), TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(userServiceIntegrationProperties.getWriteTimeout(), TimeUnit.MILLISECONDS));
                });
        return WebClient.builder()
                .baseUrl(userServiceIntegrationProperties.getUrl())
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

}
