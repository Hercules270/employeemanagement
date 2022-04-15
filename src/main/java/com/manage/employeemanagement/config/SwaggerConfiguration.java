package com.manage.employeemanagement.config;


import com.google.common.base.Predicates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.AuthorizationScopeBuilder;
import springfox.documentation.builders.LoginEndpointBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@EnableSwagger2
@Configuration
public class SwaggerConfiguration {

    private final String SERVER_URL;
    private final String REALM;
    private final String CLIENT_ID;
    private final String CLIENT_SECRET;
    private final String LOGIN_URI;

    public SwaggerConfiguration(@Value("${keycloak.server.url}") String serverUrl,
                                @Value("${keycloak.realm}") String realm,
                                @Value("${keycloak.client.employee-server.id}") String clientId,
                                @Value("${keycloak.client.employee-server.secret}") String clientSecret,
                                @Value("${keycloak.login.uri}")String loginUri) {
        this.SERVER_URL = serverUrl;
        this.REALM = realm;
        this.CLIENT_ID = clientId;
        this.CLIENT_SECRET = clientSecret;
        LOGIN_URI = loginUri;
    }



    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.manage.employeemanagement.controller"))
                .paths(PathSelectors.ant("/api/**"))
                .build()
                .apiInfo(apiInfo())
                .securitySchemes((List<SecurityScheme>) buildSecurityScheme())
                .securityContexts(buildSecurityContext());
    }

    public ApiInfo apiInfo() {
        return new ApiInfo(
                "Employee Management API",
                "Sample API",
                "1.0",
                "",
                "",
                "",
                ""
        );
    }


    @Bean
    public SecurityConfiguration securityConfiguration() {
        return SecurityConfigurationBuilder.builder()
                .clientId(CLIENT_ID).realm(REALM).appName("swagger-ui")
                .clientSecret(CLIENT_SECRET)
                .build();
    }

    private List<SecurityContext> buildSecurityContext() {
        List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(SecurityReference.builder().reference("oauth2").scopes(scopes().toArray(new AuthorizationScope[]{})).build());
        SecurityContext context = SecurityContext.builder().forPaths(Predicates.alwaysTrue()).securityReferences(securityReferences).build();
        List<SecurityContext> ret = new ArrayList<>();
        ret.add(context);
        return ret;
    }

    private List<? extends SecurityScheme> buildSecurityScheme() {
        List<SecurityScheme> lst = new ArrayList<>();

        LoginEndpoint login = new LoginEndpointBuilder().url(LOGIN_URI).build();

        List<GrantType> gTypes = new ArrayList<>();
        gTypes.add(new ImplicitGrant(login, "acces_token"));

        lst.add(new OAuth("oauth2", scopes(), gTypes));
        return lst;
    }

    private List<AuthorizationScope> scopes() {
        List<AuthorizationScope> scopes = new ArrayList<>();
        for (String scopeItem : new String[]{"openid=openid", "profile=profile"}) {
            String scope[] = scopeItem.split("=");
            if (scope.length == 2) {
                scopes.add(new AuthorizationScopeBuilder().scope(scope[0]).description(scope[1]).build());
            } else {
                log.warn("Scope '{}' is not valid (format is scope=description)", scopeItem);
            }
        }

        return scopes;
    }


}