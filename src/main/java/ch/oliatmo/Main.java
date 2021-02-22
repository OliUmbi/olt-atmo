package ch.oliatmo;

import ch.oliatmo.factorys.HomeCoachFactory;
import ch.oliatmo.services.HomeCoachService;

import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
    
        SystemTray tray = SystemTray.getSystemTray();
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");

        TrayIcon trayIcon = new TrayIcon(image, "Java AWT Tray Demo");
        trayIcon.setImageAutoSize(true);
        try {
            tray.add(trayIcon);
        } catch (Exception e) {
            System.out.println("exception " + e);
        }
        
        HomeCoachFactory homeCoachFactory = new HomeCoachFactory(
            System.getenv("oliAtmoClientId"),
            System.getenv("oliAtmoClientSecret"),
            System.getenv("oliAtmoUsername"),
            System.getenv("oliAtmoPassword"));

        HomeCoachService homeCoachService = homeCoachFactory.createHomeCoachService();

        ScheduledExecutorService execService = Executors.newScheduledThreadPool(1);
        execService.scheduleAtFixedRate(new Runnable() {
            public void run() {
                homeCoachService.requestUpdate();
            }
        }, 0L, 5L, TimeUnit.MINUTES);
    }
}
