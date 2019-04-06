/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlyquancafe.DAL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author ACER
 */
public class ConnUtils {
    public static Connection getConnection() throws SQLException, ClassNotFoundException{
        String hostName = "localhost";
        String sqlInstanceName = ".\\SQLEXPRESS";
        String database = "Quanliquancafe";
        String username = "meo9xit";
        String password ="123";
        return getSQLServerConnection(hostName,sqlInstanceName,database,username,password);
    }
    public static Connection getSQLServerConnection(String hostName,
         String sqlInstanceName, String database, String userName,
         String password) throws ClassNotFoundException, SQLException {


     Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
 

     String connectionURL = "jdbc:sqlserver://" + hostName + ":1433"
             + ";instance=" + sqlInstanceName + ";databaseName=" + database;
 
     Connection conn = DriverManager.getConnection(connectionURL, userName,
             password);
     return conn;
    }
}
