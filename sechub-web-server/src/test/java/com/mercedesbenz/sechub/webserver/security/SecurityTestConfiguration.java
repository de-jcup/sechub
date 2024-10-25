// SPDX-License-Identifier: MIT
package com.mercedesbenz.sechub.webserver.security;

import org.opentest4j.TestAbortedException;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import com.mercedesbenz.sechub.webserver.encryption.AES256Encryption;

import jakarta.servlet.http.Cookie;

@TestConfiguration
@Import({ SecurityConfiguration.class, OAuth2PropertiesConfig.class, AES256Encryption.class })
public class SecurityTestConfiguration {

    public static final String JWT = "jwt";
    public static final String ENCRYPTED_JWT_B64_ENCODED = "37eb9nQkgX13l41KCOR7nA==";
    public static final String ACCESS_TOKEN = "access_token";

    @Bean
    public RequestPostProcessor requestPostProcessor() {
        Cookie cookie = new Cookie(ACCESS_TOKEN, ENCRYPTED_JWT_B64_ENCODED);
        return new TestCookieRequestPostProcessor(cookie);
    }

    @Bean
    AuthenticationManager authenticationManager() {
        return requestedAuth -> {
            Authentication configuredAuth = SecurityContextHolder.getContext().getAuthentication();

            if (configuredAuth == null) {
                /*
                 * Test in execution has no authentication configured in the background. This is
                 * a valid test case.
                 */
                throw new AuthenticationException("No user authentication is provided in the security context") {
                };
            }

            String requestedJwt = ((BearerTokenAuthenticationToken) requestedAuth).getToken();
            String configuredJwt = ((BearerTokenAuthenticationToken) configuredAuth).getToken();

            if (configuredJwt == null || configuredJwt.isEmpty()) {
                /*
                 * Test in execution has no actual JWT value in the configured authentication
                 * context. This is not a valid test case. If a test is configured to use a JWT
                 * token, it must be provided.
                 */
                throw new TestAbortedException("No JWT token is configured");
            }

            if (!configuredJwt.equals(requestedJwt)) {
                /*
                 * This means that the JWT provided in the request cookies does not match the
                 * JWT configured in the authentication context. This is a valid test case.
                 */
                throw new JwtException("Invalid token");
            }

            return configuredAuth;
        };
    }

}
