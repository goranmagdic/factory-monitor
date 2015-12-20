package com.mag.machine;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

/**
 * Represents machine.
 */
public class Machine {
    private String url;
    private ConsumptionState consumptionState;
    private Double averageConsumption;
    private Double averageConsumptionBeforeLastStatus;
    private List<MachineStatus> statuses = new ArrayList<>();

    public Machine(String url) {
        this.url = url;
        this.consumptionState = ConsumptionState.OK;
        this.averageConsumption = 0D;
    }

    public Machine(Machine machine, MachineStatus newStatus) {
        this.url = machine.getUrl();

        statuses.addAll(machine.statuses.stream()
                .filter(removeOlderThanFiveMins())
                .collect(toList()));
        statuses.add(newStatus);
        this.consumptionState = calculateConsumptionState(machine, newStatus);
        this.averageConsumptionBeforeLastStatus = machine.averageConsumption;
        this.averageConsumption = statuses.stream().mapToDouble(MachineStatus::getCurrent).average().orElse(0D);
    }

    private Predicate<MachineStatus> removeOlderThanFiveMins() {
        return status ->
                LocalDateTime.now().minus(5, ChronoUnit.MINUTES)
                        .isBefore(status.getTimestamp());
    }

    private ConsumptionState calculateConsumptionState(Machine machine, MachineStatus newStatus) {
        if(!newStatus.alertState()) {
            return ConsumptionState.OK;
        } else if (machine.consumptionState == ConsumptionState.OK) {
            return ConsumptionState.ENTERED_ALERT;
        }
        return ConsumptionState.ALERT;
    }

    /**
     * If machin entered alert status after last status update.
     * @return entered alert.
     */
    public boolean enteredAlert() {
        return consumptionState.equals(ConsumptionState.ENTERED_ALERT);
    }

    /**
     * Returns a name of empty if not initialized.
     * @return name.
     */
    public String getName() {
        if(statuses.isEmpty()) {
            return "";
        } else {
            return getLastStatus().getName();
        }
    }

    /**
     * Returns last status, throws NPE if there are no statuses.
     * @return last status.
     */
    public MachineStatus getLastStatus() {
        return statuses.get(statuses.size() - 1);
    }

    public String getUrl() {
        return url;
    }

    /**
     * Returns average energy consumption not including last status update.
     * Takes into account only last five minutes.
     * @return average consumption
     */
    public Double getAverageConsumptionBeforeLastStatus() {
        return averageConsumptionBeforeLastStatus;
    }

    enum ConsumptionState {
        OK, ENTERED_ALERT, ALERT
    }
}
