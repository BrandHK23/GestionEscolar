package org.ge.br.dao;

import org.ge.br.model.Alumno;
import org.ge.br.model.DocAlumno;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DocAlumnoDao {
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
            List<String> rutasDocumentos = alumno.getRutasDocumentos();

            String sqlInsert = "INSERT INTO DocAlumno (id_alumno, nombre_documento, ruta_documento, existe_documento, num_documento) " +
                    "VALUES (?, ?, ?, ?, ?)";

            String sqlUpdate = "UPDATE DocAlumno SET ruta_documento = ?, existe_documento = ? " +
                    "WHERE id_alumno = ? AND num_documento = ?";

            PreparedStatement statementInsert = conexion.prepareStatement(sqlInsert);
            PreparedStatement statementUpdate = conexion.prepareStatement(sqlUpdate);

            int idAlumno = alumno.getId();
            for (int i = 0; i < rutasDocumentos.size(); i++) {
                String nombreDoc = "Documento" + (i + 1);
                String rutaDoc = rutasDocumentos.get(i);
                boolean existeDoc = new File(rutaDoc).exists();
                int numDoc = i + 1;

                // Verificar si ya existe un registro para el alumno y el número de documento
                String sqlSelect = "SELECT COUNT(*) FROM DocAlumno WHERE id_alumno = ? AND num_documento = ?";
                PreparedStatement statementSelect = conexion.prepareStatement(sqlSelect);
                statementSelect.setInt(1, idAlumno);
                statementSelect.setInt(2, numDoc);
                ResultSet resultSet = statementSelect.executeQuery();
                resultSet.next();
                int count = resultSet.getInt(1);

                if (count > 0) {
                    // Actualizar la ruta existente si no está vacía
                    if (!rutaDoc.isEmpty()) {
                        statementUpdate.setString(1, rutaDoc);
                        statementUpdate.setBoolean(2, existeDoc);
                        statementUpdate.setInt(3, idAlumno);
                        statementUpdate.setInt(4, numDoc);
                        statementUpdate.executeUpdate();
                    }
                } else {
                    // Insertar un nuevo registro si la ruta no está vacía
                    if (!rutaDoc.isEmpty()) {
                        statementInsert.setInt(1, idAlumno);
                        statementInsert.setString(2, nombreDoc);
                        statementInsert.setString(3, rutaDoc);
                        statementInsert.setBoolean(4, existeDoc);
                        statementInsert.setInt(5, numDoc);
                        statementInsert.executeUpdate();
                    }
                }
            }

            statementInsert.close();
            statementUpdate.close();
            conexion.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public List<DocAlumno> obtenerDocumentosPorAlumno(int idAlumno) {
        List<DocAlumno> documentos = new ArrayList<>();

        try {
            Connection conexion = conectar();

            String sql = "SELECT * FROM DocAlumno WHERE id_alumno = ?";
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setInt(1, idAlumno);

            ResultSet resultado = statement.executeQuery();

            while (resultado.next()) {
                DocAlumno documento = new DocAlumno();
                documento.setId(resultado.getInt("id_doc"));
                documento.setId_alumno(resultado.getInt("id_alumno"));
                documento.setNombre_doc(resultado.getString("nombre_documento"));
                documento.setRuta_doc(resultado.getString("ruta_documento"));
                documento.setExiste_doc(resultado.getBoolean("existe_documento"));
                documento.setNum_doc(resultado.getInt("num_documento"));

                documentos.add(documento);
            }

            statement.close();
            conexion.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return documentos;
    }

}
