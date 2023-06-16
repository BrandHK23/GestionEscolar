package org.ge.br.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Alumno {
    private int id;
    private String nombreCompleto;
    private String email;
    private String telefono;
    private String especialidad;
    private Date fechaRegistro;
    private Date fechaCompletado;
    private boolean status;
    private List<String> rutasDocumentos = null;

    public Alumno() {
        rutasDocumentos = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getFechaCompletado() {
        return fechaCompletado;
    }

    public void setFechaCompletado(Date fechaCompletado) {
        this.fechaCompletado = fechaCompletado;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setRutasDocumentos(List<String> nuevasRutasDocumentos) {
        if (rutasDocumentos == null) {
            rutasDocumentos = new ArrayList<>();
        }
        rutasDocumentos.addAll(nuevasRutasDocumentos);
    }


    public void agregarRutaDocumento(String rutaDocumento) {
        rutasDocumentos.add(rutaDocumento);
    }

    public List<String> getRutasDocumentos() {
        return rutasDocumentos;
    }
}
