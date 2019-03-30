/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlyquancafe;
import quanlyquancafe.DAL.ConnUtils;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author ACER
 */
public class QuanLyQuanCafe {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // TODO code application logic here
         Connection connection = ConnUtils.getConnection();
 
      // Tạo đối tượng Statement.
      Statement statement = connection.createStatement();
 
      String sql = "Select * from account";
 
      // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
      ResultSet rs = statement.executeQuery(sql);
 
      // Duyệt trên kết quả trả về.
      while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
          String empNo = rs.getString("username");
          String empName = rs.getString("pass");
          System.out.println("--------------------");
          System.out.println("EmpNo:" + empNo);
          System.out.println("EmpName:" + empName);
      }
      // Đóng kết nối
      connection.close();
    }
    
}
