package Models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Luis
 */
public class SuppliersDao {

    ConnectionMySQL cn = new ConnectionMySQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;

    public boolean registerSuppliersQuery(Suppliers supplier) {

        String query = " INSERTO INTO suppliers ( name, description, telephone, address, email, city, created, update )"
                + " VALUES (?,?,?,?,?,?,?,?) ";
        Timestamp datetime = new Timestamp(new java.util.Date().getTime());
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            // pst.setInt(1, supplier.getId());
            pst.setString(1, supplier.getName());
            pst.setString(2, supplier.getDescription());
            pst.setString(3, supplier.getTelephone());
            pst.setString(4, supplier.getAddress());
            pst.setString(5, supplier.getEmail());
            pst.setString(6, supplier.getCity());
            pst.setTimestamp(7, (datetime));
            pst.setTimestamp(8, (datetime));
            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error al registrar proveedor ");
            return false;
        }
    }

    public List listSuppliersQuery(String value) {
        List<Suppliers> list_supplier = new ArrayList();
        String query = "SELECT * FROM suppliers ORDER BY rol ASC";
        String query_search_supplier = "SELECT * FROM suppliers WHERE name LIKE '%" + value + "%'";

        try {
            conn = cn.getConnection();
            if (value.equalsIgnoreCase("")) {
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            } else {
                pst = conn.prepareStatement(query_search_supplier);
                rs = pst.executeQuery();
            }

            while (rs.next()) {
                Suppliers em = new Suppliers();
                em.setId(rs.getInt("id"));
                em.setName(rs.getString("name"));
                em.setDescription(rs.getString("description"));
                em.setTelephone(rs.getString("telephone"));
                em.setAddress(rs.getString("address"));
                em.setEmail(rs.getString("email"));
                em.setCity(rs.getString("city"));
                list_supplier.add(em);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return list_supplier;
    }

    public boolean updateSupplierQuery(Suppliers supplier) {
        String query = "UPDATE suppliers SET name = ?,  description = ?, telephone = ?,address=?, email = ?, city = ?, update = ?"
                + " WHERE id = ?";

        Timestamp datetime = new Timestamp(new java.util.Date().getTime());
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, supplier.getName());
            pst.setString(2, supplier.getDescription());
            pst.setString(3, supplier.getTelephone());
            pst.setString(4, supplier.getAddress());
            pst.setString(5, supplier.getEmail());
            pst.setString(6, supplier.getCity());
            pst.setTimestamp(7, datetime);
            pst.setInt(8, supplier.getId());
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error al actualizar al proveedor" + e);
            return false;
        }
    }

    public boolean deleteSupplierQuery(int id) {
        String query = "DELETE FROM suppliers WHERE id = " + id;
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error al eliminar el proveedor que tiene "
                    + "relación con otra tabla" + e);
            return false;
        }
    }

}
