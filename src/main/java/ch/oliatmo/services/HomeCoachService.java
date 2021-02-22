package ch.oliatmo.services;

import ch.oliatmo.endpoints.CallApi;
import ch.oliatmo.enums.Co2Level;
import ch.oliatmo.loggers.Logger;
import ch.oliatmo.mappers.HomeCoachResponseMapper;

import java.net.http.HttpResponse;

public class HomeCoachService {

    private final Logger logger;
    private final RefreshTokenService refreshTokenService;
    private final CallApi callApi;
    private final HomeCoachResponseMapper homeCoachResponseMapper;

    public HomeCoachService(
        Logger logger,
        RefreshTokenService refreshTokenService,
        CallApi callApi,
        HomeCoachResponseMapper homeCoachResponseMapper
    ) {
        this.logger = logger;
        this.refreshTokenService = refreshTokenService;
        this.callApi = callApi;
        this.homeCoachResponseMapper = homeCoachResponseMapper;
    }

    public void requestUpdate(){
        if (refreshTokenService.isRefreshTokenExpired()) {
            refreshTokenService.updateRefreshToken();
        }
        
        HttpResponse<String> response = callApi.callHomeCoach(refreshTokenService.getAccessToken());

        if (response.statusCode() == 200) {
            int co2 = homeCoachResponseMapper.getCo2(response.body());
            if (co2 > 1900) {
                logger.co2(co2, Co2Level.EXTREME);
            } else if (co2 > 1300) {
                logger.co2(co2, Co2Level.HIGH);
            } else {
                logger.co2(co2, Co2Level.NORMAL);
            }
        } else {
            logger.error("Non 200 status code on update " + response.statusCode() + " " + response.body());
        }
    }
}
