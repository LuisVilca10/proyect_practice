package Models;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Luis
 */
public class CustomersDao {

    ConnectionMySQL cn = new ConnectionMySQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;

    public boolean registerCustomerQuery(Customers customer) {

        String query = " INSERTO INTO customers (id, full_name, address, telephone, email, created, update )"
                + " VALUES (?,?,?,?,?,?,?) ";
        Timestamp datetime = new Timestamp(new Date().getTime());
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, customer.getId());
            pst.setString(2, customer.getFull_name());
            pst.setString(3, customer.getAddress());
            pst.setString(4, customer.getTelephone());
            pst.setString(5, customer.getEmail());
            pst.setTimestamp(6, (datetime));
            pst.setTimestamp(7, (datetime));
            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error al registrar cliente ");
            return false;
        }
    }

    public List listCustomerQuery(String value) {
        List<Customers> list_customer = new ArrayList();
        String query = "SELECT * FROM customers ORDER BY rol ASC";
        String query_search_customer = "SELECT * FROM customers WHERE id LIKE '%" + value + "%'";

        try {
            conn = cn.getConnection();
            if (value.equalsIgnoreCase("")) {
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            } else {
                pst = conn.prepareStatement(query_search_customer);
                rs = pst.executeQuery();
            }

            while (rs.next()) {
                Customers em = new Customers();
                em.setId(rs.getInt("id"));
                em.setFull_name(rs.getString("full_name"));
                em.setAddress(rs.getString("address"));
                em.setTelephone(rs.getString("telephone"));
                em.setEmail(rs.getString("email"));
                list_customer.add(em);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return list_customer;
    }

    public boolean updateCustomerQuery(Customers customer) {
        String query = "UPDATE customers SET full_name = ?,  address = ?, telephone = ?, email = ?, update = ?"
                + " WHERE id = ?";

        Timestamp datetime = new Timestamp(new Date().getTime());
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, customer.getFull_name());
            pst.setString(2, customer.getAddress());
            pst.setString(3, customer.getTelephone());
            pst.setString(4, customer.getEmail());
            pst.setTimestamp(5, datetime);
            pst.setInt(6, customer.getId());
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error al actualizar al cliente" + e);
            return false;
        }
    }

    public boolean deleteCustomerQuery(int id) {
        String query = "DELETE FROM customers WHERE id = " + id;
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error al eliminar el cliente que tiene "
                    + "relación con otra tabla" + e);
            return false;
        }
    }

}
