package ch.oliatmo.loggers;

import ch.oliatmo.enums.Co2Level;

import java.awt.*;
import java.awt.TrayIcon.MessageType;
import java.util.ArrayList;

public class Logger {

    public TrayIcon trayIcon;
    public ArrayList<Integer> co2History = new ArrayList<>();

    public Logger (TrayIcon trayIcon) {
        this.trayIcon = trayIcon;
    }
    
    public void info(String message) {
        writeConsole(message, "[INFO]\t");
    }
    
    public void co2(int co2, Co2Level co2Level) {
        if (co2Level.equals(Co2Level.HIGH)){
            displayNotification("CO2: " + co2, MessageType.WARNING);
        } else if (co2Level.equals(Co2Level.EXTREME)){
            displayNotification("CO2: " + co2, MessageType.ERROR);
        }
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        writeConsole("CO2: " + co2, "[INFO]\t");
        System.out.println("\n");
    
        co2History.add(co2);
        displayCo2Graph();
    }
    
    public void error(String message) {
        writeConsole(message, "[ERROR]\t");
        displayNotification(message, MessageType.ERROR);
    }
    
    private void displayCo2Graph () {
        String[] graph = new String[9];
        graph[0] = "2000 +";
        graph[1] = "     ¦";
        graph[2] = "1500 +";
        graph[3] = "     ¦";
        graph[4] = "1000 +";
        graph[5] = "     ¦";
        graph[6] = " 500 +";
        graph[7] = "     ¦";
        graph[8] = "   0 +";
        
        for (int i = 0; i < co2History.size(); i++) {
            for (int j = 0; j < graph.length; j++) {
                if (j == 8) {
                    int i8 = i + 1;
                    if (i8 % 5 == 0) {
                        graph[8] += "+";
                    } else {
                        graph[8] += "-";
                    }
                } else {
                    graph[j] += " ";
                }
            }
            int co2 = co2History.get(i) / 250;
            if (co2 < 1) {
                co2 = 1;
            }
            graph[8 - co2] = replaceLastChar(graph[8 - co2], '.');
        }
    
        for (String graphLine: graph) {
            System.out.println(graphLine);
        }
    }
    
    private void writeConsole(String message, String prefix) {
        System.out.println(prefix + message);
    }

    private void displayNotification(String message, MessageType messageType) {
        trayIcon.displayMessage("Oli-Atmo", message, messageType);
    }
    
    private String replaceLastChar(String string, char character) {
        return string.substring(0, string.length() - 1) + character;
    }
}
