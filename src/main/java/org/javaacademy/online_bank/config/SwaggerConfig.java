package org.javaacademy.online_bank.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI myOpenAPI() {
        Server moneyServer = new Server();
        moneyServer.setUrl("http://localhost:8001");
        moneyServer.setDescription("MoneyBank сервер!");

        Server euroServer = new Server();
        euroServer.setUrl("http://localhost:8082");
        euroServer.setDescription("EuroBank сервер!");

        Contact contact = new Contact();
        contact.setEmail("test@mail.ru");
        contact.setName("Астапов Алексей");

        Info info = new Info()
                .title("Онлайн банк")
                .version("0.0.1")
                .contact(contact)
                .description("Апи онлайн банка");
        return new OpenAPI().info(info).servers(List.of(moneyServer, euroServer));
    }
}
