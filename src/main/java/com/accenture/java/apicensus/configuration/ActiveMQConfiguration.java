package com.accenture.java.apicensus.configuration;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActiveMQConfiguration {

    @Value("${activemq.host}")
    private String host;

    @Value("${activemq.port}")
    private String port;

    @Value("${activemq.username}")
    private String username;

    @Value("${activemq.password}")
    private String password;

    @Bean("activemq")
    public ActiveMQComponent activeMQComponent() {
        ActiveMQComponent activeMQComponent = new ActiveMQComponent();
        activeMQComponent.setBrokerURL("tcp://" + host + ":" + port);
        activeMQComponent.setUsername(username);
        activeMQComponent.setPassword(password);
        activeMQComponent.setTrustAllPackages(true);
        return activeMQComponent;
    }
}
