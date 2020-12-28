package com.atguigu.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

public class JmsTopicConsumer {
    public static final String ACTIVEMQ_URL = "tcp://127.0.0.1:61616";
    public static final String TOPIC_NAME = "topic01";

    public static void main(String[] args) throws JMSException, IOException {
        System.out.println("******3号TOPIC消费者");
        //1.创建连接工厂,按照给定的url地址，采用默认用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2 通过连接工厂，获得连接connection并访问启动
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        //3 创建会话session
        //两个参数，第一个叫事务，第二个叫签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4 创建目的地（具体是队列还是主题）
        Topic topic = session.createTopic(TOPIC_NAME);

        //5 创建消息的消费者
        MessageConsumer consumer = session.createConsumer(topic);
        //6 通过使用消息消费


        //通过监听的方法来消费消息


//        consumer.setMessageListener(new MessageListener() {
//            @Override
//            public void onMessage(Message message) {
//                if(message != null && message instanceof TextMessage){
//                    TextMessage textMessage = (TextMessage) message;
//                    try {
//                        System.out.println("消费者接收到消息："+textMessage.getText());
//                    } catch (JMSException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });

        consumer.setMessageListener((message)->{
            if(message != null && message instanceof TextMessage){
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("TOPIC消费者接收到消息："+textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        //保证 console not close
        System.in.read();
        consumer.close();
        session.close();
        connection.close();

        System.out.println("**************TOPIC消息从MQ中取出*******************");
    }
}
