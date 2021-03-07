package ch.oliatmo.services;

import ch.oliatmo.dtos.RefreshToken;
import ch.oliatmo.endpoints.CallApi;
import ch.oliatmo.loggers.Logger;
import ch.oliatmo.mappers.AuthenticationRequestMapper;
import ch.oliatmo.mappers.RefreshRequestMapper;
import ch.oliatmo.mappers.TokenMapper;

import java.net.http.HttpResponse;
import java.util.Date;

public class RefreshTokenService {

    private final Logger logger;
    private final AuthenticationRequestMapper authenticationRequestMapper;
    private final RefreshRequestMapper refreshRequestMapper;
    private final TokenMapper tokenMapper;
    private final CallApi callApi;
    private RefreshToken refreshToken;
    private Date expireDate;

    public RefreshTokenService(
        Logger logger,
        AuthenticationRequestMapper authenticationRequestMapper,
        RefreshRequestMapper refreshRequestMapper,
        TokenMapper tokenMapper,
        CallApi callApi
    ) {
        this.logger = logger;
        this.authenticationRequestMapper = authenticationRequestMapper;
        this.refreshRequestMapper = refreshRequestMapper;
        this.tokenMapper = tokenMapper;
        this.callApi = callApi;
        initializeRefreshToken();
    }

    public void updateRefreshToken() {
        String refreshURlEncode = refreshRequestMapper.build(refreshToken.getRefresh_token());

        HttpResponse<String> response = callApi.callAuthentication(refreshURlEncode);
        
        if (response.statusCode() == 200) {
            try {
                refreshToken = tokenMapper.mapRefreshResponseToRefreshToken(response.body(), refreshToken);
                resetExpireDate();
            } catch (Exception e) {
                logger.error("Failed to Map new refresh Token " + response.statusCode());
            }
        } else {
            none200StatusCode(response);
        }
    }

    public boolean isRefreshTokenExpired() {
        return expireDate.before(new Date());
    }

    public String getAccessToken() {
        return refreshToken.getAccess_token();
    }

    private void initializeRefreshToken() {
        logger.info("Initializing Program");
        String authUrlEncode = authenticationRequestMapper.build("read_homecoach");

        HttpResponse<String> response = callApi.callAuthentication(authUrlEncode);

        if (response.statusCode() == 200){
            refreshToken = tokenMapper.mapResponseToRefreshToken(response.body());
            resetExpireDate();
        } else {
            none200StatusCode(response);
        }
    }

    private void resetExpireDate() {
        expireDate = new Date(refreshToken.getExpire_in() * 1000 + new Date().getTime());
    }

    private void none200StatusCode(HttpResponse<String> response) {
        logger.error("Non 200 status code on update " + response.statusCode() + " " + response.body());
    }
}
