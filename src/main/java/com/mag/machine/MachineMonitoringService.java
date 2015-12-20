package com.mag.machine;

import com.mag.alert.AlertService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Service monitoring machines, monitors statuses, publishes alerts.
 */
@Service
public class MachineMonitoringService implements InitializingBean {


    @Autowired
    private MachineParkClient machineParkClient;

    @Autowired
    private AlertService alertService;

    private List<Machine> machines = new ArrayList<>();

    @Scheduled(fixedDelay=5000)
    public void loadMachineStatuses() {
        machines = machines.parallelStream().map(
                machine -> {
                    MachineStatus newStatus = machineParkClient.getMachineStatus(machine.getUrl());
                    return new Machine(machine, newStatus);
                }
        ).collect(toList());

        machines.stream().filter(Machine::enteredAlert).forEach(this::publishAlert);

    }

    private void publishAlert(Machine machine) {
        alertService.publishAlert(
                machine.getName(),
                machine.getLastStatus().getTimestamp(),
                machine.getAverageConsumptionBeforeLastStatus()
        );
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        String[] machineUrls = machineParkClient.getMachineUrls();
        for(String machineUrl : machineUrls){
            machines.add(new Machine(machineUrl));
        }
    }
}
