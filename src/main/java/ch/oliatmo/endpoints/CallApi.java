package ch.oliatmo.endpoints;

import ch.oliatmo.loggers.Logger;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CallApi {

    private final Logger logger;
    private final HttpClient client = HttpClient.newHttpClient();

    public CallApi(Logger logger) {
        this.logger = logger;
    }

    public HttpResponse<String> callHomeCoach(String accessToken){
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://api.netatmo.com/api/gethomecoachsdata?device_id=70%3Aee%3A50%3A67%3A4b%3A76"))
                    .GET()
                    .header("accept", "application/json")
                    .header("Authorization", "Bearer " + accessToken)
                    .build();

            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            logger.error("Exception calling Home Coach: " + e);
            return null;
        }
    }

    public HttpResponse<String> callAuthentication(String body){
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://api.netatmo.com/oauth2/token"))
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .header("content-type", "application/x-www-form-urlencoded")
                    .build();

            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            logger.error("Exception calling Authentication: " + e);
            return null;
        }
    }
}
