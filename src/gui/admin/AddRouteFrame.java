package gui.admin;

import services.AdminService;

import javax.swing.*;
import java.awt.*;

public class AddRouteFrame extends JFrame {

    private JTextField sourceField, destinationField, distanceField;
    private AdminService adminService;

    public AddRouteFrame() {

        adminService = new AdminService();

        setTitle("Add Route");
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.add(new JLabel("Source:"));
        sourceField = new JTextField();
        panel.add(sourceField);

        panel.add(new JLabel("Destination:"));
        destinationField = new JTextField();
        panel.add(destinationField);

        panel.add(new JLabel("Distance:"));
        distanceField = new JTextField();
        panel.add(distanceField);

        JButton addBtn = new JButton("Add Route");
        panel.add(addBtn);

        add(panel);

        addBtn.addActionListener(e -> addRoute());

        setVisible(true);
    }

    private void addRoute() {

        String source = sourceField.getText().trim();
        String destination = destinationField.getText().trim();
        String distanceText = distanceField.getText().trim();

        // Check empty fields
        if (source.isEmpty() || destination.isEmpty() || distanceText.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "All fields are required!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int distance;

        // Validate number
        try {
            distance = Integer.parseInt(distanceText);

            if (distance <= 0) {
                JOptionPane.showMessageDialog(this,
                        "Distance must be greater than 0!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Distance must be a valid number!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean success = adminService.addRoute(source, destination, distance);

        if (success) {
            JOptionPane.showMessageDialog(this, "Route Added Successfully!");
            sourceField.setText("");
            destinationField.setText("");
            distanceField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to Add Route!");
        }
    }

}

