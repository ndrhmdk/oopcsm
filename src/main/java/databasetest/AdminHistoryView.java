package databasetest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author ADMIN
 */
public class AdminHistoryView {
    public static void header() {
        System.out.printf("%-10s %-25s %-15s %-20s %-30s %-20s %-20s %-10s\n", 
                  "TicketID", "BookingDate", "BookingStatus", "TotalPrice", "Movie Title", "Customer", "Staff", "BookingType");
    }
    
    public static void main(String[] args) {
    JDBC db = new JDBC();
    String query = "SELECT t.TicketID, t.BookingDate, t.BookingStatus, t.TotalPrice, " +
                   "m.Title AS MovieName, c.Name AS CustomerName, s.Name AS StaffName, t.BookingType " +
                   "FROM [Ticket] t " +
                   "LEFT JOIN [TicketSeat] ts ON t.TicketID = ts.TicketID " +
                   "LEFT JOIN [SeatSchedule] ss ON ts.ScheduleSeatID = ss.ScheduleSeatID " +
                   "LEFT JOIN [Schedule] sch ON ss.ScheduleID = sch.ScheduleID " +
                   "LEFT JOIN [Movie] m ON sch.MovieID = m.MovieID " +
                   "LEFT JOIN [Customer] c ON t.CustomerID = c.CustomerID " +
                   "LEFT JOIN [Staff] s ON t.StaffID = s.StaffID " +
                   "WHERE c.Name = 'Bertha Mckee'";
    
    header();
    
    try {
        Statement pstm = db.getConn().createStatement();
        ResultSet result = pstm.executeQuery(query);
        
        while (result.next()) {
            int ticketID = result.getInt("TicketID"); 
            String bookingDate = result.getString("BookingDate"); 
            String bookingStatus = result.getString("BookingStatus"); 
            double totalPrice = result.getDouble("TotalPrice"); 
            String movieName = result.getString("MovieName");
            String customerName = result.getString("CustomerName"); 
            String staffName = result.getString("StaffName"); 
            if (result.wasNull()) {
                staffName = "";
            }
            String bookingType = result.getString("BookingType"); 

            System.out.printf("%-10d %-25s %-15s %-20.2f %-30s %-20s %-20s %-10s\n", 
                    ticketID, bookingDate, bookingStatus, totalPrice, movieName, customerName, staffName, bookingType);

        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}

}
