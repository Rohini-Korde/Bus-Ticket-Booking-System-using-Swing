package gui.user;

import model.Schedule;
import model.User;
import services.UserService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SearchBusFrame extends JFrame {

    private JTextField sourceField, destinationField, dateField;
    private JTable table;
    private UserService userService;
    private User user;

    public SearchBusFrame(User user) {

        this.user = user;
        userService = new UserService();

        setTitle("Search Bus");
        setSize(700, 400);
        setLocationRelativeTo(null);

        JPanel topPanel = new JPanel(new GridLayout(2, 4, 20, 20));
        topPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        sourceField = new JTextField();
        destinationField = new JTextField();
        dateField = new JTextField();

        JButton searchBtn = new JButton("Search");
        topPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        topPanel.add(new JLabel("Source:"));
        topPanel.add(sourceField);
        topPanel.add(new JLabel("Destination:"));
        topPanel.add(destinationField);
        topPanel.add(new JLabel("Date (yyyy-MM-dd):"));
        topPanel.add(dateField);
        topPanel.add(new JLabel(""));
        topPanel.add(searchBtn);

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);

        JButton bookBtn = new JButton("Book Selected");
        
     // Create panel for button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 15)); 
        // CENTER alignment, 15px vertical padding

        bottomPanel.add(bookBtn);

        // Optional: Add padding around panel
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        searchBtn.addActionListener(e -> search());
        bookBtn.addActionListener(e -> bookSelected());

        setVisible(true);
    }

    private void search() {

        List<Schedule> schedules = userService.searchSchedule(
                sourceField.getText(),
                destinationField.getText(),
                dateField.getText()
        );

        if (schedules == null || schedules.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No schedules found!");
            return;
        }

        String[] columns = {"Schedule ID", "Bus ID", "Departure", "Arrival", "Price"};

        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (Schedule s : schedules) {
            model.addRow(new Object[]{
                    s.getScheduleId(),
                    s.getBusId(),
                    s.getDepartureTime(),
                    s.getArrivalTime(),
                    s.getPrice()
            });
        }

        table.setModel(model);
    }

    private void bookSelected() {

        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a schedule first!");
            return;
        }

        int scheduleId = (int) table.getValueAt(selectedRow, 0);

        new BookingFrame(user, scheduleId);
    }
}
