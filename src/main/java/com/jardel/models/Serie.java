package com.jardel.models;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.ForeignKey;

@Entity
public class Serie implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    private String titulo;
    private String sinopse;
    private String filename;
    private int quantidade_ep;
    private int quantidade_temp;
    private double tempo_medio;
    private String comentario;
    
    @Column(columnDefinition="boolean default true")
    private boolean finalizada;
    private double avaliacao;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date data_modificacao;

    @ForeignKey(name="usuario_id")
    @ManyToOne
    private Usuario usuario;


    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String file) {
        this.filename = file;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSinopse() {
        return this.sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public int getQuantidade_ep() {
        return this.quantidade_ep;
    }

    public void setQuantidade_ep(int quantidade_ep) {
        this.quantidade_ep = quantidade_ep;
    }

    public int getQuantidade_temp() {
        return this.quantidade_temp;
    }

    public void setQuantidade_temp(int quantidade_temp) {
        this.quantidade_temp = quantidade_temp;
    }

    public double getTempo_medio() {
        return this.tempo_medio;
    }

    public void setTempo_medio(double tempo_medio) {
        this.tempo_medio = tempo_medio;
    }

    public String getComentario() {
        return this.comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public boolean isFinalizada() {
        return this.finalizada;
    }

    public boolean getFinalizada() {
        return this.finalizada;
    }

    public void setFinalizada(boolean finalizada) {
        this.finalizada = finalizada;
    }

    public double getAvaliacao() {
        return this.avaliacao;
    }

    public void setAvaliacao(double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Date getData_modificacao() {
        return this.data_modificacao;
    }

    public void setData_modificacao(Date data_modificacao) {
        this.data_modificacao = data_modificacao;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Serie)) {
            return false;
        }
        Serie serie = (Serie) o;
        return id == serie.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}