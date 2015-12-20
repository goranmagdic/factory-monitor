package com.mag.machine;

import org.springframework.web.client.RestTemplate;

/**
 * Rest client for Machine Park API.
 * initApi in order for class to initialize.
 */
public class MachineParkClient {

    public MachineParkClient (RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private RestTemplate restTemplate;

    private MachineParkApi machineParkApi;

    private static final String API_ROOT ="http://machinepark.actyx.io/api/v1";
    private static final String API_ROOT_PLACEHOLDER ="$API_ROOT";

    /**
     * Initializes Api. Needs to be called as part of initialization before any othermethod.
     */
    public void initApi() {
        machineParkApi = restTemplate.getForObject(API_ROOT, MachineParkApi.class);
    }

    /**
     * Returns urls of all machines in park.
     * @return array of machine urls.
     */
    public String[] getMachineUrls() {
        return restTemplate.getForObject(formatUrl(machineParkApi.getMachineListUrl()), String[].class);
    }

    /**
     * Returns machine status of machine with url provided.
     * @param machineUrl machine url that includes $API_ROOT placeholder.
     * @return machine status.
     */
    public MachineStatus getMachineStatus (String machineUrl) {
        return restTemplate.getForObject(formatUrl(machineUrl), MachineStatus.class);
    }

    private String formatUrl(String url) {
       return url.replace(API_ROOT_PLACEHOLDER, API_ROOT);
    }
}
