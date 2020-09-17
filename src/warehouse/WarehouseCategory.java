/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warehouse;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author gabrieladvice
 */


public class WarehouseCategory {
    
    private static WarehouseModel model;
    private static ArrayList<String> categories;
    private static JFrame category;
    private static JPanel mainPanel;
    private static JPanel backPanel;
    private static JPanel catPanel;
    private static JPanel newCatPanel;
    private static String backText;
    
    public WarehouseCategory(JFrame category){   
        model = new WarehouseModel();
        
        WarehouseCategory.category = category;
        
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.white);
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        
        backPanel = new JPanel();
        backPanel.setBackground(Color.white);
        backPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        backPanel.setBounds(0,50,150,50);      

        catPanel = new JPanel();
        catPanel.setBackground(Color.white);
        catPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        catPanel.setBounds(0,100,150,400);

        newCatPanel = new JPanel();
        newCatPanel.setBackground(Color.white);
        newCatPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        newCatPanel.setBounds(0,500,150,50);
        
        mainPanel.add(backPanel);
        mainPanel.add(catPanel);
        mainPanel.add(newCatPanel);
        
        category.add(mainPanel);
        category.setSize(250,300);
        category.setVisible(true);
        category.setLocationRelativeTo(null);   
        
        CategoryView();
    }    
    public void CategoryView(){
        repaintPanel();
        
        this.categories = model.getCategory();    

        JButton back = new JButton();
        back.setText(getBackBtn());
        back.setSize(100,100);
        
        JButton newCat = new JButton("+ new category");
        newCat.setSize(100,100);
        
        newCatPanel.add(newCat);
        
        if(model.getCategoryId()!=0 || model.getParentId()!=0){
            backPanel.add(back);            
        }
        
        ActionListener e = new ActionListener(){
                public void actionPerformed(ActionEvent ae) {

                try {
                    model.setSubCategory(ae.getActionCommand());
                    setBackBtn(model.getParentName());
                    removePanel();
                    CategoryView();
                } catch (SQLException ex) {
                    Logger.getLogger(Warehouse.class.getName()).log(Level.SEVERE, null, ex);
                }
            }   
        };


        for (int i = 0; i<this.categories.size() ; i++) {
                JButton btn = new JButton(this.categories.get(i));
                btn.setSize(50,50);
                btn.addActionListener(e);
                catPanel.add(btn);
        }
        
        
        back.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {

                try {
                    model.setSubBackCategory();
                    setBackBtn(model.getParentName());
                    removePanel();
                    CategoryView();
                } catch (SQLException ex) {
                    Logger.getLogger(Warehouse.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
        
        newCat.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                    JFrame category = new JFrame("New Category");
                    new WarehouseAddCategory(category);
                    category.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            try {
                                model.setCategory();
                                WarehouseCategory.categories = model.getCategory();                                
                                removePanel();
                                CategoryView();
                            } catch (SQLException ex) {
                               Logger.getLogger(Warehouse.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
            }
        });


    }
    private void setBackBtn(String backText){ 
        WarehouseCategory.backText=backText;       
    }
    
    private String getBackBtn(){
        return "< "+model.getParentName();
    }
    
    
    
    private void removePanel(){
        backPanel.removeAll();
        catPanel.removeAll();
        newCatPanel.removeAll();
    }
    
    private void repaintPanel(){
        catPanel.revalidate();
        catPanel.repaint();
        
        backPanel.revalidate();
        backPanel.repaint();
        
        newCatPanel.revalidate();
        newCatPanel.repaint(); 
    }

}
