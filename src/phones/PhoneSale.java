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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
public class PhoneSale {
    public PhoneSale(){
        
        PhoneModel model = new PhoneModel();
        
        JFrame sale = new JFrame("Sale - "+model.getPhoneName());
        
        JLabel label = new JLabel("Sale phone");
        label.setBounds(10,10,300,30);
        label.setFont(new Font("Serif", Font.PLAIN, 24));
        
        JLabel customer = new JLabel("Name: ");
        customer.setBounds(10,50,300,20);
        JTextField customerField = new JTextField();
        customerField.setBounds(100,50,300,20);
                            
        JLabel id = new JLabel("ID card: ");
        id.setBounds(10,75,300,20);
        JTextField idField = new JTextField();
        idField.setBounds(100,75,300,20);
        
        JLabel zipCode = new JLabel("Zip Code: ");
        zipCode.setBounds(10,100,300,20);
        JTextField zipCodeField = new JTextField();
        zipCodeField.setBounds(100,100,300,20); 
        
        JLabel town = new JLabel("Town: ");
        town.setBounds(10,125,300,20);
        JTextField townField = new JTextField();
        townField.setBounds(100,125,300,20); 
                            
        JLabel address = new JLabel("Address: ");
        address.setBounds(10,150,300,20);
        JTextField addressField = new JTextField();
        addressField.setBounds(100,150,300,20); 
                            
        JLabel phone = new JLabel("Phone number: ");
        phone.setBounds(10,175,300,20);
        JTextField phoneField = new JTextField();
        phoneField.setBounds(100,175,300,20);
        
        JLabel name = new JLabel("Model:                  "+model.getPhoneName());
        name.setBounds(10,225,300,20);
        
        JLabel imei = new JLabel("IMEI:                      "+model.getImei());
        imei.setBounds(10,250,300,20);  
        
        JLabel accessories = new JLabel("Accessories: ");
        accessories.setBounds(10,275,90,20);
                    
        if(model.getBattery().equalsIgnoreCase("1")){
            JCheckBox bat=new JCheckBox("Battery",true);
            bat.setEnabled(false);
            bat.setBounds(100,275,80,20);
            sale.add(bat);
        }else{
            JCheckBox bat=new JCheckBox("Battery",false);
            bat.setEnabled(false);
            bat.setBounds(100,275,80,20);
            sale.add(bat);                
        }

        if(model.getCharger().equalsIgnoreCase("1")){
            JCheckBox chr=new JCheckBox("Charger",true);
            chr.setEnabled(false);
            chr.setBounds(190,275,80,20);  
            sale.add(chr);
        }else{
            JCheckBox chr=new JCheckBox("Charger",false);
            chr.setEnabled(false);
            chr.setBounds(190,275,80,20);
            sale.add(chr);
        }

        if(model.getBox().equalsIgnoreCase("1")){
            JCheckBox box=new JCheckBox("Box",true);
            box.setEnabled(false);
            box.setBounds(280,275,60,20);
            sale.add(box);
        }else{
            JCheckBox box=new JCheckBox("Box",false);
            box.setEnabled(false);
            box.setBounds(280,275,60,20);
            sale.add(box);
        }
        JLabel salePhone = new JLabel("Sale: ");
        salePhone.setBounds(10,300,300,20);
        JTextField saleField = new JTextField();
        saleField.setBounds(100,300,300,20);
                            
        JButton btn = new JButton("Sale");
        btn.setBounds(10,390,100,20);
        
        btn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
            if((customerField.getText().equals("")) && (idField.getText().equals("")) && (zipCodeField.getText().equals("")) && (townField.getText().equals("")) && (addressField.getText().equals("")) && (phoneField.getText().equals(""))){
                model.salePhone(Integer.parseInt(saleField.getText()));
                JOptionPane.showMessageDialog(null, "Sale completed!");
                try {
                    new PhoneDetails();
                    sale.dispose();
                } catch (SQLException ex) {
                    Logger.getLogger(PhoneSale.class.getName()).log(Level.SEVERE, null, ex);
                }

            }else{
                model.salePhoneContract(customerField.getText(),zipCodeField.getText(),townField.getText(),addressField.getText(),phoneField.getText(),idField.getText(),Integer.parseInt(saleField.getText()));
                int ans = JOptionPane.showConfirmDialog(null,"Would you like print sale contract?","Print",JOptionPane.YES_NO_OPTION);
                if(ans==JOptionPane.YES_OPTION){
                    PhonePrint print = new PhonePrint();
                    try {
                        print.soldContract();
                    } catch (PrinterException ex) {
                        Logger.getLogger(PhoneSale.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(PhoneSale.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                JOptionPane.showMessageDialog(null, "Sale completed!");
                try {
                    new PhoneDetails();
                    sale.dispose();
                } catch (SQLException ex) {
                    Logger.getLogger(PhoneSale.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }    
        });
        
        sale.addWindowListener(new WindowAdapter() {
        @Override
            public void windowClosing(WindowEvent e) {
                try {
                    new PhoneDetails();
                        sale.dispose();
                    } catch (SQLException ex) {
                        Logger.getLogger(PhoneSale.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
        });
        
        sale.add(label);
        sale.add(customer);
        sale.add(customerField);
        sale.add(id);
        sale.add(idField);
        sale.add(zipCode);
        sale.add(zipCodeField);
        sale.add(town);
        sale.add(townField);
        sale.add(address);
        sale.add(addressField);
        sale.add(phone);
        sale.add(phoneField);
        sale.add(name);
        sale.add(imei);
        sale.add(accessories);
        sale.add(salePhone);
        sale.add(saleField);
        sale.add(btn);
        sale.setLayout(null);
        sale.setSize(450,450);
        sale.setVisible(true);
        sale.setLocationRelativeTo(null);
        sale.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

    }
    
}
