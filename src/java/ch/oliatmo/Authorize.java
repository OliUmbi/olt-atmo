package ch.oliatmo;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Authorize {

    public String execute(){
        return auth(createAuthCredentials());
//        System.out.println(auth(createRefreshCredentials("600b18e70b68e114c367022b|ed52b4dac4f5f420697328f1168bdaea")));
    }

    private String auth(String body){
        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://api.netatmo.com/oauth2/token"))
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .header("content-type", "application/x-www-form-urlencoded")
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println(response.statusCode());

            return response.body();
        } catch (Exception e) {
            System.out.println("Problem with connection: " + e);
        }
        return null;
    }

    private String createAuthCredentials(){
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("grant_type", "password");
        parameters.put("client_id", "600e75cefb3abb7fac6a173f");
        parameters.put("client_secret", "e1a2zJojAKY7edkK6zz98xfmxIb9wCG");
        parameters.put("username", "mumbricht@hispeed.ch");
        parameters.put("password", "GagiMitBohne99!");
        parameters.put("scope", "read_homecoach");
        return parameters.keySet().stream()
                .map(key -> key + "=" + URLEncoder.encode(parameters.get(key), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));
    }

    private String createRefreshCredentials(String refreshToken){
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("grant_type", "refresh_token");
        parameters.put("refresh_token", refreshToken);
        parameters.put("client_id", "600e75cefb3abb7fac6a173f");
        parameters.put("client_secret", "e1a2zJojAKY7edkK6zz98xfmxIb9wCG");
        return parameters.keySet().stream()
                .map(key -> key + "=" + URLEncoder.encode(parameters.get(key), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));
    }
}
