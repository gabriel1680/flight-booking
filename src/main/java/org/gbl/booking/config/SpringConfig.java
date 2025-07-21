package org.gbl.booking.config;

import org.gbl.booking.domain.BookingRepository;
import org.gbl.booking.repository.BookingJPARepository;
import org.gbl.booking.repository.BookingJPARepositoryImpl;
import org.gbl.flight_admin.app.service.EventDispatcher;
import org.gbl.shared.application.service.SpringEventDispatcher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public EventDispatcher dispatcher(ApplicationEventPublisher publisher) {
        return new SpringEventDispatcher(publisher);
    }

    @Bean
    public BookingRepository bookingRepository(BookingJPARepository repository) {
        return new BookingJPARepositoryImpl(repository);
    }
}
