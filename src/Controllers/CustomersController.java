package Controllers;

import Models.*;
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
public class CustomersController implements ActionListener, MouseListener, KeyListener {

    private Customers cus;
    private CustomersDao cusdao;
    private SystemView views;

    DefaultTableModel model = new DefaultTableModel();

    public CustomersController(Customers cus, CustomersDao cusdao, SystemView views) {
        this.cus = cus;
        this.cusdao = cusdao;
        this.views = views;
        this.views.btn_register_customer.addActionListener(this);
        this.views.btn_cancel_customer.addActionListener(this);
        this.views.btn_update_customer.addActionListener(this);
        this.views.customers_table.addMouseListener(this);
        this.views.txt_search_customer.addKeyListener(this);
        this.views.btn_delete_customer.addActionListener(this);
        this.views.jPanelCustomers.addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btn_register_customer) {
            if (views.txt_customer_id.getText().equals("")
                    || views.txt_customer_fullname.getText().equals("")
                    || views.txt_customer_adress.getText().equals("")
                    || views.txt_customer_telephone.getText().equals("")
                    || views.txt_customer_email.getText().equals("")) {
                JOptionPane.showMessageDialog(views, "Todos los campos son obligatorios");
            } else {
                cus.setId(Integer.parseInt(views.txt_customer_id.getText().trim()));
                cus.setFull_name(views.txt_customer_fullname.getText().trim());
                cus.setAddress(views.txt_customer_adress.getText().trim());
                cus.setTelephone(views.txt_customer_telephone.getText().trim());
                cus.setEmail(views.txt_customer_email.getText().trim());
                if (cusdao.registerCustomerQuery(cus)) {
                    cleanTable();
                    cleanFields();
                    listAllCustomers();
                    JOptionPane.showMessageDialog(views, "Cliente creado con exito");
                } else {
                    JOptionPane.showMessageDialog(views, "Error al crear cliente");
                }

            }
        } else if (e.getSource() == views.btn_update_customer) {
            if (views.txt_customer_id.equals("")) {
                JOptionPane.showMessageDialog(views, "Selecionar al cliente que desea actualizar");
            } else {
                if (views.txt_customer_id.getText().equals("")
                        || views.txt_customer_fullname.getText().equals("")
                        || views.txt_customer_adress.getText().equals("")
                        || views.txt_customer_telephone.getText().equals("")
                        || views.txt_customer_email.getText().equals("")) {
                    JOptionPane.showMessageDialog(views, "Todo los campos son obligatorios");
                } else {
                    cus.setId(Integer.parseInt(views.txt_customer_id.getText().trim()));
                    cus.setFull_name(views.txt_customer_fullname.getText().trim());
                    cus.setAddress(views.txt_customer_adress.getText().trim());
                    cus.setTelephone(views.txt_customer_telephone.getText().trim());
                    cus.setEmail(views.txt_customer_email.getText().trim());
                    if (cusdao.updateCustomerQuery(cus)) {
                        cleanTable();
                        cleanFields();
                        listAllCustomers();
                        views.btn_register_customer.setEnabled(true);
                        JOptionPane.showMessageDialog(views, "Cliente actualizado con exito");
                    } else {
                        JOptionPane.showMessageDialog(views, "Error al actualizar el cliente");
                    }
                }
            }
        } else if (e.getSource() == views.btn_delete_customer) {
            int row = views.customers_table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(views, "Seleccione el cliente que desea eliminar");
            } else {
                String clienteSeleccionado = views.customers_table.getValueAt(row, 1).toString(); // Obtiene el nombre del cliente
                int id = Integer.parseInt(views.customers_table.getValueAt(row, 0).toString());
                int question = JOptionPane.showConfirmDialog(views, "¿Estas seguro de elimar al cliente " + clienteSeleccionado + "?");
                if (question == 0 && cusdao.deleteCustomerQuery(id) != false) {
                    cleanTable();
                    cleanFields();
                    views.btn_register_customer.setEnabled(true);
                    listAllCustomers();
                    JOptionPane.showMessageDialog(views, "El cliente a sido eliminado");
                }
            }
        } else if (e.getSource() == views.btn_cancel_customer) {
            views.btn_register_customer.setEnabled(true);
            cleanFields();
        }

    }

    public void listAllCustomers() {
        List<Customers> list = cusdao.listCustomerQuery(views.txt_search_customer.getText());
        model = (DefaultTableModel) views.customers_table.getModel();
        Object[] row = new Object[5];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getId();
            row[1] = list.get(i).getFull_name();
            row[2] = list.get(i).getTelephone();
            row[3] = list.get(i).getAddress();
            row[4] = list.get(i).getEmail();
            model.addRow(row);
        }
        views.customers_table.setModel(model);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.customers_table) {
            int row = views.customers_table.rowAtPoint(e.getPoint());
            views.txt_customer_id.setText(views.customers_table.getValueAt(row, 0).toString());
            views.txt_customer_fullname.setText(views.customers_table.getValueAt(row, 1).toString());
            views.txt_customer_telephone.setText(views.customers_table.getValueAt(row, 2).toString());
            views.txt_customer_adress.setText(views.customers_table.getValueAt(row, 3).toString());
            views.txt_customer_email.setText(views.customers_table.getValueAt(row, 4).toString());

            views.btn_register_customer.setEnabled(false);
            views.txt_customer_id.setEditable(false);

        } else if (e.getSource() == views.jPanelCustomers) {
            views.jTabbedPane1.setSelectedIndex(2);
            cleanTable();
            cleanFields();
            listAllCustomers();
            
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
        if (e.getSource() == views.txt_search_customer) {
            cleanTable();
            listAllCustomers();
        }
    }

    public void cleanFields() {
        views.txt_customer_id.setText("");
        views.txt_customer_id.setEditable(true);
        views.txt_customer_fullname.setText("");
        views.txt_customer_adress.setText("");
        views.txt_customer_telephone.setText("");
        views.txt_customer_email.setText("");
    }

    public void cleanTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1;
        }
    }
}
