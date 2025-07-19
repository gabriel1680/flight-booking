package org.gbl.flight_admin.in.http.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan("org.gbl.flight_admin")
@EntityScan("org.gbl.flight_admin")
@EnableJpaRepositories("org.gbl.flight_admin")
public class WebServerConfiguration {
}
