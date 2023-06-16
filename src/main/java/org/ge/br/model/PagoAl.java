package org.ge.br.model;
import java.sql.Date;

public class PagoAl {
    private int idPago;
    private int idAlumno;
    private String tipoPago;
    private Date fechaPago;
    private boolean facturaEntregada;
    private boolean pagadoEfectivo;
    private int cantidadPagada;
    private String comentarios;

    public PagoAl(int idPago, int idAlumno, String tipoPago, Date fechaPago, boolean facturaEntregada,
                  boolean pagadoEfectivo, int cantidadPagada, String comentarios) {
        this.idPago = idPago;
        this.idAlumno = idAlumno;
        this.tipoPago = tipoPago;
        this.fechaPago = fechaPago;
        this.facturaEntregada = facturaEntregada;
        this.pagadoEfectivo = pagadoEfectivo;
        this.cantidadPagada = cantidadPagada;
        this.comentarios = comentarios;
    }

    public PagoAl(int idPago, int id, String tipoPago, java.util.Date date, boolean facturaEntregada, boolean pagadoEfectivo, int cantidadPagada, String comentarios) {
    }

    public PagoAl(int idAlumno, String tipoPago, Date fechaPago, boolean facturaEntregada, boolean pagadoEfectivo, int cantidadPagada, String comentarios) {
    }

    public PagoAl() {

    }

    public int getIdPago() {
        return idPago;
    }

    public int getIdAlumno() {
        return idAlumno;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public boolean isFacturaEntregada() {
        return facturaEntregada;
    }

    public boolean isPagadoEfectivo() {
        return pagadoEfectivo;
    }

    public int getCantidadPagada() {
        return cantidadPagada;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public void setFacturaEntregada(boolean facturaEntregada) {
        this.facturaEntregada = facturaEntregada;
    }

    public void setPagadoEfectivo(boolean pagadoEfectivo) {
        this.pagadoEfectivo = pagadoEfectivo;
    }

    public void setCantidadPagada(int cantidadPagada) {
        this.cantidadPagada = cantidadPagada;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
}
