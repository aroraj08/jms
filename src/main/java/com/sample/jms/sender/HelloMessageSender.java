package com.sample.jms.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.jms.config.JMSConfig;
import com.sample.jms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class HelloMessageSender {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    //@Scheduled(fixedRate = 2000)
    public void sendMessage() {
        System.out.println("I am sending message");

        HelloWorldMessage message = HelloWorldMessage.builder().id(UUID.randomUUID())
                                        .message("helloWorld..").build();
        jmsTemplate.convertAndSend(JMSConfig.MY_QUEUE, message);

        System.out.println("Message sent!");
    }

    @Scheduled(fixedRate = 2000)
    public void sendAndReceiveMessage() throws JMSException {

        HelloWorldMessage message = HelloWorldMessage.builder()
                .message("hello..").build();

        Message receivedMessage  =
                jmsTemplate.sendAndReceive(JMSConfig.MY_SEND_RECV_QUEUE, new MessageCreator() {

            @Override
            public Message createMessage(Session session) throws JMSException {

                Message textMessage = null;
                try {
                    textMessage = session.createTextMessage(objectMapper.writeValueAsString(message));
                    textMessage.setStringProperty("_type", "com.sample.jms.model.HelloWorldMessage");

                    return textMessage;
                } catch (JsonProcessingException e) {
                   throw new RuntimeException("exception occured while processing JSON");
                }
            }
        });

        System.out.println(receivedMessage.getBody(String.class));
    }
}
