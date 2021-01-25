package ch.oliatmo;

import java.awt.*;

public class Notification {

    public static void displayNotification(String message, boolean isError) {
        SystemTray tray = SystemTray.getSystemTray();

        Image image = Toolkit.getDefaultToolkit().createImage("icon.jpg");

        TrayIcon trayIcon = new TrayIcon(image, "Oli-Atmo");
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("Oli-Atmo");

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("Problems with notifications: " + e);
        }

        TrayIcon.MessageType messageType;
        if (isError) {
            messageType = TrayIcon.MessageType.ERROR;
        } else {
            messageType = TrayIcon.MessageType.INFO;
        }

        trayIcon.displayMessage("Oli-Atmo", message, messageType);
    }
}
