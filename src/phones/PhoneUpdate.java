/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package catalog;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import phones.PhoneModel;

/**
 *
 * @author Gabika
 */
public class PhoneUpdate {

    public PhoneUpdate() throws SQLException{
        PhoneModel model = new PhoneModel();
                
        JFrame update = new JFrame("Update - "+model.getPhoneName());   
        
        JLabel label = new JLabel("Update phone");                
        label.setBounds(10,10,300,30);        
        label.setFont(new Font("Serif", Font.PLAIN, 24));

                JLabel name = new JLabel("Model: ");
                name.setBounds(10,50,500,20);      
                
                JTextField nameField = new JTextField(model.getPhoneName());
                nameField.setBounds(100,50,300,20);
                                
                JLabel imei = new JLabel("IMEI: ");
                imei.setBounds(10,75,500,20);
                               
                JTextField imeiField = new JTextField(model.getImei());
                imeiField.setBounds(100,75,300,20);
                
                JCheckBox status = new JCheckBox("New");
                status.setBounds(100,100,80,20);
                if(model.getStatus().equals("1")){
                    status.setSelected(true);
                }else{
                    status.setSelected(false);
                }
                
                JLabel accessorries = new JLabel("Accessories: ");
                accessorries.setBounds(10,125,90,20);
                
                JCheckBox bat=new JCheckBox("Battery",true);   
                bat.setBounds(100,125,80,20);
                update.add(bat);
                if(status.isSelected()){ bat.setEnabled(false); }
                if(model.getBattery().equalsIgnoreCase("1")){
                    bat.setSelected(true);
                }else{
                    bat.setSelected(false);
                }
                
                JCheckBox chr=new JCheckBox("Charger",true);
                chr.setBounds(190,125,80,20);
                update.add(chr);
                if(status.isSelected()){ chr.setEnabled(false); }                
                if(model.getCharger().equalsIgnoreCase("1")){
                    chr.setSelected(true);
                }else{
                    chr.setSelected(false);
                }
                
                JCheckBox box=new JCheckBox("Box",true);      
                box.setBounds(280,125,60,20);
                update.add(box);
                if(status.isSelected()){ box.setEnabled(false); }
                if(model.getBox().equalsIgnoreCase("1")){
                    box.setSelected(true);
                }else{
                    box.setSelected(false);
                }
                                
                JLabel buy = new JLabel("Buy: ");
                buy.setBounds(10,150,500,20);
                                
                JTextField buyField = new JTextField(model.getBuy());
                buyField.setBounds(100,150,300,20);
                
                JLabel supplier = new JLabel("Provider: ");
                supplier.setBounds(10,175,300,20);
                                
                JComboBox<ArrayList> supplierField = new JComboBox(model.getSuppliers().toArray());
                supplierField.setBounds(100,175,300,20);
                supplierField.setSelectedItem(model.getSupplierName());
                                
                JLabel serviceProvider = new JLabel("Supplier: ");
                serviceProvider.setBounds(10,200,300,20);
                                
                JComboBox<ArrayList> serviceProviderField = new JComboBox(model.getProvider().toArray());
                serviceProviderField.setBounds(100,200,300,20);
                serviceProviderField.setSelectedItem(model.getProviderName());                              
                                
                JLabel comment = new JLabel("Comment: ");
                comment.setBounds(10,225,300,20);
                                
                JTextField commentField = new JTextField(model.getComment());
                commentField.setBounds(100,225,300,20);
                                
                JButton updateBtn = new JButton("Update");
                updateBtn.setBounds(10,290,100,20);
                
                updateBtn.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        model.updatePhoneDetails(nameField.getText(),imeiField.getText(),Integer.parseInt(buyField.getText()),bat.isSelected(),chr.isSelected(),box.isSelected(),status.isSelected(),commentField.getText(), (String) serviceProviderField.getSelectedItem(), (String) supplierField.getSelectedItem());
                        JOptionPane.showMessageDialog(null, "Update successfull!");
                        try {
                            new PhoneDetails(nameField.getText(), imeiField.getText());
                            update.dispose();
                        } catch (SQLException ex) {
                            Logger.getLogger(PhoneUpdate.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                    
                });
                
            update.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    try {
                        new PhoneDetails();
                        update.dispose();
                    } catch (SQLException ex) {
                        Logger.getLogger(PhoneUpdate.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
                    
                
            
            updateBtn.setMnemonic(KeyEvent.VK_ENTER);
            
            if(supplierField.getSelectedItem().equals("felvásárlás")){
                supplierField.setEnabled(false);
            }
            
            status.addActionListener(new ActionListener(){
            @Override
                public void actionPerformed(ActionEvent ae) {
                    if(status.isSelected()){
                        bat.setSelected(true);
                        chr.setSelected(true);
                        box.setSelected(true);
                        bat.setEnabled(false);
                        chr.setEnabled(false);
                        box.setEnabled(false);  
                    }else{
                        bat.setSelected(false);
                        chr.setSelected(false);
                        box.setSelected(false);
                        bat.setEnabled(true);
                        chr.setEnabled(true);
                        box.setEnabled(true); 
                    }
                }

        });
                update.add(label);
                update.add(name);
                update.add(nameField);
                update.add(imei);
                update.add(imeiField);
                update.add(accessorries);
                update.add(buy);
                update.add(buyField);
                update.add(comment);
                update.add(commentField);
                update.add(supplier);
                update.add(supplierField);
                update.add(serviceProvider);
                update.add(serviceProviderField);
                update.add(status);
                update.add(updateBtn);
                update.setLayout(null);
                update.setSize(450,350);
                update.setVisible(true);
                update.setLocationRelativeTo(null);
                update.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            }


}
