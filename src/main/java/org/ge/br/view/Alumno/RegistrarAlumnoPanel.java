package org.ge.br.view.Alumno;


import org.ge.br.dao.AlumnoDao;
import org.ge.br.model.Alumno;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.*;

public class RegistrarAlumnoPanel {
    private static JPanel panel;
    private JComboBox<String> cmbEspecialidad;

    public RegistrarAlumnoPanel() {
        panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Agregar campos de entrada de datos
        JLabel lblNombre = new JLabel("Nombre:");
        JTextField txtNombre = new JTextField(20);
        JLabel lblApellido = new JLabel("Apellido:");
        JTextField txtApellido = new JTextField(20);
        JLabel lblEmail = new JLabel("Email:");
        JTextField txtEmail = new JTextField(20);
        JLabel lblTelefono = new JLabel("Teléfono:");
        JTextField txtTelefono = new JTextField(20);
        JLabel lblEspecialidad = new JLabel("Especialidad:");
        cmbEspecialidad = new JComboBox<>(new String[]{"Endodoncia", "Ortodoncia", "Ambas"});

        // Agregar componentes al panel usando GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblNombre, gbc);
        gbc.gridx = 1;
        panel.add(txtNombre, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lblApellido, gbc);
        gbc.gridx = 1;
        panel.add(txtApellido, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(lblEmail, gbc);
        gbc.gridx = 1;
        panel.add(txtEmail, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(lblTelefono, gbc);
        gbc.gridx = 1;
        panel.add(txtTelefono, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(lblEspecialidad, gbc);
        gbc.gridx = 1;
        panel.add(cmbEspecialidad, gbc);

        // Agregar botón para el registro
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        JButton btnRegistrar = new JButton("Registrar");
        panel.add(btnRegistrar, gbc);

        // Configurar acción del botón Registrar
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = txtNombre.getText().trim();
                String apellido = txtApellido.getText().trim();
                String email = txtEmail.getText().trim();
                String telefono = txtTelefono.getText().trim();
                String especialidad = cmbEspecialidad.getSelectedItem().toString();

                // Verificar que los campos no estén vacíos
                if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || telefono.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Todos los campos son obligatorios", "Error de Registro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Crear objeto Alumno y establecer los valores
                Alumno alumno = new Alumno();
                alumno.setStatus(false);
                alumno.setNombreCompleto(nombre + " " + apellido);
                alumno.setEmail(email);
                alumno.setTelefono(telefono);
                alumno.setEspecialidad(especialidad);

                // Establecer las fechas de registro y completado
                alumno.setFechaRegistro(new java.sql.Date(new Date().getTime()));
                alumno.setFechaCompletado(null);

                // Realizar las operaciones necesarias para registrar al alumno
                AlumnoDao dao = new AlumnoDao();
                dao.guardar(alumno);

                // Mostrar mensaje de éxito
                JOptionPane.showMessageDialog(panel, "Alumno registrado con éxito", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);

                // Limpiar los campos de entrada después del registro
                txtNombre.setText("");
                txtApellido.setText("");
                txtEmail.setText("");
                txtTelefono.setText("");
                cmbEspecialidad.setSelectedIndex(0);
            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }
}
