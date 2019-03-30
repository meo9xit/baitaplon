/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlyquancafe.BLL;

import java.sql.SQLException;
import java.util.ArrayList;
import quanlyquancafe.DAL.Account_DAL;

/**
 *
 * @author ACER
 */
public class Account {
    private String username;
    private String password;
    private String type;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public static ArrayList<Account> getListAccount() throws SQLException, ClassNotFoundException{
        return Account_DAL.getListAccount();
    }
    
    public void insertAccount() throws SQLException, ClassNotFoundException{
        Account_DAL.insertAccount(this);
    }
    
    public void deleteAccount() throws SQLException, ClassNotFoundException{
        Account_DAL.deleteAccountByUsername(this.username);
    }
    
    public void updateAccount() throws SQLException, ClassNotFoundException{
        Account_DAL.updateAccount(this);
    }
    
}
