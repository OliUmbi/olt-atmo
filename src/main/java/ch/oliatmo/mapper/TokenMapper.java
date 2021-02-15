package ch.oliatmo.mapper;

import ch.oliatmo.Logger;
import ch.oliatmo.dtos.RefreshToken;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.logging.Level;

public class TokenMapper {

    private final Logger logger = Logger.getInstance();

    public RefreshToken mapResponseToRefreshToken(String response) {
        try {
            return new ObjectMapper().readValue(response, RefreshToken.class);
        } catch (Exception e){
            logger.log("Exception mapping to Refresh: " + e, Level.SEVERE);
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
