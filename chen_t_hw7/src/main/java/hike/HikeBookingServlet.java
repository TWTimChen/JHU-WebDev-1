package hike;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import edu.jhu.en605681.*;


@WebServlet("/HikeBookingServlet")
public class HikeBookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private String durationStr, outMessage;
    private int hikeTypeVal, year, month, day;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Process user input and calculate cost
        String hikeTypeStr = request.getParameter("hikeType");
        String yearStr = request.getParameter("year");
        String monthStr = request.getParameter("month");
        String dayStr = request.getParameter("day");
        durationStr = request.getParameter("duration");
        String submitButton = request.getParameter("submit");
        
        // Check if any of the parameters are null
        if (submitButton != null) {
	        try {
	            // Convert input values to appropriate data types
	            hikeTypeVal = Integer.parseInt(hikeTypeStr);
	            year = Integer.parseInt(yearStr);
	            month = Integer.parseInt(monthStr);
	            day = Integer.parseInt(dayStr);
	            
	            int duration = Integer.parseInt(durationStr);
	            
	            if (hikeTypeStr == null || yearStr == null || monthStr == null || dayStr == null || durationStr == null) {
	            	throw new IllegalArgumentException("One or more required parameters are missing.");
	            }
	
	            // Create BookingDay object and validate it
	            BookingDay bookingDay = new BookingDay(year, month, day);
	            if (!bookingDay.isValidDate()) {
	                throw new IllegalArgumentException("Invalid date.");
	            }
	
	            // Check if the date is before today
	            BookingDay today = new BookingDay(
	                Calendar.getInstance().get(Calendar.YEAR),
	                Calendar.getInstance().get(Calendar.MONTH),
	                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
	            );
	
	            // Check if the date is before today
	            if (bookingDay.before(today)) {
	                throw new IllegalArgumentException("The date must be today or later.");
	            }
	
	            // Calculate the cost based on user input
	            HikeType selectedHike = HikeType.values()[hikeTypeVal];
	            Rates rate = new Rates(selectedHike);
	            int[] durations = rate.getDurations();
	
	            // Check if duration is valid for the selected hike
	            boolean validDuration = false;
	            for (int i = 0; i < durations.length; i++) {
	                if (duration == durations[i]) {
	                    validDuration = true;
	                    break;
	                }
	            }
	
	            if (!validDuration) {
	                String validDurationStr = "";
	                for (int i = 0; i < durations.length; i++) {
	                    validDurationStr += durations[i];
	                    if (i < durations.length - 1) {
	                        validDurationStr += ", ";
	                    }
	                }
	                throw new IllegalArgumentException("Invalid duration for the selected hike. Valid durations for this hike are " + validDurationStr + " days.");
	            }
	            
	            rate.setBeginDate(bookingDay);
	            rate.setDuration(duration);
	
	            if (!rate.isValidDates()) {
	                String validDateStr = "";
	                for (String s : rate.getDetails()) {
	                    validDateStr += s + "<br>";
	                }
	                throw new IllegalArgumentException("Invalid date range for the selected hike. <br>" + validDateStr);
	            }
	
	            // Calculate the cost
	            double totalCost = rate.getCost();
	
	            // Generate HTML response based on the result
	            outMessage = "<p>Results:</p>";
	            outMessage += "<p>Hike Type: " + selectedHike.toString() + "</p>";
	            outMessage += "<p>Begin Date: " + bookingDay.toString() + "</p>";
	            outMessage += "<p>Duration: " + duration + " days</p>";
	            outMessage += "<p>Total Cost: $" + totalCost + "</p>";
	
	        } catch (NumberFormatException e) {
	        	outMessage = "<br><p><strong>Warning:</strong><br>" + "Invalid interger " + e.getMessage() + "</p>";
	        } catch (IllegalArgumentException e) {
	            // Handle invalid input errors
	        	outMessage = "<br><p><strong>Warning:</strong><br>" + e.getMessage() + "</p>";
	        }
	        
        }
        
        // HTML form for user input
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head><title>Hike Booking</title></head>");
        out.println("<body>");
        out.println("<h1>Hike Booking</h1>");
        out.println("<form method='post' action='HikeBookingServlet'>");
        
        // Create a table for layout
        out.println("<table>");
        out.println("<tr>");
        out.println("<td>Select a Hike:</td>");
        out.println("<td><select name='hikeType'>");
        // Iterate through HikeType enum and create options
        for (HikeType hikeType : HikeType.values()) {
        	int ordinalValue = hikeType.ordinal();
            String selectedAttribute = (ordinalValue == hikeTypeVal) ? "selected" : "";
            out.println("<option value='" + ordinalValue + "' " + selectedAttribute + ">" + hikeType.toString() + "</option>");
        }
        out.println("</select></td>");
        out.println("</tr>");
        
        // Year Dropdown
        out.println("<tr>");
        out.println("<td>Select a Year:</td>");
        out.println("<td><select name='year'>");
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int year = currentYear; year <= 2050; year++) {
            String selectedAttribute = (year == this.year) ? "selected" : "";
            out.println("<option value='" + year + "' " + selectedAttribute + ">" + year + "</option>");
        }
        out.println("</select></td>");
        out.println("</tr>");

        // Month Dropdown
        out.println("<tr>");
        out.println("<td>Select a Month:</td>");
        out.println("<td><select name='month'>");
        for (int month = 1; month <= 12; month++) {
            String selectedAttribute = (month == this.month) ? "selected" : "";
            out.println("<option value='" + month + "' " + selectedAttribute + ">" + month + "</option>");
        }
        out.println("</select></td>");
        out.println("</tr>");

        // Day Dropdown
        out.println("<tr>");
        out.println("<td>Select a Day:</td>");
        out.println("<td><select name='day'>");
        for (int day = 1; day <= 31; day++) {
            String selectedAttribute = (day == this.day) ? "selected" : "";
            out.println("<option value='" + day + "' " + selectedAttribute + ">" + day + "</option>");
        }
        out.println("</select></td>");
        out.println("</tr>");

        out.println("<tr>");
        out.println("<td>Duration (days):</td>");
        if (this.durationStr != null) {
        	out.println("<td><input type='text' name='duration' value='" + this.durationStr + "'></td>");
        } else {
        	out.println("<td><input type='text' name='duration' ></td>");
        }
        out.println("</tr>");
        out.println("</table>");

        out.println("<input type='submit' name='submit' value='Book Hike'>");
        out.println("</form>");
        
        if (outMessage != null) {
        	out.println(outMessage);
        }

        out.println("</body>");
        out.println("</html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}

