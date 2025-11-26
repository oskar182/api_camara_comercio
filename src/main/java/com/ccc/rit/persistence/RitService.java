package com.ccc.rit.persistence;

import com.ccc.rit.config.MessageProvider;
import jakarta.enterprise.context.RequestScoped;
import jakarta.json.JsonObject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.Map;

@RequestScoped
public class RitService {

    @PersistenceContext(unitName = "ritPU")
    private EntityManager entityManager;

    @Transactional
    public InfoCaco registrarSolicitud(JsonObject payload, String token) {
        InfoCaco cabecera = new InfoCaco();
        cabecera.setToken(token);
        cabecera.setRawData(payload.toString());
        cabecera.setEstado(MessageProvider.getMessage("message.rit.received"));
        cabecera.setCodigoEstado("200");

        for (Map.Entry<String, Object> entry : payload.entrySet()) {
            InfoCcpr detalle = new InfoCcpr();
            detalle.setCampo(entry.getKey());
            detalle.setValor(String.valueOf(entry.getValue()));
            cabecera.addCampo(detalle);
        }

        entityManager.persist(cabecera);
        return cabecera;
    }
}
