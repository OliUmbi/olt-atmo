package ch.oliatmo;

import ch.oliatmo.mapper.AuthenticationRequestMapper;
import ch.oliatmo.mapper.HomeCoachResponseMapper;
import ch.oliatmo.mapper.RefreshRequestMapper;
import ch.oliatmo.mapper.TokenMapper;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        CallApi callApi = new CallApi();
        TokenMapper tokenMapper = new TokenMapper();
        HomeCoachResponseMapper homeCoachResponseMapper = new HomeCoachResponseMapper();
        RefreshRequestMapper refreshRequestMapper = new RefreshRequestMapper(
                System.getenv("clientId"),
                System.getenv("clientSecret")
        );

        AuthenticationRequestMapper authenticationRequestMapper = new AuthenticationRequestMapper(
                System.getenv("clientId"),
                System.getenv("clientSecret"),
                System.getenv("username"),
                System.getenv("password")
        );

        ApiService apiService = new ApiService(
                callApi,
                refreshRequestMapper,
                authenticationRequestMapper,
                tokenMapper,
                homeCoachResponseMapper
        );

        ScheduledExecutorService execService = Executors.newScheduledThreadPool(1);

        execService.scheduleAtFixedRate(new Runnable() {
            public void run() {
                apiService.requestUpdate();
            }
        }, 0L, 5L, TimeUnit.MINUTES);
    }

}
