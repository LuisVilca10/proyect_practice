package Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionMySQL {
    private String database_name= "urkupiñamarket";
    private String user = "root";
    private String password = "";
    private String url = "jdbc:mysql://localhost:3306/" + database_name;
    Connection conn =null;
    
    public Connection getConnection() {
        try {
            //obtner valor del driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            System.err.println("A ocurrido un mensaje de error"+e.getMessage());
        }catch(SQLException e){
            System.err.println("a ocurrido un sql exception" + e.getMessage());
        }
        return conn;
    }

}
