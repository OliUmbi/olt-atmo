package ch.oliatmo;

import ch.oliatmo.dtos.RefreshToken;
import ch.oliatmo.mapper.AuthenticationRequestMapper;
import ch.oliatmo.mapper.HomeCoachResponseMapper;
import ch.oliatmo.mapper.RefreshRequestMapper;
import ch.oliatmo.mapper.TokenMapper;

import java.net.http.HttpResponse;
import java.util.Date;
import java.util.logging.Level;

public class ApiService {

    private final Logger logger = new Logger();
    private final CallApi callApi;
    private final RefreshRequestMapper refreshRequestMapper;
    private final AuthenticationRequestMapper authenticationRequestMapper;
    private final TokenMapper tokenMapper;
    private final HomeCoachResponseMapper homeCoachResponseMapper;

    private RefreshToken refreshToken = new RefreshToken();

    public ApiService(
            CallApi callApi,
            RefreshRequestMapper refreshRequestMapper,
            AuthenticationRequestMapper authenticationRequestMapper,
            TokenMapper tokenMapper,
            HomeCoachResponseMapper homeCoachResponseMapper)
    {
        this.callApi = callApi;
        this.refreshRequestMapper = refreshRequestMapper;
        this.authenticationRequestMapper = authenticationRequestMapper;
        this.tokenMapper = tokenMapper;
        this.homeCoachResponseMapper = homeCoachResponseMapper;
        inti();
    }

    public void requestUpdate(){
        if ((refreshToken.getExpire_in() * 1000) < new Date().getTime()) { refreshToken(); }

        HttpResponse<String> response = callApi.callHomeCoach(refreshToken.getAccess_token());

        if (response.statusCode() == 200) {
            int co2 = homeCoachResponseMapper.getCo2(response.body());
            if (co2 > 1300) {
                logger.log("CO2 is to high " + co2, Level.WARNING);
            }
            logger.log("Co2: \t" + co2, Level.INFO);
        } else {
            logger.log("Non 200 status code " + response.statusCode(), Level.SEVERE);
        }
    }

    private void inti() {
        logger.log("Initialize program", Level.INFO);

        String authUrlEncode = authenticationRequestMapper.build("read_homecoach");

        HttpResponse<String> response = callApi.callAuthentication(authUrlEncode);

        if (response.statusCode() == 200){
            refreshToken = tokenMapper.mapResponseToRefreshToken(response.body());
            logger.log("Token: \t" + refreshToken.getAccess_token(), Level.INFO);
            logger.log("Expire: \t" + refreshToken.getExpire_in(), Level.INFO);
        } else {
            logger.log("Non 200 status code " + response.statusCode(), Level.SEVERE);
        }
    }

    private void refreshToken() {
        logger.log("Refreshing token", Level.INFO);

        String refreshURlEncode = refreshRequestMapper.build(refreshToken.getRefresh_token());

        HttpResponse<String> response = callApi.callAuthentication(refreshURlEncode);

        if (response.statusCode() == 200) {
            try {
                refreshToken = tokenMapper.mapRefreshResponseToRefreshToken(response.body(), refreshToken);
            } catch (Exception e) {
                logger.log("Failed to Map new refresh Token " + response.statusCode(), Level.SEVERE);
            }

            logger.log("Token: \t" + refreshToken.getAccess_token(), Level.INFO);
            logger.log("Expire: \t" + refreshToken.getExpire_in(), Level.INFO);
        } else {
            logger.log("Non 200 status code " + response.statusCode(), Level.SEVERE);
        }
    }
}
