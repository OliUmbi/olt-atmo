package ch.oliatmo;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CallApi {

    public HttpResponse<String> callHomeCoach(String accessToken){
        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://api.netatmo.com/api/gethomecoachsdata?device_id=70%3Aee%3A50%3A67%3A4b%3A76"))
                    .GET()
                    .header("accept", "application/json")
                    .header("Authorization", "Bearer " + accessToken)
                    .build();

            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            Notification.displayNotification("Exception calling Home Coach: " + e, true);
            return null;
        }
    }

    public HttpResponse<String> callAuthentication(String body){
        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://api.netatmo.com/oauth2/token"))
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .header("content-type", "application/x-www-form-urlencoded")
                    .build();

            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            Notification.displayNotification("Exception calling Authentication: " + e, true);
            return null;
        }
    }
}
