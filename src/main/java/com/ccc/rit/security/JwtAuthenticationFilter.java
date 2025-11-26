package com.ccc.rit.security;

import com.ccc.rit.config.MessageProvider;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Optional;

@Provider
@PreMatching
@Priority(Priorities.AUTHENTICATION)
@Dependent
public class JwtAuthenticationFilter implements ContainerRequestFilter {

    @Inject
    private JwtService jwtService;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String path = requestContext.getUriInfo().getPath();
        if (path.startsWith("auth")) {
            return;
        }

        String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            abort(requestContext, Response.Status.UNAUTHORIZED, MessageProvider.getMessage("message.token.missing"));
            return;
        }

        String token = authHeader.substring("Bearer ".length());
        Optional<Claims> claims = jwtService.validateToken(token);
        if (claims.isEmpty()) {
            abort(requestContext, Response.Status.UNAUTHORIZED, MessageProvider.getMessage("message.token.invalid"));
        }
    }

    private void abort(ContainerRequestContext requestContext, Response.Status status, String message) {
        requestContext.abortWith(Response.status(status)
                .entity(new SimpleMessage(message))
                .build());
    }

    public static class SimpleMessage {
        private final String message;

        public SimpleMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
