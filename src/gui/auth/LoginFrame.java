package gui.auth;

import java.awt.GridLayout;

import javax.swing.*;

import gui.admin.AdminDashboard;
import gui.user.UserDashboard;
import model.User;
import services.AuthenticateService;

public class LoginFrame extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;
    private AuthenticateService authService;

    public LoginFrame() {

        authService = new AuthenticateService();

        setTitle("Bus Booking System - Login");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton loginBtn = new JButton("Login");
        JButton registerBtn = new JButton("Register");

        panel.add(loginBtn);
        panel.add(registerBtn);

        add(panel);

        // Login Action
        loginBtn.addActionListener(e -> login());

        // Open Register Frame
        registerBtn.addActionListener(e -> {
            new RegisterFrame();
            dispose();
        });

        setVisible(true);
    }

    private void login() {

        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        User user = authService.login(email, password);

        if (user != null) {

            JOptionPane.showMessageDialog(this, "Login Successful!");

            if (user.getRole().equalsIgnoreCase("admin")) {
                new AdminDashboard();
            } else {
                new UserDashboard(user);
            }

            dispose();

        } else {
            JOptionPane.showMessageDialog(this, "Invalid Credentials!");
        }
    }
}
