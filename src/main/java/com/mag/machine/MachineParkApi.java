package com.mag.machine;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class contains urls for machine park API.
 */
public class MachineParkApi {

    @JsonProperty("machines_list")
    private String machineListUrl;

    @JsonProperty("env_sensor")
    private String envSensorUrl;

    public String getMachineListUrl() {
        return machineListUrl;
    }

    public void setMachineListUrl(String machineListUrl) {
        this.machineListUrl = machineListUrl;
    }

    public String getEnvSensorUrl() {
        return envSensorUrl;
    }

    public void setEnvSensorUrl(String envSensorUrl) {
        this.envSensorUrl = envSensorUrl;
    }
}
