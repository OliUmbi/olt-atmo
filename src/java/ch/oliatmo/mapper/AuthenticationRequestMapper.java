package ch.oliatmo.mapper;

import java.util.HashMap;

public class AuthenticationRequestMapper extends AbstractRequestMapper {

    private final String clientId;
    private final String clientSecret;
    private final String username;
    private final String password;

    public AuthenticationRequestMapper(String clientId, String clientSecret, String username, String password) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.username = username;
        this.password = password;
    }

    public String build(String scope) {
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("grant_type", "password");
        parameters.put("client_id", clientId);
        parameters.put("client_secret", clientSecret);
        parameters.put("username", username);
        parameters.put("password", password);
        parameters.put("scope", scope);

        return createUrlEncode(parameters);
    }
}
