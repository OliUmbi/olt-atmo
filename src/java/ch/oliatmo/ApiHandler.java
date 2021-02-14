package ch.oliatmo;

import ch.oliatmo.dto.Refresh;
import ch.oliatmo.dto.Token;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.path.json.JsonPath;

import java.net.URLEncoder;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;

public class ApiHandler {

    private final CallApi callApi = new CallApi();
    private Token token;
    private Date tokenExpireDate;

    public void inti() {
        Notification.printConsole("Initialize program");
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("grant_type", "password");
        parameters.put("client_id", "600e75cefb3abb7fac6a173f");
        parameters.put("client_secret", "e1a2zJojAKY7edkK6zz98xfmxIb9wCG");
        parameters.put("username", "mumbricht@hispeed.ch");
        parameters.put("password", "GagiMitBohne99!");
        parameters.put("scope", "read_homecoach");

        HttpResponse<String> response = callApi.callAuthentication(createUrlEncode(parameters));

        if (response.statusCode() == 200){
            token = mapResponseToToken(response.body());
            resetExpireDate();
            Notification.printConsole("Token: \t\t" + token.getAccess_token());
            Notification.printConsole("Expire date: \t" + tokenExpireDate);
        } else {
            Notification.displayNotification("Non 200 status code " + response.statusCode(), true);
        }
    }

    public void requestUpdate(){
        if (tokenExpireDate.before(new Date())){
            Notification.printConsole("Refreshing token");

            HashMap<String, String> parameters = new HashMap<>();
            parameters.put("grant_type", "refresh_token");
            parameters.put("refresh_token", token.getRefresh_token());
            parameters.put("client_id", "600e75cefb3abb7fac6a173f");
            parameters.put("client_secret", "e1a2zJojAKY7edkK6zz98xfmxIb9wCG");

            HttpResponse<String> response = callApi.callAuthentication(createUrlEncode(parameters));

            if (response.statusCode() == 200) {
                Refresh refresh = mapResponseToRefresh(response.body());
                assert refresh != null;
                token.setAccess_token(refresh.getAccess_token());
                token.setRefresh_token(refresh.getRefresh_token());
                token.setExpire_in(refresh.getExpires_in());
                resetExpireDate();
                Notification.printConsole("Token: \t\t" + token.getAccess_token());
                Notification.printConsole("Expire date: \t" + tokenExpireDate);
            } else {
                Notification.displayNotification("Non 200 status code " + response.statusCode(), true);
            }
        }

        HttpResponse<String> response = callApi.callHomeCoach(token.getAccess_token());

        if (response.statusCode() == 200) {
            HashMap<String, Object> map = JsonPath.from(response.body()).get("body.devices[0].dashboard_data");
            int co2 = (int) map.get("CO2");
            if (co2 > 1300) {
                Notification.displayNotification("CO2 is to high " + co2, false);
            }
            Notification.printConsole("Co2: \t\t" + co2);
        } else {
            Notification.displayNotification("Non 200 status code " + response.statusCode(), true);
        }
    }

    private String createUrlEncode(HashMap<String, String> parameters){
        return parameters.keySet().stream()
                .map(key -> key + "=" + URLEncoder.encode(parameters.get(key), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));
    }

    private Token mapResponseToToken(String response){
        try {
            return new ObjectMapper().readValue(response, Token.class);
        } catch (Exception e){
            Notification.displayNotification("Exception mapping to Token: " + e, true);
            return null;
        }
    }

    private Refresh mapResponseToRefresh(String response){
        try {
            return new ObjectMapper().readValue(response, Refresh.class);
        } catch (Exception e){
            Notification.displayNotification("Exception mapping to Refresh: " + e, true);
            return null;
        }
    }

    private void resetExpireDate(){
        tokenExpireDate = new Date(new Date().getTime() + (token.getExpire_in() * 1000));
    }
}
