package de.workshops.bookshelf;

import jakarta.xml.bind.JAXB;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class ApplicationInformationConfiguration {

    @Bean
    public ApplicationInformation applicationInformation(@Value("${application.info.path}") Resource resource) throws IOException {
        try (InputStream inputStream = resource.getInputStream()) {
            return JAXB.unmarshal(inputStream, ApplicationInformation.class);
        }
    }
}
