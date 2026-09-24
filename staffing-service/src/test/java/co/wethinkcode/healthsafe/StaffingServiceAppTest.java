package co.wethinkcode.healthsafe;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StaffingServiceAppTest {

    @Test
    void shouldReturnScheduleForExistingWard() throws Exception {

        Javalin app = StaffingServiceApp.createApp();
        app.start(7033);

        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(
                    HttpRequest.newBuilder()
                            .uri(URI.create(
                                    "http://localhost:7033/wards/W-05/schedule"
                            ))
                            .GET()
                            .build(),
                    HttpResponse.BodyHandlers.ofString()
            );

            assertEquals(200, response.statusCode());

        } finally {
            app.stop();
        }
    }

    @Test
    void shouldReturn404ForUnknownWard() throws Exception {

        Javalin app = StaffingServiceApp.createApp();
        app.start(7033);

        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(
                    HttpRequest.newBuilder()
                            .uri(URI.create(
                                    "http://localhost:7033/wards/W-999/schedule"
                            ))
                            .GET()
                            .build(),
                    HttpResponse.BodyHandlers.ofString()
            );

            assertEquals(404, response.statusCode());

        } finally {
            app.stop();
        }
    }

    @Test
    void shouldIncludeAlertLevelInSchedule() throws Exception {

        Javalin app = StaffingServiceApp.createApp();
        app.start(7033);

        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(
                    HttpRequest.newBuilder()
                            .uri(URI.create(
                                    "http://localhost:7033/wards/W-05/schedule"
                            ))
                            .GET()
                            .build(),
                    HttpResponse.BodyHandlers.ofString()
            );

            assertEquals(200, response.statusCode());

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode json = objectMapper.readTree(response.body());

            assertEquals("W-05", json.get("wardId").asText());
            assertEquals(0, json.get("alertLevel").asInt());
            assertTrue(json.get("schedule").asText().contains("Normal staffing"));

        } finally {
            app.stop();
        }
    }
}

