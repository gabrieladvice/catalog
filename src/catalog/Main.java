/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package catalog;


import account.Account;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import phones.PhonePrint;
import warehouse.Warehouse;

public class Main {

    public Main(String username) throws SQLException {
        Model model = new Model(username);
        
        JFrame f = new JFrame("CataLog");
        
        
        JPanel panel = new JPanel();
        panel.setBounds(200,50,1200,600);
        panel.setBackground(Color.WHITE);
        
        JLabel err = new JLabel();
        err.setBounds(10,70,100,20);

        JLabel welcome = new JLabel("Welcome "+model.getEmployeeName());
        welcome.setBounds(10,50,200,20);

        JLabel companyName = new JLabel(model.getCompanyName());
        companyName.setBounds(10,75,200,20);

        JLabel officeName = new JLabel(model.getOfficeName());
        officeName.setBounds(10,100,200,20);

        JButton account = new JButton("Account");
        account.setBounds(10,150,100,20);

        JButton dashboard = new JButton("Dashboard");
        dashboard.setBounds(10,200,100,20);

        JButton phones = new JButton("Phones");
        phones.setBounds(10,250,100,20);
        
        JButton storage = new JButton("Storage");
        storage.setBounds(10,300,100,20);
        
        JButton test = new JButton("TEST");
        test.setBounds(10,500,100,20);

        new Dashboard(panel, model.getUsername(), model.getOfficeId());   //miért látja ha másik packagebe van bent??
        
        
        account.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                try {
                    new Account(model.getUsername());
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,ex,"",JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        dashboard.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {

                try {
                    new Dashboard(panel, model.getUsername(), model.getOfficeId());
                } catch (SQLException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        phones.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try {
                    new Phones(panel, model.getUsername(), model.getOfficeId());
                } catch (SQLException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        storage.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    new Warehouse(panel, model.getComapnyId());
                } catch (SQLException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
        
        test.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                //new Test();
            }
            
        });

        f.add(err);
        f.add(welcome);
        f.add(account);
        f.add(companyName);
        f.add(officeName);
        f.add(dashboard);
        f.add(phones);
        f.add(storage);
        f.add(test);
        f.add(panel);
        f.setSize(1500,700);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);

        }

}


