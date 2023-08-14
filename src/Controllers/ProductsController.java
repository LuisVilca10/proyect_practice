package Controllers;

import Models.*;
import static Models.EmployeesDao.rol_user;
import Views.SystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ProductsController implements ActionListener, MouseListener, KeyListener {

    private Products pro;
    private ProductsDao prodao;
    private SystemView views;
    String rol = rol_user;
    DefaultTableModel model = new DefaultTableModel();

    public ProductsController(Products pro, ProductsDao prodao, SystemView views) {
        this.pro = pro;
        this.prodao = prodao;
        this.views = views;
        this.views.btn_register_product.addActionListener(this);
        this.views.btn_update_product.addActionListener(this);
        this.views.btn_delete_product.addActionListener(this);
        this.views.btn_cancel_product.addActionListener(this);
        this.views.products_table.addMouseListener(this);
        this.views.txt_search_product.addKeyListener(this);
        this.views.jPanelProducts.addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btn_register_product) {
            if (views.txt_product_code.getText().equals("")
                    || views.txt_product_name.getText().equals("")
                    || views.txt_product_description.getText().equals("")
                    || views.txt_product_unit_price.getText().equals("")
                    || views.cmb_product_category.getSelectedItem().toString().equals("")) {
                JOptionPane.showMessageDialog(views, "todos los campos son obligatorios");
            } else {
                pro.setCode(Integer.parseInt(views.txt_product_code.getText()));
                pro.setName(views.txt_product_name.getText().trim());
                pro.setDescription(views.txt_product_description.getText().trim());
                pro.setUnit_price(Double.parseDouble(views.txt_product_unit_price.getText()));
                DynamicCombobox cate_id = (DynamicCombobox) views.cmb_product_category.getSelectedItem();
                pro.setCategory_id(cate_id.getId());
                if (prodao.registerProductsQuery(pro)) {
                    cleanTable();
                    cleanFields();
                    listAllProducts();
                    JOptionPane.showMessageDialog(views, "el producto a sido registrado con exito");
                } else {
                    JOptionPane.showMessageDialog(views, "ha ocurrido un error");
                }
            }
        } else if (e.getSource() == views.btn_update_product) {
            if (views.txt_product_id.getText().equals("")) {
                JOptionPane.showMessageDialog(views, "Seleccionar un producto para modificar");
            } else {
                if (views.txt_product_code.getText().equals("")
                        || views.txt_product_name.getText().equals("")
                        || views.txt_product_description.getText().equals("")
                        || views.txt_product_unit_price.getText().equals("")
                        || views.cmb_product_category.getSelectedItem().toString().equals("")) {
                    JOptionPane.showMessageDialog(views, "todos los campos son obligatorios");
                } else {
                    pro.setCode(Integer.parseInt(views.txt_product_code.getText()));
                    pro.setName(views.txt_product_name.getText().trim());
                    pro.setDescription(views.txt_product_description.getText().trim());
                    pro.setUnit_price(Double.parseDouble(views.txt_product_unit_price.getText()));
                    DynamicCombobox cate_id = (DynamicCombobox) views.cmb_product_category.getSelectedItem();
                    pro.setCategory_id(cate_id.getId());
                    pro.setId(Integer.parseInt(views.txt_product_id.getText()));
                    if (prodao.updateProductQuery(pro)) {
                        cleanTable();
                        cleanFields();
                        listAllProducts();
                        views.btn_register_product.setEnabled(true);
                        JOptionPane.showMessageDialog(views, "el producto a sido actualizado con exito");
                    } else {
                        JOptionPane.showMessageDialog(views, "ha ocurrido un error");
                    }
                }
            }
        } else if (e.getSource() == views.btn_delete_product) {
            int row = views.products_table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(views, "Selecionar un producto que deseé eliminar");
            } else {
                String name = views.products_table.getValueAt(row, 2).toString();
                int id = Integer.parseInt(views.products_table.getValueAt(row, 0).toString());
                int question = JOptionPane.showConfirmDialog(views, "Deseas eliminar el producto " + name + "?");
                if (question == 0 && prodao.deleteProductQuery(id) != false) {
                    cleanFields();
                    cleanTable();
                    views.btn_register_category.setEnabled(true);
                    listAllProducts();
                    JOptionPane.showMessageDialog(views, "el producto se a eliminada con exito");
                }
            }
        } else if (e.getSource() == views.btn_cancel_product) {
            views.btn_register_product.setEnabled(true);
            cleanFields();
        }
    }

    public void listAllProducts() {
        if (rol.equals("Administrador") || rol.equals("Auxiliar")) {
            List<Products> list = prodao.listProductsQuery(views.txt_search_product.getText());
            model = (DefaultTableModel) views.products_table.getModel();
            Object[] row = new Object[7];
            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getId();
                row[1] = list.get(i).getCode();
                row[2] = list.get(i).getName();
                row[3] = list.get(i).getDescription();
                row[4] = list.get(i).getUnit_price();
                row[5] = list.get(i).getProduct_quantity();
                row[6] = list.get(i).getCategory_name();
                model.addRow(row);
            }
            views.products_table.setModel(model);
            if (rol.equals("Auxiliar")) {
                views.btn_register_product.setEnabled(false);
                views.btn_update_product.setEnabled(false);
                views.btn_cancel_product.setEnabled(false);
                views.btn_delete_product.setEnabled(false);
                views.txt_product_code.setEnabled(false);
                views.txt_product_description.setEnabled(false);
                views.txt_product_id.setEditable(false);
                views.txt_product_name.setEditable(false);
                views.txt_product_unit_price.setEditable(false);
            }
        }
    }

    public void cleanTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1;
        }
    }

    public void cleanFields() {
        views.txt_product_code.setText("");
        views.txt_product_name.setText("");
        views.txt_product_unit_price.setText("");
        views.txt_product_description.setText("");
        views.cmb_product_category.setSelectedIndex(0);
        views.txt_product_id.setText("");

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.products_table) {
            int row = views.products_table.rowAtPoint(e.getPoint());
            views.txt_product_id.setText(views.products_table.getValueAt(row, 0).toString());
            pro = prodao.searchProduct(Integer.parseInt(views.txt_product_id.getText()));
            views.txt_product_code.setText("" + pro.getCode());
            views.txt_product_name.setText(pro.getName());
            views.txt_product_description.setText(pro.getDescription());
            views.txt_product_unit_price.setText("" + pro.getUnit_price());
            views.cmb_product_category.setSelectedItem(new DynamicCombobox(pro.getCategory_id(), pro.getCategory_name()));
            views.btn_register_product.setEnabled(false);
        } else if (e.getSource() == views.jPanelProducts){
            views.jTabbedPane1.setSelectedIndex(0);
            cleanFields();
            cleanTable();
            listAllProducts();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == views.txt_search_product) {
            cleanTable();
            listAllProducts();
        }
    }
}
