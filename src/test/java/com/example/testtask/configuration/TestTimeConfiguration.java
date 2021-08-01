package com.example.testtask.configuration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

@TestConfiguration
public class TestTimeConfiguration {

    public static final ZoneId MOSCOW = ZoneId.of("Europe/Moscow");
    public static final String TEST_DATE_TIME = "2020-10-12T10:15:30.00Z";

    @Bean
    @Primary
    public Clock clock() {
        return Clock.fixed(Instant.parse(TEST_DATE_TIME), MOSCOW);
    }
}
