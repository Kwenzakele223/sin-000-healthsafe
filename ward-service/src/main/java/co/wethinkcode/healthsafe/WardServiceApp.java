package co.wethinkcode.healthsafe;

import io.javalin.Javalin;

import java.util.List;
import java.util.stream.Collectors;

public class WardServiceApp {

    public static Javalin createApp() {

        WardClient wardClient = new WardClient();

        Javalin app = Javalin.create();

        app.get("/health", ctx -> ctx.result("OK"));

        // TODO (Provides lists of wards and departments.)
        // Add domain endpoints for ward-service here.

        app.get("/wards", ctx -> {
            ctx.json(wardClient.getWards());
        });

        app.get("/wards/{id}", ctx -> {

            String wardId = ctx.pathParam("id");

            Ward ward = wardClient.getWards()
                    .stream()
                    .filter(w -> w.getWardId().equalsIgnoreCase(wardId))
                    .findFirst()
                    .orElse(null);

            if (ward == null) {
                ctx.status(404);
                return;
            }

            ctx.json(ward);
        });

        app.get("/departments", ctx -> {

            List<String> departments = wardClient.getWards()
                    .stream()
                    .map(Ward::getDepartment)
                    .distinct()
                    .sorted()
                    .collect(Collectors.toList());

            ctx.json(departments);
        });

        // MQ TODO: subscribes to ActiveMQ topic MqConfig.TOPIC at MqConfig.BROKER_URL (see co.wethinkcode.healthsafe.mq.MqConfig)
        // MQ TODO: publishes to ActiveMQ queue MqConfig.QUEUE when it detects an equipment failure on one of its wards.

        return app;
    }

    public static void main(String[] args) {
        createApp().start(7031);
    }
}

