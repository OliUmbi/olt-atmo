package ch.oliatmo.mappers;

import ch.oliatmo.dtos.RefreshToken;
import ch.oliatmo.loggers.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TokenMapper {

    private final Logger logger;

    public TokenMapper(Logger logger) {
        this.logger = logger;
    }

    public RefreshToken mapResponseToRefreshToken(String response) {
        try {
            return new ObjectMapper().readValue(response, RefreshToken.class);
        } catch (Exception e){
            logger.error("Exception mapping to Refresh: " + e);
            return null;
        }
    }

    public RefreshToken mapRefreshResponseToRefreshToken(String response, RefreshToken refreshToken) throws Exception {
        RefreshToken refreshResponse = mapResponseToRefreshToken(response);
        if (refreshToken == null) {
            throw new Exception("Refresh Token is empty");
        }
        refreshToken.setAccess_token(refreshResponse.getAccess_token());
        refreshToken.setRefresh_token(refreshResponse.getRefresh_token());
        refreshToken.setExpire_in(refreshResponse.getExpires_in());
        return refreshToken;
    }
}
