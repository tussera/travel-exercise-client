package com.tusso.travel.client;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.tusso.travel.client.domain.LocationConsumer;

@SpringBootApplication
public class TravelExerciseClientApplication {

    private static final Logger logger = LoggerFactory.getLogger(TravelExerciseClientApplication.class);


    public static void main(String[] args) {
        SpringApplication.run(TravelExerciseClientApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.basicAuthentication("someuser", "psw").build();
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
        return args -> {
            final ResponseEntity<List<LocationConsumer>> response =
                    restTemplate.exchange("http://localhost:8080/travel/locations/airport/US", HttpMethod.GET, null,
                            new ParameterizedTypeReference<List<LocationConsumer>>() {});
            final List<LocationConsumer> locations = response.getBody();

            for (final LocationConsumer location : locations) {
                logger.info(location.toString());
            }
        };
    }

}
