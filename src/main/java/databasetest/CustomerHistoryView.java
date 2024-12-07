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
        
        String username = "Rehan Rich";
        
        String query = "SELECT " +
                            "t.TicketID, " +
                            "t.BookingDate, " +
                            "t.BookingStatus, " +
                            "t.TotalPrice, " +
                            "m.Title AS MovieName, " +
                            "c.Name AS CustomerName, " +
                            "s.Name AS StaffName, " +
                            "t.BookingType " +
                        "FROM Ticket t " +
                        "LEFT JOIN TicketSeat ts ON t.TicketID = ts.TicketID " +
                        "LEFT JOIN SeatSchedule ss ON ts.ScheduleSeatID = ss.ScheduleSeatID " +
                        "LEFT JOIN Schedule sch ON ss.ScheduleID = sch.ScheduleID " +
                        "LEFT JOIN Movie m ON sch.MovieID = m.MovieID " +
                        "LEFT JOIN [User] c ON t.CustomerID = c.UserID " +
                        "LEFT JOIN [User] s ON t.StaffID = s.UserID " +
                        "WHERE c.Name = '" + username + "';";

        
        header();
        
        try {
            Statement pstm = jdbc.getConn().createStatement();
            ResultSet result = pstm.executeQuery(query);
            
            while (result.next()) {
                String bookingDate = result.getString("BookingDate");
                String bookingType = result.getString("BookingType");
                double totalPrice = result.getDouble("TotalPrice");
                String movieName = result.getString("MovieName");
                String bookingStatus = result.getString("BookingStatus");
                System.out.printf("%-25s %-15s %-20.2f %-30s %-15s\n", bookingDate, bookingType, totalPrice, movieName, bookingStatus);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }   
    }
}

