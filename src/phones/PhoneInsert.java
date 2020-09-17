/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package catalog;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PrinterException;
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
import phones.PhonePrint;

/**
 *
 * @author Gabika
 */
public class PhoneInsert {

    public PhoneInsert() throws SQLException{
        
        JFrame insert = new JFrame("Add new phone");
        PhoneModel model = new PhoneModel();
        
        JLabel label = new JLabel("Add new phone");
        label.setBounds(10,10,300,30);
        label.setFont(new Font("Serif", Font.PLAIN, 24));

        JLabel name = new JLabel("Model: ");
        name.setBounds(10,50,500,20);      
                
        JTextField nameField = new JTextField();
        nameField.setBounds(100,50,300,20);
                                
        JLabel imei = new JLabel("IMEI: ");
        imei.setBounds(10,75,500,20);
                               
        JTextField imeiField = new JTextField();
        imeiField.setBounds(100,75,300,20);
        
        JCheckBox status = new JCheckBox("New");
        status.setBounds(100,100,80,20);
                
        JLabel accessorries = new JLabel("Accessories: ");
        accessorries.setBounds(10,125,90,20);
                
        JCheckBox bat=new JCheckBox("Battery");   
        bat.setBounds(100,125,80,20);
        insert.add(bat);
        
        JCheckBox chr=new JCheckBox("Charger");
        chr.setBounds(190,125,80,20);
        insert.add(chr);
                
        JCheckBox box=new JCheckBox("Box");      
        box.setBounds(280,125,60,20);
        insert.add(box);
        
        JLabel buy = new JLabel("Buy: ");
        buy.setBounds(10,150,500,20);
                                
        JTextField buyField = new JTextField();
        buyField.setBounds(100,150,300,20);
                
        JLabel supplier = new JLabel("Provider: ");
        supplier.setBounds(10,175,300,20);
                                
        JComboBox<ArrayList> supplierField = new JComboBox(model.getSuppliers().toArray());
        supplierField.setBounds(100,175,300,20);
                                
        JLabel serviceProvider = new JLabel("Supplier: ");
        serviceProvider.setBounds(10,200,300,20);
                                
        JComboBox<ArrayList> serviceProviderField = new JComboBox(model.getProvider().toArray());
        serviceProviderField.setBounds(100,200,300,20);                          
                
        JLabel comment = new JLabel("Comment: ");
        comment.setBounds(10,225,300,20);
                                
        JTextField commentField = new JTextField(model.getComment());
        commentField.setBounds(100,225,300,20);
        //-----------------------------------------------------------------
        JLabel customer = new JLabel("Name: ");
        customer.setBounds(10,275,300,20);
        JTextField customerField = new JTextField();
        customerField.setBounds(100,275,300,20);
                            
        JLabel id = new JLabel("ID card: ");
        id.setBounds(10,300,300,20);
        JTextField idField = new JTextField();
        idField.setBounds(100,300,300,20);
        
        JLabel zipCode = new JLabel("Zip Code: ");
        zipCode.setBounds(10,325,150,20);
        JTextField zipCodeField = new JTextField();
        zipCodeField.setBounds(100,325,300,20);
        
        JLabel town = new JLabel("Town: ");
        town.setBounds(10,350,300,20);
        JTextField townField = new JTextField();
        townField.setBounds(100,350,300,20); 
                            
        JLabel address = new JLabel("Address: ");
        address.setBounds(10,375,300,20);
        JTextField addressField = new JTextField();
        addressField.setBounds(100,375,300,20); 
                            
        JLabel phone = new JLabel("Phone number: ");
        phone.setBounds(10,400,300,20);
        JTextField phoneField = new JTextField();
        phoneField.setBounds(100,400,300,20);
                                
        JButton insertBtn = new JButton("Insert");
        
        if(supplierField.getSelectedItem().equals("felvásárlás")){
               insertBtn.setBounds(10,490,100,20);
               insert.setSize(450,550);
               insert.add(customer);
               insert.add(customerField);
               insert.add(id);
               insert.add(idField);
               insert.add(zipCode);
               insert.add(zipCodeField);
               insert.add(town);
               insert.add(townField);
               insert.add(address);
               insert.add(addressField);
               insert.add(phone);
               insert.add(phoneField);
           }else{
               insertBtn.setBounds(10,290,100,20);
               insert.setSize(450,350);
               insert.remove(customer);
               insert.remove(customerField);
               insert.remove(id);
               insert.remove(idField);
               insert.remove(zipCode);
               insert.remove(zipCodeField);
               insert.remove(town);
               insert.remove(townField);
               insert.remove(address);
               insert.remove(addressField);
               insert.remove(phone);
               insert.remove(phoneField);
        }  
                
        insertBtn.addActionListener(new ActionListener(){
        @Override
            public void actionPerformed(ActionEvent e) {
                if(supplierField.getSelectedItem().equals("felvásárlás")){
                    model.insertNewPhone(nameField.getText(),imeiField.getText(),Integer.parseInt(buyField.getText()),bat.isSelected(),chr.isSelected(),box.isSelected(),status.isSelected(),commentField.getText(), (String) serviceProviderField.getSelectedItem(), (String) supplierField.getSelectedItem());
                    model.buyPhone(nameField.getText(), imeiField.getText(), customerField.getText(),Integer.parseInt(zipCodeField.getText()),townField.getText(),addressField.getText(),phoneField.getText(),idField.getText());
                    int ans = JOptionPane.showConfirmDialog(null,"Would you like print buy contract?","Print",JOptionPane.YES_NO_OPTION);
                    if(ans==JOptionPane.YES_OPTION){
                        PhonePrint print = new PhonePrint();
                        try {
                            print.boughtContract();
                        } catch (SQLException ex) {
                            Logger.getLogger(PhoneSale.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (PrinterException ex) {
                            Logger.getLogger(PhoneInsert.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Insert successfull!");
                    insert.dispose();


                }else{
                    model.insertNewPhone(nameField.getText(),imeiField.getText(),Integer.parseInt(buyField.getText()),bat.isSelected(),chr.isSelected(),box.isSelected(),status.isSelected(),commentField.getText(), (String) serviceProviderField.getSelectedItem(), (String) supplierField.getSelectedItem());
                    insert.dispose();


                }
            }
                    
        });
        
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
        
        
        supplierField.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e){
                    if(supplierField.getSelectedItem().equals("felvásárlás")){
                        insertBtn.setBounds(10,490,100,20);
                        insert.setSize(450,550);
                        insert.add(customer);
                        insert.add(customerField);
                        insert.add(id);
                        insert.add(idField);
                        insert.add(zipCode);
                        insert.add(zipCodeField);
                        insert.add(town);
                        insert.add(townField);
                        insert.add(address);
                        insert.add(addressField);
                        insert.add(phone);
                        insert.add(phoneField);
                    }else{
                        insertBtn.setBounds(10,290,100,20);
                        insert.setSize(450,350);
                        insert.remove(customer);
                        insert.remove(customerField);
                        insert.remove(id);
                        insert.remove(idField);
                        insert.remove(zipCode);
                        insert.remove(zipCodeField);
                        insert.remove(town);
                        insert.remove(townField);
                        insert.remove(address);
                        insert.remove(addressField);
                        insert.remove(phone);
                        insert.remove(phoneField);
                 }  
           } 

        });
        
        
        insert.add(label);                        
        insert.add(name);
        insert.add(nameField);
        insert.add(imei);
        insert.add(imeiField);
        insert.add(status);
        insert.add(accessorries);
        insert.add(buy);
        insert.add(buyField);
        insert.add(comment);
        insert.add(commentField);
        insert.add(supplier);
        insert.add(supplierField);
        insert.add(serviceProvider);
        insert.add(serviceProviderField);
        insert.add(insertBtn);
        insert.setLayout(null);
        insert.setVisible(true);
        insert.setLocationRelativeTo(null);
    }

    
}
