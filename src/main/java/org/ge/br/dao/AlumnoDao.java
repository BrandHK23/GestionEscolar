package org.ge.br.dao;

import org.ge.br.model.Alumno;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlumnoDao {
    public Connection conectar() {
        String baseDeDatos = "EscuelaOdontologia_V21";
        String usuario = "root";
        String password = "2301";
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

    public void guardar(Alumno alumno) {
        try {
            Connection conexion = conectar();

            String sql = "INSERT INTO Alumnos (nombre_completo, email, telefono, especialidad, fecha_registro, fecha_completado, Status) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, alumno.getNombreCompleto());
            statement.setString(2, alumno.getEmail());
            statement.setString(3, alumno.getTelefono());
            statement.setString(4, alumno.getEspecialidad());
            statement.setDate(5, new java.sql.Date(alumno.getFechaRegistro().getTime()));
            if (alumno.getFechaCompletado() != null) {
                statement.setDate(6, new java.sql.Date(alumno.getFechaCompletado().getTime()));
            } else {
                statement.setNull(6, Types.DATE);
            }
            statement.setBoolean(7, alumno.isStatus());

            statement.executeUpdate();

            statement.close();
            conexion.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Alumno obtenerAlumnoPorId(int id) {
        Alumno alumno = null;
        try {
            Connection conexion = conectar();

            String sql = "SELECT * FROM Alumnos WHERE id_alumno = ?";
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet resultado = statement.executeQuery();

            if (resultado.next()) {
                alumno = new Alumno();
                alumno.setId(resultado.getInt("id_alumno"));
                alumno.setNombreCompleto(resultado.getString("nombre_completo"));
                alumno.setEmail(resultado.getString("email"));
                alumno.setTelefono(resultado.getString("telefono"));
                alumno.setEspecialidad(resultado.getString("especialidad"));
                alumno.setFechaRegistro(resultado.getDate("fecha_registro"));
                alumno.setFechaCompletado(resultado.getDate("fecha_completado"));
                alumno.setStatus(resultado.getBoolean("Status"));
            }

            statement.close();
            conexion.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return alumno;
    }

    private void guardarRutasDocumentos(int idAlumno, List<String> rutasDocumentos) {
        try {
            Connection conexion = conectar();

            String sql = "INSERT INTO DocAlumno (id_alumno, nombre_documento, ruta_documento, existe_documento) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conexion.prepareStatement(sql);

            for (String ruta : rutasDocumentos) {
                statement.setInt(1, idAlumno);
                statement.setString(2, "");
                statement.setString(3, ruta);
                statement.setBoolean(4, new File(ruta).exists());
                statement.addBatch();
            }

            statement.executeBatch();

            statement.close();
            conexion.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Alumno> buscarTodosLosAlumnos() {
        List<Alumno> listado = new ArrayList<>();
        try {
            Connection conexion = conectar();

            String sql = "SELECT * FROM Alumnos";
            PreparedStatement statement = conexion.prepareStatement(sql);

            ResultSet resultado = statement.executeQuery();

            while (resultado.next()) {
                Alumno alumno = obtenerAlumnoFromResultSet(resultado);
                listado.add(alumno);
            }

            statement.close();
            conexion.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return listado;
    }

    public List<Alumno> buscarAlumnosPorEspecialidad(String especialidad) {
        List<Alumno> listado = new ArrayList<>();
        try {
            Connection conexion = conectar();

            String sql = "SELECT * FROM Alumnos WHERE especialidad = ?";
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setString(1, especialidad);

            ResultSet resultado = statement.executeQuery();

            while (resultado.next()) {
                Alumno alumno = obtenerAlumnoFromResultSet(resultado);
                listado.add(alumno);
            }

            statement.close();
            conexion.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return listado;
    }

    public List<Alumno> buscarAlumnosInteresadosPorEspecialidad(String especialidad) {
        List<Alumno> listado = new ArrayList<>();
        try {
            Connection conexion = conectar();

            String sql = "SELECT * FROM Alumnos WHERE Status = ? AND (especialidad = ?)";
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setBoolean(1, false);
            statement.setString(2, especialidad);

            ResultSet resultado = statement.executeQuery();

            while (resultado.next()) {
                Alumno alumno = obtenerAlumnoFromResultSet(resultado);
                listado.add(alumno);
            }

            statement.close();
            conexion.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return listado;
    }

    public List<Alumno> buscarAlumnosTodosTodos(String Status) {
        List<Alumno> listado = new ArrayList<>();
        try {
            Connection conexion = conectar();

            String sql = "SELECT * FROM Alumnos WHERE Status = ?";
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setBoolean(1, Boolean.parseBoolean(Status));

            ResultSet resultado = statement.executeQuery();

            while (resultado.next()) {
                Alumno alumno = obtenerAlumnoFromResultSet(resultado);
                listado.add(alumno);
            }

            statement.close();
            conexion.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return listado;
    }

    public List<Alumno> buscarAlumnosNuevosIngresosPorEspecialidad(String especialidad) {
        List<Alumno> listado = new ArrayList<>();
        try {
            Connection conexion = conectar();

            String sql = "SELECT * FROM Alumnos WHERE Status = ? AND especialidad = ?";
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setBoolean(1, true);
            statement.setString(2, especialidad);

            ResultSet resultado = statement.executeQuery();

            while (resultado.next()) {
                Alumno alumno = obtenerAlumnoFromResultSet(resultado);
                listado.add(alumno);
            }

            statement.close();
            conexion.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return listado;
    }

    private Alumno obtenerAlumnoFromResultSet(ResultSet resultado) throws SQLException {
        Alumno alumno = new Alumno();
        alumno.setId(resultado.getInt("id_alumno"));
        alumno.setNombreCompleto(resultado.getString("nombre_completo"));
        alumno.setEmail(resultado.getString("email"));
        alumno.setTelefono(resultado.getString("telefono"));
        alumno.setEspecialidad(resultado.getString("especialidad"));
        alumno.setFechaRegistro(resultado.getDate("fecha_registro"));
        alumno.setFechaCompletado(resultado.getDate("fecha_completado"));
        alumno.setStatus(resultado.getBoolean("Status"));
        return alumno;
    }



    public void actualizarEstadoYFechaCompletado(int idAlumno, boolean status, Date fechaCompletado) {
        try {
            Connection conexion = conectar();

            String sql = "UPDATE Alumnos SET Status = ?, fecha_completado = ? WHERE id_alumno = ?";
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setBoolean(1, status);
            statement.setDate(2, new java.sql.Date(fechaCompletado.getTime()));
            statement.setInt(3, idAlumno);

            statement.executeUpdate();

            statement.close();
            conexion.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void actualizarEspecialidad(int idAlumno, String nuevaEspecialidad) {
        try{
            Connection conexion = conectar();

            String sql = "UPDATE Alumnos SET especialidad = ? WHERE id_alumno = ?";
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setString(1,nuevaEspecialidad);
            statement.setInt(2,idAlumno);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
