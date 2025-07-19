package org.gbl.flight_admin.in.http;

import org.gbl.flight_admin.config.WebServerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebServerConfiguration.class, args);
    }
}
