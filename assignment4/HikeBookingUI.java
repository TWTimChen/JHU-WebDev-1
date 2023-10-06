package assignment4;

import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.io.*;
import java.net.Socket;
import java.util.Calendar;

import edu.jhu.en605681.*;

public class HikeBookingUI {

    private JFrame frame;
    private JComboBox<HikeType> hikesComboBox;
    private JComboBox<Integer> durationComboBox;
    private JTextField dayField, monthField, yearField;
    private JButton bookButton;
    private BookingDay date;
    // for socket connection
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public HikeBookingUI() {
        // Create and configure frame, using GridBagLayout
        frame = new JFrame("Hike Booking System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;

        // Hike selection dropdown
        gbc.gridy = 0;
        hikesComboBox = new JComboBox<>(HikeType.values());
        hikesComboBox.setSelectedIndex(-1);  // No selection by default
        JPanel hikePanel = new JPanel();
        hikePanel.add(new JLabel("Select a Hike:"));
        hikePanel.add(hikesComboBox);
        frame.add(hikePanel, gbc);

        // Date input
        gbc.gridy = 1;
        JPanel datePanel = new JPanel();
        datePanel.add(new JLabel("Booking Date:"));
        monthField = new JTextField(2);
        dayField = new JTextField(2);
        yearField = new JTextField(4);
        monthField.setToolTipText("MM");
        dayField.setToolTipText("DD");
        yearField.setToolTipText("YYYY");
        datePanel.add(monthField);
        datePanel.add(new JLabel("/"));
        datePanel.add(dayField);
        datePanel.add(new JLabel("/"));
        datePanel.add(yearField);
        frame.add(datePanel, gbc);

        // Duration dropdown
        gbc.gridy = 2;
        durationComboBox = new JComboBox<>();
        JPanel DurationPanel = new JPanel();
        DurationPanel.add(new JLabel("Select Duration (days):"));
        DurationPanel.add(durationComboBox);
        frame.add(DurationPanel, gbc);

        // Book button
        gbc.gridy = 3;
        bookButton = new JButton("Book Hike");
        frame.add(bookButton, gbc);

        // Update durations based on selected hike
        hikesComboBox.addItemListener(e -> updateDurationOptions((HikeType) hikesComboBox.getSelectedItem()));

        // Calculate total cost on button click
        bookButton.addActionListener(this::calculateCostAction);

        // Display frame
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void updateDurationOptions(HikeType selectedHike) {
        // Clear duration options
        durationComboBox.removeAllItems();

        // Add new duration options based on selected hike
        Rates rate = new Rates(selectedHike);
        int[] durations = rate.getDurations();
        for (int i = 0; i < durations.length; i++) {
            durationComboBox.addItem(durations[i]);
        }
    }

    private boolean validateDate() {
        try {
            // Parse date from input fields
            int day = Integer.parseInt(dayField.getText());
            int month = Integer.parseInt(monthField.getText());
            int year = Integer.parseInt(yearField.getText());
            this.date = new BookingDay(year, month, day);
            
            // Validate date for basic formating and range errors
            if (!date.isValidDate()) {
                throw new IllegalArgumentException(date.getValidation());
            }

            // Check if the date is before today
            BookingDay today = new BookingDay(
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH) + 1,  // Calendar months are 0-based
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            );

            if (date.before(today)) {
                throw new IllegalArgumentException("The date must be today or later.");
            }

            return true;
        
        // Catch errors for invalid input due to non-numeric characters
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame,"Invalid input for date.", "Input Error", JOptionPane.ERROR_MESSAGE);
        
        // Catch errors for invalid input due to out-of-range values
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
            String errorMessage = String.format(
                "Valid years for this hike are between current year and %d and valid months are between 1 and 12",
                BookingDay.DEFAULT_MAX_YEAR
            );
            JOptionPane.showMessageDialog(frame, errorMessage, "Input Error", JOptionPane.INFORMATION_MESSAGE);
        }
        return false;
    }

    private void handleServerResponse(String response) {
        // Check for invalid response
        String[] parts = response.split("#", 2);
        if (parts.length != 2) {
            JOptionPane.showMessageDialog(frame, "Invalid response from server.", "Server Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        double totalCost = Double.parseDouble(parts[0]);
        // Check for invalid date range
        if (totalCost == -0.01) {
            JOptionPane.showMessageDialog(frame, parts[1], "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        // Display total cost
        String message = parts[1];
        JOptionPane.showMessageDialog(frame, message + " Total Cost: $" + totalCost, "Quote Result", JOptionPane.INFORMATION_MESSAGE);
    }

    private void calculateCostAction(ActionEvent e) {
        // Validate hike and duration selections
        if (hikesComboBox.getSelectedItem() == null || durationComboBox.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(frame, "Please select both a hike and a duration.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        HikeType hike = (HikeType) hikesComboBox.getSelectedItem();
        int duration = (int) durationComboBox.getSelectedItem();

        // Validate date for basic formating and range errors
        if (!validateDate()) {
            return;
        }

        // Initialize socket and streams
        try {
            socket = new Socket("web6.jhuep.com", 20025);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error connecting to server.", "Connection Error", JOptionPane.ERROR_MESSAGE);
        }

        // Prepare data for the server
        //hike_id#begin_year#begin_month#begin_day#duration
        String hikeData = hike.ordinal() + "#" +
                          date.getYear() + "#" +
                          date.getMonth() + "#" +
                          date.getDayOfMonth() + "#" +
                          duration;

        try {
            // Send data to server
            out.println(hikeData);

            // Check for responses from the server
            String response = in.readLine();
            if (response != null) {
                handleServerResponse(response);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            // Handle errors
        } finally {
            // Close the socket and streams
            try {
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                // Handle errors while closing the socket
            }
        }

        /* Old code for calculating cost locally
        // Get response from server

        Rates rate = new Rates(hike);
        rate.setBeginDate(date);
        rate.setDuration(duration);

        // Validate dates for hike-specific errors
        if (!rate.isValidDates()) {
            for (String s : rate.getDetails()) {
                JOptionPane.showMessageDialog(frame, s, "Input Error", JOptionPane.ERROR_MESSAGE);
            }

            String errorMessage = String.format(
                "Valid dates for this hike are between %d/%d and %d/%d.",
                rate.getSeasonStartMonth(),
                rate.getSeasonStartDay(),
                rate.getSeasonEndMonth(),
                rate.getSeasonEndDay()
            );

            JOptionPane.showMessageDialog(frame, errorMessage, "Input Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Calculate and display total cost
        double totalCost = rate.getCost();
        JOptionPane.showMessageDialog(frame, "Total Cost: $" + totalCost, "Total Cost", JOptionPane.INFORMATION_MESSAGE);
        */
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HikeBookingUI());
    }
}

