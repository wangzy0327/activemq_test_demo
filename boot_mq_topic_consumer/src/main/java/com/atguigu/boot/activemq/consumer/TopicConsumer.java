package com.atguigu.boot.activemq.consumer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

@Component
public class TopicConsumer {

    @JmsListener(destination = "${mytopic}")
    public void receive(TextMessage text){
        try {
            System.out.println("consumer receive topic : "+text.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
