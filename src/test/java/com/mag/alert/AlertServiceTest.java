package com.mag.alert;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class AlertServiceTest {

    @Test
    public void alerts_noAlertsPublished_emptyList() {
        AlertService alertService = new AlertService();
        List<Alert> publishedAlerts = alertService.alerts();
        assertThat(publishedAlerts.size(), is(0));
    }

    @Test
    public void alerts_alertsPublished_allAlertsReturned() {
        AlertService alertService = new AlertService();
        List<Alert> alerts = createRandomAlerts(4);
        publishAlerts(alertService, alerts);
        List<Alert> publishedAlerts = alertService.alerts();
        assertThat(publishedAlerts.size(), is(4));
        assertThat(publishedAlerts.containsAll(alerts), is (true));
    }

    private List<Alert> createRandomAlerts(int number) {
        Random random = new Random();
        List<Alert> alerts = new ArrayList<>(number);
        for(int i = 0; i < number; i ++) {
            alerts.add(
                    new Alert(
                            UUID.randomUUID().toString(),
                            LocalDateTime.ofEpochSecond(random.nextInt(), 0, ZoneOffset.UTC),
                            random.nextDouble()
                    )
            );
        }
        return alerts;
    }

    private void publishAlerts(AlertService alertService, List<Alert> alerts) {
        for(Alert alert : alerts) {
            alertService.publishAlert(alert.getMachine(), alert.getTime(), alert.getAverageConsumption());
        }
    }

    @Test
    public void publishAlert_moreThan30AlertsPublished_last30LeftInQueue() {
        AlertService alertService = new AlertService();
        List<Alert> alerts = createRandomAlerts(31);
        publishAlerts(alertService, alerts);
        List<Alert> publishedAlerts = alertService.alerts();
        assertThat(publishedAlerts.size(), is(30));
        assertThat(publishedAlerts.containsAll(alerts.subList(1, alerts.size())), is (true));
    }

}