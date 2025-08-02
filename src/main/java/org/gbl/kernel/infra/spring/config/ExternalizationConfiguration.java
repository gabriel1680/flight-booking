package org.gbl.kernel.infra.spring.config;

import org.gbl.admin.app.event.BookingConfirmed;
import org.gbl.admin.app.event.BookingFailed;
import org.gbl.admin.in.config.AdminAmqpConfiguration;
import org.gbl.booking.config.BookingAmqpConfiguration;
import org.gbl.booking.event.BookingCreated;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.modulith.events.EventExternalizationConfiguration;
import org.springframework.modulith.events.RoutingTarget;

@Configuration
public class ExternalizationConfiguration {

    @Bean
    public EventExternalizationConfiguration eventExternalizationConfiguration() {
        return EventExternalizationConfiguration.externalizing()
                .selectByPackage("org.gbl.*")
                .route(BookingCreated.class, evt ->
                        RoutingTarget.forTarget(BookingAmqpConfiguration.TOPIC_EXCHANGE)
                                .andKey("booking.created"))
                .route(BookingFailed.class, evt ->
                        RoutingTarget.forTarget(AdminAmqpConfiguration.TOPIC_EXCHANGE)
                                .andKey("booking.failed"))
                .route(BookingConfirmed.class, evt ->
                        RoutingTarget.forTarget(AdminAmqpConfiguration.TOPIC_EXCHANGE)
                                .andKey("booking.confirmed"))
                .build();
    }
}
