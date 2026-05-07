package gui.admin;

import services.AdminService;

import javax.swing.*;
import java.awt.*;

public class AddScheduleFrame extends JFrame {

    private JTextField busIdField, routeIdField, departureField,
            arrivalField, dateField, priceField;

    private AdminService adminService;

    public AddScheduleFrame() {

        adminService = new AdminService();

        setTitle("Add Schedule");
        setSize(450, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.add(new JLabel("Bus ID:"));
        busIdField = new JTextField();
        panel.add(busIdField);

        panel.add(new JLabel("Route ID:"));
        routeIdField = new JTextField();
        panel.add(routeIdField);

        panel.add(new JLabel("Departure (HH:MM:SS):"));
        departureField = new JTextField();
        panel.add(departureField);

        panel.add(new JLabel("Arrival (HH:MM:SS):"));
        arrivalField = new JTextField();
        panel.add(arrivalField);

        panel.add(new JLabel("Travel Date (yyyy-MM-dd):"));
        dateField = new JTextField();
        panel.add(dateField);

        panel.add(new JLabel("Price:"));
        priceField = new JTextField();
        panel.add(priceField);

        JButton addBtn = new JButton("Add Schedule");
        panel.add(addBtn);

        add(panel);

        addBtn.addActionListener(e -> addSchedule());

        setVisible(true);
    }

    private void addSchedule() {

        String busIdText = busIdField.getText().trim();
        String routeIdText = routeIdField.getText().trim();
        String departure = departureField.getText().trim();
        String arrival = arrivalField.getText().trim();
        String date = dateField.getText().trim();
        String priceText = priceField.getText().trim();

        // Check empty fields
        if (busIdText.isEmpty() || routeIdText.isEmpty() ||
            departure.isEmpty() || arrival.isEmpty() ||
            date.isEmpty() || priceText.isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "All fields are required!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int busId, routeId;
        double price;

        try {
            busId = Integer.parseInt(busIdText);
            routeId = Integer.parseInt(routeIdText);
            price = Double.parseDouble(priceText);

            if (busId <= 0 || routeId <= 0) {
                JOptionPane.showMessageDialog(this,
                        "Bus ID and Route ID must be greater than 0!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (price <= 0) {
                JOptionPane.showMessageDialog(this,
                        "Price must be greater than 0!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Bus ID, Route ID must be numbers and Price must be valid!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean success = adminService.addSchedule(
                busId, routeId, departure, arrival, date, price);

        if (success) {
            JOptionPane.showMessageDialog(this, "Schedule Added Successfully!");

            // Clear fields after success
            busIdField.setText("");
            routeIdField.setText("");
            departureField.setText("");
            arrivalField.setText("");
            dateField.setText("");
            priceField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to Add Schedule!");
        }
    }

}

