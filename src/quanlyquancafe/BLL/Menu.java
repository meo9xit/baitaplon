/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlyquancafe.BLL;

import java.sql.SQLException;
import java.util.ArrayList;
import quanlyquancafe.DAL.Menu_DAL;

/**
 *
 * @author ACER
 */
public class Menu {
    private int id;
    private String name;
    private int price;
    private int category;

    public Menu(){}
    
    public Menu(int id){
        this.id = id;
    }
    
    public Menu(String name){
        this.name = name;
    }
    
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Menu other = (Menu) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!this.name.equals(other.name))
            return false;
        return true;
    }
    
    
    
    public static ArrayList<Menu> getListMenu() throws SQLException, ClassNotFoundException{
        return Menu_DAL.getListMenu();
    }
    
    public void insertMenu() throws SQLException, ClassNotFoundException{
        Menu_DAL.insertMenu(this);
    }
    
    public void deleteMenu() throws SQLException, ClassNotFoundException{
        Menu_DAL.deleteMenuByID(id);
    }
    
    public void updateMenu() throws SQLException, ClassNotFoundException{
        Menu_DAL.updateMenu(this);
    }
}
