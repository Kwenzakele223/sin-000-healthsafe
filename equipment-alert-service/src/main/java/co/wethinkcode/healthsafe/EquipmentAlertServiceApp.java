package co.wethinkcode.healthsafe;

import co.wethinkcode.healthsafe.mq.EquipmentFailureSubscriber;
import io.javalin.Javalin;

public class EquipmentAlertServiceApp {

    public static void main(String[] args) {

        EquipmentFailureSubscriber subscriber =
                new EquipmentFailureSubscriber();

        subscriber.start();

        Javalin app = Javalin.create();

        app.get("/health", ctx -> ctx.result("OK"));

        app.start(7034);
    }
}

// MQ TODO: consumes ActiveMQ queue MqConfig.QUEUE at MqConfig.BROKER_URL (see co.wethinkcode.healthsafe.mq.MqConfig)
// Producer: ward-service publishes here when it detects an equipment failure on one of its wards.