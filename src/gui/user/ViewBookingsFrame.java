package gui.user;

import model.User;
import services.UserService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViewBookingsFrame extends JFrame {

    private JTable table;
    private UserService userService;
    private User user;

    public ViewBookingsFrame(User user) {

        this.user = user;
        userService = new UserService();

        setTitle("My Bookings");
        setSize(700, 400);
        setLocationRelativeTo(null);

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        JButton cancelBtn = new JButton("Cancel Selected Booking");

     // Create panel for button
     JPanel bottomPanel = new JPanel();
     bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 15)); 
     // CENTER alignment, 15px vertical padding

     bottomPanel.add(cancelBtn);

     // Optional: Add padding around panel
     bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

     add(scrollPane, BorderLayout.CENTER);
     add(bottomPanel, BorderLayout.SOUTH);


        loadBookings();

        cancelBtn.addActionListener(e -> cancelBooking());

        setVisible(true);
    }

    private void loadBookings() {

        List<String> bookings = userService.viewMyBookings(user.getUserId());

        if (bookings == null || bookings.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No bookings found!");
            return;
        }

        String[] columns = {"Booking ID", "Schedule ID", "Seat", "Date", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (String b : bookings) {

            // Split string properly (since earlier DAO returned formatted string)
            // Better practice would be returning Booking object
            String[] parts = b.split("\\|");

            Object[] row = new Object[5];

            row[0] = parts[0].replace("Booking ID: ", "").trim();
            row[1] = parts[1].replace("Schedule ID: ", "").trim();
            row[2] = parts[2].replace("Seat: ", "").trim();
            row[3] = parts[3].replace("Date: ", "").trim();
            row[4] = parts[4].replace("Status: ", "").trim();

            model.addRow(row);
        }

        table.setModel(model);
    }

    private void cancelBooking() {

        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a booking first!");
            return;
        }

        int bookingId = Integer.parseInt(
                table.getValueAt(selectedRow, 0).toString()
        );
        
        int scheduleId = Integer.parseInt(
                table.getValueAt(selectedRow, 1).toString()
        ); 
        
        String status = table.getValueAt(selectedRow, 4).toString();

        if (status.equalsIgnoreCase("Cancelled")) {
            JOptionPane.showMessageDialog(this, "Booking already cancelled!");
            return;
        }

        boolean success = userService.cancelBooking(bookingId);
        

        if (success) {
            JOptionPane.showMessageDialog(this, "Booking Cancelled!");
            loadBookings(); // refresh table
           
        } else {
            JOptionPane.showMessageDialog(this, "Cancel Failed!");
        }
    }
    
    
}

