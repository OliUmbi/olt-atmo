package ch.oliatmo.mapper;

import java.util.HashMap;

public class RefreshRequestMapper extends AbstractRequestMapper {

    private final String clientId;
    private final String clientSecret;

    public RefreshRequestMapper(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public String build(String refreshToken) {
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("grant_type", "refresh_token");
        parameters.put("refresh_token", refreshToken);
        parameters.put("client_id", clientId);
        parameters.put("client_secret", clientSecret);

        return createUrlEncode(parameters);
    }
}
