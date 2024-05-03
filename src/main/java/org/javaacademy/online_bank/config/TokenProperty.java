package org.javaacademy.online_bank.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "token")
@Configuration
@Getter
@Setter
public class TokenProperty {
    private String prefix;
    private String postfix;
}
