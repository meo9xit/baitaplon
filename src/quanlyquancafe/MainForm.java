/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlyquancafe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.AbstractTableModel;
import quanlyquancafe.BLL.Account;
import quanlyquancafe.BLL.Bill;
import quanlyquancafe.BLL.BillInfo;
import quanlyquancafe.BLL.Category;
import quanlyquancafe.BLL.Menu;
import quanlyquancafe.BLL.Table;



/**
 *
 * @author ACER
 */
public class MainForm extends javax.swing.JDialog {
    Date date = new Date();
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    ActionListener taskperformer = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            
            lblCurrentDate.setText(dateFormat.format(date));
        }
    };
    Timer timer = new Timer(100,taskperformer);
    Account acc = new Account();
    ArrayList<Menu> lMenu = new ArrayList<>();
    ArrayList<Table> lTable = new ArrayList<>();
    ArrayList<Category> lCategory = new ArrayList();
    ArrayList<Bill> lBill = new ArrayList<>();
    ArrayList<BillInfo> lBillInfo = new ArrayList<>();
    /**
     * Creates new form MainForm
     */
    class TableMenu extends AbstractTableModel{
        String[] name = {"Tên món","Giá","Loại"};
        Class[] classes = {String.class, Integer.class, String.class};
        ArrayList<Menu> menu = new ArrayList<>();
        
        public TableMenu(ArrayList<Menu> menu){
            this.menu = menu;
        }
        @Override
        public int getRowCount() {
            return menu.size();
        }

        @Override
        public int getColumnCount() {
            return name.length;
        }

        @Override
        public Object getValueAt(int i, int i1) {
            switch(i1){
                case 0: return menu.get(i).getName();
                case 1: return menu.get(i).getPrice();
                case 2: return lCategory.get(lCategory.indexOf(new Category(menu.get(i).getCategory()))).getName();
                default: return null;
            }
        }
        
        @Override
        public String getColumnName(int i){
            return name[i];
        }
        
        @Override
        public Class getColumnClass(int i){
            return classes[i];
        }
        
    }
    
    class TableTable extends AbstractTableModel{
        ArrayList<Table> table = new ArrayList<>();
        String[] name = {"Tên bàn","Trạng thái"};
        Class[] classes = {String.class, Boolean.class};
        
        public TableTable(ArrayList<Table> table){
            this.table = table;
        }
        
        @Override
        public int getRowCount() {
            return table.size();
        }

        @Override
        public int getColumnCount() {
            return name.length;
        }

        @Override
        public Object getValueAt(int i, int i1) {
            switch(i1){
                case 0: return table.get(i).getTableName();
                case 1: return table.get(i).isStatus();
                default: return null;
            }
        }
        
        @Override
        public String getColumnName(int i ){
            return name[i];
        }
        
        @Override
        public Class getColumnClass(int i){
            return classes[i];
        }
        
    }
    

    
    class TableBill extends AbstractTableModel{
        ArrayList<BillInfo> billInfos = new ArrayList<>();
        ArrayList<Menu> lMenu = new ArrayList<>();
        String[] name = {"Tên món","Số lượng","Đơn giá","Tổng"};
        Class[] classes = {String.class, Integer.class, Integer.class, Integer.class};
        
        
        public TableBill(ArrayList<BillInfo> arr, ArrayList<Menu> lMenu){
            this.billInfos = arr;
            this.lMenu = lMenu;
        }
        
        int getTotal(int index){
            return billInfos.get(index).getCount()*lMenu.get(lMenu.indexOf(new Menu(billInfos.get(index).getMenuId()))).getPrice();
        }
        
        @Override
        public int getRowCount() {
            return billInfos.size();
        }

        @Override
        public int getColumnCount() {
            return name.length;
        }

        @Override
        public Object getValueAt(int i, int i1) {
            switch(i1){
                case 0: return lMenu.get(lMenu.indexOf(new Menu(billInfos.get(i).getMenuId()))).getName();
                case 1: return billInfos.get(i).getCount();
                case 2: return lMenu.get(lMenu.indexOf(new Menu(billInfos.get(i).getMenuId()))).getPrice();
                case 3: return getTotal(i);
                default: return null;
            }
        }
        
        @Override
        public String getColumnName(int i){
            return name[i];
        }
        
        @Override
        public Class getColumnClass(int i){
            return classes[i];
        }
    }
    
    void loadData(){
        try{
            lMenu = Menu.getListMenu();
            lBill = Bill.getListBill();
            lBillInfo = BillInfo.getListBillInfo();
            lCategory = Category.getListCategory();
            lTable = Table.getListTable();
        }
        catch(ClassNotFoundException | SQLException ex){
            JOptionPane.showMessageDialog(new JFrame(), "Lỗi kết nối với CSDL!");
        }
        
    }
    
    void loadMenu(){
        for(Menu mn: lMenu){
            cbMenu.addItem(mn.getName());
        }
    }
    
    void loadForm(){
        loadData();
        tableMenu.setModel(new TableMenu(lMenu));
        tableTable.setModel(new TableTable(lTable));
        loadMenu();
    }
    
    
    boolean showBill(int tableID){
        ArrayList<BillInfo> arr = new ArrayList<>();
        try{
            arr = BillInfo.getListBillInfoByTableID(tableID);
        }
        catch(ClassNotFoundException | SQLException ex){
            JOptionPane.showMessageDialog(new JFrame(), "Lỗi kết nối!");
        }
        if(arr == null)
            return false;
        else tableBill.setModel(new TableBill(arr,lMenu));
        return true;
    }
    
    Bill getUncheckedBillByTableID(int tableID){
        Bill b = new Bill();

        return b;
    }
    
    
    
    
    public MainForm(java.awt.Frame parent, boolean modal, Account acc) {
        super(parent, modal);
        initComponents();
        this.setTitle("Quản lý giao dịch quán cà phê (nhóm 8)");
        timer.setRepeats(true);
        timer.start();
        this.acc = acc;
        loadForm();
        if(!acc.getType().equals("Quản lý"))
            jMenu1.setEnabled(false);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        leftPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableMenu = new javax.swing.JTable();
        lblCurrentDate = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableTable = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableBill = new javax.swing.JTable();
        cbMenu = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        txtCount = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnAddMenu = new javax.swing.JButton();
        btnDeleteMenu = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        lblCurrentTable = new javax.swing.JLabel();
        btnCancel = new javax.swing.JButton();
        cbBookedTable = new javax.swing.JComboBox<>();
        btnChange = new javax.swing.JButton();
        cbFreeTable = new javax.swing.JComboBox<>();
        btnPay = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        leftPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        tableMenu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tableMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMenuMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableMenu);

        javax.swing.GroupLayout leftPanelLayout = new javax.swing.GroupLayout(leftPanel);
        leftPanel.setLayout(leftPanelLayout);
        leftPanelLayout.setHorizontalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(leftPanelLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(lblCurrentDate)
                .addContainerGap(139, Short.MAX_VALUE))
        );
        leftPanelLayout.setVerticalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, leftPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblCurrentDate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tableTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tableTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableTable);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 127, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tableBill.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(tableBill);

        cbMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbMenuActionPerformed(evt);
            }
        });

        jLabel1.setText("Món: ");

        txtCount.setText("1");

        jLabel2.setText("Số lượng:");

        btnAddMenu.setText("Thêm");
        btnAddMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddMenuActionPerformed(evt);
            }
        });

        btnDeleteMenu.setText("Hủy món");
        btnDeleteMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteMenuActionPerformed(evt);
            }
        });

        jLabel3.setText("Giảm giá: ");

        jLabel4.setText("Thành tiền: ");

        btnCancel.setText("Hủy bàn");

        btnChange.setText("Chuyển bàn");

        btnPay.setText("Thanh toán");

        jMenu1.setText("Admin");
        jMenu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu1MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Tài khoản");

        jMenuItem1.setText("Đăng xuất");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(leftPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnPay))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnChange, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cbFreeTable, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addComponent(cbBookedTable, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4))
                                .addGap(29, 29, 29)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                                    .addComponent(jTextField2))))
                        .addGap(38, 38, 38))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(66, 66, 66)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addGap(27, 27, 27)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtCount)
                                    .addComponent(cbMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(78, 78, 78)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnDeleteMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnAddMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblCurrentTable))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)))
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(leftPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(btnAddMenu))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(btnDeleteMenu))
                        .addGap(25, 25, 25))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(lblCurrentTable)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(btnCancel)
                    .addComponent(cbBookedTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnChange)
                        .addComponent(cbFreeTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPay)
                .addGap(8, 8, 8))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
        

    
    
    
    private void tableMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMenuMouseClicked
        // TODO add your handling code here:
        int row = tableMenu.getSelectedRow();
        cbMenu.setSelectedIndex(row);
    }//GEN-LAST:event_tableMenuMouseClicked

    private void tableTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableTableMouseClicked
        // TODO add your handling code here:
        int r = tableTable.getSelectedRow();
        int id = lTable.get(r).getID();
        showBill(id);
    }//GEN-LAST:event_tableTableMouseClicked

    void insertBillInfoToBill(Bill bill){
           
    }
    
    private void btnAddMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddMenuActionPerformed

        int tableID = lTable.get(tableTable.getSelectedRow()).getID();
        boolean flag = false;
        for(Bill bill: lBill){
            if(bill.isStatus()==false&&bill.getIdTable()==tableID){
                showBill(tableID);
                BillInfo bi = new BillInfo();
                bi.setBillId(bill.getId());
                bi.setMenuId(lMenu.get(cbMenu.getSelectedIndex()).getId());
                try{
                    int count = Integer.parseInt(txtCount.getText());
                    if(count<=0)
                        throw new Exception("Số lượng phải lớn hơn 0");
                    bi.setCount(Integer.parseInt(txtCount.getText()));
                }
                catch(java.lang.NumberFormatException ex){
                    JOptionPane.showMessageDialog(new JFrame(), "Sô lượng không được bỏ trống!");
                    return;
                } catch(Exception ex){
                    JOptionPane.showMessageDialog(new JFrame(), ex.toString());
                    return;
                }
                if(lBillInfo.contains(bi)){
                    lBillInfo.get(lBillInfo.indexOf(bi)).setCount(lBillInfo.get(lBillInfo.indexOf(bi)).getCount()+bi.getCount());
                    try {
                        lBillInfo.get(lBillInfo.indexOf(bi)).updateBillInfo();
                    } catch (SQLException ex) {
                        Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else{
                  lBillInfo.add(bi);
                  try {
                    bi.insertBillInfo();
                } catch (ClassNotFoundException ex) {
                    //JOptionPane.showMessageDialog(new JFrame(), "Lỗi kết nối!");
                } catch (SQLException ex) {    
                   Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
                
                lTable.get(lTable.indexOf(new Table(tableID))).setStatus(true);
                tableTable.setModel(new TableTable(lTable));
                showBill(tableID);
                flag = true;
                break;
            }

        }
        if(!flag){
            try {
                Bill.insertBill(tableID);
                lBill = Bill.getListBill();
            } catch (SQLException ex) {
                 Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                 Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
            }
            Bill bill = lBill.get(lBill.size()-1);
            BillInfo bi = new BillInfo();
            bi.setBillId(bill.getId());
            bi.setMenuId(lMenu.get(cbMenu.getSelectedIndex()).getId());
            bi.setCount(Integer.parseInt(txtCount.getText()));
            lBillInfo.add(bi);
            try {
                bi.insertBillInfo();
            } catch (ClassNotFoundException ex) {
                //JOptionPane.showMessageDialog(new JFrame(), "Lỗi kết nối!");
            } catch (SQLException ex) {    
               Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
            }
            lTable.get(lTable.indexOf(new Table(tableID))).setStatus(true);
            tableTable.setModel(new TableTable(lTable));
            showBill(tableID);
        }
        lTable.get(lTable.indexOf(new Table(tableID))).setStatus(true);
        try {
            lTable.get(lTable.indexOf(new Table(tableID))).updateTable();
        } catch (SQLException ex) {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        tableTable.setModel(new TableTable(lTable));
        int index = lTable.indexOf(new Table(tableID));
        tableTable.setRowSelectionInterval(index, index);
    }//GEN-LAST:event_btnAddMenuActionPerformed

    private void jMenu1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu1MouseClicked
        // TODO add your handling code here:
        new AdminForm(this, true, lMenu, lTable, lCategory, lBill, lBillInfo).setVisible(true);
    }//GEN-LAST:event_jMenu1MouseClicked

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        this.dispatchEvent(new WindowEvent(this,WindowEvent.WINDOW_CLOSING));
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void cbMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMenuActionPerformed
        // TODO add your handling code here:
        int r = cbMenu.getSelectedIndex();
        tableMenu.setRowSelectionInterval(r, r);
    }//GEN-LAST:event_cbMenuActionPerformed

    private void btnDeleteMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteMenuActionPerformed
        // TODO add your handling code here:
        int r = tableBill.getSelectedRow();
        if(r==-1){
            JOptionPane.showMessageDialog(new JFrame(), "Phải chọn món muốn hủy trong bảng!");
            return;
        }
        BillInfo bi = new BillInfo();
        int selectedTable = tableTable.getSelectedRow();
        bi.setBillId(getBillByTableID(lTable.get(selectedTable).getID()).getId());
        bi.setMenuId(getMenuByName(tableBill.getValueAt(r, 0).toString()).getId());
        bi.setCount(Integer.parseInt(tableBill.getValueAt(r, 1).toString()));
        lBillInfo.remove(bi);
        try {
            bi.deleteBillInfo();
        } catch (SQLException ex) {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        int tableID = lTable.get(tableTable.getSelectedRow()).getID();
        showBill(tableID);
    }//GEN-LAST:event_btnDeleteMenuActionPerformed
    
    Menu getMenuByName(String name){
        for(Menu menu: lMenu){
            if(menu.getName().equals(name)){
                return menu;
            }
        }
        return null;
    }
    
    Bill getBillByTableID(int tableID){
        for(Bill bill: lBill){
            if(bill.getIdTable()==tableID)
                return bill;
        }
        return null;
    }
    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the dialog */
////        java.awt.EventQueue.invokeLater(new Runnable() {
////            public void run() {
////                MainForm dialog = new MainForm(new javax.swing.JFrame(), true, acc);
////                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
////                    @Override
////                    public void windowClosing(java.awt.event.WindowEvent e) {
////                        System.exit(0);
////                    }
////                });
////                dialog.setVisible(true);
////            }
////        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddMenu;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnChange;
    private javax.swing.JButton btnDeleteMenu;
    private javax.swing.JButton btnPay;
    private javax.swing.JComboBox<String> cbBookedTable;
    private javax.swing.JComboBox<String> cbFreeTable;
    private javax.swing.JComboBox<String> cbMenu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JLabel lblCurrentDate;
    private javax.swing.JLabel lblCurrentTable;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JTable tableBill;
    private javax.swing.JTable tableMenu;
    private javax.swing.JTable tableTable;
    private javax.swing.JTextField txtCount;
    // End of variables declaration//GEN-END:variables
}
