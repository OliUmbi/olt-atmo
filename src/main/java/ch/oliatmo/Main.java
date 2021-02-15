package ch.oliatmo;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        HomeCoachService homeCoachService = new HomeCoachService();

        ScheduledExecutorService execService = Executors.newScheduledThreadPool(1);
        execService.scheduleAtFixedRate(new Runnable() {
            public void run() {
                homeCoachService.requestUpdate();
            }
        }, 0L, 5L, TimeUnit.MINUTES);
    }
}
