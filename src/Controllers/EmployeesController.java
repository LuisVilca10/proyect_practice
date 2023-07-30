package Controllers;

import Models.*;
import Views.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class EmployeesController implements ActionListener, MouseListener, KeyListener {

    private Employees em;
    private EmployeesDao emdao;
    private SystemView views;
    String rol = EmployeesDao.rol_user;
    DefaultTableModel model = new DefaultTableModel();

    public EmployeesController(Employees em, EmployeesDao emdao, SystemView views) {
        this.em = em;
        this.emdao = emdao;
        this.views = views;
        this.views.btn_register_employee.addActionListener(this);
        this.views.btn_update_employee.addActionListener(this);
        this.views.employees_table.addMouseListener(this);
        this.views.txt_search_employee.addKeyListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == views.btn_register_employee) {
            if (views.txt_employee_id.getText().equals("")
                    || views.txt_employee_fullname.getText().equals("")
                    || views.txt_employee_username.getText().equals("")
                    || views.txt_employee_address.getText().equals("")
                    || views.txt_employee_telephone.getText().equals("")
                    || views.txt_employee_email.getText().equals("")
                    || views.cmb_rol.getSelectedItem().toString().equals("")
                    || String.valueOf(views.txt_employee_password.getPassword()).equals("")) {
                JOptionPane.showMessageDialog(views, "Todos los campos son obligatorios");
            } else {
                em.setId(Integer.parseInt(views.txt_employee_id.getText().trim()));
                em.setFull_name(views.txt_employee_fullname.getText().trim());
                em.setUsername(views.txt_employee_username.getText().trim());
                em.setAddress(views.txt_employee_address.getText().trim());
                em.setTelephone(views.txt_employee_telephone.getText().trim());
                em.setEmail(views.txt_employee_email.getText().trim());
                em.setPassword(String.valueOf(views.txt_employee_password.getPassword()));
                em.setRol(views.cmb_rol.getSelectedItem().toString());

                if (emdao.registerEmployeeQuery(em)) {
                    cleanTable();
                    cleanFields();
                    listAllEmployees();
                    JOptionPane.showMessageDialog(views, "Todos los campos se registraron");
                } else {
                    JOptionPane.showMessageDialog(views, "Error al registrar el empleado");
                }
            }
        } else if (e.getSource() == views.btn_update_employee) {
            if (views.txt_employee_id.equals("")) {
                JOptionPane.showMessageDialog(views, "Selecionar una fila");
            } else {
                if (views.txt_employee_id.getText().equals("")
                        || views.txt_employee_fullname.getText().equals("")
                        || views.txt_employee_address.getText().equals("")
                        || views.txt_employee_telephone.getText().equals("")
                        || views.txt_employee_email.getText().equals("")
                        || views.cmb_rol.getSelectedItem().toString().equals("")) {
                    JOptionPane.showMessageDialog(views, "Todos los campos son obligatorios");
                } else {
                    em.setId(Integer.parseInt(views.txt_employee_id.getText().trim()));
                    em.setFull_name(views.txt_employee_fullname.getText().trim());
                    em.setUsername(views.txt_employee_username.getText().trim());
                    em.setAddress(views.txt_employee_address.getText().trim());
                    em.setTelephone(views.txt_employee_telephone.getText().trim());
                    em.setEmail(views.txt_employee_email.getText().trim());
                    em.setPassword(String.valueOf(views.txt_employee_password.getPassword()));
                    em.setRol(views.cmb_rol.getSelectedItem().toString());
                    if (emdao.updateEmployeeQuery(em)) {
                        cleanTable();
                        cleanFields();
                        listAllEmployees();
                        views.btn_register_employee.setEnabled(true);
                        JOptionPane.showMessageDialog(views, "Todos los campos se actulizaron");
                    } else {
                        JOptionPane.showMessageDialog(views, "Error al actualizar el empleado");
                    }
                }
            }
        }
    }

    public void listAllEmployees() {
        if (rol.equals("Administrador")) {
            List<Employees> list = emdao.listEmployeeQuery(views.txt_search_employee.getText());
            model = (DefaultTableModel) views.employees_table.getModel();
            Object[] row = new Object[7];
            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getId();
                row[1] = list.get(i).getFull_name();
                row[2] = list.get(i).getUsername();
                row[3] = list.get(i).getAddress();
                row[4] = list.get(i).getTelephone();
                row[5] = list.get(i).getEmail();
                row[6] = list.get(i).getRol();
                model.addRow(row);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.employees_table) {
            int row = views.employees_table.rowAtPoint(e.getPoint());

            views.txt_employee_id.setText(views.employees_table.getValueAt(row, 0).toString());
            views.txt_employee_fullname.setText(views.employees_table.getValueAt(row, 1).toString());
            views.txt_employee_username.setText(views.employees_table.getValueAt(row, 2).toString());
            views.txt_employee_address.setText(views.employees_table.getValueAt(row, 3).toString());
            views.txt_employee_telephone.setText(views.employees_table.getValueAt(row, 4).toString());
            views.txt_employee_email.setText(views.employees_table.getValueAt(row, 5).toString());
            views.cmb_rol.setSelectedItem(views.employees_table.getValueAt(row, 6).toString());

            //deshabilitar
            views.txt_employee_id.setEditable(false);
            views.txt_employee_password.setEnabled(false);
            views.btn_register_employee.setEnabled(false);

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
        if (e.getSource() == views.txt_search_employee) {
            cleanTable();
            listAllEmployees();
        }
    }
    public void cleanFields() {
       views.txt_employee_id.setText("");
       views.txt_employee_id.setEditable(true);
       views.txt_employee_fullname.setText("");
       views.txt_employee_username.setText("");
       views.txt_employee_address.setText("");
       views.txt_employee_telephone.setText("");
       views.txt_employee_email.setText("");
       views.txt_employee_password.setText("");
       views.cmb_rol.setSelectedIndex(0);
       
    }
    public void cleanTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1;
        }
    }
}
