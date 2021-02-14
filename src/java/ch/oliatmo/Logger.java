package ch.oliatmo;

import java.awt.*;
import java.util.logging.Level;

public class Logger {

    private TrayIcon trayIcon;

    public Logger() {
        createSystemTray();
    }

    public void log(String message, Level level) {
        writeConsole(message, level);
        displayNotification(message, level);
    }

    private void writeConsole(String message, Level level) {
        System.out.println("[" + level.toString() + "] " + message);
    }

    private void displayNotification(String message, Level level) {
        if (level.equals(Level.SEVERE)) {
            trayIcon.displayMessage("Oli-Atmo", message, TrayIcon.MessageType.ERROR);
        } else if (level.equals(Level.WARNING)) {
            trayIcon.displayMessage("Oli-Atmo", message, TrayIcon.MessageType.WARNING);
        }
    }

    private void createSystemTray() {
        SystemTray tray = SystemTray.getSystemTray();
        Image image = Toolkit.getDefaultToolkit().createImage("../../../resources/icon.jpg");

        TrayIcon trayIcon = new TrayIcon(image, "Oli-Atmo");
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("Oli-Atmo");

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("Problems with notifications: " + e);
        }

        this.trayIcon = trayIcon;
    }
}
