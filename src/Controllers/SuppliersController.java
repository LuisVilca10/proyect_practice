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

/**
 *
 * @author Luis
 */
public class SuppliersController implements ActionListener, MouseListener, KeyListener {

    private Suppliers sup;
    private SuppliersDao supdao;
    private SystemView views;
    String rol = rol_user;
    DefaultTableModel model = new DefaultTableModel();

    public SuppliersController(Suppliers sup, SuppliersDao supdao, SystemView views) {
        this.sup = sup;
        this.supdao = supdao;
        this.views = views;
        this.views.btn_register_supplier.addActionListener(this);
        this.views.btn_update_supplier.addActionListener(this);
        this.views.btn_cancel_supplier.addActionListener(this);
        this.views.btn_delete_supplier.addActionListener(this);
        this.views.suppliers_table.addMouseListener(this);
        this.views.jPanelSuppliers.addMouseListener(this);
        this.views.txt_search_supplier.addKeyListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btn_register_supplier) {
            if (views.txt_supplier_name.getText().equals("")
                    || views.txt_supplier_description.getText().equals("")
                    || views.txt_supplier_address.getText().equals("")
                    || views.txt_supplier_telephone.getText().equals("")
                    || views.txt_supplier_email.getText().equals("")
                    || views.cmb_supplier_city.getSelectedItem().toString().equals("")) {
                JOptionPane.showMessageDialog(views, "Todos los campos son obligatorios");
            } else {
                sup.setName(views.txt_supplier_name.getText().trim());
                sup.setDescription(views.txt_supplier_description.getText().trim());
                sup.setAddress(views.txt_supplier_address.getText().trim());
                sup.setTelephone(views.txt_supplier_telephone.getText().trim());
                sup.setEmail(views.txt_supplier_email.getText().trim());
                sup.setCity(views.cmb_supplier_city.getSelectedItem().toString());
                if (supdao.registerSuppliersQuery(sup)) {
                    cleanTable();
                    cleanFields();
                    listAllSupliers();
                    JOptionPane.showMessageDialog(views, "Cliente creado con exito");
                } else {
                    JOptionPane.showMessageDialog(views, "Error al crear cliente");
                }
            }
        } else if (e.getSource() == views.btn_update_supplier) {
            if (views.txt_supplier_id.getText().equals("")) {
                JOptionPane.showMessageDialog(views, "Seleccionar un cleinte");
            } else {
                if (views.txt_supplier_id.getText().equals("")
                        || views.txt_supplier_name.getText().equals("")
                        || views.txt_supplier_description.getText().equals("")
                        || views.txt_supplier_address.getText().equals("")
                        || views.txt_supplier_telephone.getText().equals("")
                        || views.txt_supplier_email.getText().equals("")
                        || views.cmb_supplier_city.getSelectedItem().toString().equals("")) {
                    JOptionPane.showMessageDialog(views, "Todos los campos son obligatorios");
                } else {
                    sup.setName(views.txt_supplier_name.getText().trim());
                    sup.setDescription(views.txt_supplier_description.getText().trim());
                    sup.setAddress(views.txt_supplier_address.getText().trim());
                    sup.setTelephone(views.txt_supplier_telephone.getText().trim());
                    sup.setEmail(views.txt_supplier_email.getText().trim());
                    sup.setCity(views.cmb_supplier_city.getSelectedItem().toString());
                    sup.setId(Integer.parseInt(views.txt_supplier_id.getText()));
                    if (supdao.updateSupplierQuery(sup)) {
                        cleanTable();
                        cleanFields();
                        listAllSupliers();
                        views.btn_register_supplier.setEnabled(true);
                        JOptionPane.showMessageDialog(views, "Cliente actualizado con esxito");
                    } else {
                        JOptionPane.showMessageDialog(views, "Error al crear cliente");
                    }
                }
            }
        } else if (e.getSource() == views.btn_delete_supplier) {
            int row = views.suppliers_table.getSelectedRow();
            String name = views.suppliers_table.getValueAt(row, 1).toString();
            if (row == -1) {
                JOptionPane.showMessageDialog(views, "Seleccionar al proveedor que desea eliminar ");
            } else {
                int id = Integer.parseInt(views.suppliers_table.getValueAt(row, 0).toString());
                int question = JOptionPane.showConfirmDialog(views, "Estas seguro de elimar al proveedor " + name + "?");
                if (question == 0 && supdao.deleteSupplierQuery(id) != false) {
                    cleanTable();
                    cleanFields();
                    views.btn_register_supplier.setEnabled(true);
                    listAllSupliers();
                    JOptionPane.showMessageDialog(views, "El proveedor a sido eliminado");
                }
            }
        } else if (e.getSource() == views.btn_cancel_supplier) {
            views.btn_register_supplier.setEnabled(true);
            cleanFields();
        }
    }

    public void listAllSupliers() {
        if (rol.equals("Administrador")) {
            List<Suppliers> list = supdao.listSuppliersQuery(views.txt_search_supplier.getText());
            model = (DefaultTableModel) views.suppliers_table.getModel();
            Object[] row = new Object[7];
            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getId();
                row[1] = list.get(i).getName();
                row[2] = list.get(i).getDescription();
                row[3] = list.get(i).getAddress();
                row[4] = list.get(i).getTelephone();
                row[5] = list.get(i).getEmail();
                row[6] = list.get(i).getCity();
                model.addRow(row);
            }
            views.suppliers_table.setModel(model);
        }
    }

    public void cleanTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1;
        }
    }

    public void cleanFields() {
        views.txt_supplier_id.setText("");
        views.txt_supplier_name.setText("");
        views.txt_supplier_address.setText("");
        views.txt_supplier_telephone.setText("");
        views.txt_supplier_email.setText("");
        views.txt_supplier_description.setText("");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.suppliers_table) {
            int row = views.suppliers_table.rowAtPoint(e.getPoint());
            views.txt_supplier_id.setText(views.suppliers_table.getValueAt(row, 0).toString());
            views.txt_supplier_name.setText(views.suppliers_table.getValueAt(row, 1).toString());
            views.txt_supplier_description.setText(views.suppliers_table.getValueAt(row, 2).toString());
            views.txt_supplier_address.setText(views.suppliers_table.getValueAt(row, 3).toString());
            views.txt_supplier_telephone.setText(views.suppliers_table.getValueAt(row, 4).toString());
            views.txt_supplier_email.setText(views.suppliers_table.getValueAt(row, 5).toString());
            views.cmb_supplier_city.setSelectedItem(views.suppliers_table.getValueAt(row, 6).toString());
            views.btn_register_supplier.setEnabled(false);
            views.txt_supplier_id.setEditable(false);
        } else if (e.getSource() == views.jPanelSuppliers) {
            if (rol.equals("Administrador")) {
                views.jTabbedPane1.setSelectedIndex(4);
                cleanTable();
                cleanFields();
                listAllSupliers();
            } else {
                views.jTabbedPane1.setEnabledAt(4, false);
                views.jLabelSuppliers.setEnabled(false);
                JOptionPane.showMessageDialog(views, "no tienes permisos");
            }
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
        if (e.getSource() == views.txt_search_supplier) {
            cleanTable();
            listAllSupliers();

        }
    }
}
