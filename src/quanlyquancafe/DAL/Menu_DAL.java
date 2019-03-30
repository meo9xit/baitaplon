/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlyquancafe.DAL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import quanlyquancafe.BLL.Menu;

/**
 *
 * @author ACER
 */
public class Menu_DAL {
    public static ArrayList<Menu> getListMenu() throws SQLException, ClassNotFoundException{
        Connection conn =  ConnUtils.getConnection();
        Statement cmd = conn.createStatement();
        String sql = "select * from menu";
        ArrayList<Menu> arr = new ArrayList<Menu>();
        ResultSet rs = cmd.executeQuery(sql);
        while(rs.next()){
            Menu mn = new Menu();    
            mn.setId(rs.getInt("id"));
            mn.setName(rs.getString("name"));
            mn.setPrice(rs.getInt("price"));
            mn.setCategory(rs.getInt("idcategory"));
            arr.add(mn);
        }
        cmd.close();
        conn.close();
        return arr;
    }
    public static void insertMenu(Menu mn) throws SQLException, ClassNotFoundException{
        Connection conn =  ConnUtils.getConnection();
        Statement cmd = conn.createStatement();
        String sql = "insert into menu(id,name,price,idcategory) values ("
                + mn.getId()+ ",'"+mn.getName()+"',"+mn.getPrice()+","
                + mn.getCategory()+ ")";
        int r = cmd.executeUpdate(sql);
        cmd.close();
        conn.close();
    }
    public static void deleteMenuByID(int id) throws SQLException, ClassNotFoundException{
        Connection conn =  ConnUtils.getConnection();
        Statement cmd = conn.createStatement();
        String sql = "delete from menu where id = "+id;
        int r = cmd.executeUpdate(sql);
        cmd.close();
        conn.close();
    }
    public static void updateMenu(Menu mn) throws SQLException, ClassNotFoundException{
        Connection conn =  ConnUtils.getConnection();
        Statement cmd = conn.createStatement();
        String sql ="update menu set name = '"+mn.getName()+"',"
                + " price = "+mn.getPrice()+", idcategory = "+mn.getCategory()+
                " where id = "+mn.getId();
        int r = cmd.executeUpdate(sql);
        cmd.close();
        conn.close();
    }
}
