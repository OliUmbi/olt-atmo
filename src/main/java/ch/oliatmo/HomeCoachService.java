package ch.oliatmo;

import ch.oliatmo.mapper.HomeCoachResponseMapper;

import java.net.http.HttpResponse;
import java.util.logging.Level;

public class HomeCoachService {

    private final Logger logger = Logger.getInstance();
    private final RefreshTokenService refreshTokenService = new RefreshTokenService();
    private final CallApi callApi = new CallApi();
    private final HomeCoachResponseMapper homeCoachResponseMapper = new HomeCoachResponseMapper();

    public void requestUpdate(){
        if (refreshTokenService.isRefreshTokenExpired()) { refreshTokenService.updateRefreshToken(); }

        HttpResponse<String> response = callApi.callHomeCoach(refreshTokenService.getAccessToken());

        if (response.statusCode() == 200) {
            int co2 = homeCoachResponseMapper.getCo2(response.body());
            if (co2 > 1300) {
                logger.log("Co2: " + co2, Level.WARNING);
            } else {
                logger.log("Co2: " + co2, Level.INFO);
            }
        } else {
            logger.log("Non 200 status code " + response.statusCode(), Level.SEVERE);
        }
    }
}
