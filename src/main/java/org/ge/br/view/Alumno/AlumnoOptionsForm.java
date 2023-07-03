package org.ge.br.view.Alumno;

import org.ge.br.view.Inicio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AlumnoOptionsForm extends JFrame {
    private CardLayoutManager cardLayoutManager;

    public AlumnoOptionsForm() {
        // Configurar el JFrame
        setTitle("Opciones Alumno");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 400); // Ajustar el tamaño de la ventana
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla

        // Crear el panel principal con margen
        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Agregar un margen
        cardLayoutManager = new CardLayoutManager(cardPanel);

        // Crear los paneles de opciones y agregarlos al panel principal
        JPanel consultarAlumnoPanel = createConsultarAlumnoPanel();
        cardLayoutManager.addPanel("ConsultarAlumno", consultarAlumnoPanel);

        JPanel registrarAlumnoPanel = createRegistrarAlumnoPanel();
        cardLayoutManager.addPanel("RegistrarAlumno", registrarAlumnoPanel);

        // Mostrar el primer panel
        cardLayoutManager.showPanel("ConsultarAlumno");

        // Agregar el panel principal al JFrame
        add(cardPanel);

        // Crear botones de navegación
        JButton btnConsultarAlumno = new JButton("Consultar Alumno");
        JButton btnRegistrarAlumno = new JButton("Registrar Alumno");
        JButton btnVolver = new JButton("Volver");

        // Configurar acciones de los botones de navegación
        btnConsultarAlumno.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSize(1200,400);
                setLocationRelativeTo(null);
                cardLayoutManager.showPanel("ConsultarAlumno");

            }
        });

        btnRegistrarAlumno.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSize(400, 350);
                setLocationRelativeTo(null);
                setResizable(false);
                cardLayoutManager.showPanel("RegistrarAlumno");

            }
        });

        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cerrar la ventana actual
                Inicio inicio = new Inicio(); // Crear una instancia de la clase Inicio
                inicio.setVisible(true); // Mostrar la ventana de Inicio
            }
        });

        // Crear panel de botones de navegación
        JPanel navigationPanel = new JPanel();
        navigationPanel.add(btnConsultarAlumno);
        navigationPanel.add(btnRegistrarAlumno);
        navigationPanel.add(btnVolver);

        // Agregar el panel de botones de navegación al JFrame
        add(navigationPanel, BorderLayout.NORTH);

        // Agregar listener de ventana para ajustar el tamaño después de la creación
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                super.windowOpened(e);
                consultarAlumnoPanel.setPreferredSize(new Dimension(1024, 400));
                registrarAlumnoPanel.setPreferredSize(new Dimension(600, 400));
                pack(); // Ajustar el tamaño del JFrame para que coincida con los paneles
            }
        });
    }

    private JPanel createConsultarAlumnoPanel() {
        ConsultarAlumnoPanel panel = new ConsultarAlumnoPanel();
        return panel.getPanel();
    }

    private JPanel createRegistrarAlumnoPanel() {
        RegistrarAlumnoPanel panel = new RegistrarAlumnoPanel();
        return panel.getPanel();
    }
}

