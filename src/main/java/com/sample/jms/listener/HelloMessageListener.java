package com.sample.jms.listener;

import com.sample.jms.config.JMSConfig;
import com.sample.jms.model.HelloWorldMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.Message;

@Component
public class HelloMessageListener {

    @JmsListener(destination = JMSConfig.MY_QUEUE)
    public void listen(@Payload HelloWorldMessage helloWorldMessage,
                       @Headers MessageHeaders headers,
                       Message message) {

        System.out.println("Got a message...");
        System.out.println(helloWorldMessage);
    }
}

