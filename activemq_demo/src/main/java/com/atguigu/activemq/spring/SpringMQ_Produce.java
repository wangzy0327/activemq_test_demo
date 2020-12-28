package com.atguigu.activemq.spring;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

@Service
public class SpringMQ_Produce {

    @Autowired
    private JmsTemplate jmsTemplate;

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        SpringMQ_Produce produce = (SpringMQ_Produce) ctx.getBean(SpringMQ_Produce.class);

        produce.jmsTemplate.send((session)->{
//            TextMessage textMessage = session.createTextMessage("*******spring和activeMQ的整合case");
            TextMessage textMessage = session.createTextMessage("*******spring和activeMQ的整合case for topic");
            return textMessage;
        });
        System.out.println("************send  task over");
    }
}
