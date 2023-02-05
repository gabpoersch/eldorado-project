package com.eldorado.microservice.employee.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue saveQueue() {
        return new Queue("save-registry");
    }

    @Bean
    public Queue updateQueue() {
        return new Queue("update-registry");
    }

    @Bean
    public Queue deleteQueue() {
        return new Queue("delete-registry");
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> applicationReadyEventApplicationListener (RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }

}
