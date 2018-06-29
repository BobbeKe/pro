package cn.e3mall.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;

public class ActiveMqTest {

    @Test
    public void testQueueProducer() throws Exception {
        //1、创建连接工厂对象，需要指定服务的ip及端口
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.133:61616");
        //2、使用工厂对象创建connection连接
        Connection connection = connectionFactory.createConnection();
        //3、开启连接，调用Connection对象的start
        connection.start();
        //4、创建一个session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5、使用session对象创建destination对象。两种形式queue、topic。现在应该使用queue
        Queue queue = session.createQueue("test-queue");
        //6、使用session对象创建一个Producer
        MessageProducer producer = session.createProducer(queue);
        //7、创建一个Massage对象，可以使用TextMessage
        //TextMessage message = new ActiveMQTextMessage();
        TextMessage textMessage = session.createTextMessage("Hello --22--");
        //8、发送消息
        producer.send(textMessage);
        //9、关闭资源
        producer.close();
        session.close();
        connection.close();
    }

    @Test
    public void testQueueConsumer() throws Exception {
        //1、创建连接工厂对象ConnectionFactory，需要指定服务的ip及端口
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.133:61616");
        //2、创建连接Connection
        Connection connection = connectionFactory.createConnection();
        //3、开启连接
        connection.start();
        //4、创建session
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //5、使用session对象创建destination对象。两种形式queue、topic。现在应该使用queue
        Queue queue = session.createQueue("spring-queue");
        //6、使用session对象创建一个Consumer
        MessageConsumer consumer = session.createConsumer(queue);
        //7、接收消息
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                //打印结果
                TextMessage textMessage = (TextMessage) message;
                String msg;
                try {
                    msg = textMessage.getText();
                    System.out.println(msg);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        //等待接收消息
        System.in.read();
        //8、关闭资源
        consumer.close();
        session.close();
        connection.close();
    }

    @Test
    public void testTopicProducer() throws Exception {
        //1、创建连接工厂对象，需要指定服务的ip及端口
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.133:61616");
        //2、使用工厂对象创建connection连接
        Connection connection = connectionFactory.createConnection();
        //3、开启连接，调用Connection对象的start
        connection.start();
        //4、创建一个session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5、使用session对象创建destination对象。两种形式queue、topic。现在应该使用queue
        Topic topic = session.createTopic("topic-msg");
        //6、使用session对象创建一个Producer
        MessageProducer producer = session.createProducer(topic);
        //7、创建一个Massage对象，可以使用TextMessage
        //TextMessage message = new ActiveMQTextMessage();
        TextMessage textMessage = session.createTextMessage("Hello --Boys--");
        //8、发送消息
        producer.send(textMessage);
        //9、关闭资源
        producer.close();
        session.close();
        connection.close();
    }

    @Test
    public void testTopicConsumer() throws Exception {
        //1、创建连接工厂对象ConnectionFactory，需要指定服务的ip及端口
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.133:61616");
        //2、创建连接Connection
        Connection connection = connectionFactory.createConnection();
        //3、开启连接
        connection.start();
        //4、创建session
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //5、使用session对象创建destination对象。两种形式queue、topic。现在应该使用queue
        Topic topic = session.createTopic("topic-msg");
        //6、使用session对象创建一个Consumer
        MessageConsumer consumer = session.createConsumer(topic);
        //7、接收消息
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                //打印结果
                TextMessage textMessage = (TextMessage) message;
                String msg;
                try {
                    msg = textMessage.getText();
                    System.out.println(msg);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        //等待接收消息
        System.out.println("topic-msg消费者2");
        System.in.read();
        //8、关闭资源
        consumer.close();
        session.close();
        connection.close();
    }
}
