package co.wethinkcode.healthsafe;

import io.javalin.Javalin;

import java.util.Map;

public class AlertLevelServiceApp {

    public static Javalin createApp() {

        Javalin app = Javalin.create();

        app.get("/health", ctx -> ctx.result("OK"));

        // TODO (Tracks the hospital Emergency Status (0-8, 8 = full Code Blue).)
        // Add domain endpoints for alert-level-service here.

        int currentLevel = 0;

        app.get("/alert-level", ctx -> {
            ctx.json(Map.of("level", currentLevel));
        });

        return app;
    }

    public static void main(String[] args) {
        createApp().start(7032);
    }
}