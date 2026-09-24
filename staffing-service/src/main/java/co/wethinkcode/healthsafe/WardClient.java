package co.wethinkcode.healthsafe;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

class WardClient {

    private final HttpClient httpClient;

    public WardClient() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public boolean wardExists(String wardId) {

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:7031/wards/" + wardId))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            return response.statusCode() == 200;

        } catch (Exception e) {
            throw new RuntimeException("Could not contact Ward Service", e);
        }
    }
}