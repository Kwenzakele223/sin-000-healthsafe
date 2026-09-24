package co.wethinkcode.healthsafe.mq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

public class EquipmentFailurePublisher {

    public void publish(String message) {

        try {
            ActiveMQConnectionFactory factory =
                    new ActiveMQConnectionFactory(MqConfig.BROKER_URL);

            try (Connection connection = factory.createConnection()) {

                connection.start();

                Session session = connection.createSession(
                        false,
                        Session.AUTO_ACKNOWLEDGE
                );

                Queue queue =
                        session.createQueue(MqConfig.QUEUE);

                MessageProducer producer =
                        session.createProducer(queue);

                TextMessage textMessage =
                        session.createTextMessage(message);

                producer.send(textMessage);

                producer.close();
                session.close();
            }

        } catch (Exception e) {

            throw new RuntimeException(
                    "Could not publish equipment failure alert",
                    e
            );
        }
    }
}