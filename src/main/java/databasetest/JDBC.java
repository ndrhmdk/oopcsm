package databasetest;

import java.sql.Connection;
import java.sql.DriverManager;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC {
    static final String url = "jdbc:sqlserver://localhost:1433;trustServerCertificate=true;databaseName=dbCMSTest";
    static final String user = "sa";
    static final String password = "andr";
    
    private Connection conn;
    private Statement statement;
//    private PreparedStatement pstm;
    
    public JDBC() {
        try {
            conn = DriverManager.getConnection(url, user, password);
            statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public Connection getConn() {
        return conn;
    }
    
    public Statement getStatement() {
        return statement;
    }
    
    public void close() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
