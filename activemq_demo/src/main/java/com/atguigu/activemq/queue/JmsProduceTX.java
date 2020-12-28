package com.atguigu.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsProduceTX {

//    public static final String ACTIVEMQ_URL = "tcp://127.0.0.1:61616";
    public static final String ACTIVEMQ_URL = "tcp://localhost:61616";
    public static final String QUEUE_NAME = "queue-tx";

    public static void main(String[] args) throws JMSException {
        //1.创建连接工厂,按照给定的url地址，采用默认用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2 通过连接工厂，获得连接connection并访问启动
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        //3 创建会话session
        //两个参数，第一个叫事务，第二个叫签收
        //是否启动事务
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        //4 创建目的地（具体是队列还是主题）
        Queue queue = session.createQueue(QUEUE_NAME);
        //5 创建消息的生产者
        MessageProducer messageProducer = session.createProducer(queue);
        //6 通过使用messageProducer生产3条消息发送到MQ的队列里面
        for(int i = 0;i < 6;i++){
            //7 创建消息
            TextMessage textMessage = session.createTextMessage("msg --- " + i);//理解为一个字符串

            //8 通过messageProducer发送给mq
            messageProducer.send(textMessage);

            MapMessage mapMessage = session.createMapMessage();
            mapMessage.setString("k1","mapmessage-v"+i);
            messageProducer.send(mapMessage);
            //设置消息头 属性
//            messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
//            messageProducer.setPriority(4);
        }
        //9 关闭资源
        messageProducer.close();
        //commit
        session.commit();
        session.close();
        connection.close();


        try {
            //ok sesson.commit
        } catch (Exception e) {
            e.printStackTrace();
            //error
//            session.rollback();
        } finally {
//            if(session != null){
//                session.close();
//            }
        }

        System.out.println("**************事务消息发布到MQ完成*******************");
    }
}
