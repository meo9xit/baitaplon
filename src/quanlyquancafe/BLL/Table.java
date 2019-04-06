/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlyquancafe.BLL;

import java.sql.SQLException;
import java.util.ArrayList;
import quanlyquancafe.DAL.Table_DAL;

/**
 *
 * @author ACER
 */
public class Table {


    private int ID;
    private String tableName;
    private boolean status;
    
    public Table(){}
    
    public Table(int id){
        this.ID =  id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    public static ArrayList<Table> getListTable() throws SQLException, ClassNotFoundException{
        return Table_DAL.getListTable();
    }
    
    public static void insertTable() throws SQLException, ClassNotFoundException{
        Table_DAL.insertTable();
    }
    
    public void deleteTable() throws SQLException, ClassNotFoundException{
        Table_DAL.deleteTableById(ID);
    }
    
    public void updateTable() throws SQLException, ClassNotFoundException{
        Table_DAL.updateTable(this);
    }

    @Override
    public int hashCode() {
        int hash = 3;
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
        final Table other = (Table) obj;
        if (this.ID != other.ID) {
            return false;
        }
        return true;
    }
    
    
}
