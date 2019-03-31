/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlyquancafe.DAL;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import quanlyquancafe.BLL.Table;

/**
 *
 * @author ACER
 */
public class Table_DAL {
    public static ArrayList<Table> getListTable() throws SQLException, ClassNotFoundException{
         ArrayList<Table> arr = new ArrayList<Table>();
        Connection conn =  ConnUtils.getConnection();
        Statement cmd = conn.createStatement();
        String sql = "select * from ServeTable";
        ResultSet rs = cmd.executeQuery(sql);
        while(rs.next()){
            Table tb = new Table();
            tb.setID(rs.getInt("id"));
            tb.setTableName(rs.getString("name"));
            tb.setStatus(rs.getBoolean("status"));
            arr.add(tb);
        }
        cmd.close();
        conn.close();
        return arr;
    }
    public static void insertTable() throws SQLException, ClassNotFoundException{
        Connection conn = ConnUtils.getConnection();
        String sql = "{call USP_InsertTable()}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.execute();
        cmd.close();
        conn.close();
    }
    public static void deleteTableById(int id) throws SQLException, ClassNotFoundException{
        Connection conn = ConnUtils.getConnection();
        String sql = "{call USP_DeleteTable(?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setInt(1, id);
        cmd.execute();
        cmd.close();
        conn.close();
    }
    public static void updateTable(Table tb) throws SQLException, ClassNotFoundException{
        Connection conn = ConnUtils.getConnection();
        Statement cmd = conn.createStatement();
        String sql = "update ServeTable set name = '"+tb.getTableName()+
                "', status = '"+tb.isStatus()+"' where id = "+tb.getID();
        int row = cmd.executeUpdate(sql);
        cmd.close();
        conn.close();
    }
}
