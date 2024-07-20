package com.schmogel.eventosfull.application.security;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "client")
public class ClientProperties {
    private List<ClientCredentials> credentials;

    public List<ClientCredentials> getCredentials() {
        return credentials;
    }

    public void setCredentials(List<ClientCredentials> credentials) {
        this.credentials = credentials;
    }
}
