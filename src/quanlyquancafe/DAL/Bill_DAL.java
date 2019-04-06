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
import quanlyquancafe.BLL.Bill;

/**
 *
 * @author ACER
 */
public class Bill_DAL {
    public static ArrayList<Bill> getListBill() throws SQLException, ClassNotFoundException{
        ArrayList<Bill> arr = new ArrayList<Bill>();
        Connection conn =  ConnUtils.getConnection();
        Statement cmd = conn.createStatement();
        String sql = "select * from bill";
        ResultSet rs = cmd.executeQuery(sql);
        while(rs.next()){
            Bill bill = new Bill();
            bill.setId(rs.getInt("id"));
            bill.setDateCheck(rs.getDate("dateCheck"));
            bill.setIdTable(rs.getInt("idTable"));
            bill.setStatus(rs.getBoolean("status"));
            bill.setDiscount(rs.getFloat("discount"));
            arr.add(bill);
        }
        cmd.close();
        conn.close();
        return arr;
    }
    public static void insertBill(int idTable) throws SQLException, ClassNotFoundException{
        Connection conn =  ConnUtils.getConnection();
        String sql = "{call USP_InsertBill(?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setInt(1, idTable);
        cmd.execute();
        cmd.close();
        conn.close();
    }
    public static void deleteBillByID(int id) throws SQLException, ClassNotFoundException{
        Connection conn =  ConnUtils.getConnection();
        Statement cmd = conn.createStatement();
        String sql = "delete from Bill where id = "+id;
        int r = cmd.executeUpdate(sql);
        cmd.close();
        conn.close();
    }
    public static void updateBill(Bill bill) throws SQLException, ClassNotFoundException{
        Connection conn =  ConnUtils.getConnection();
        Statement cmd = conn.createStatement();
        String sql = "update Bill set dateCheck = '"+bill.getDateCheck()+
                "', idTable = "+bill.getIdTable()+", status = '"+bill.isStatus()+
                "', discount = "+bill.getDiscount()+" where id = "+bill.getId();
        int r = cmd.executeUpdate(sql);
        cmd.close();
        conn.close();
    }
}
