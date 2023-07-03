package org.ge.br.view;

import org.ge.br.view.Alumno.AlumnoOptionsForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Inicio extends JFrame {
    private JButton btnAlumnos, btnProfesores, btnPagos, btnCalendarios;
    public Inicio() {
    // Configurar el JFrame
    setTitle("Opciones");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(400, 300);
    setLayout(null);
    setLocationRelativeTo(null); // Centrar la ventana en la pantalla

        // Crear los botones de opciones
    btnAlumnos = new JButton("Alumnos");
    btnAlumnos.setBounds(125, 40, 150, 30);
    add(btnAlumnos);

    btnProfesores = new JButton("Profesores");
    btnProfesores.setBounds(125, 90, 150, 30);
    add(btnProfesores);

    btnPagos = new JButton("Pagos");
    btnPagos.setBounds(125, 140, 150, 30);
    add(btnPagos);

    btnCalendarios = new JButton("Calendarios");
    btnCalendarios.setBounds(125, 190, 150, 30);
    add(btnCalendarios);


        // Agregar ActionListener a los botones de opciones
    btnAlumnos.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(Inicio.this, "Opción Alumnos seleccionada");
            AlumnoOptionsForm vAOF = new AlumnoOptionsForm();
            vAOF.setVisible(true);
            vAOF.setSize(1200,400);
            setVisible(false);
        }
    });

    btnProfesores.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(Inicio.this, "Opción Profesores seleccionada");
        }
    });

    btnPagos.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(Inicio.this, "Opción Pagos seleccionada");
        }
    });

    btnCalendarios.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(Inicio.this, "Opción Calendarios seleccionada");
        }
    });

}
}
