package org.medilabo.configuration;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataLoader {
    private final DataSource dataSource;

    @PostConstruct
    public void loadData() {
        log.info("Loading test data for patients");
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(
                false, false, "UTF-8",
                new ClassPathResource("data.sql")
        );
        resourceDatabasePopulator.execute(dataSource);
        log.info("Finished loading test data for patients");
    }
}
