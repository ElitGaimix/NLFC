package fr.elitgaimix.nlfc.runnables;


import fr.elitgaimix.nlfc.NLRController;

import java.util.concurrent.TimeUnit;

public class ELRSRunnable implements Runnable {

    private volatile long lastHeartbeat = System.nanoTime();

    public void onHeartbeatReceived() {
        lastHeartbeat = System.nanoTime();
        NLRController.INSTANCE.rocket.connected = true;
    }

    @Override
    public void run() {
        long elapsed = System.nanoTime() - lastHeartbeat;

        if (elapsed > TimeUnit.SECONDS.toNanos(1)) {
            System.out.println("Heartbeat perdu !");
            NLRController.INSTANCE.rocket.connected = false;
        }
        NLRController.INSTANCE.rocket.sendHeartBeat();
    }
}
