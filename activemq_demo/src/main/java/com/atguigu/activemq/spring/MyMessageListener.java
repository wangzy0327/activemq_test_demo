package com.atguigu.activemq.spring;

import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@Component
public class MyMessageListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        if(message != null && message instanceof TextMessage){
            try {
                TextMessage textMessage = (TextMessage) message;
                System.out.println(textMessage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
