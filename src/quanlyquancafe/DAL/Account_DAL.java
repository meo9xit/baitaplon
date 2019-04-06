/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlyquancafe.DAL;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import quanlyquancafe.BLL.Account;

/**
 *
 * @author ACER
 */
public class Account_DAL {
    public static Account getAccountByUsername(String username) throws SQLException, ClassNotFoundException{
        Account acc = new Account();
        Connection conn = ConnUtils.getConnection();
        Statement cmd = conn.createStatement();
        String sql = "Select * from Account where username = '"+username+"'";
        ResultSet rs = cmd.executeQuery(sql);
        rs.next();
        try{
        acc.setUsername(username);
        acc.setPassword(rs.getString("pass"));
        acc.setType(rs.getString("type"));
        } catch(Exception ex){
            throw new ClassNotFoundException();
        }
        cmd.close();
        conn.close();
        return acc;
    }
    public static void insertAccount(Account acc) throws SQLException, ClassNotFoundException{
        Connection conn = ConnUtils.getConnection();
        Statement cmd = conn.createStatement();
        String sql = "insert into account(username,pass,type) values ('"+acc.getUsername()+
                "','"+acc.getPassword()+"','"+acc.getType()+"')";
        int row = cmd.executeUpdate(sql);
        cmd.close();
        conn.close();
    }
    public static void deleteAccountByUsername(String username) throws SQLException, ClassNotFoundException{
        Connection conn = ConnUtils.getConnection();
        Statement cmd = conn.createStatement();
        String sql = "delete from account where username = '"+username+"'";
        int row = cmd.executeUpdate(sql);
        cmd.close();
        conn.close();
    }
    public static void updateAccount(Account acc) throws SQLException, ClassNotFoundException{
        Connection conn = ConnUtils.getConnection();
        Statement cmd = conn.createStatement();
        String sql = "update account set "+
                "pass = '"+acc.getPassword()+"', type = '"+acc.getType()+"' "+
                "where username = '"+acc.getUsername()+"'";
        int row = cmd.executeUpdate(sql);
        cmd.close();
        conn.close();
    }
    public static ArrayList<Account> getListAccount() throws SQLException, ClassNotFoundException{
        ArrayList<Account> arr = new ArrayList<Account>();
        Connection conn =  ConnUtils.getConnection();
        Statement cmd = conn.createStatement();
        String sql = "select * from account";
        ResultSet rs = cmd.executeQuery(sql);
        while(rs.next()){
            Account acc = new Account();
            acc.setUsername(rs.getString("username"));
            acc.setPassword(rs.getString("pass"));
            acc.setType(rs.getString("type"));
            arr.add(acc);
        }
        cmd.close();
        conn.close();
        return arr;

    }
}
