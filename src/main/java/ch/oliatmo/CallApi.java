package ch.oliatmo;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;

public class CallApi {

    private final Logger logger = Logger.getInstance();
    private final HttpClient client = HttpClient.newHttpClient();

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
            logger.log("Exception calling Home Coach: " + e, Level.SEVERE);
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
            logger.log("Exception calling Authentication: " + e, Level.SEVERE);
            return null;
        }
    }
}
