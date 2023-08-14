package Controllers;

import Models.*;
import static Models.EmployeesDao.rol_user;
import Views.SystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class PurchasesController implements KeyListener, ActionListener {

    private Purchases pur;
    private PurchasesDao purdao;
    private SystemView views;
    private int getIdSupplier = 0;
    private int item = 0;
    DefaultTableModel model = new DefaultTableModel();
    DefaultTableModel temp;
    Products pro = new Products();
    ProductsDao prodao = new ProductsDao();
    String rol = rol_user;

    public PurchasesController(Purchases pur, PurchasesDao purdao, SystemView views) {
        this.pur = pur;
        this.purdao = purdao;
        this.views = views;
        this.views.txt_purchase_product_code.addKeyListener(this);
        this.views.txt_purchase_price.addKeyListener(this);
        this.views.btn_add_product_to_buy.addActionListener(this);

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getSource() == views.txt_purchase_product_code) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (views.txt_purchase_product_code.getText().equals("")) {
                    JOptionPane.showMessageDialog(views, "ingresar el codigo del producto");
                } else {
                    int id = Integer.parseInt(views.txt_purchase_product_code.getText());
                    pro = prodao.searchCode(id);
                    views.txt_purchase_product_name.setText(pro.getName());
                    views.txt_purchase_id.setText("" + pro.getId());
                    views.txt_purchase_amount.requestFocus();
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == views.txt_purchase_price) {
            int quantity;
            double price = 0.0;

            String amountText = views.txt_purchase_amount.getText();
            if (amountText.isEmpty()) {
                quantity = 1;
                views.txt_purchase_amount.setText("" + price);
            } else {
                quantity = Integer.parseInt(amountText);
                price = Double.parseDouble(views.txt_purchase_price.getText());
                views.txt_purchase_subtotal.setText("" + quantity * price);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btn_add_product_to_buy) {
            DynamicCombobox sup_cmb = (DynamicCombobox) views.cmb_purchase_supplier.getSelectedItem();
            int sup_id = sup_cmb.getId();

            if (getIdSupplier == 0) {
                getIdSupplier = sup_id;
            } else {
                if (getIdSupplier != sup_id) {
                    JOptionPane.showMessageDialog(views, "no se puede realizar una misma compra a varios proveedores");
                } else {
                    int amount = Integer.parseInt(views.txt_purchase_amount.getText());
                    String product_name = views.txt_purchase_product_name.getText();
                    double price = Double.parseDouble(views.txt_purchase_price.getText());
                    int purchase_id = Integer.parseInt(views.txt_purchase_id.getText());
                    String sup_name = views.cmb_purchase_supplier.getSelectedItem().toString();

                    if (amount > 0) {
                        temp = (DefaultTableModel) views.purchases_table.getModel();
                        for (int i = 0; i < views.purchases_table.getRowCount(); i++) {
                            if (views.purchases_table.getValueAt(i, 1).equals(views.txt_purchase_product_name.getText())) {
                                JOptionPane.showMessageDialog(views, "El producto ya esta registrado en la tabla");
                                return;
                            }
                        }

                        ArrayList list = new ArrayList();
                        item = 1;
                        list.add(item);
                        list.add(purchase_id);
                        list.add(product_name);
                        list.add(amount);
                        list.add(price);
                        list.add(amount * price);
                        list.add(sup_name);

                        Object[] obj = new Object[6];
                        obj[0] = list.get(1);
                        obj[1] = list.get(2);
                        obj[2] = list.get(3);
                        obj[3] = list.get(4);
                        obj[4] = list.get(5);
                        obj[5] = list.get(6);
                        temp.addRow(obj);
                        views.purchases_table.setModel(temp);
                        cleanFieldsPurchases();
                        views.cmb_purchase_supplier.setEditable(false);
                        views.txt_purchase_product_code.requestFocus();
                        calculatePurchase();
                    }
                }
            }
        }
    }

    public void cleanFieldsPurchases() {
        views.txt_purchase_product_name.setText("");
        views.txt_purchase_price.setText("");
        views.txt_purchase_amount.setText("");
        views.txt_purchase_product_code.setText("");
        views.txt_purchase_subtotal.setText("");
        views.txt_purchase_id.setText("");
        views.txt_purchase_total_to_pa.setText("");
    }

    public void calculatePurchase() {
        double total = 0.0;
        int numrow = views.purchases_table.getRowCount();
        for (int i = 0; i < numrow; i++) {
            total = total + Double.parseDouble(String.valueOf(views.purchases_table.getValueAt(i, 4)));
        }
        views.txt_purchase_total_to_pa.setText("" + total);
    }
}
