package gui.main;

import gui.auth.LoginFrame;

import javax.swing.*;
import java.awt.*;

public class MainGUI extends JFrame {

    public MainGUI() {

        setTitle("Bus Ticket Booking System");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Set Background Image
        ImageIcon bgIcon = new ImageIcon(getClass().getResource("/images/bus.jpg"));
        Image bgImage = bgIcon.getImage();

        JPanel backgroundPanel = new JPanel() {
        	 protected void paintComponent(Graphics g) {
                 super.paintComponent(g);

                 // Draw Background Image
                 g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);

                 // Add Gradient Overlay
                 Graphics2D g2d = (Graphics2D) g;
                 GradientPaint gp = new GradientPaint(
                         0, 0, new Color(0, 0, 0, 150),   // Top (dark transparent)
                         0, getHeight(), new Color(0, 0, 0, 200) // Bottom (more dark)
                 );

                 g2d.setPaint(gp);
                 g2d.fillRect(0, 0, getWidth(), getHeight());
             }
        };

        backgroundPanel.setLayout(new GridLayout(4, 1));

        // Title
        JLabel title = new JLabel("BUS TICKET BOOKING SYSTEM", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setForeground(Color.WHITE);

        // Subtitle
        JLabel subtitle = new JLabel("Fast • Secure • Easy Travel", JLabel.CENTER);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 18));
        subtitle.setForeground(Color.WHITE);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);

        JButton startBtn = new JButton("Start");
        JButton exitBtn = new JButton("Exit");

        startBtn.setPreferredSize(new Dimension(120, 40));
        exitBtn.setPreferredSize(new Dimension(120, 40));

        buttonPanel.add(startBtn);
        buttonPanel.add(exitBtn);

        backgroundPanel.add(new JLabel()); // empty space
        backgroundPanel.add(title);
        backgroundPanel.add(subtitle);
        backgroundPanel.add(buttonPanel);

        add(backgroundPanel);

        // Button Actions
        startBtn.addActionListener(e -> {
            new LoginFrame();
            dispose();
        });

        exitBtn.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGUI());
    }
}
