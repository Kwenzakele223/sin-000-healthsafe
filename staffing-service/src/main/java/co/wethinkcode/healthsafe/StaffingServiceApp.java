package co.wethinkcode.healthsafe;

import io.javalin.Javalin;

public class StaffingServiceApp {

    public static Javalin createApp() {

        WardClient wardClient = new WardClient();
        AlertLevelClient alertLevelClient = new AlertLevelClient();

        Javalin app = Javalin.create();

        app.get("/health", ctx -> ctx.result("OK"));

        // TODO (Provides on-call schedules for doctors based on ward and status.)
        // Add domain endpoints for staffing-service here.

        app.get("/wards/{id}/schedule", ctx -> {

            String wardId = ctx.pathParam("id");

            if (!wardClient.wardExists(wardId)) {
                ctx.status(404);
                return;
            }

            int alertLevel = alertLevelClient.getAlertLevel();

            String schedule;

            if (alertLevel >= 8) {
                schedule = "Full emergency staffing for ward " + wardId;
            } else if (alertLevel >= 5) {
                schedule = "Increased staffing for ward " + wardId;
            } else {
                schedule = "Normal staffing for ward " + wardId;
            }

            ctx.json(
                    new ScheduleResponse(
                            wardId,
                            alertLevel,
                            schedule
                    )
            );
        });

        return app;
    }

    public static void main(String[] args) {
        createApp().start(7033);
    }
}

// MQ TODO: publishes to ActiveMQ topic MqConfig.TOPIC at MqConfig.BROKER_URL (see co.wethinkcode.healthsafe.mq.MqConfig)

