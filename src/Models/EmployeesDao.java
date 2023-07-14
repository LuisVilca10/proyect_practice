package Models;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmployeesDao {
  //establecemos conecion
  ConnectionMySQL cn=new ConnectionMySQL();
  Connection conn;
  PreparedStatement pst;
  ResultSet rs;
  
  //enviamos datos
  public static int id_user = 0;
  public static String full_name_user = "";
  public static String username_user = "";
  public static String address_user = "";
  public static String telephone_user = "";
  public static String email_user = "";
  public static String rol_user = "";


  
  //login
  public Employees loginQuery(String user, String password){
      String query = "SELECT * FROM employess WHERE username = ? AND password = ?";
      Employees employee = new Employees();
      try {
          conn = cn.getConnection();
          pst = conn.prepareStatement(query);
          //enviar parametros
          pst.setString(1, user);
          pst.setString(2, password);
          rs = pst.executeQuery();
          
          if(rs.next()){
                employee.setId(rs.getInt("id"));
                id_user = employee.getId();
                employee.setFull_name(rs.getString("full name"));
                full_name_user = employee.getFull_name();
                employee.setUsername(rs.getString("username"));
                username_user = employee.getUsername(); 
                employee.setAddress(rs.getString("address"));
                address_user = employee.getAddress();
                employee.setTelephone(rs.getString("telephone"));
                telephone_user = employee.getTelephone();
                employee.setEmail(rs.getString("email"));
                email_user = employee.getEmail();
                employee.setRol(rs.getString("rol"));
                rol_user = employee.getRol();
          }
          
      } catch (SQLException e) {
          JOptionPane.showMessageDialog(null, "Error al obtener al empleado" + e);
      }
      return employee;
  }
  
  //registrar empleado
  public boolean registerEmployeeQuery(Employees employee){
        String query = "INSERTO INTO employees(id, full_name, user_name, address, telephone, emial, password, rol, created, updated)"
                + "VALUES(?,?,?,?,?,?,?,?,?,?)";
        Timestamp datetime = new Timestamp(new Date().getTime());
        try {
          conn = cn.getConnection();
          pst = conn.prepareStatement(query);
          pst.setInt(1, employee.getId());
          pst.setString(2, employee.getFull_name());
          pst.setString(3, employee.getUsername());
          pst.setString(4, employee.getAddress());
          pst.setString(5, employee.getTelephone());
          pst.setString(6, employee.getEmail());
          pst.setString(7, employee.getPassword());
          pst.setString(8, employee.getRol());
          pst.setTimestamp(9, datetime);
          pst.setTimestamp(10, datetime);
          pst.execute();
          return true;
      } catch (SQLException e) {
          JOptionPane.showMessageDialog(null, "error al registrar al empleado" + e);
          return false;
      }
  }
  
  //listar empleados
  public List listEmployeeQuery(String value){
      List<Employees> list_employee= new ArrayList();
      String query = "SELECT * FROM employees ORDER BY rol ASC";
      String query_search_employee = "SELECT * FROM employees WHERE id LIKE '%" + value + "%'";
      try {
          conn = cn.getConnection();
          if(value.equalsIgnoreCase("")){
              pst = conn.prepareStatement(query);
              rs = pst.executeQuery();
          }else{
              pst = conn.prepareStatement(query_search_employee);
              rs = pst.executeQuery();
          }
          
        while (rs.next()) {
              Employees em = new Employees();
              em.setId(rs.getInt("id"));
              em.setFull_name(rs.getString("full_name"));
              em.setUsername(rs.getString("username"));
              em.setAddress(rs.getString("address"));
              em.setTelephone(rs.getString("telephone"));
              em.setEmail(rs.getString("email"));
              em.setRol(rs.getString("rol"));
              list_employee.add(em);
        }
      } catch (SQLException e) {
          JOptionPane.showMessageDialog(null, e.toString());
      }
      return list_employee;
  }
}
