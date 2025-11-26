package com.ccc.rit.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TABLA_INFOCCPR")
public class InfoCcpr {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "INFOCACO_ID")
    private InfoCaco cabecera;

    @Column(name = "CAMPO", length = 120)
    private String campo;

    @Column(name = "VALOR", length = 1024)
    private String valor;

    public Long getId() {
        return id;
    }

    public InfoCaco getCabecera() {
        return cabecera;
    }

    public void setCabecera(InfoCaco cabecera) {
        this.cabecera = cabecera;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
