package com.mag.alert;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Service for handling {@link Alert}s.
 * It will keep 30 alerts that were added last.
 */
@Service
public class AlertService {

    private static final int ALERT_QUEUE_SIZE = 30;

    private ArrayBlockingQueue<Alert> alerts = new ArrayBlockingQueue<>(ALERT_QUEUE_SIZE);

    /**
     * Publishes alert for machine. If alert queue already has 30 alerts, method will remove first one.
     * @param machine machine name.
     * @param timestamp when alert occurred.
     * @param averageConsumption average consumption.
     */
    public synchronized void publishAlert(String machine, LocalDateTime timestamp, Double averageConsumption){
        if(alerts.size() == ALERT_QUEUE_SIZE) {
            alerts.remove();
        }
        alerts.add(new Alert(machine, timestamp, averageConsumption));
    }

    public List<Alert> alerts() {
        return new ArrayList<>(alerts);
    }
}
