package gui.user;

import model.TicketDetails;
import model.User;
import services.UserService;
import util.TicketGenerator;

import javax.swing.*;
import java.util.List;

import dao.BookingDAO;
import dao.BusDAO;
import dao.ScheduleDAO;

import java.awt.*;

public class BookingFrame extends JFrame {

    private UserService userService;
    private User user;
    private int scheduleId;

    public BookingFrame(User user, int scheduleId) {

        this.user = user;
        this.scheduleId = scheduleId;

        userService = new UserService();
        BusDAO busDAO = new BusDAO();
        ScheduleDAO scheduleDAO = new ScheduleDAO();

        // Get busId from schedule
        int busId = scheduleDAO.getBusIdByScheduleId(scheduleId);

        // Get total seats from bus
        int totalSeats = busDAO.getTotalSeatsByBusId(busId);

        setTitle("Seat Selection");
        setSize(700, 400);
        setLocationRelativeTo(null);

        // Calculate rows dynamically (4 seats per row)
        int columns = 4;
        int rows = (int) Math.ceil((double) totalSeats / columns);

        JPanel panel = new JPanel(new GridLayout(rows, columns, 10, 10));

        List<Integer> bookedSeats = userService.getBookedSeats(scheduleId);

        for (int i = 1; i <= totalSeats; i++) {

            JButton seatBtn = new JButton(String.valueOf(i));
            int seatNumber = i;

            if (bookedSeats.contains(seatNumber)) {
                seatBtn.setEnabled(false);
                seatBtn.setBackground(Color.RED);
            } else {
                seatBtn.setBackground(Color.GREEN);

                seatBtn.addActionListener(e -> {
                	int bookingId = userService.bookTicket(
                	        user.getUserId(),
                	        scheduleId,
                	        seatNumber
                	);

                	if (bookingId != -1) {

                	    seatBtn.setEnabled(false);
                	    seatBtn.setBackground(Color.RED);

                	    JOptionPane.showMessageDialog(this,
                	            "Seat Booked! Booking ID: " + bookingId);

                	    // 🔥 Fetch ticket details
                	    TicketDetails details =
                	            new BookingDAO().getTicketDetails(bookingId);

                	    if (details != null) {
                	        TicketGenerator.generateTicket(
                	                user.getUserId(),
                	                bookingId,
                	                details
                	        );
                	    }

                	} else {
                	    JOptionPane.showMessageDialog(this, "Booking Failed!");
                	}

                });
            }

            panel.add(seatBtn);
        }

        add(new JScrollPane(panel));
        setVisible(true);
    }


//    private void bookSeat(int seatNumber) {
//
//        boolean success = userService.bookTicket(
//                user.getUserId(),
//                scheduleId,
//                seatNumber
//        );
//
//        if (success)
//            JOptionPane.showMessageDialog(this, "Seat Booked Successfully!");
//        else
//            JOptionPane.showMessageDialog(this, "Booking Failed!");
//    }
}

