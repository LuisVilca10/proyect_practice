
package Controllers;

import Views.SystemView;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SettingsController implements MouseListener{
    
    private SystemView views;
    
    public SettingsController(SystemView views){
        this.views = views;
        this.views.jPanelProducts.addMouseListener(this);
        this.views.jPanelPurchases.addMouseListener(this);
        this.views.jPanelCustomers.addMouseListener(this);
        this.views.jPanelEmployoes.addMouseListener(this);
        this.views.jPanelSuppliers.addMouseListener(this);
        this.views.jPanelCategories.addMouseListener(this);
        this.views.jPanelReports.addMouseListener(this);
        this.views.jPanelSettings.addMouseListener(this);     
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if(e.getSource() == views.jPanelProducts){
            views.jPanelProducts.setBackground(new Color(171, 196, 190));
        }else if(e.getSource() == views.jPanelPurchases){
            views.jPanelPurchases.setBackground(new Color(171, 196, 190));
        }else if(e.getSource() == views.jPanelCustomers){
            views.jPanelCustomers.setBackground(new Color(171, 196, 190));
        }else if(e.getSource() == views.jPanelEmployoes){
            views.jPanelEmployoes.setBackground(new Color(171, 196, 190));
        }else if(e.getSource() == views.jPanelSuppliers){
            views.jPanelSuppliers.setBackground(new Color(171, 196, 190));
        }else if(e.getSource() == views.jPanelCategories){
            views.jPanelCategories.setBackground(new Color(171, 196, 190));
        }else if(e.getSource() == views.jPanelReports){
            views.jPanelReports.setBackground(new Color(171, 196, 190));
        }else if(e.getSource() == views.jPanelSettings){
            views.jPanelSettings.setBackground(new Color(171, 196, 190));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(e.getSource() == views.jPanelProducts){
            views.jPanelProducts.setBackground(new Color(38,104,85));
        }else if(e.getSource() == views.jPanelPurchases){
            views.jPanelPurchases.setBackground(new Color(38,104,85));
        }else if(e.getSource() == views.jPanelCustomers){
            views.jPanelCustomers.setBackground(new Color(38,104,85));
        }else if(e.getSource() == views.jPanelEmployoes){
            views.jPanelEmployoes.setBackground(new Color(38,104,85));
        }else if(e.getSource() == views.jPanelSuppliers){
            views.jPanelSuppliers.setBackground(new Color(38,104,85));
        }else if(e.getSource() == views.jPanelCategories){
            views.jPanelCategories.setBackground(new Color(38,104,85));
        }else if(e.getSource() == views.jPanelReports){
            views.jPanelReports.setBackground(new Color(38,104,85));
        }else if(e.getSource() == views.jPanelSettings){
            views.jPanelSettings.setBackground(new Color(38,104,85));
        }
    }
    
}
