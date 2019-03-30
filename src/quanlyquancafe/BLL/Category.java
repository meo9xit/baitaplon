/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlyquancafe.BLL;

import java.sql.SQLException;
import java.util.ArrayList;
import quanlyquancafe.DAL.Category_DAL;

/**
 *
 * @author ACER
 */
public class Category {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public static ArrayList<Category> getListCategory() throws SQLException, ClassNotFoundException{
        return Category_DAL.getListCategory();
    }
    
    public void insertCategory() throws SQLException, ClassNotFoundException{
        Category_DAL.insertCategory(this);
    }
    
    public void deleteCategory() throws SQLException, ClassNotFoundException{
        Category_DAL.deleteCategoryByID(id);
    }
    
    public void updateCategory() throws SQLException, ClassNotFoundException{
        Category_DAL.updateCategory(this);
    }
}
