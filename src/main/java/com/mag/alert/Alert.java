package com.mag.alert;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Class representing alert..
 */
public class Alert {

    private String machine;
    private LocalDateTime time;
    private Double averageConsumption;

    public Alert(String machine, LocalDateTime time, Double averageConsumption) {
        this.machine = machine;
        this.time = time;
        this.averageConsumption = averageConsumption;
    }

    public String getMachine() {
        return machine;
    }
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    public LocalDateTime getTime() {
        return time;
    }

    public Double getAverageConsumption() {
        return averageConsumption;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alert alert = (Alert) o;
        return Objects.equals(machine, alert.machine) &&
                Objects.equals(time, alert.time) &&
                Objects.equals(averageConsumption, alert.averageConsumption);
    }

    @Override
    public int hashCode() {
        return Objects.hash(machine, time, averageConsumption);
    }
}
