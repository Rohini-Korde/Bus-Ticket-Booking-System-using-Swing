package gui.auth;

import javax.swing.*;

import services.AuthenticateService;

import java.awt.*;

public class RegisterFrame extends JFrame {

    private JTextField nameField, emailField, phoneField;
    private JPasswordField passwordField;
    private AuthenticateService authService;

    public RegisterFrame() {

        authService = new AuthenticateService();

        setTitle("Register");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        panel.add(new JLabel("Phone:"));
        phoneField = new JTextField();
        panel.add(phoneField);

        JButton registerBtn = new JButton("Register");
        panel.add(registerBtn);

        add(panel);

        registerBtn.addActionListener(e -> register());

        setVisible(true);
    }

    private void register() {

        boolean success = authService.register(
                nameField.getText(),
                emailField.getText(),
                new String(passwordField.getPassword()),
                phoneField.getText()
        );

        if (success) {
            JOptionPane.showMessageDialog(this, "Registration Successful!");
            new LoginFrame();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Registration Failed!");
        }
    }
}
