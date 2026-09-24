package co.wethinkcode.healthsafe;

import io.javalin.Javalin;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WardServiceAppTest {

    @Test
    void shouldReturnWardWhenWardExists() throws Exception {

        Javalin app = WardServiceApp.createApp();

        app.start(7031);

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:7031/wards/W-05"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        assertEquals(200, response.statusCode());

        app.stop();
    }

    @Test
    void shouldReturn404WhenWardDoesNotExist() throws Exception {

        Javalin app = WardServiceApp.createApp();

        app.start(7031);

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:7031/wards/W-999"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        assertEquals(404, response.statusCode());

        app.stop();
    }

    @Test
    void shouldReturnDepartments() throws Exception {

        Javalin app = WardServiceApp.createApp();
        app.start(7031);

        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(
                    HttpRequest.newBuilder()
                            .uri(URI.create("http://localhost:7031/departments"))
                            .GET()
                            .build(),
                    HttpResponse.BodyHandlers.ofString()
            );

            assertEquals(200, response.statusCode());
            assertTrue(response.body().contains("Cardiology"));
            assertTrue(response.body().contains("Paediatrics"));
            assertTrue(response.body().contains("Oncology"));
            assertTrue(response.body().contains("Radiology"));
            assertTrue(response.body().contains("ICU"));
            assertTrue(response.body().contains("Maternity"));

        } finally {
            app.stop();
        }
    }
}

