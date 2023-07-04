package org.ge.br.view.Alumno;

import org.ge.br.dao.DocAlumnoDao;
import org.ge.br.model.Alumno;
import org.ge.br.model.DocAlumno;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddDocument extends JFrame {
    private Alumno alumnoSeleccionado;
    private JLabel[] filePathLabels;
    private JButton[] openDirButtons;

    public AddDocument(Alumno alumno) {
        alumnoSeleccionado = alumno;

        setTitle("Agregar Documento");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Obtener nombres de los documentos
        String[] nombresDocumentos = {"Titulo", "Cédula Profesional", "Acta de nacimiento", "Fotografias", "CURP", "Comprobante de domicilio", "Carta de intencion", "INE"};

        // Crear panel para mostrar los campos de ruta de los documentos
        JPanel panelDocumentos = new JPanel(new GridLayout(nombresDocumentos.length, 4, 10, 0));

        // Crear campos de texto para las rutas de los documentos
        filePathLabels = new JLabel[nombresDocumentos.length];
        openDirButtons = new JButton[nombresDocumentos.length];

        for (int i = 0; i < nombresDocumentos.length; i++) {
            JLabel lblDocumento = new JLabel(nombresDocumentos[i]);
            JLabel lblRuta = new JLabel();
            lblRuta.setPreferredSize(new Dimension(200, lblRuta.getPreferredSize().height));
            JButton btnSeleccionar = new JButton("Agregar");
            JButton btnOpenDir = new JButton("Abrir");
            btnOpenDir.setEnabled(false);

            // Configurar acción del botón "Seleccionar"
            final int index = i;
            btnSeleccionar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFileChooser fileChooser = new JFileChooser();
                    int option = fileChooser.showOpenDialog(AddDocument.this);
                    if (option == JFileChooser.APPROVE_OPTION) {
                        String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                        lblRuta.setText(filePath);
                        btnOpenDir.setEnabled(true);
                    }
                }
            });

            // Configurar acción del botón "Abrir Directorio"
            btnOpenDir.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String rutaDocumento = lblRuta.getText();
                    abrirDirectorio(rutaDocumento);
                }
            });

            // Agregar los componentes al panel
            panelDocumentos.add(lblDocumento);
            panelDocumentos.add(lblRuta);
            panelDocumentos.add(btnSeleccionar);
            panelDocumentos.add(btnOpenDir);

            // Guardar referencia al label y al botón en los arrays
            filePathLabels[i] = lblRuta;
            openDirButtons[i] = btnOpenDir;
        }

        // Obtener las rutas de los documentos existentes para el alumno seleccionado
        obtenerRutasDocumentosExistentes();

        // Crear botón "Guardar"
        JButton btnGuardar = new JButton("Guardar");

        // Configurar acción del botón "Guardar"
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener las rutas de los documentos
                List<String> rutasDocumentos = new ArrayList<>();
                for (JLabel filePathLabel : filePathLabels) {
                    String rutaDocumento = filePathLabel.getText();
                    rutasDocumentos.add(rutaDocumento);
                }

                // Asignar las rutas de los documentos al alumno seleccionado
                alumnoSeleccionado.setRutasDocumentos(rutasDocumentos);

                // Guardar las rutas de los documentos en la tabla DocAlumno
                DocAlumnoDao docAlumnoDao = new DocAlumnoDao();
                docAlumnoDao.guardar(alumnoSeleccionado);

                // Cerrar la ventana
                dispose();
            }
        });

        // Crear panel para los botones
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnGuardar);

        // Crear panel principal y agregar los paneles anteriores con un margen de 10 píxeles
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        panelPrincipal.add(panelDocumentos, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        // Agregar el panel principal al frame
        add(panelPrincipal);

        pack();
        setLocationRelativeTo(null);
    }

    private void obtenerRutasDocumentosExistentes() {
        DocAlumnoDao docAlumnoDao = new DocAlumnoDao();
        List<DocAlumno> documentos = docAlumnoDao.obtenerDocumentosPorAlumno(alumnoSeleccionado.getId());

        for (DocAlumno documento : documentos) {
            int numDoc = documento.getNum_doc();
            if (numDoc > 0 && numDoc <= filePathLabels.length) {
                JLabel label = filePathLabels[numDoc - 1];
                label.setText(documento.getRuta_doc());
                JButton button = openDirButtons[numDoc - 1];
                button.setEnabled(true);
            }
        }
    }

    private void abrirDirectorio(String ruta) {
        try {
            Desktop.getDesktop().open(new File(ruta));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}







