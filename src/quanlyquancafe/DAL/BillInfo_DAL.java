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
import quanlyquancafe.BLL.BillInfo;

/**
 *
 * @author ACER
 */
public class BillInfo_DAL {
    public static ArrayList<BillInfo> getListBillInfo() throws SQLException, ClassNotFoundException{
        ArrayList<BillInfo> arr = new ArrayList<BillInfo>();
        Connection conn = ConnUtils.getConnection();
        String sql = "Select * from BillInfo";
        Statement cmd = conn.createStatement();
        ResultSet rs = cmd.executeQuery(sql);
        while(rs.next()){
            BillInfo bi = new BillInfo();
            bi.setBillId(rs.getInt("idBill"));
            bi.setMenuId(rs.getInt("idFood"));
            bi.setCount(rs.getInt("count"));
            arr.add(bi);
        }
        cmd.close();
        conn.close();
        return arr;
    }
    public static void insertBillInfor(BillInfo bi) throws SQLException, ClassNotFoundException{
        Connection conn = ConnUtils.getConnection();
        String sql = "{call USP_InsertBillInfo(?,?,?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setInt(1, bi.getBillId());
        cmd.setInt(2, bi.getMenuId());
        cmd.setInt(3, bi.getCount());
        cmd.execute();
        cmd.close();
        conn.close();
    }
    public static void deleteBillInfoB(BillInfo bi) throws SQLException, ClassNotFoundException{
        Connection conn =  ConnUtils.getConnection();
        Statement cmd = conn.createStatement();
        String sql = "delete from BillInfo where idBill = "+bi.getBillId()+
                " and idFood = "+bi.getMenuId();
        int r = cmd.executeUpdate(sql);
        cmd.close();
        conn.close();
    }
    public static void updateBillInfo(BillInfo bi) throws SQLException, ClassNotFoundException{
        Connection conn =  ConnUtils.getConnection();
        Statement cmd = conn.createStatement();
        String sql = "update BillInfo set count = "+bi.getCount()+""
                + " where idBill = "+bi.getBillId()+" and idFood = "+bi.getMenuId();
        int r = cmd.executeUpdate(sql);
        cmd.close();
        conn.close();
    }
    
    public static ArrayList<BillInfo> getListBillInfoByTableID(int tableID) throws SQLException, ClassNotFoundException{
        ArrayList<BillInfo> arr = new ArrayList<>();
        Connection conn = ConnUtils.getConnection();
        Statement cmd = conn.createStatement();
        String sql = "Select billinfo.idbill, billinfo.idfood, billinfo.count from "
                + "billinfo, bill where billinfo.idbill = bill.id and bill.status = 'false' and bill.idtable = "+tableID;
        ResultSet rs = cmd.executeQuery(sql);
        while(rs.next()){
            BillInfo bi = new BillInfo();
            bi.setBillId(rs.getInt("idBill"));
            bi.setMenuId(rs.getInt("idFood"));
            bi.setCount(rs.getInt("count"));
            arr.add(bi);
        }
        cmd.close();
        conn.close();
        return arr;
    }
}
