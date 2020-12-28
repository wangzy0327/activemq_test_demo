package com.atguigu.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

public class JmsConsumerTX {
    public static final String ACTIVEMQ_URL = "tcp://127.0.0.1:61616";
    public static final String QUEUE_NAME = "queue-tx";

    public static void main(String[] args) throws JMSException, IOException {
        System.out.println("******2号消费者");
        //1.创建连接工厂,按照给定的url地址，采用默认用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2 通过连接工厂，获得连接connection并访问启动
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        //3 创建会话session
        //两个参数，第一个叫事务，第二个叫签收
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        //4 创建目的地（具体是队列还是主题）
        Queue queue = session.createQueue(QUEUE_NAME);

        //5 创建消息的消费者
        MessageConsumer consumer = session.createConsumer(queue);
        //6 通过使用消息消费
        /*
        同步阻塞方式（receive()）

        订阅者或接收者调用MessageConsumer的receive()方法来接收消息，receive方法在能够接收到消息之前（或超时之前）将一直阻塞

        for(int i = 0;i < 3;i++){
            //7 消费消息
            TextMessage textMessage = (TextMessage) consumer.receive();
            if(textMessage != null){
                System.out.println("接收到的消息是："+textMessage.getText());
            }else{
                break;
            }
        }
        //9 关闭资源
        consumer.close();
        session.close();
        connection.close();
        */

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
//                if(message != null && message instanceof MapMessage){
//                    MapMessage mapMessage = (MapMessage) message;
//                    try {
//                        System.out.println("消费者接收到消息："+mapMessage.getString("k1"));
//                    } catch (JMSException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
        //保证 console not close
//        System.in.read();
        while(true){
            Message message = consumer.receive(3000L);
            if(message != null && message instanceof TextMessage){
                System.out.println("消费者接收到消息："+((TextMessage)message).getText());
            }else if(message != null && message instanceof MapMessage){
                System.out.println("消费者接收到消息："+((MapMessage)message).getString("k1"));
            }else{
                break;
            }
        }
        consumer.close();
        //注意避免消息的重复消费
        session.commit();
        session.close();
        connection.close();

        System.out.println("**************事务 消息从MQ中取出*******************");
    }
}
