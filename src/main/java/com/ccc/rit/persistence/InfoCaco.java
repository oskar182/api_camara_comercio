package com.ccc.rit.persistence;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TABLA_INFOCACO")
@NamedQuery(name = "InfoCaco.findByToken", query = "SELECT i FROM InfoCaco i WHERE i.token = :token")
public class InfoCaco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TOKEN", length = 512)
    private String token;

    @Column(name = "RAW_DATA", columnDefinition = "CLOB")
    private String rawData;

    @Column(name = "ESTADO", length = 50)
    private String estado;

    @Column(name = "CODIGO_ESTADO", length = 10)
    private String codigoEstado;

    @Column(name = "FECHA_CREACION")
    private LocalDateTime fechaCreacion;

    @OneToMany(mappedBy = "cabecera", cascade = CascadeType.ALL)
    private List<InfoCcpr> campos = new ArrayList<>();

    public InfoCaco() {
        this.fechaCreacion = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRawData() {
        return rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCodigoEstado() {
        return codigoEstado;
    }

    public void setCodigoEstado(String codigoEstado) {
        this.codigoEstado = codigoEstado;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public List<InfoCcpr> getCampos() {
        return campos;
    }

    public void addCampo(InfoCcpr campo) {
        campo.setCabecera(this);
        this.campos.add(campo);
    }
}
