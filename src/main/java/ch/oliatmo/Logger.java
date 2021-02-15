package ch.oliatmo;

import java.awt.*;
import java.util.logging.Level;

public class Logger {

    public static void log(String message, Level level) {
        writeConsole(message, level);
        displayNotification(message, level);
    }

    private static void writeConsole(String message, Level level) {
        if (level.equals(Level.INFO)) {
            System.out.println("[" + level.toString() + "]\t\t" + message);
        } else {
            System.out.println("[" + level.toString() + "]\t" + message);
        }
    }

    private static void displayNotification(String message, Level level) {
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

        if (level.equals(Level.SEVERE)) {
            trayIcon.displayMessage("Oli-Atmo", message, TrayIcon.MessageType.ERROR);
        } else if (level.equals(Level.WARNING)) {
            trayIcon.displayMessage("Oli-Atmo", message, TrayIcon.MessageType.WARNING);
        }
    }
}
