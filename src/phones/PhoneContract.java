/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phones;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;


/**
 *
 * @author Gabika
 */
public class PhoneContract {
    public PhoneContract(){

    }
    public void PhoneSoldContract(String phoneName,String phoneImei) throws SQLException{

        PhoneModel model = new PhoneModel();
        model.getPhoneDetails(phoneName,phoneImei);
        model.getSoldPhone();
        JFrame contract = new JFrame("Sold Contract - "+model.getPhoneName());
        
        JLabel label = new JLabel("Sold phone contract");
        label.setBounds(10,10,300,30);
        label.setFont(new Font("Serif", Font.PLAIN, 24));
        
        JLabel customer = new JLabel("Name: ");
        customer.setBounds(10,50,150,20);
        JLabel customerField = new JLabel(model.getCustomerName());
        customerField.setBounds(100,50,150,20);
        
        JLabel id = new JLabel("ID card: ");
        id.setBounds(10,75,150,20);
        JLabel idField = new JLabel(model.getCustomerIdCard());
        idField.setBounds(100,75,150,20);
        
        JLabel zipCode = new JLabel("Zip Code: ");
        zipCode.setBounds(10,100,150,20);
        JLabel zipCodeField = new JLabel(model.getCustomerZipCode());
        zipCodeField.setBounds(100,100,150,20); 
        
        JLabel town = new JLabel("Town: ");
        town.setBounds(10,125,150,20);
        JLabel townField = new JLabel(model.getCustomerTown());
        townField.setBounds(100,125,150,20); 
                            
        JLabel address = new JLabel("Address: ");
        address.setBounds(10,150,150,20);
        JLabel addressField = new JLabel(model.getCustomerAddress());
        addressField.setBounds(100,150,150,20); 
                            
        JLabel phone = new JLabel("Phone number: ");
        phone.setBounds(10,175,150,20);
        JLabel phoneField = new JLabel(model.getCustomerPhone());
        phoneField.setBounds(100,175,150,20);
        
        //-------------------------------------------------------------
        
        JLabel name = new JLabel("Model: ");
        name.setBounds(240,50,500,20);      
                
        JLabel nameField = new JLabel(model.getPhoneName());
        nameField.setBounds(290,50,300,20);
                                
        JLabel imei = new JLabel("IMEI: ");
        imei.setBounds(240,75,500,20);
                               
        JLabel imeiField = new JLabel(model.getImei());
        imeiField.setBounds(290,75,300,20);
        
        JLabel accessorries = new JLabel("Accessories: ");
        accessorries.setBounds(240,100,90,20);
        
        JCheckBox bat=new JCheckBox("Battery");   
        bat.setBounds(290,100,70,20);
        bat.setEnabled(false);
        contract.add(bat);
        if(model.getBattery().equals("1")){
            bat.setSelected(true);
        }
        
        JCheckBox chr=new JCheckBox("Charger");
        chr.setBounds(360,100,80,20);
        contract.add(chr);
        chr.setEnabled(false);
        if(model.getCharger().equals("1")){
            chr.setSelected(true);
        }
                
        JCheckBox box=new JCheckBox("Box");      
        box.setBounds(440,100,60,20);
        contract.add(box);
        box.setEnabled(false);
        if(model.getBox().equals("1")){
            box.setSelected(true);
        }
        
        JLabel buy = new JLabel("Sale: ");
        buy.setBounds(240,125,500,20);
                                
        JLabel buyField = new JLabel(model.getSale()+" Ft");
        buyField.setBounds(290,125,300,20);
        
        JButton print = new JButton("Print");
        print.setBounds(10,200,100,20);
        
        print.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                
                PhonePrint print = new PhonePrint();
                try {
                    print.soldContract();
                    contract.dispose();
                } catch (PrinterException ex) {
                    Logger.getLogger(PhoneContract.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(PhoneContract.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            
        });

        contract.add(label);
        contract.add(customer);
        contract.add(customerField);
        contract.add(id);
        contract.add(idField);
        contract.add(zipCode);
        contract.add(zipCodeField);
        contract.add(town);
        contract.add(townField);
        contract.add(address);
        contract.add(addressField);
        contract.add(phone);
        contract.add(phoneField);
        contract.add(name);
        contract.add(nameField);
        contract.add(imei);
        contract.add(imeiField);
        contract.add(accessorries);
        contract.add(buy);
        contract.add(buyField);
        contract.add(print);
        contract.setLayout(null);
        contract.setSize(600,600);
        contract.setVisible(true);
        contract.setLocationRelativeTo(null);
    }
    public void PhoneBoughtContract(String phoneName,String phoneImei) throws SQLException{
        
        PhoneModel model = new PhoneModel();
        model.getPhoneDetails(phoneName,phoneImei);        
        model.getBoughtPhone();
        JFrame contract = new JFrame("Bought Contract - "+model.getPhoneName());
        
        
        JLabel label = new JLabel("Bought phone contract");
        label.setBounds(10,10,300,30);
        label.setFont(new Font("Serif", Font.PLAIN, 24));
        
        JLabel customer = new JLabel("Name: ");
        customer.setBounds(10,50,150,20);
        JLabel customerField = new JLabel(model.getCustomerName());
        customerField.setBounds(100,50,150,20);
        
        JLabel id = new JLabel("ID card: ");
        id.setBounds(10,75,150,20);
        JLabel idField = new JLabel(model.getCustomerIdCard());
        idField.setBounds(100,75,150,20);
        
        JLabel zipCode = new JLabel("Zip Code: ");
        zipCode.setBounds(10,100,150,20);
        JLabel zipCodeField = new JLabel(model.getCustomerZipCode());
        zipCodeField.setBounds(100,100,150,20); 
        
        JLabel town = new JLabel("Town: ");
        town.setBounds(10,125,150,20);
        JLabel townField = new JLabel(model.getCustomerTown());
        townField.setBounds(100,125,150,20); 
                            
        JLabel address = new JLabel("Address: ");
        address.setBounds(10,150,150,20);
        JLabel addressField = new JLabel(model.getCustomerAddress());
        addressField.setBounds(100,150,150,20); 
                            
        JLabel phone = new JLabel("Phone number: ");
        phone.setBounds(10,175,150,20);
        JLabel phoneField = new JLabel(model.getCustomerPhone());
        phoneField.setBounds(100,175,150,20);
        
        //-------------------------------------------------------------
        
        JLabel name = new JLabel("Model: ");
        name.setBounds(240,50,500,20);      
                
        JLabel nameField = new JLabel(model.getPhoneName());
        nameField.setBounds(290,50,300,20);
                                
        JLabel imei = new JLabel("IMEI: ");
        imei.setBounds(240,75,500,20);
                               
        JLabel imeiField = new JLabel(model.getImei());
        imeiField.setBounds(290,75,300,20);
        
        JLabel accessorries = new JLabel("Accessories: ");
        accessorries.setBounds(240,100,90,20);
        
        JCheckBox bat=new JCheckBox("Battery");   
        bat.setBounds(290,100,70,20);
        bat.setEnabled(false);
        contract.add(bat);
        if(model.getBattery().equals("1")){
            bat.setSelected(true);
        }

        JCheckBox chr=new JCheckBox("Charger");
        chr.setBounds(360,100,80,20);
        contract.add(chr);
        chr.setEnabled(false);
        if(model.getCharger().equals("1")){
            chr.setSelected(true);
        }
                
        JCheckBox box=new JCheckBox("Box");      
        box.setBounds(440,100,60,20);
        contract.add(box);
        box.setEnabled(false);
        if(model.getBox().equals("1")){
            box.setSelected(true);
        }
        
        JLabel buy = new JLabel("Buy: ");
        buy.setBounds(240,125,500,20);
                                
        JLabel buyField = new JLabel(model.getBuy()+" Ft");
        buyField.setBounds(290,125,300,20);
        
        JButton print = new JButton("Print");
        print.setBounds(10,200,100,20);
        
        print.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                
                PhonePrint print = new PhonePrint();
                try {
                    print.boughtContract();
                    contract.dispose();
                } catch (PrinterException ex) {
                    Logger.getLogger(PhoneContract.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(PhoneContract.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            
        });
      
        
        contract.add(label);
        contract.add(customer);
        contract.add(customerField);
        contract.add(id);
        contract.add(idField);
        contract.add(zipCode);
        contract.add(zipCodeField);
        contract.add(town);
        contract.add(townField);
        contract.add(address);
        contract.add(addressField);
        contract.add(phone);
        contract.add(phoneField);
        contract.add(name);
        contract.add(nameField);
        contract.add(imei);
        contract.add(imeiField);
        contract.add(accessorries);
        contract.add(buy);
        contract.add(buyField);
        contract.add(print);
        contract.setLayout(null);
        contract.setSize(600,600);
        contract.setVisible(true);
        contract.setLocationRelativeTo(null);
    }
    
}
