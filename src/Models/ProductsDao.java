package Models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Luis
 */
public class ProductsDao {

    ConnectionMySQL cn = new ConnectionMySQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;

    public boolean registerProductsQuery(Products product) {

        String query = " INSERT INTO products ( code, name, description, unit_price, created, updated, category_id )"
                + " VALUES (?,?,?,?,?,?,?) ";
        Timestamp datetime = new Timestamp(new java.util.Date().getTime());
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            // pst.setInt(1, supplier.getId());
            pst.setInt(1, product.getCode());
            pst.setString(2, product.getName());
            pst.setString(3, product.getDescription());
            pst.setDouble(4, product.getUnit_price());
            pst.setTimestamp(5, (datetime));
            pst.setTimestamp(6, (datetime));
            pst.setInt(7, product.getCategory_id());
            pst.execute();
            return true;

        } catch (SQLException e) {
            System.err.println("el error es: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "error al registrar producto ");
            return false;
        }
    }

    public List<Products> listProductsQuery(String value) {
        List<Products> list_product = new ArrayList<>();
        String query = "SELECT pro.*, ca.name AS category_name FROM products pro "
                + "INNER JOIN categories ca ON pro.category_id = ca.id";
        String query_search_product = "SELECT pro.*, ca.name AS category_name FROM products pro "
                + "INNER JOIN categories ca ON pro.category_id = ca.id "
                + "WHERE pro.name LIKE '%" + value + "%'";

        try {
            conn = cn.getConnection();
            if (value.equalsIgnoreCase("")) {
                pst = conn.prepareStatement(query);
            } else {
                pst = conn.prepareStatement(query_search_product);
            }

            rs = pst.executeQuery();

            while (rs.next()) {
                Products em = new Products();
                em.setId(rs.getInt("id"));
                em.setCode(rs.getInt("code"));
                em.setName(rs.getString("name"));
                em.setDescription(rs.getString("description"));
                em.setUnit_price(rs.getDouble("unit_price"));
                em.setProduct_quantity(rs.getInt("product_quantity"));
                em.setCategory_name(rs.getString("category_name"));
                list_product.add(em);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        } finally {
            // Cierra recursos (pst, rs, conn) apropiadamente
            // ...
        }
        return list_product;
    }

    public boolean updateProductQuery(Products product) {
        String query = "UPDATE products SET code = ?, name = ?, description = ?, unit_price = ?, `updated` = ?, category_id = ?"
                + " WHERE id = ?";

        Timestamp datetime = new Timestamp(new java.util.Date().getTime());
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, product.getCode());
            pst.setString(2, product.getName());
            pst.setString(3, product.getDescription());
            pst.setDouble(4, product.getUnit_price());
            pst.setTimestamp(5, datetime);
            pst.setInt(6, product.getCategory_id());
            pst.setInt(7, product.getId());
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error al actualizar el producto" + e);
            return false;
        }
    }

    public boolean deleteProductQuery(int id) {
        String query = "DELETE FROM products WHERE id = " + id;
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error al eliminar el producto que tiene "
                    + "relación con otra tabla" + e);
            return false;
        }
    }

    public Products searchProduct(int id) {
        String query = "SELECT pro.*, ca.name AS category_name FROM products pro "
                + "INNER JOIN categories ca ON pro.category_id = ca.id WHERE pro.id = ?";
        Products pr = new Products();
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();

            if (rs.next()) {
                pr.setId(rs.getInt("id"));
                pr.setCode(rs.getInt("code"));
                pr.setName(rs.getString("name"));
                pr.setDescription(rs.getString("description"));
                pr.setUnit_price(rs.getDouble("unit_price"));
                pr.setCategory_id(rs.getInt("category_id"));
                pr.setCategory_name(rs.getString("category_name"));
            }
        } catch (SQLException e) {
            System.err.println("el error: " + e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return pr;
    }

    public Products searchCode(int code) {
        String query = "SELECT pro.id, pro.name FROM products pro WHERE pro.code = ?";
        Products pr = new Products();
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, code);
            rs = pst.executeQuery();

            if (rs.next()) {
                pr.setId(rs.getInt("id"));
                pr.setName(rs.getString("name"));;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return pr;
    }

    public Products searchId(int id) {
        String query = "SELECT pro.product_quantity FROM products pro WHERE pro.id = ?";
        Products product = new Products();
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            if (rs.next()) {
                product.setProduct_quantity(rs.getInt("product_quantity"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return product;
    }

    public boolean updateStockQuery(int amount, int product_id) {
        String query = "UPDATE products SET product_quantity = ? WHERE id = ?";
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, amount);
            pst.setInt(2, product_id);
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }
}
