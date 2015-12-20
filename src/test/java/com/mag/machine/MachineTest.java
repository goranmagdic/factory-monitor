package com.mag.machine;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class MachineTest {

    @Test
    public void constructor_url_limitedInitialization() {
        //when
        Machine machine = new Machine("randomUrl");
        //then
        assertThat(machine.getUrl(), is("randomUrl"));
        assertThat(machine.getAverageConsumptionBeforeLastStatus(), is(nullValue()));
        assertThat(machine.enteredAlert(), is(false));
    }

    @Test
    public void machine_entersEnteredAlertStateAfterOk() {
        //given
        Machine machine = new Machine("randomUrl");
        MachineStatus alertStatus = newAlertStatus();
        //when
        Machine newMachine = new Machine(machine, alertStatus);
        //then
        assertThat(newMachine.enteredAlert(), is(true));
    }

    private MachineStatus newAlertStatus() {
        MachineStatus status = new MachineStatus();
        status.setCurrent(20D);
        status.setCurrentAlert(10D);
        status.setTimestamp(LocalDateTime.now());
        return status;
    }

    @Test
    public void machine_exitsAlertStateAfterSecondAlertStatus() {
        //given
        Machine machine = new Machine("randomUrl");
        Machine enteredAlertMachine = new Machine(machine, newAlertStatus());
        //when
        Machine newMachine = new Machine(enteredAlertMachine, newAlertStatus());
        //then
        assertThat(newMachine.enteredAlert(), is(false));
    }

    @Test
    public void getAverageConsumptionBeforeLastStatus_takesLastFiveMinutesIntoAccount() {

        //given
        Machine machine = new Machine("randomUrl");
        Machine machine1 =
                new Machine(machine, newStatus(100D, LocalDateTime.now().minus(10, ChronoUnit.MINUTES)));

        Machine machine2 =
                new Machine(machine1, newStatus(3D, LocalDateTime.now().minus(2, ChronoUnit.MINUTES)));
        Machine machine3 =
                new Machine(machine2, newStatus(2D, LocalDateTime.now().minus(2, ChronoUnit.MINUTES)));

        //when
        Machine newMachine =
                new Machine(machine3, newStatus(0D, LocalDateTime.now()));
        //then
        assertThat(newMachine.getAverageConsumptionBeforeLastStatus(), is(2.5D));

    }

    private MachineStatus newStatus(Double current, LocalDateTime time) {
        MachineStatus status = new MachineStatus();
        status.setCurrent(current);
        status.setCurrentAlert(5000D);
        status.setTimestamp(time);
        return status;
    }

    @Test
    public void getAverageConsumptionBeforeLastStatus_doesNotIncludeLastStatus() {

        //given
        Machine machine = new Machine("randomUrl");
        Machine machine1 =
                new Machine(machine, newStatus(3D, LocalDateTime.now()));

        //when
        Machine newMachine =
                new Machine(machine1, newStatus(4D, LocalDateTime.now()));
        //then
        assertThat(newMachine.getAverageConsumptionBeforeLastStatus(), is(3D));

    }


}