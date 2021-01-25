package ch.oliatmo;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Loop {

    private final ApiHandler apiHandler = new ApiHandler();

    public void loop(){
        apiHandler.inti();

        ScheduledExecutorService execService = Executors.newScheduledThreadPool(1);

        execService.scheduleAtFixedRate(new Runnable() {
            public void run() {
                apiHandler.requestUpdate();
            }
        }, 0L, 5L, TimeUnit.MINUTES);
    }
}
