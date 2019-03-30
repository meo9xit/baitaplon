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
import quanlyquancafe.BLL.Category;

/**
 *
 * @author ACER
 */
public class Category_DAL {
    public static ArrayList<Category> getListCategory() throws SQLException, ClassNotFoundException{
        ArrayList<Category> arr = new ArrayList<Category>();
        Connection conn =  ConnUtils.getConnection();
        Statement cmd = conn.createStatement();
        String sql = "select * from category";
        ResultSet rs = cmd.executeQuery(sql);
        while(rs.next()){
            Category ctg = new Category();
            ctg.setId(rs.getInt("id"));
            ctg.setName(rs.getString("name"));
            arr.add(ctg);
        }
        cmd.close();
        conn.close();
        return arr;
    }
    public static void insertCategory(Category ctg) throws SQLException, ClassNotFoundException{
        Connection conn =  ConnUtils.getConnection();
        Statement cmd = conn.createStatement();
        String sql = "insert into category(id,name) values ("+ctg.getId()+
                ",'"+ctg.getName()+"')";
        int r = cmd.executeUpdate(sql);
        cmd.close();
        conn.close();
    }
    public static void deleteCategoryByID(int id) throws SQLException, ClassNotFoundException{
        Connection conn =  ConnUtils.getConnection();
        Statement cmd = conn.createStatement();
        String sql = "delete from category where id = "+id;
        int r = cmd.executeUpdate(sql);
        cmd.close();
        conn.close();
    }
    public static void updateCategory(Category ctg) throws SQLException, ClassNotFoundException{
        Connection conn =  ConnUtils.getConnection();
        Statement cmd = conn.createStatement();
        String sql = "update category set name = '"+ctg.getName()+"'"
                + " where id = "+ctg.getId();
        int r = cmd.executeUpdate(sql);
        cmd.close();
        conn.close();
    }
}
