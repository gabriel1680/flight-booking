package org.gbl.flight_admin.in.http.config;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.WebApplicationInitializer;

@Configuration
public class ApplicationInitializer implements WebApplicationInitializer {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationInitializer.class);

    @Autowired
    private Environment environment;

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        LOG.info("Application initialization...");
        for (String profileName : environment.getActiveProfiles()) {
            LOG.info("Currently active profile - " + profileName);
        }
    }
}
