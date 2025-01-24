package ru.tdd.tgbot.runners;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

/**
 * @author Tribushko Danil
 * @since 21.01.2025
 * Раннер для запуска миграций Flyway
 */
@Component
public class FlywayRunner implements CommandLineRunner {

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.driver-class-name}")
    private String driver;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    @Override
    public void run(String... args) throws Exception {
        var dataSource = DataSourceBuilder.create()
                .url(url)
                .driverClassName(driver)
                .username(username)
                .password(password)
                .build();

        Flyway.configure()
                .baselineOnMigrate(true)
                .baselineVersion("0")
                .locations("classpath:/db/migration")
                .dataSource(dataSource)
                .load()
                .migrate();
    }
}
