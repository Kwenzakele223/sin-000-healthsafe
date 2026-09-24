package co.wethinkcode.healthsafe.mq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

public class StaffingEventSubscriber {

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

            Topic topic =
                    session.createTopic(MqConfig.TOPIC);

            MessageConsumer consumer =
                    session.createConsumer(topic);

            consumer.setMessageListener(message -> {

                try {
                    if (message instanceof TextMessage textMessage) {

                        System.out.println(
                                "Ward Service received staffing event: "
                                        + textMessage.getText()
                        );
                    }

                } catch (Exception e) {

                    System.err.println(
                            "Could not process staffing event: "
                                    + e.getMessage()
                    );
                }
            });

            System.out.println(
                    "Ward Service subscribed to "
                            + MqConfig.TOPIC
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Could not subscribe to staffing events",
                    e
            );
        }
    }
}