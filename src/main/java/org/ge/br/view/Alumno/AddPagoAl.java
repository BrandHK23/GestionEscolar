package org.ge.br.view.Alumno;

import org.ge.br.dao.AlumnoDao;
import org.ge.br.dao.PagosAlDao;
import org.ge.br.model.Alumno;
import org.ge.br.model.PagoAl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.List;

public class AddPagoAl extends JFrame {
    private Alumno alumnoSeleccionado;
    private JTable tblPagos;
    private DefaultTableModel tableModel;
    private AlumnoDao alumnoDao;
    private JComboBox<String> cmbEspecialidad;

    public AddPagoAl(Alumno alumno) {
        alumnoSeleccionado = alumno;

        setTitle("Agregar Pago");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Crear panel para los componentes de registro de pagos
        JPanel panelPagos = new JPanel();
        panelPagos.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5); // Espaciado entre componentes

        // Agregar opciones de tipo de pago
        String[] tiposPago = {"Inscripción", "Reinscripción", "Mensualidad", "Pago Extra"};
        JComboBox<String> cmbTipoPago = new JComboBox<>(tiposPago);
        JLabel lblTipoPago = new JLabel("Tipo de Pago:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelPagos.add(lblTipoPago, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panelPagos.add(cmbTipoPago, gbc);
        // Agregar opción de pago en efectivo o tarjeta
        JRadioButton rbtnEfectivo = new JRadioButton("Efectivo");
        JRadioButton rbtnDeposito = new JRadioButton("Depósito");
        ButtonGroup group = new ButtonGroup();
        group.add(rbtnEfectivo);
        group.add(rbtnDeposito);

        JPanel panelOpcionesPago = new JPanel();
        panelOpcionesPago.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelOpcionesPago.add(rbtnEfectivo);
        panelOpcionesPago.add(rbtnDeposito);

        JLabel lblFormaPago = new JLabel("Forma de Pago:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelPagos.add(lblFormaPago, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panelPagos.add(panelOpcionesPago, gbc);

        // Agregar campo para la cantidad pagada
        JLabel lblCantidadPagada = new JLabel("Cantidad Pagada:");
        JTextField txtCantidadPagada = new JTextField();
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelPagos.add(lblCantidadPagada, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panelPagos.add(txtCantidadPagada, gbc);

        // Agregar checkbox para indicar si se entregó factura
        JCheckBox chkFacturaEntregada = new JCheckBox("Factura Entregada");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panelPagos.add(chkFacturaEntregada, gbc);

        // Crear caja de comentarios
        JLabel lblComentarios = new JLabel("Comentarios:");
        JTextArea txtComentarios = new JTextArea(5, 20);
        txtComentarios.setLineWrap(true);
        txtComentarios.setWrapStyleWord(true);
        JScrollPane scrollPaneComentarios = new JScrollPane(txtComentarios);

        JLabel lblComentariosTitulo = new JLabel("Comentarios:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panelPagos.add(lblComentariosTitulo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panelPagos.add(scrollPaneComentarios, gbc);

        // Agregar botón para guardar el pago
        JButton btnGuardarPago = new JButton("Guardar Pago");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.EAST; // Alineación a la derecha
        gbc.gridwidth = 2; // Ocupar dos columnas
        panelPagos.add(btnGuardarPago, gbc);

        btnGuardarPago.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener los valores de los campos de pago
                int idAlumno = alumnoSeleccionado.getId();
                String tipoPago = cmbTipoPago.getSelectedItem().toString();
                Date fechaPago = new Date(System.currentTimeMillis());  // Obtén la fecha actual
                boolean facturaEntregada = chkFacturaEntregada.isSelected();
                boolean pagadoEfectivo = rbtnEfectivo.isSelected();
                int cantidadPagada = Integer.parseInt(txtCantidadPagada.getText());
                String comentarios = txtComentarios.getText();

                //Obtenemos la especialidad seleccionada por el alumno al guardar el pago
                String especialidadSeleccionada = cmbEspecialidad.getSelectedItem().toString();

                // Actualizar la especialidad del alumno en la base de datos
                    String nuevaEspecialidad = especialidadSeleccionada;
                    alumnoDao.actualizarEspecialidad(idAlumno, nuevaEspecialidad);

                // Actualizar el alumno en la base de datos
                if (tipoPago.equals("Inscripción")) {
                    alumnoDao.actualizarEstadoYFechaCompletado(idAlumno, true, fechaPago);
                }

                // Crear el objeto PagoAl
                PagoAl pago = new PagoAl(0, idAlumno, tipoPago, fechaPago, facturaEntregada, pagadoEfectivo, cantidadPagada, comentarios);

                // Guardar el pago en la base de datos
                PagosAlDao pagosAlDao = new PagosAlDao();
                pagosAlDao.guardar(pago);

                // Actualizar la tabla de pagos
                actualizarTablaPagos(idAlumno);

                // Mostrar mensaje de éxito
                JOptionPane.showMessageDialog(AddPagoAl.this, "Pago registrado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                // Limpiar campos y restablecer valores predeterminados
                txtCantidadPagada.setText("");
                txtComentarios.setText("");
                cmbTipoPago.setSelectedIndex(0);
                cmbEspecialidad.setSelectedIndex(0);
                chkFacturaEntregada.setSelected(false);
                rbtnEfectivo.setSelected(true);
            }
        });


        // Crear panel para mostrar la información del alumno
        JPanel panelDatosAlumno = new JPanel();
        panelDatosAlumno.setLayout(new GridBagLayout());
        panelDatosAlumno.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbcDatosAlumno = new GridBagConstraints();
        gbcDatosAlumno.anchor = GridBagConstraints.WEST;
        gbcDatosAlumno.insets = new Insets(5, 5, 5, 5);

        JLabel lblIDAlumno = new JLabel("ID:");
        JLabel lblIDAlumnoValor = new JLabel(String.valueOf(alumnoSeleccionado.getId()));
        JLabel lblNombreCompleto = new JLabel("Alumno:");
        JLabel lblNombreCompletoValor = new JLabel(alumnoSeleccionado.getNombreCompleto());
        JLabel lblEspecialidad = new JLabel("Especialidad:");
        JLabel lblEspecialidadValor = new JLabel(alumnoSeleccionado.getEspecialidad());
        JLabel lblEmail = new JLabel("Email:");
        JLabel lblEmailValor = new JLabel(alumnoSeleccionado.getEmail());

        gbcDatosAlumno.gridx = 0;
        gbcDatosAlumno.gridy = 0;
        panelDatosAlumno.add(lblIDAlumno, gbcDatosAlumno);
        gbcDatosAlumno.gridx = 1;
        gbcDatosAlumno.gridy = 0;
        panelDatosAlumno.add(lblIDAlumnoValor, gbcDatosAlumno);
        gbcDatosAlumno.gridx = 0;
        gbcDatosAlumno.gridy = 1;
        panelDatosAlumno.add(lblNombreCompleto, gbcDatosAlumno);
        gbcDatosAlumno.gridx = 1;
        gbcDatosAlumno.gridy = 1;
        panelDatosAlumno.add(lblNombreCompletoValor, gbcDatosAlumno);
        gbcDatosAlumno.gridx = 0;
        gbcDatosAlumno.gridy = 2;
        panelDatosAlumno.add(lblEspecialidad, gbcDatosAlumno);
        gbcDatosAlumno.gridx = 1;
        gbcDatosAlumno.gridy = 2;
        panelDatosAlumno.add(lblEspecialidadValor, gbcDatosAlumno);
        gbcDatosAlumno.gridx = 0;
        gbcDatosAlumno.gridy = 3;
        panelDatosAlumno.add(lblEmail, gbcDatosAlumno);
        gbcDatosAlumno.gridx = 1;
        gbcDatosAlumno.gridy = 3;
        panelDatosAlumno.add(lblEmailValor, gbcDatosAlumno);

        // Configurar la tabla de pagos
        configurarTablaPagos();
        // Actualizar la tabla de pagos
        actualizarTablaPagos(alumnoSeleccionado.getId());

        // Agregar los paneles al contenedor principal
        JPanel panelContenedor = new JPanel();
        panelContenedor.setLayout(new BorderLayout());
        panelContenedor.add(panelDatosAlumno, BorderLayout.WEST);
        panelContenedor.add(panelPagos, BorderLayout.EAST);

        add(panelContenedor, BorderLayout.NORTH);

        pack();
        setSize(900, 400);
        setLocationRelativeTo(null);

        //Crear ComboBox para la seleccion de especialidad
        String[] especialidades = {"Endodoncia", "Ortodoncia"};
        cmbEspecialidad = new JComboBox<>(especialidades);
        cmbEspecialidad.setEnabled(false); //Inicialmente deshabilitado
        // Agregar el ComboBox al panel de datos del alumno
        JLabel lblEspecialidadSeleccionada = new JLabel("Especialidad seleccionada:");
        gbcDatosAlumno.gridx = 0;
        gbcDatosAlumno.gridy = 4;
        gbcDatosAlumno.gridwidth = 2;
        panelDatosAlumno.add(lblEspecialidadSeleccionada, gbcDatosAlumno);
        gbcDatosAlumno.gridx = 0;
        gbcDatosAlumno.gridy = 5;
        gbcDatosAlumno.gridwidth = 2;
        panelDatosAlumno.add(cmbEspecialidad, gbcDatosAlumno);

        //Verificar si la especialidad del alumno es "Ambos"
        if(alumnoSeleccionado.getEspecialidad().equals("Ambas")){
            cmbEspecialidad.setEnabled(true); //Habilitar el ComboBox
        }else {
            cmbEspecialidad.setEnabled(false); //Deshabilitar el ComboBox
        }
        // Inicializar el objeto AlumnoDao
        alumnoDao = new AlumnoDao();
    }

    private void configurarTablaPagos() {
        // Crear el modelo de la tabla
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID Pago");
        tableModel.addColumn("Fecha Pago");
        tableModel.addColumn("Tipo Pago");
        tableModel.addColumn("Comentario");
        tableModel.addColumn("Efectivo");
        tableModel.addColumn("Cantidad Pagada");
        tableModel.addColumn("Factura Entregada");

        tblPagos = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tblPagos);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);

        // Configurar el ancho de las columnas
        TableColumnModel columnModel = tblPagos.getColumnModel();
        int[] columnWidths = {50, 150, 100, 100, 100, 100, 100};

        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            TableColumn column = columnModel.getColumn(i);
            column.setPreferredWidth(columnWidths[i]);
        }
    }

    private void actualizarTablaPagos(int idAlumno) {
        tableModel.setRowCount(0); // Limpiar los datos actuales de la tabla

        // Obtener los pagos del alumno desde la base de datos
        PagosAlDao pagosAlDao = new PagosAlDao();
        List<PagoAl> pagos = pagosAlDao.obtenerPagosPorAlumno(idAlumno);

        // Agregar los pagos a la tabla
        for (PagoAl pago : pagos) {
            Object[] rowData = {
                    pago.getIdPago(),
                    pago.getFechaPago(),
                    pago.getTipoPago(),
                    pago.getComentarios(),
                    pago.isPagadoEfectivo(),
                    pago.getCantidadPagada(),
                    pago.isFacturaEntregada()
            };
            tableModel.addRow(rowData);
        }

        tableModel.fireTableDataChanged(); // Actualizar la tabla
    }
}
