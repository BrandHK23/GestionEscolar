package org.ge.br.export;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.sql.*;

public class DatabaseToExcelExporter {
    String excelFilePath = "/home/brandon/Excel_Path/Pagos_AlumnosV21.xlsx";
    String excelFIlePath2 = "/home/brandon/Excel_Path/Datos_AlumnosV21.xlsx";

    public Connection conectar() {
        String baseDeDatos = "EscuelaOdontologia_V21";
        String usuario = "root";
        String password = "2301";
        String host = "localhost";
        String puerto = "3306";
        String driver = "com.mysql.cj.jdbc.Driver";
        String conexionUrl = "jdbc:mysql://" + host + ":" + puerto + "/" + baseDeDatos + "?useSSL=false";

        Connection conexion = null;
        try {
            Class.forName(driver);
            conexion = DriverManager.getConnection(conexionUrl, usuario, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return conexion;
    }

    public void exportExcelPagos() {
        try {
            Connection conexion = conectar();
            String sql = "SELECT a.id_alumno, a.nombre_completo," +
                    "       p.id_pago, p.tipo_pago, p.fecha_pago, p.factura_entregada, p.pagado_efectivo, p.cantidad_pagada, p.comentarios" +
                    " FROM Alumnos a" +
                    " JOIN PagosAl p ON a.id_alumno = p.id_alumno";
            Statement statement = conexion.createStatement();
            ResultSet result = statement.executeQuery(sql);

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Datos");
            int rowNum = 0;

            ResultSetMetaData metaData = result.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Agregar fila de encabezado con los nombres de las columnas
            Row headerRow = sheet.createRow(rowNum++);
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                headerRow.createCell(i - 1).setCellValue(columnName);
            }

            // Agregar los datos de cada fila
            while (result.next()) {
                Row row = sheet.createRow(rowNum++);
                for (int i = 1; i <= columnCount; i++) {
                    Object value = result.getObject(i);
                    row.createCell(i - 1).setCellValue(value != null ? value.toString() : "");
                }
            }

            // Ajustar el tamaño de las celdas al contenido
            for (int i = 0; i < columnCount; i++) {
                sheet.autoSizeColumn(i);
            }

            try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
                workbook.write(outputStream);
            }
            System.out.println("Datos exportados correctamente a Excel.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exportExcelAlumnos() {
        try {
            Connection conexion = conectar();
            String sql = "SELECT * FROM Alumnos";
            Statement statement = conexion.createStatement();
            ResultSet result = statement.executeQuery(sql);

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Datos");
            int rowNum = 0;

            ResultSetMetaData metaData = result.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Agregar fila de encabezado con los nombres de las columnas
            Row headerRow = sheet.createRow(rowNum++);
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                headerRow.createCell(i - 1).setCellValue(columnName);
            }

            // Agregar los datos de cada fila
            while (result.next()) {
                Row row = sheet.createRow(rowNum++);
                for (int i = 1; i <= columnCount; i++) {
                    Object value = result.getObject(i);
                    row.createCell(i - 1).setCellValue(value != null ? value.toString() : "");
                }
            }

            // Ajustar el tamaño de las celdas al contenido
            for (int i = 0; i < columnCount; i++) {
                sheet.autoSizeColumn(i);
            }

            try (FileOutputStream outputStream = new FileOutputStream(excelFIlePath2)) {
                workbook.write(outputStream);
            }
            System.out.println("Datos exportados correctamente a Excel.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

