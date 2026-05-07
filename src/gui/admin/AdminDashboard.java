package gui.admin;

import javax.swing.*;

import gui.auth.LoginFrame;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {

    public AdminDashboard() {

        setTitle("Admin Dashboard");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel title = new JLabel("ADMIN PANEL", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));

        JButton addBusBtn = new JButton("Add Bus");
        JButton addRouteBtn = new JButton("Add Route");
        JButton addScheduleBtn = new JButton("Add Schedule");
        JButton logoutBtn = new JButton("Logout");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.add(addBusBtn);
        panel.add(addRouteBtn);
        panel.add(addScheduleBtn);
        panel.add(logoutBtn);

        add(title, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        // Button Actions
        addBusBtn.addActionListener(e -> new AddBusFrame());
        addRouteBtn.addActionListener(e -> new AddRouteFrame());
        addScheduleBtn.addActionListener(e -> new AddScheduleFrame());
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        setVisible(true);
    }
}


