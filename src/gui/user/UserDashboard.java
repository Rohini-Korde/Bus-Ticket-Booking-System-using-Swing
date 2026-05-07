package gui.user;

import model.User;

import javax.swing.*;

import gui.auth.LoginFrame;

import java.awt.*;

import javax.swing.*;
import java.awt.*;

public class UserDashboard extends JFrame {

    private User user;

    public UserDashboard(User user) {

        this.user = user;

        setTitle("User Dashboard");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel title = new JLabel("WELCOME " + user.getName(), JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));

        JButton searchBtn = new JButton("Search Bus");
        JButton viewBookingBtn = new JButton("View My Bookings");
        JButton logoutBtn = new JButton("Logout");

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.add(searchBtn);
        panel.add(viewBookingBtn);
        panel.add(logoutBtn);

        add(title, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        searchBtn.addActionListener(e -> new SearchBusFrame(user));
        viewBookingBtn.addActionListener(e -> new ViewBookingsFrame(user));
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        setVisible(true);
    }
}


