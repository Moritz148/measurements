package com.envite.gmt.measurements;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.spring.client.annotation.Deployment;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@Deployment(resources = "classpath:benchmark.bpmn")
public class MeasurementsApplication implements CommandLineRunner {

    private final ZeebeClient zeebeClient = ZeebeClient.newClientBuilder()
            .gatewayAddress("zeebe:26500")
            .usePlaintext()
            .build();


    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MeasurementsApplication.class, args);

        context.close();
    }

    @Override
    public void run(String... args) throws Exception {
        if(zeebeClient==null){
            System.out.println("Zeebe Client not set");
            return;
        }

        var bpmnProcessId = "benchmark2";

        zeebeClient.newCreateInstanceCommand()
                .bpmnProcessId(bpmnProcessId)
                .latestVersion()
                .withResult()
                .send()
                .join();
    }

}
