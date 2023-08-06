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

public class CategoriesController implements ActionListener, MouseListener, KeyListener {

    private Categories cat;
    private CategoriesDao catdao;
    private SystemView views;
    String rol = EmployeesDao.rol_user;
    DefaultTableModel model = new DefaultTableModel();

    public CategoriesController(Categories cat, CategoriesDao catdao, SystemView views) {
        this.cat = cat;
        this.catdao = catdao;
        this.views = views;
        this.views.btn_register_category.addActionListener(this);
        this.views.categories_table.addMouseListener(this);
        this.views.txt_search_category.addKeyListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btn_register_category) {
            if (views.txt_category_name.getText().equals("")) {
                JOptionPane.showMessageDialog(views, "Todos los campos son obligatorios");
            } else {
                cat.setName(views.txt_category_name.getText().trim());
                if (catdao.registerCategoriesQuery(cat)) {
                    cleanFields();
                    cleanTable();
                    listAllCategories();
                    JOptionPane.showMessageDialog(views, "La categoria a sido registrada");
                } else {
                    JOptionPane.showMessageDialog(views, "Error al registrar la categoria");
                }
            }
        }
    }

    public void listAllCategories() {
        if (rol.equals("Administrador")) {
        List<Categories> list = catdao.listCategoriesQuery(views.txt_search_category.getText());
        model = (DefaultTableModel) views.categories_table.getModel();
        Object[] row = new Object[2];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getId();
            row[1] = list.get(i).getName();
            model.addRow(row);
        }
        views.categories_table.setModel(model);
       }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.categories_table) {
            int row = views.categories_table.rowAtPoint(e.getPoint());
            views.txt_category_id.setText(views.categories_table.getValueAt(row, 0).toString());
            views.txt_category_name.setText(views.categories_table.getValueAt(row, 1).toString());
            views.btn_register_category.setEnabled(false);
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
        if (e.getSource() == views.txt_search_category) {
            cleanTable();
            listAllCategories();
        }

    }

    public void cleanTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1;
        }
    }

    public void cleanFields() {
        views.txt_category_id.setText("");
        views.txt_category_name.setText("");
    }
}
