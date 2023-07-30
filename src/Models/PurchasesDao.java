package Models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Luis
 */
public class PurchasesDao {

    ConnectionMySQL cn = new ConnectionMySQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;

    //registrar compra
    public boolean registerPurchasesQuery(int supplier_id, int employee_id, double total) {
        String query = " INSERTO INTO purchases (supplier_id, employee_id, total, created)"
                + " VALUES (?,?,?,?) ";
        Timestamp datetime = new Timestamp(new java.util.Date().getTime());
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            // pst.setInt(1, supplier.getId());
            pst.setInt(1, supplier_id);
            pst.setInt(2, employee_id);
            pst.setDouble(3, total);
            pst.setTimestamp(4, datetime);
            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error al registrar la compra ");
            return false;
        }
    }

    //Registrar detalle de la compra
    public boolean registerPurchasesDetailQuery(int purchase_id, double purchase_precie, int purchase_amount,
            double purchase_subtotal, int product_id) {
        String query = " INSERTO INTO purchase_details (purchase_id, purchase_precie, purchase_amount, purchase_subtotal, purchase_date,product_id)"
                + " VALUES (?,?,?,?,?,?) ";
        Timestamp datetime = new Timestamp(new java.util.Date().getTime());
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            // pst.setInt(1, supplier.getId());
            pst.setInt(1, purchase_id);
            pst.setDouble(2, purchase_precie);
            pst.setInt(3, purchase_amount);
            pst.setDouble(4, purchase_subtotal);
            pst.setTimestamp(5, datetime);
            pst.setInt(6, product_id);
            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error al registrar los detalles de la compra ");
            return false;
        }
    }

    //Obtener id de la compra
    public int purchseId() {
        int id = 0;
        String query = "SELECT MAX(id) AS id FROM purchases";
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return id;
    }

    //listar todas las compras realizadas
    public List listAllPurchasesQuery() {
        List<Purchases> list_purchases = new ArrayList();
        String query = "SELECT pu.*, su.name AS supplier_name FROM purchases pu, suppliers su WHERE pu.supplier_id = su.id ORDER BY pu.id ASC";
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                Purchases em = new Purchases();
                em.setId(rs.getInt("id"));
                em.setSupplier_name_product(rs.getString("supplier_name"));
                em.setTotal(rs.getDouble("total"));
                em.setCreated(rs.getString("created"));
                list_purchases.add(em);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return list_purchases;
    }

    //listar compras para imprimir
    public List listPurchasesDetailQuery(int id) {
        List<Purchases> list_purchases = new ArrayList();
        String query = "SELECT pu.created, pude.purchase_price, pude.purchase_amount, pude.purchase_subtotal, "
                + "su.name AS supplier_name, pro.name AS product_name, "
                + "em.full_name FROM purchases pu INNER JOIN purchase_details "
                + "pude ON pu.id = pude.purchase_id INNER JOIN products pro "
                + "ON pude.product_id = pro.id INNER JOIN suppliers su ON pu.supplier_id = su.id INNER JOIN "
                + "employees em ON pu.employe_id = em.id WHERE pu.id = ?";
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            while (rs.next()) {
                Purchases em = new Purchases();
                em.setProduct_name(rs.getString("product_name"));
                em.setPurchase_amount(rs.getInt("purchase_amount"));
                em.setPurchase_price(rs.getDouble("purchase_price"));
                em.setPurchase_subtotal(rs.getDouble("purchase_subtotal"));
                em.setSupplier_name_product(rs.getString("supplier_name"));
                em.setCreated(rs.getString("created"));
                em.setPurcharser(rs.getString("full_name"));
                list_purchases.add(em);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return list_purchases;
    }
    

    public boolean updateProductQuery(Products product) {
        String query = "UPDATE products SET code = ?, name = ?, description = ?, unit_price = ?, created = ?, update = ?, category_id = ?"
                + " WHERE id = ?";

        Timestamp datetime = new Timestamp(new java.util.Date().getTime());
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, product.getCode());
            pst.setString(2, product.getName());
            pst.setString(3, product.getDescription());
            pst.setDouble(4, product.getUnit_price());
            pst.setTimestamp(5, (datetime));
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
        String query = "SELECT pro.*, ca.name AS category_name FROM products pro, categories ca "
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
