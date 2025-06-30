package org.medilabo.note.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Note service configuration class.
 * Provides beans required for note service operation.
 */
@Configuration
public class NoteServiceConfig {

    /**
     * Creates a ModelMapper bean for DTO-Entity mapping.
     * @return Configured ModelMapper instance
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
