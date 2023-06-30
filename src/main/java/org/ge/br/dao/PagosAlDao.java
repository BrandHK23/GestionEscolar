package org.ge.br.dao;

import org.ge.br.model.PagoAl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PagosAlDao {
    public Connection conectar() {
        String baseDeDatos = "EscuelaOdontologia_V2";
        String usuario = "root";
        String password = "1234";
        String host = "Localhost";
        String puerto = "3306";
        String drive = "com.mysql.cj.jdbc.Driver";
        String conexionUrl = "jdbc:mysql://" + host + ":" + puerto + "/" + baseDeDatos + "?useSSL=false";

        Connection conexion = null;
        try {
            Class.forName(drive);
            conexion = DriverManager.getConnection(conexionUrl, usuario, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return conexion;
    }

    public void guardar(PagoAl pago) {
        try {
            Connection conexion = conectar();

            String sql = "INSERT INTO PagosAl (id_alumno, tipo_pago, fecha_pago, factura_entregada, pagado_efectivo, cantidad_pagada, comentarios) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setInt(1, pago.getIdAlumno());
            statement.setString(2, pago.getTipoPago());
            statement.setDate(3, new java.sql.Date(pago.getFechaPago().getTime()));
            statement.setBoolean(4, pago.isFacturaEntregada());
            statement.setBoolean(5, pago.isPagadoEfectivo());
            statement.setInt(6, pago.getCantidadPagada());
            statement.setString(7, pago.getComentarios());

            statement.executeUpdate();

            statement.close();
            conexion.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<PagoAl> obtenerPagosPorAlumno(int idAlumno) {
        List<PagoAl> pagos = new ArrayList<>();
        try {
            Connection conexion = conectar();

            String sql = "SELECT * FROM PagosAl WHERE id_alumno = ?";
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setInt(1, idAlumno);

            ResultSet resultado = statement.executeQuery();

            while (resultado.next()) {
                PagoAl pago = new PagoAl();
                pago.setIdPago(resultado.getInt("id_pago"));
                pago.setIdAlumno(resultado.getInt("id_alumno"));
                pago.setTipoPago(resultado.getString("tipo_pago"));
                pago.setFechaPago(resultado.getDate("fecha_pago"));
                pago.setFacturaEntregada(resultado.getBoolean("factura_entregada"));
                pago.setPagadoEfectivo(resultado.getBoolean("pagado_efectivo"));
                pago.setCantidadPagada(resultado.getInt("cantidad_pagada"));
                pago.setComentarios(resultado.getString("comentarios"));

                pagos.add(pago);
            }

            statement.close();
            conexion.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return pagos;
    }
}
