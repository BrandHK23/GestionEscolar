package org.ge.br.view.Alumno;


import org.ge.br.config.NonEditableCellEditor;
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
    private JComboBox<String> cmbExportar;
    private JComboBox<String> cmbAgregar;
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

        //Crear panel opcionesAlumno
        JPanel panelOpcionesAl = new JPanel();
        panelOpcionesAl.setLayout(new FlowLayout());

        //Crear panel Opciones
        JPanel panelOpciones = new JPanel();
        panelOpciones.setLayout(new FlowLayout());

        //__ Crear subPanel opciones
        JPanel subPanelOpciones = new JPanel();
        subPanelOpciones.setLayout(new BoxLayout(subPanelOpciones, BoxLayout.Y_AXIS));
        panelOpciones.add(subPanelOpciones);


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

        // Agregar opciones de exportación
        JLabel lblExportar = new JLabel("Exportar:");
        JButton btnExportar = new JButton("Exportar");

        // Agregar combobox Exportar
        cmbExportar = new JComboBox<>(new String[]{"Alumnos","Pagos"});
        subPanelOpciones.add(lblExportar);
        subPanelOpciones.add(cmbExportar);
        subPanelOpciones.add(btnExportar);

        //Espacio vertical entre los botones del subPanel
        subPanelOpciones.add(Box.createVerticalStrut(10));

        // Agregar opciones de agregar
        JLabel lblAgregar = new JLabel("Agregar:");
        JButton btnAgregar = new JButton("Agregar");

        // Agregar combobox Agregar
        cmbAgregar = new JComboBox<>(new String[]{"Documento","Pago"});
        subPanelOpciones.add(lblAgregar);
        subPanelOpciones.add(cmbAgregar);
        subPanelOpciones.add(btnAgregar);


        // Agregar boton de eliminar alumno
        JButton btnEliminarAlumno = new JButton("Elimnar alumno");
        panelOpcionesAl.add(btnEliminarAlumno);

        // Agregar panel de opcionesAL al panel principal
        panel.add(panelOpcionesAl, BorderLayout.SOUTH);

        // Agregar panel de opciones al panel principal
        panel.add(panelOpciones, BorderLayout.EAST);

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

        btnEliminarAlumno.addActionListener(e -> {
            int selectedRow = tblAlumnos.getSelectedRow();

            if(selectedRow != -1){
                int selectedId = (int) tblAlumnos.getValueAt(selectedRow, 0);
                alumnoSeleccionado = alumnoDao.obtenerAlumnoPorId(selectedId);

                if(alumnoSeleccionado != null){
                    int confirmacion = JOptionPane.showConfirmDialog(null,"¿Está seguro de eliminar al alumno '" + alumnoSeleccionado.getNombreCompleto() + "' de la base de datos?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                    if(confirmacion == JOptionPane.YES_OPTION){
                        alumnoDao.eliminarAlumno(selectedId);
                    }else{

                    }
                }
            }
        });

        // Configurar acciones de los botones de exportación
        btnExportar.addActionListener(e -> {
            String exportar = cmbExportar.getSelectedItem().toString();

            if (exportar.equals("Pagos")){
                DatabaseToExcelExporter databaseToExcelExporter = new DatabaseToExcelExporter();
                databaseToExcelExporter.exportExcelPagos();
                JOptionPane.showMessageDialog(null, "Datos exportados correctamente");
            }else if(exportar.equals("Alumnos")){
                DatabaseToExcelExporter databaseToExcelExporter = new DatabaseToExcelExporter();
                databaseToExcelExporter.exportExcelAlumnos();
                JOptionPane.showMessageDialog(null, "Datos exportados correctamente");
            }else{
                JOptionPane.showMessageDialog(panel, "Opción no válida.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        });

        // Configurar acción del botón Agregar Documento
        btnAgregar.addActionListener(e -> {
            String agregar = cmbAgregar.getSelectedItem().toString();
            int selectedRow = tblAlumnos.getSelectedRow();

            if (agregar.equals("Documento")){
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
            }else if(agregar.equals("Pago")){
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

            // Asignar el TableCellEditor personalizado
            tblAlumnos.getColumnModel().getColumn(i).setCellEditor(new NonEditableCellEditor());
        }

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