package com.eldorado.microservice.notification;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class EmployeeListener {

    @RabbitListener(queues = "save-registry")
    public void saveEmployeeRegistry(Message message) {
        byte[] body = message.getBody();
        String employeeData = new String(body);

        System.out.println("User created sucessfully: " + employeeData);
    }

    @RabbitListener(queues = "update-registry")
    public void updateEmployeeRegistry(Message message) {
        byte[] body = message.getBody();
        String employeeData = new String(body);

        System.out.println("User updated sucessfully: " + employeeData);
    }

    @RabbitListener(queues = "delete-registry")
    public void deleteEmployeeRegistry(Message message) {
        byte[] body = message.getBody();
        String employeeData = new String(body);

        System.out.println("User deleted sucessfully: " + employeeData);
    }

}
