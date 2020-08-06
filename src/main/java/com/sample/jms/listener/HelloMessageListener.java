package com.sample.jms.listener;

import com.sample.jms.config.JMSConfig;
import com.sample.jms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class HelloMessageListener {

    private final JmsTemplate jmsTemplate;

    //@JmsListener(destination = JMSConfig.MY_QUEUE)
    public void listen(@Payload HelloWorldMessage helloWorldMessage,
                       @Headers MessageHeaders headers,
                       Message message) {

        System.out.println("Got a message...");
        System.out.println(helloWorldMessage);
    }

    @JmsListener(destination = JMSConfig.MY_SEND_RECV_QUEUE)
    public void listenAndReturn(@Payload HelloWorldMessage helloWorldMessage,
                                @Headers MessageHeaders messageHeaders, Message message)
            throws JMSException {

        HelloWorldMessage payloadMessage =
                HelloWorldMessage.builder()
                    .message("World...")
                    .id(UUID.randomUUID())
                    .build();

        jmsTemplate.convertAndSend(message.getJMSReplyTo(), payloadMessage);
    }
}

