package ch.oliatmo;

import java.awt.*;
import java.awt.TrayIcon.MessageType;
import java.util.logging.Level;

public class Logger {

    private static Logger singleInstance = null;
    public TrayIcon trayIcon;

    public static Logger getInstance() {
        if (singleInstance == null) {
            singleInstance = new Logger();
        }
        return singleInstance;
    }

    public void log(String message, Level level) {
        writeConsole(message, level);
        displayNotification(message, level);
    }

    private void writeConsole(String message, Level level) {
        if (level.equals(Level.INFO)) {
            System.out.println("[" + level.toString() + "]\t\t" + message);
        } else {
            System.out.println("[" + level.toString() + "]\t" + message);
        }
    }

    private void displayNotification(String message, Level level) {
        if (trayIcon == null) {
            try {
                createSystemTray();
            } catch (Exception e) {
                writeConsole("Problems with notifications: " + e, Level.SEVERE);
            }
        }

        if (level.equals(Level.SEVERE)) {
            trayIcon.displayMessage("Oli-Atmo", message, MessageType.ERROR);
        } else if (level.equals(Level.WARNING)) {
            trayIcon.displayMessage("Oli-Atmo", message, MessageType.WARNING);
        }
    }

    private void createSystemTray() throws AWTException {
        SystemTray tray = SystemTray.getSystemTray();
        Image image = Toolkit.getDefaultToolkit().createImage("icon.jpg");

        TrayIcon trayIcon = new TrayIcon(image, "Oli-Atmo");
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("Oli-Atmo");
        tray.add(trayIcon);

        this.trayIcon = trayIcon;
    }
}
