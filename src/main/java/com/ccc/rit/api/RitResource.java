package com.ccc.rit.api;

import com.ccc.rit.config.MessageProvider;
import com.ccc.rit.persistence.InfoCaco;
import com.ccc.rit.persistence.RitService;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/rit")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RitResource {

    @Inject
    private RitService ritService;

    @POST
    @Path("/registros")
    public Response registrarInformacion(JsonObject payload, @Context HttpHeaders headers) {
        String token = headers.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring("Bearer ".length());
        }

        InfoCaco cabecera = ritService.registrarSolicitud(payload, token);
        return Response.status(Response.Status.CREATED)
                .entity(new RitResponse(MessageProvider.getMessage("message.rit.persisted"), cabecera.getId()))
                .build();
    }

    public static class RitResponse {
        private final String message;
        private final Long registroId;

        public RitResponse(String message, Long registroId) {
            this.message = message;
            this.registroId = registroId;
        }

        public String getMessage() {
            return message;
        }

        public Long getRegistroId() {
            return registroId;
        }
    }
}
