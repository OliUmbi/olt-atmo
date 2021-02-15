package ch.oliatmo;

import ch.oliatmo.dtos.RefreshToken;
import ch.oliatmo.mapper.AuthenticationRequestMapper;
import ch.oliatmo.mapper.RefreshRequestMapper;
import ch.oliatmo.mapper.TokenMapper;

import java.net.http.HttpResponse;
import java.util.Date;
import java.util.logging.Level;

public class RefreshTokenService {

    private final AuthenticationRequestMapper authenticationRequestMapper = new AuthenticationRequestMapper(
            System.getenv("oliAtmoClientId"),
            System.getenv("oliAtmoClientSecret"),
            System.getenv("oliAtmoUsername"),
            System.getenv("oliAtmoPassword"));
    private final RefreshRequestMapper refreshRequestMapper = new RefreshRequestMapper(
            System.getenv("oliAtmoClientId"),
            System.getenv("oliAtmoClientSecret"));
    private final TokenMapper tokenMapper = new TokenMapper();
    private final CallApi callApi = new CallApi();
    private RefreshToken refreshToken;
    private Date expireDate;
    private final Date currentDate = new Date();

    public RefreshTokenService() {
        initializeRefreshToken();
    }

    public void updateRefreshToken() {
        Logger.log("Updating Token", Level.INFO);
        String refreshURlEncode = refreshRequestMapper.build(refreshToken.getRefresh_token());

        HttpResponse<String> response = callApi.callAuthentication(refreshURlEncode);

        if (response.statusCode() == 200) {
            try {
                refreshToken = tokenMapper.mapRefreshResponseToRefreshToken(response.body(), refreshToken);
                resetExpireDate();
            } catch (Exception e) {
                Logger.log("Failed to Map new refresh Token " + response.statusCode(), Level.SEVERE);
            }
        } else {
            none200StatusCode(response.statusCode());
        }
    }

    public boolean isRefreshTokenExpired() {
        return currentDate.after(expireDate);
    }

    public String getAccessToken() {
        return refreshToken.getAccess_token();
    }

    private void initializeRefreshToken() {
        Logger.log("Initializing Program", Level.INFO);
        String authUrlEncode = authenticationRequestMapper.build("read_homecoach");

        HttpResponse<String> response = callApi.callAuthentication(authUrlEncode);

        if (response.statusCode() == 200){
            refreshToken = tokenMapper.mapResponseToRefreshToken(response.body());
            resetExpireDate();
        } else {
            none200StatusCode(response.statusCode());
        }
    }

    private void resetExpireDate() {
        expireDate = new Date(refreshToken.getExpire_in() * 1000 + currentDate.getTime());
        Logger.log("Expire:\t" + expireDate.toString(), Level.INFO);
    }

    private void none200StatusCode(int statusCode) {
        Logger.log("Non 200 status code " + statusCode, Level.SEVERE);
    }
}
