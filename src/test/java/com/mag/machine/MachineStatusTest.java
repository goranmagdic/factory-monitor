package com.mag.machine;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class MachineStatusTest {

    @Test
    public void alertState_currentLessThenAlert_false() {
        MachineStatus machineStatus = new MachineStatus();
        machineStatus.setCurrent(22D);
        machineStatus.setCurrentAlert(33D);
        assertThat(machineStatus.alertState(), is(false));
    }


    @Test
    public void alertState_currentEqualsAlert_false() {
        MachineStatus machineStatus = new MachineStatus();
        machineStatus.setCurrent(22D);
        machineStatus.setCurrentAlert(22D);
        assertThat(machineStatus.alertState(), is(false));
    }


    @Test
    public void alertState_currentGreaterThenAlert_true() {
        MachineStatus machineStatus = new MachineStatus();
        machineStatus.setCurrent(23D);
        machineStatus.setCurrentAlert(22D);
        assertThat(machineStatus.alertState(), is(true));
    }
}