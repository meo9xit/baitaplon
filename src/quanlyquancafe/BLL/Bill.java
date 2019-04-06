/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlyquancafe.BLL;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import quanlyquancafe.DAL.Bill_DAL;

/**
 *
 * @author ACER
 */
public class Bill {
    private int id;
    private Date dateCheck;
    private int idTable;
    private boolean status;
    private float discount;
    private float total;

    public float getTotal(ArrayList<BillInfo> arr, ArrayList<Menu> lmenu){
        for(BillInfo bi:arr){
            if(bi.getBillId()==this.id){
                total = bi.getCount()*lmenu.get(lmenu.indexOf(new Menu(bi.getMenuId()))).getPrice();
            }
        }
        total = total * (1-discount);
        return total;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateCheck() {
        return dateCheck;
    }

    public void setDateCheck(Date dateCheck) {
        this.dateCheck = dateCheck;
    }


    public int getIdTable() {
        return idTable;
    }

    public void setIdTable(int idTable) {
        this.idTable = idTable;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }
    
    public static ArrayList<Bill> getListBill() throws SQLException, ClassNotFoundException{
        return Bill_DAL.getListBill();
    }
    
    public static void insertBill(int idTable) throws SQLException, ClassNotFoundException{
        Bill_DAL.insertBill(idTable);
    }
    
    public void deleteBill() throws SQLException, ClassNotFoundException{
        Bill_DAL.deleteBillByID(id);
    }
    
    public void updateBill() throws SQLException, ClassNotFoundException{
        Bill_DAL.updateBill(this);
    }
    
}
