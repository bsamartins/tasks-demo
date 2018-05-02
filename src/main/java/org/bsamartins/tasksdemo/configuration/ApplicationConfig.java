package org.bsamartins.tasksdemo.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = { SecurityConfig.class, PersistenceConfig.class, WebConfiguration.class })
@ComponentScan("org.bsamartins")
public class ApplicationConfig {
}
