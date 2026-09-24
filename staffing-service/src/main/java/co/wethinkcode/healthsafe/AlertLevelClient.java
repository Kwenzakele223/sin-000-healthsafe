package co.wethinkcode.healthsafe;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

class AlertLevelClient {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public AlertLevelClient() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public int getAlertLevel() {

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:7032/alert-level"))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            if (response.statusCode() != 200) {
                throw new RuntimeException(
                        "Alert Level Service returned status "
                                + response.statusCode()
                );
            }

            JsonNode json = objectMapper.readTree(response.body());

            return json.get("level").asInt();

        } catch (Exception e) {
            throw new RuntimeException(
                    "Could not contact Alert Level Service",
                    e
            );
        }
    }
}

