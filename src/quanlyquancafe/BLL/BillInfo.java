/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlyquancafe.BLL;

import java.sql.SQLException;
import java.util.ArrayList;
import quanlyquancafe.DAL.BillInfo_DAL;

/**
 *
 * @author ACER
 */
public class BillInfo {
    private int billId;
    private int menuId;
    private int count;

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
    public static ArrayList<BillInfo> getListBillInfo() throws SQLException, ClassNotFoundException{
        return BillInfo_DAL.getListBillInfo();
    }
    
    public void insertBillInfo() throws SQLException, ClassNotFoundException{
        BillInfo_DAL.insertBillInfor(this);
    }
    
    public void deleteBillInfo() throws SQLException, ClassNotFoundException {
        BillInfo_DAL.deleteBillInfoB(this);
    }
    
    public void updateBillInfo() throws SQLException, ClassNotFoundException{
        BillInfo_DAL.updateBillInfo(this);
    }
    
    public static ArrayList<BillInfo> getListBillInfoByTableID(int tableID) throws SQLException, ClassNotFoundException{
        return BillInfo_DAL.getListBillInfoByTableID(tableID);
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final BillInfo other = (BillInfo) obj;
        if (this.billId != other.billId) {
            return false;
        }
        if (this.menuId != other.menuId) {
            return false;
        }
        return true;
    }
    
    
    
}
