package databasetest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerHistoryView {
    public static void header() {
        System.out.printf("%-25s %-15s %-20s %-30s %-10s\n", 
                  "BookingDate", "BookingStatus", "TotalPrice", "MovieTitle", "BookingType");
    }
    
    public static void main(String[] args) {
        JDBC jdbc = new JDBC();
        
        String customerName = "Rehan Rich";
        
        String query =  "SELECT t.BookingDate, t.BookingType, t.TotalPrice, m.Title AS MovieTitle, t.BookingStatus " +
                        "FROM Ticket t " +
                        "JOIN TicketSeat ts ON t.TicketID = ts.TicketID " +
                        "JOIN SeatSchedule ss ON ts.ScheduleSeatID = ss.ScheduleSeatID " +
                        "JOIN Schedule s ON ss.ScheduleID = s.ScheduleID " +
                        "JOIN Movie m ON s.MovieID = m.MovieID " +
                        "JOIN Customer c ON t.CustomerID = c.CustomerID " +
                        "WHERE c.Name = '" + customerName + "'";
        
        header();
        
        try {
            Statement pstm = jdbc.getConn().createStatement();
            ResultSet result = pstm.executeQuery(query);
            
            while (result.next()) {
                String bookingDate = result.getString("BookingDate");
                String bookingType = result.getString("BookingType");
                double totalPrice = result.getDouble("TotalPrice");
                String movieName = result.getString("MovieTitle");
                String bookingStatus = result.getString("BookingStatus");
                System.out.printf("%-25s %-15s %-20.2f %-30s %-15s\n", bookingDate, bookingType, totalPrice, movieName, bookingStatus);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }   
    }
}

