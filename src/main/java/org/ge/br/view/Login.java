package org.ge.br.view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.prefs.Preferences;


public class Login extends JFrame{

    private static final String PRIMERA_EJECUCION = "primera_ejecucion";

    private JPanel MainLogin = new JPanel();
    private JButton btnLogin;
    private JTextField txtFieldUser;
    private JPasswordField passwordField;
    private JLabel lblUser;
    private JLabel lblPassword;
    public static String texto = "";
    public static String texto2 = "";

    public static String admin = "admin";
    public static String psw = "admin";

    public Login() {
// Configurar el JFrame
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 250);
        setLayout(null);
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla
        setResizable(false);

        // Crear y configurar los componentes
        lblUser = new JLabel("Usuario");
        lblUser.setBounds(45, 40, 100, 30);
        add(lblUser);

        txtFieldUser = new JTextField();
        txtFieldUser.setBounds(125, 40, 150, 30);
        add(txtFieldUser);

        lblPassword = new JLabel("Contraseña");
        lblPassword.setBounds(45, 80, 100, 30);
        add(lblPassword);

        passwordField = new JPasswordField();
        passwordField.setBounds(125, 80, 150, 30);
        add(passwordField);

        btnLogin = new JButton("Login");
        btnLogin.setBounds(125, 130, 100, 30);
        btnLogin.addActionListener(this::actionPerformed);
        add(btnLogin);

        // Agregar los componentes al JFrame
        add(btnLogin);
        add(txtFieldUser);
        add(passwordField);
        add(lblUser);
        add(lblPassword);
    }
    private boolean isValidCredentials(String username, char[] password) {
        // Aquí debes implementar la lógica para verificar las credenciales en tu sistema
        // Por simplicidad, en este ejemplo consideraremos que las credenciales son válidas si el nombre de usuario es "admin" y la contraseña es "password"
        String validUsername = "admin";
        char[] validPassword = "admin".toCharArray();

        return username.equals(validUsername) && Arrays.equals(password, validPassword);
    }
    public void actionPerformed(ActionEvent e){
        String username = txtFieldUser.getText();
        char[] password = passwordField.getPassword();

        if (isValidCredentials(username, password)) {
            dispose(); // Cerrar la ventana de inicio de sesión
            Inicio optionsForm = new Inicio();
            optionsForm.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(Login.this, "Credenciales inválidas", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Limpiar los campos de usuario y contraseña después del inicio de sesión
        txtFieldUser.setText("");
        passwordField.setText("");
    }

    public static void main(String[] args) {
        Login vlogin = new Login();
        vlogin.setVisible(true);
    }
}