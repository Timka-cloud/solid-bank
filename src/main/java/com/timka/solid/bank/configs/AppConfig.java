package com.timka.solid.bank.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("secrets.properties")
public class AppConfig {
}
