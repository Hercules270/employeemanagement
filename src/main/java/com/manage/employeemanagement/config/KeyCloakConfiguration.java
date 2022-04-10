package com.manage.employeemanagement.config;


import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeyCloakConfiguration {

    private final String SERVER_URL;
    private final String REALM;
    private final String CLIENT_ID;
    private final String CLIENT_SECRET;

    public KeyCloakConfiguration(@Value("${keycloak.server.url}") String serverUrl,
                                 @Value("${keycloak.realm}")String realm,
                                 @Value("${keycloak.client.id}") String clientId,
                                 @Value("${keycloak.client.secret}") String clientSecret) {
        this.SERVER_URL = serverUrl;
        this.REALM = realm;
        this.CLIENT_ID = clientId;
        this.CLIENT_SECRET = clientSecret;
    }

    @Bean
    public Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(SERVER_URL)
                .realm(REALM)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .build();
    }

}
