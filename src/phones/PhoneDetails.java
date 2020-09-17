/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package catalog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import phones.PhoneContract;
import phones.PhoneModel;

public final class PhoneDetails {

    private static JPanel panel;
    
    PhoneModel model = new PhoneModel();
    PhoneContract phoneContract = new PhoneContract();
    
    public PhoneDetails() throws SQLException{
        model.getPhoneDetails(model.getPhoneName(),model.getImei());
        PhoneDetailsView();
    }
    
    public PhoneDetails(String phoneName, String imei) throws SQLException{
        model.getPhoneDetails(phoneName, imei);
        PhoneDetailsView();
    }
    
    public PhoneDetails(JTable table) throws SQLException{
        model.getPhoneDetails((String) table.getValueAt(table.getSelectedRow(),0),table.getValueAt(table.getSelectedRow(),1).toString());
        PhoneDetailsView();
    }
    
    public void PhoneDetailsView(){
        
        JFrame details = new JFrame(model.getPhoneName()+" - Detail");
        JLabel name = new JLabel("Model: "+model.getPhoneName());
        name.setBounds(10,10,500,20);
        
        JLabel imei = new JLabel("IMEI: "+model.getImei());
        imei.setBounds(10,30,500,20);  
        
        JLabel accessorries = new JLabel("Accessories: ");
        accessorries.setBounds(10,50,90,20);
        
        JCheckBox bat=new JCheckBox("Battery");
        bat.setEnabled(false);
        bat.setBounds(100,50,80,20);
        details.add(bat);      
        if(model.getBattery().equalsIgnoreCase("1")){
            bat.setSelected(true);

        }else{
            bat.setSelected(false); 
        }
        
        JCheckBox chr=new JCheckBox("Charger");
        chr.setEnabled(false);
        chr.setBounds(190,50,80,20);  
        details.add(chr);
        if(model.getCharger().equalsIgnoreCase("1")){
            chr.setSelected(true);

        }else{
            chr.setSelected(false);
        }
        
        JCheckBox box=new JCheckBox("Box");
        box.setEnabled(false);
        box.setBounds(280,50,60,20);
        details.add(box);
        if(model.getBox().equalsIgnoreCase("1")){
            box.setSelected(true);
        }else{
            box.setSelected(false);
        }
                    
        JLabel buy = new JLabel("Buy: "+model.getBuy()+" Ft");
        buy.setBounds(10,70,500,20);
              
        JLabel price = new JLabel("Price: "+model.getSale()+" Ft");
        price.setBounds(10,90,500,20);
                    
        JLabel buyDate = new JLabel("Buy Date: "+model.getBuyDate());
        buyDate.setBounds(10,110,500,20);
                    
        JLabel saleDate = new JLabel("Sale Date: "+model.getSaleDate());
        saleDate.setBounds(10,130,500,20);
                    
        JLabel serviceProvider = new JLabel("Service provider: "+model.getProviderName());
        serviceProvider.setBounds(10,150,500,20);
                    
        JLabel supplierName = new JLabel("Supplier: "+model.getSupplierName());
        supplierName.setBounds(10,170,500,20);
        
        JCheckBox status = new JCheckBox("new",true);
        status.setEnabled(false);
        status.setBounds(10,190,60,20);
        details.add(status);  
        if(model.getStatus().equalsIgnoreCase("1")){
            status.setSelected(true);

        }else{
            status.setSelected(false); 
        }                    
                    
            JLabel comment = new JLabel("Comment: "+model.getComment());
            comment.setBounds(10,210,500,20);
                    
            JButton sale = new JButton("Sale");
            sale.setBounds(10,240,100,20);
            
            JButton update = new JButton("Update");
            update.setBounds(120,240,100,20);
            
            JButton saleContract = new JButton("Sold Contract");
            JButton buyContract = new JButton("Bought Contract");

        if(model.getSold().equalsIgnoreCase("0")){

            details.add(sale);
            details.add(update);
            if(model.getSupplierName().equals("felvásárlás")){
                details.add(buyContract);
                buyContract.setBounds(230,240,130,20);
            }
        }else{
            details.add(saleContract);
            saleContract.setBounds(10,240,120,20);
            if(model.getSupplierName().equals("felvásárlás")){
                details.add(buyContract);
                buyContract.setBounds(10,240,130,20);
                saleContract.setBounds(150,240,130,20);
            }

        }
        
        saleContract.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    phoneContract.PhoneSoldContract(name.getText(),imei.getText());
                    
                } catch (SQLException ex) {
                    Logger.getLogger(PhoneDetails.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            
        });
        
        buyContract.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    phoneContract.PhoneBoughtContract(name.getText(),imei.getText());
                } catch (SQLException ex) {
                    Logger.getLogger(PhoneDetails.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            
        });
        
        sale.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                new PhoneSale();
                details.dispose();            
            } 
        });
                    
        update.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                try {
                    new PhoneUpdate();
                    details.dispose();
                } catch (SQLException ex) {
                    Logger.getLogger(PhoneDetails.class.getName()).log(Level.SEVERE, null, ex);
                }

            }          
        });
        
        sale.setMnemonic(KeyEvent.VK_S);
        update.setMnemonic(KeyEvent.VK_U);
        
        if(model.getSold().equals("1")){
           details.add(price);
           details.add(saleDate);
        }else{
           buyDate.setBounds(10,90,500,20);
           serviceProvider.setBounds(10,110,500,20);
           supplierName.setBounds(10,130,500,20);
           status.setBounds(10,150,60,20);
           comment.setBounds(10,170,500,20);
                   
        }
        details.add(name);
        details.add(imei);
        details.add(accessorries);
        details.add(buy);
        details.add(buyDate);
        details.add(comment);
        details.add(serviceProvider);
        details.add(supplierName);
        details.setLayout(null);
        details.setFocusCycleRoot(true);
        details.setSize(600,300);
        details.setVisible(true);
        details.setLocationRelativeTo(null);
                
        }

    }

