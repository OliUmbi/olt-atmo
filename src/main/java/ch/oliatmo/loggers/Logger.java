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
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            writeConsole(e.getMessage() ,"[INFO]\t");
        }
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
        String[] graph = createGraph();
        
        for (int i = 0; i < co2History.size(); i++) {
            for (int j = 0; j < graph.length; j++) {
                if (j == 20) {
                    int k = i + 1;
                    if (k % 5 == 0) {
                        graph[20] += "+";
                    } else {
                        graph[20] += "-";
                    }
                } else {
                    graph[j] += " ";
                }
            }
            int co2 = co2History.get(i) / 100;
            if (co2 < 1) {
                co2 = 1;
            }
            graph[20 - co2] = replaceLastChar(graph[20 - co2], '.');
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
    
    private String[] createGraph() {
        String[] graph = new String[21];
        graph[0] = "2000 +";
        graph[1] = "     ¦";
        graph[2] = "1800 +";
        graph[3] = "     ¦";
        graph[4] = "1600 +";
        graph[5] = "     ¦";
        graph[6] = "1400 +";
        graph[7] = "     ¦";
        graph[8] = "1200 +";
        graph[9] = "     ¦";
        graph[10] = "1000 +";
        graph[11] = "     ¦";
        graph[12] = " 800 +";
        graph[13] = "     ¦";
        graph[14] = " 600 +";
        graph[15] = "     ¦";
        graph[16] = " 400 +";
        graph[17] = "     ¦";
        graph[18] = " 200 +";
        graph[19] = "     ¦";
        graph[20] = "   0 +";
        return graph;
    }
    
    private String replaceLastChar(String string, char character) {
        return string.substring(0, string.length() - 1) + character;
    }
}
