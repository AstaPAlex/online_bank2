package org.javaacademy.online_bank.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "bank")
@Configuration
@Getter
@Setter
public class BankProperty {
    private String name;
    private String partnerUrl;
}
