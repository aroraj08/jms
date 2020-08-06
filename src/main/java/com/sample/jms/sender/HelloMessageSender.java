package com.sample.jms.sender;

import com.sample.jms.config.JMSConfig;
import com.sample.jms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class HelloMessageSender {

    private final JmsTemplate jmsTemplate;

    @Scheduled(fixedRate = 2000)
    public void sendMessage() {
        System.out.println("I am sending message");

        HelloWorldMessage message = HelloWorldMessage.builder().id(UUID.randomUUID())
                                        .message("helloWorld..").build();
        jmsTemplate.convertAndSend(JMSConfig.MY_QUEUE, message);

        System.out.println("Message sent!");
    }
}
