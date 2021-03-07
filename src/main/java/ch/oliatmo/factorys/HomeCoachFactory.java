package ch.oliatmo.factorys;

import ch.oliatmo.endpoints.CallApi;
import ch.oliatmo.loggers.Logger;
import ch.oliatmo.mappers.AuthenticationRequestMapper;
import ch.oliatmo.mappers.HomeCoachResponseMapper;
import ch.oliatmo.mappers.RefreshRequestMapper;
import ch.oliatmo.mappers.TokenMapper;
import ch.oliatmo.services.HomeCoachService;
import ch.oliatmo.services.RefreshTokenService;

import java.awt.*;

public class HomeCoachFactory {

    private final String clientId;
    private final String clientSecret;
    private final String username;
    private final String password;

    private Logger singleInstanceLogger = null;

    public HomeCoachFactory(String clientId, String clientSecret, String username, String password) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.username = username;
        this.password = password;
    }

    public Logger createLogger() {
        if (singleInstanceLogger == null) {
            singleInstanceLogger = new Logger(createTrayIcon());
        }
        return singleInstanceLogger;
    }

    public HomeCoachService createHomeCoachService() {
        return new HomeCoachService(
            createLogger(),
            createRefreshTokenService(),
            createCallApi(),
            createHomeCoachResponseMapper()
        );
    }

    public RefreshTokenService createRefreshTokenService() {
        return new RefreshTokenService(
                createLogger(),
                createAuthenticationRequestMapper(),
                createRefreshRequestMapper(),
                createTokenMapper(),
                createCallApi()
        );
    }

    public CallApi createCallApi() {
        return new CallApi(createLogger());
    }

    public AuthenticationRequestMapper createAuthenticationRequestMapper() {
        return new AuthenticationRequestMapper(clientId, clientSecret, username, password);
    }

    public HomeCoachResponseMapper createHomeCoachResponseMapper() {
        return new HomeCoachResponseMapper();
    }

    public RefreshRequestMapper createRefreshRequestMapper() {
        return new RefreshRequestMapper(clientId, clientSecret);
    }

    public TokenMapper createTokenMapper() {
        return new TokenMapper(createLogger());
    }

    private TrayIcon createTrayIcon() {
        try {
            SystemTray tray = SystemTray.getSystemTray();
            Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
            TrayIcon trayIcon = new TrayIcon(image, "Java AWT Tray Demo");
            trayIcon.setImageAutoSize(true);
            tray.add(trayIcon);
            return trayIcon;
        } catch (Exception e) {
            System.out.println("exception " + e);
        }
        return null;
    }
}
