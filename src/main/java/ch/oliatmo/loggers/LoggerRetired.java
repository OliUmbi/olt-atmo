//package ch.oliatmo.loggers;
//
//import ch.oliatmo.enums.LogLevel;
//
//import java.awt.*;
//import java.awt.TrayIcon.MessageType;
//import java.util.ArrayList;
//import java.util.logging.Level;
//
//public class LoggerRetired {
//
//    public TrayIcon trayIcon;
//    private ArrayList<Integer> co2s;
//
//    public LoggerRetired (TrayIcon trayIcon) {
//        this.trayIcon = trayIcon;
//    }
//
//    public void log(String message, LogLevel logLevel) {
//        if (logLevel.equals(LogLevel.CO2)) {
//            displayCo2Graph();
//        }
//        writeConsole(message, level);
//        displayNotification(message, level);
//    }
//
//    private void displayCo2Graph () {
//
//
//
//        System.out.println("2000 +                              ..    ");
//        System.out.println("     ¦                            ..  .   ");
//        System.out.println("1500 +                          ..     .  ");
//        System.out.println("     ¦...                   ....        ..");
//        System.out.println("1000 +   ...           .....              ");
//        System.out.println("     ¦      ..   ......                   ");
//        System.out.println(" 500 +        ...                         ");
//        System.out.println("     ¦                                    ");
//        System.out.println("   0 +---+---+---+---+---+---+---+---+---+");
//        System.out.println("     12  22  32  42  52  02  12  22  32  42");
//    }
//
//    private void writeConsole(String message, Level level) {
//        if (level.equals(Level.INFO)) {
//            System.out.println("[" + level.toString() + "]\t\t" + message);
//        } else {
//            System.out.println("[" + level.toString() + "]\t" + message);
//        }
//    }
//
//    private void displayNotification(String message, Level level) {
//        if (level.equals(Level.SEVERE)) {
//            trayIcon.displayMessage("Oli-Atmo", message, MessageType.ERROR);
//        } else if (level.equals(Level.WARNING)) {
//            trayIcon.displayMessage("Oli-Atmo", message, MessageType.WARNING);
//        }
//    }
//}
