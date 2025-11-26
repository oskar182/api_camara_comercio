package com.ccc.rit.api;

import com.ccc.rit.api.dto.AuthRequest;
import com.ccc.rit.api.dto.AuthResponse;
import com.ccc.rit.config.MessageProvider;
import com.ccc.rit.security.JwtService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    private JwtService jwtService;

    @POST
    @Path("/token")
    public Response generarToken(AuthRequest request) {
        String usuarioConfig = MessageProvider.getSecurityProperty("security.username");
        String claveConfig = MessageProvider.getSecurityProperty("security.password");

        if (request == null || !usuarioConfig.equals(request.getUsuario()) || !claveConfig.equals(request.getContrasena())) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new AuthResponse(false, MessageProvider.getMessage("message.auth.invalid"), null, null))
                    .build();
        }

        String token = jwtService.generateToken(request.getUsuario());
        AuthResponse response = new AuthResponse(true, MessageProvider.getMessage("message.auth.success"), token, "1800");
        return Response.ok(response).build();
    }
}
