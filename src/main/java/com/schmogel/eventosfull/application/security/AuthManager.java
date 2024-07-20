package com.schmogel.eventosfull.application.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthManager {

    private final ClientProperties clientProperties;

    public boolean validarRequisicao(String authorization, String client) {

        if (Objects.isNull(authorization) || Objects.isNull(client)) return false;

        return clientProperties.getCredentials().stream()
                .filter(clientCredentials -> client.equals(clientCredentials.client()))
                .map(this::generateHash)
                .anyMatch(authorization::equals);
    }

    private String generateHash(ClientCredentials clientCredentials) {

        String combinedString = clientCredentials.client() + ":" + clientCredentials.secret();
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(combinedString.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
