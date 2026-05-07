package gui.admin;


import services.AdminService;

import javax.swing.*;
import java.awt.*;

public class AddBusFrame extends JFrame {

    private JTextField nameField, typeField, seatsField;
    private AdminService adminService;

    public AddBusFrame() {

        adminService = new AdminService();

        setTitle("Add Bus");
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        panel.add(new JLabel("Bus Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Bus Type:"));
        typeField = new JTextField();
        panel.add(typeField);

        panel.add(new JLabel("Total Seats:"));
        seatsField = new JTextField();
        panel.add(seatsField);

        JButton addBtn = new JButton("Add Bus");
        panel.add(addBtn);

        add(panel);

        addBtn.addActionListener(e -> addBus());

        setVisible(true);
    }

    private void addBus() {

        String name = nameField.getText().trim();
        String type = typeField.getText().trim();
        String seatsText = seatsField.getText().trim();

        // Check if any field is empty
        if (name.isEmpty() || type.isEmpty() || seatsText.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                    "All fields are required!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int seats;

        // Check if seats is a valid number
        try {
            seats = Integer.parseInt(seatsText);

            if (seats <= 0) {
                JOptionPane.showMessageDialog(this,
                        "Seats must be greater than 0!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Seats must be a valid number!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean success = adminService.addBus(name, type, seats);

        if (success) {
            JOptionPane.showMessageDialog(this, "Bus Added Successfully!");
            nameField.setText("");
            typeField.setText("");
            seatsField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to Add Bus!");
        }
    }

}

