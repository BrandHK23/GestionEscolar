package org.ge.br.export;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.*;

public class DatabaseToExcelExporter {
    String fileNamePagos = "Pagos.xlsx";
    String fileNameAlumnos = "Alumnos.xlsx";
    String excelFilePagos = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "FilePath";
    String excelFileAlumnos = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "FilePath";

    public Connection conectar() {
        String baseDeDatos = "EscuelaOdontologia_V2";
        String usuario = "root";
        String password = "1234";
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

            // Verificar si la carpeta existe, de lo contrario crearla
            File carpeta = new File(excelFilePagos);
            if(!carpeta.exists()){
                carpeta.mkdirs();
            }

            // Verificar si el archivo existe, de ser así, generar una versión nueva numerada
            int version = 1;
            String newFileName = fileNamePagos;
            File archivo = new File(excelFilePagos + File.separator + newFileName);
            while (archivo.exists()){
                // Generar nuevo nombre de archivo añadiendo la versión
                version++;
                newFileName = String.format("%s (%d).xlsx", fileNamePagos.substring(0, fileNamePagos.lastIndexOf('.')), version);
                archivo = new File(excelFilePagos + File.separator + newFileName);
            }

            // Crear el archivo en la ruta especificada
            try (FileOutputStream outputStream = new FileOutputStream(excelFilePagos + File.separator + newFileName)) {
                workbook.write(outputStream);
            }

            System.out.println("Pagos.xlsx exportados correctamente a Excel en el archivo: " + newFileName);


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

            // Verificar si la carpeta existe, de lo contrario crearla
            File carpeta = new File(excelFilePagos);
            if(!carpeta.exists()){
                carpeta.mkdirs();
            }

            // Verificar si el archivo existe, de ser así, generar una versión nueva numerada
            int version = 1;
            String newFileName = fileNameAlumnos;
            File archivo = new File(excelFileAlumnos + File.separator + newFileName);
            while (archivo.exists()){
                // Generar nuevo nombre de archivo añadiendo la versión
                version++;
                newFileName = String.format("%s (%d).xlsx", fileNameAlumnos.substring(0, fileNameAlumnos.lastIndexOf('.')), version);
                archivo = new File(excelFileAlumnos + File.separator + newFileName);
            }

            // Crear el archivo en la ruta especificada
            try (FileOutputStream outputStream = new FileOutputStream(excelFileAlumnos + File.separator + newFileName)) {
                workbook.write(outputStream);
            }

            System.out.println("Alumnos.xlsx exportados correctamente a Excel en el archivo: " + newFileName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

