package ch.oliatmo;

import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        try {
            SystemTray tray = SystemTray.getSystemTray();
            Image image = Toolkit.getDefaultToolkit().createImage("icon.png");

            TrayIcon trayIcon = new TrayIcon(image, "Java AWT Tray Demo");
            //Let the system resize the image if needed
            trayIcon.setImageAutoSize(true);
            //Set tooltip text for the tray icon
            tray.add(trayIcon);
        } catch (Exception e) {
            System.out.println("exception " + e);
        }

        HomeCoachService homeCoachService = new HomeCoachService();

        ScheduledExecutorService execService = Executors.newScheduledThreadPool(1);
        execService.scheduleAtFixedRate(new Runnable() {
            public void run() {
                homeCoachService.requestUpdate();
            }
        }, 0L, 5L, TimeUnit.MINUTES);
    }
}
