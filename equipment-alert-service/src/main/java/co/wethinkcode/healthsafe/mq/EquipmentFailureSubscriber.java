package co.wethinkcode.healthsafe.mq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.Queue;
import javax.jms.TextMessage;

public class EquipmentFailureSubscriber {

    public void start() {

        try {
            ActiveMQConnectionFactory factory =
                    new ActiveMQConnectionFactory(MqConfig.BROKER_URL);

            Connection connection =
                    factory.createConnection();

            connection.start();

            Session session = connection.createSession(
                    false,
                    Session.AUTO_ACKNOWLEDGE
            );

            Queue queue =
                    session.createQueue(MqConfig.QUEUE);

            MessageConsumer consumer =
                    session.createConsumer(queue);

            consumer.setMessageListener(message -> {

                try {
                    if (message instanceof TextMessage textMessage) {

                        System.out.println(
                                "Equipment Alert Service received: "
                                        + textMessage.getText()
                        );
                    }

                } catch (Exception e) {

                    System.err.println(
                            "Could not process equipment failure alert: "
                                    + e.getMessage()
                    );
                }
            });

            System.out.println(
                    "Equipment Alert Service subscribed to "
                            + MqConfig.QUEUE
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Could not subscribe to equipment failure queue",
                    e
            );
        }
    }
}
