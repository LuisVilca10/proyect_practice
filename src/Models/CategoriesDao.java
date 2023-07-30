package Models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Luis
 */
public class CategoriesDao {

    ConnectionMySQL cn = new ConnectionMySQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;

    public boolean registerCategoriesQuery(Categories category) {

        String query = " INSERTO INTO categories ( name, created, update )"
                + " VALUES (?,?,?) ";
        Timestamp datetime = new Timestamp(new java.util.Date().getTime());
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            // pst.setInt(1, supplier.getId());
            pst.setString(1, category.getName());
            pst.setTimestamp(2, (datetime));
            pst.setTimestamp(3, (datetime));
            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error al registrar categoria ");
            return false;
        }
    }

    public List listCategoriesQuery(String value) {
        List<Categories> list_category = new ArrayList();
        String query = "SELECT * FROM categories ORDER BY rol ASC";
        String query_search_category = "SELECT * FROM categories WHERE name LIKE '%" + value + "%'";

        try {
            conn = cn.getConnection();
            if (value.equalsIgnoreCase("")) {
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            } else {
                pst = conn.prepareStatement(query_search_category);
                rs = pst.executeQuery();
            }

            while (rs.next()) {
                Categories em = new Categories();
                em.setId(rs.getInt("id"));
                em.setName(rs.getString("name"));
                list_category.add(em);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return list_category;
    }

    public boolean updateCategoryQuery(Categories category) {
        String query = "UPDATE categories SET name = ?, update = ?"
                + " WHERE id = ?";

        Timestamp datetime = new Timestamp(new java.util.Date().getTime());
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, category.getName());
            pst.setTimestamp(2, datetime);
            pst.setInt(4, category.getId());
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error al actualizar la categoria" + e);
            return false;
        }
    }

    public boolean deleteCategoryQuery(int id) {
        String query = "DELETE FROM categories WHERE id = " + id;
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error al eliminar la categoria que tiene "
                    + "relación con otra tabla" + e);
            return false;
        }
    }

}
