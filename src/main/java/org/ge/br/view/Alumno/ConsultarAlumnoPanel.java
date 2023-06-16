package org.ge.br.view.Alumno;


import org.ge.br.dao.AlumnoDao;
import org.ge.br.export.DatabaseToExcelExporter;
import org.ge.br.model.Alumno;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.List;

public class ConsultarAlumnoPanel {
    private JPanel panel;
    private JComboBox<String> cmbStatus;
    private JComboBox<String> cmbEspecialidad;
    private JTable tblAlumnos;
    private Alumno alumnoSeleccionado = null;
    private AlumnoDao alumnoDao;

    public ConsultarAlumnoPanel() {
        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Crear panel para los filtros de búsqueda
        JPanel panelFiltros = new JPanel();
        panelFiltros.setLayout(new FlowLayout());

        // Agregar filtro de Status
        JLabel lblStatus = new JLabel("Status:");
        cmbStatus = new JComboBox<>(new String[]{"Todos", "Interesado", "Nuevo Ingreso"});
        panelFiltros.add(lblStatus);
        panelFiltros.add(cmbStatus);

        // Agregar filtro de Especialidad
        JLabel lblEspecialidad = new JLabel("Especialidad:");
        cmbEspecialidad = new JComboBox<>(new String[]{"Todos", "Endodoncia", "Ortodoncia", "Ambas"});
        panelFiltros.add(lblEspecialidad);
        panelFiltros.add(cmbEspecialidad);

        // Agregar botón de búsqueda
        JButton btnBuscar = new JButton("Buscar");
        panelFiltros.add(btnBuscar);

        // Agregar botones de exportación
        JButton btnExportarPagos = new JButton("Exportar Pagos");
        JButton btnExportarAlumnos = new JButton("Exportar Alumnos");
        panelFiltros.add(btnExportarPagos);
        panelFiltros.add(btnExportarAlumnos);

        // Agregar botones de agregar documento y agregar pago
        JButton btnAgregarDocumento = new JButton("Agregar Documento");
        JButton btnAgregarPago = new JButton("Agregar Pago");
        panelFiltros.add(btnAgregarDocumento);
        panelFiltros.add(btnAgregarPago);

        // Agregar panel de filtros al panel principal
        panel.add(panelFiltros, BorderLayout.NORTH);

        // Crear tabla para mostrar los resultados
        tblAlumnos = new JTable();
        JScrollPane scrollPane = new JScrollPane(tblAlumnos);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Configurar acción del botón Buscar
        btnBuscar.addActionListener(e -> {
            String status = cmbStatus.getSelectedItem().toString();
            String especialidad = cmbEspecialidad.getSelectedItem().toString();

            // Realizar la búsqueda en la base de datos
            List<Alumno> alumnos;
            if (status.equals("Todos") && especialidad.equals("Todos")) {
                alumnos = alumnoDao.buscarTodosLosAlumnos();
            } else if (status.equals("Todos") && especialidad.equals("Endodoncia")) {
                alumnos = alumnoDao.buscarAlumnosPorEspecialidad("Endodoncia");
            } else if (status.equals("Todos") && especialidad.equals("Ortodoncia")) {
                alumnos = alumnoDao.buscarAlumnosPorEspecialidad("Ortodoncia");
            } else if (status.equals("Interesado") && especialidad.equals("Todos")) {
                alumnos = alumnoDao.buscarAlumnosTodosTodos("false");
            } else if (status.equals("Interesado") && especialidad.equals("Endodoncia")) {
                alumnos = alumnoDao.buscarAlumnosInteresadosPorEspecialidad("Endodoncia");
            } else if (status.equals("Interesado") && especialidad.equals("Ortodoncia")) {
                alumnos = alumnoDao.buscarAlumnosInteresadosPorEspecialidad("Ortodoncia");
            } else if (status.equals("Interesado") && especialidad.equals("Ambas")) {
                alumnos = alumnoDao.buscarAlumnosInteresadosPorEspecialidad("Ambas");
            } else if (status.equals("Nuevo Ingreso") && especialidad.equals("Todos")) {
                alumnos = alumnoDao.buscarAlumnosTodosTodos("true");
            } else if (status.equals("Nuevo Ingreso") && especialidad.equals("Endodoncia")) {
                alumnos = alumnoDao.buscarAlumnosNuevosIngresosPorEspecialidad("Endodoncia");
            } else if (status.equals("Nuevo Ingreso") && especialidad.equals("Ortodoncia")) {
                alumnos = alumnoDao.buscarAlumnosNuevosIngresosPorEspecialidad("Ortodoncia");
            } else {
                JOptionPane.showMessageDialog(panel, "Opción no válida.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Actualizar la tabla con los resultados de la búsqueda
            actualizarTablaAlumnos(alumnos);
        });

        // Configurar acciones de los botones de exportación
        btnExportarPagos.addActionListener(e -> {
            DatabaseToExcelExporter databaseToExcelExporter = new DatabaseToExcelExporter();
            databaseToExcelExporter.exportExcelPagos();
            JOptionPane.showMessageDialog(null, "Datos exportados correctamente");
        });

        btnExportarAlumnos.addActionListener(e -> {
            DatabaseToExcelExporter databaseToExcelExporter = new DatabaseToExcelExporter();
            databaseToExcelExporter.exportExcelAlumnos();
            JOptionPane.showMessageDialog(null, "Datos exportados correctamente");
        });

        // Configurar acción del botón Agregar Documento
        btnAgregarDocumento.addActionListener(e -> {
            int selectedRow = tblAlumnos.getSelectedRow();

            if (selectedRow != -1) {
                int selectedId = (int) tblAlumnos.getValueAt(selectedRow, 0);
                alumnoSeleccionado = alumnoDao.obtenerAlumnoPorId(selectedId);

                if (alumnoSeleccionado != null) {
                    JOptionPane.showMessageDialog(panel, alumnoSeleccionado.getNombreCompleto() + " seleccionado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    AddDocument addDocument = new AddDocument(alumnoSeleccionado);
                    addDocument.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(panel, "No se pudo obtener el alumno seleccionado.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(panel, "Debe seleccionar un alumno de la tabla.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        //Configurar acción del botón Agregar Pago
        btnAgregarPago.addActionListener(e -> {
            int selectedRow = tblAlumnos.getSelectedRow();

            if (selectedRow != -1) {
                int selectedId = (int) tblAlumnos.getValueAt(selectedRow, 0);
                alumnoSeleccionado = alumnoDao.obtenerAlumnoPorId(selectedId);

                if (alumnoSeleccionado != null) {
                    JOptionPane.showMessageDialog(panel, alumnoSeleccionado.getNombreCompleto() + " seleccionado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    AddPagoAl addPagoAl = new AddPagoAl(alumnoSeleccionado);
                    addPagoAl.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(panel, "No se pudo obtener el alumno seleccionado.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(panel, "Debe seleccionar un alumno de la tabla.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Configurar la tabla de alumnos
        configurarTablaAlumnos();

        // Inicializar el objeto AlumnoDao
        alumnoDao = new AlumnoDao();

        // Cargar todos los alumnos al iniciar la ventana
        //cargarTodosLosAlumnos();
    }

    public JPanel getPanel() {
        return panel;
    }

    private void configurarTablaAlumnos() {
        // Crear el modelo de la tabla
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Nombre Completo");
        tableModel.addColumn("Email");
        tableModel.addColumn("Teléfono");
        tableModel.addColumn("Especialidad");
        tableModel.addColumn("Fecha Registro");
        tableModel.addColumn("Fecha Completado");
        tableModel.addColumn("Status");

        tblAlumnos.setModel(tableModel);

        // Configurar el ancho de las columnas
        TableColumnModel columnModel = tblAlumnos.getColumnModel();
        int[] columnWidths = {30, 150, 150, 100, 100, 100, 100, 80};

        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            TableColumn column = columnModel.getColumn(i);
            column.setPreferredWidth(columnWidths[i]);
        }

        // Configurar el renderizado de las celdas para alinear el texto al centro
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < tblAlumnos.getColumnCount(); i++) {
            tblAlumnos.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Configurar la selección de filas para permitir la selección de un solo alumno a la vez
        tblAlumnos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Configurar el evento de selección de filas
        tblAlumnos.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = tblAlumnos.getSelectedRow();
            if (selectedRow != -1) {
                int selectedId = (int) tblAlumnos.getValueAt(selectedRow, 0);
                alumnoSeleccionado = alumnoDao.obtenerAlumnoPorId(selectedId);
            } else {
                alumnoSeleccionado = null;
            }
        });
    }

    private void actualizarTablaAlumnos(List<Alumno> alumnos) {
        DefaultTableModel tableModel = (DefaultTableModel) tblAlumnos.getModel();
        tableModel.setRowCount(0);

        for (Alumno alumno : alumnos) {
            Object[] rowData = new Object[8];
            rowData[0] = alumno.getId();
            rowData[1] = alumno.getNombreCompleto();
            rowData[2] = alumno.getEmail();
            rowData[3] = alumno.getTelefono();
            rowData[4] = alumno.getEspecialidad();
            rowData[5] = alumno.getFechaRegistro();
            rowData[6] = alumno.getFechaCompletado();
            rowData[7] = alumno.isStatus();

            tableModel.addRow(rowData);
        }
    }
}