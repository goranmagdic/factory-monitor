package com.mag;

import com.mag.machine.MachineParkClient;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

/**
 * Factory monitor configuration.
 */
@EnableScheduling
@SpringBootApplication
public class FactoryMonitorApp {

    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder()
                .sources(FactoryMonitorApp.class)
                .run(args);
    }

    @Bean
    MachineParkClient machineParkClient(){
        MachineParkClient machineParkClient =  new MachineParkClient(new RestTemplate());
        machineParkClient.initApi();
        return machineParkClient;
    }

}
