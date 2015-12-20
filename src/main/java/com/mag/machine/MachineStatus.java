package com.mag.machine;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

/**
 * Represents machine status in point in time.
 */
public class MachineStatus {

    private String name;
    private LocalDateTime timestamp;
    private Double current;
    private String state;
    private String location;
    @JsonProperty("current_alert")
    private Double currentAlert;
    private String type;

    public boolean alertState() {
        return this.current > this.currentAlert;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Double getCurrent() {
        return current;
    }

    public void setCurrent(Double current) {
        this.current = current;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getCurrentAlert() {
        return currentAlert;
    }

    public void setCurrentAlert(Double currentAlert) {
        this.currentAlert = currentAlert;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
