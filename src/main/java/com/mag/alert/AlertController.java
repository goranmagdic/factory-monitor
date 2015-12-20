package com.mag.alert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Controller for accessing Alerts.
 */
@Controller
public class AlertController {

    @Autowired
    private AlertService alertService;

    /**
     * Retrieves list of all active alerts.
     * @return alerts.
     */
    @RequestMapping("/alerts")
    @ResponseBody
    public List<Alert> getAlerts() {
        return alertService.alerts();
    }

}
