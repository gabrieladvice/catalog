/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warehouse;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author gabrieladvice
 */
public class WarehouseAddPhoneModel {
    
    private static boolean successfull = false;
    private final JFrame phonemodel;
    
    public WarehouseAddPhoneModel(JFrame phone_model, String phone_brand){
        
        this.phonemodel = phone_model;
        
        WarehouseModel model = new WarehouseModel();
        
        JLabel name = new JLabel("Name: ");
        name.setBounds(10,50,100,20);      
                
        JTextField nameField = new JTextField();
        nameField.setBounds(100,50,200,20);
        
        JButton insertBtn = new JButton("Insert");
        insertBtn.setBounds(10,100,100,50);
        
        insertBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {

                try {
                    if(model.insertNewPhoneModel(nameField.getText(), phone_brand)){
                        successfull = true;
                        phone_model.dispose();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(WarehouseAddPhoneModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
        
        phonemodel.add(name);
        phonemodel.add(nameField);
        phonemodel.add(insertBtn);
        phonemodel.setSize(300,300);
        phonemodel.setLayout(null);
        phonemodel.setVisible(true);
        phonemodel.setLocationRelativeTo(null);
    }

    
    public boolean getSuccessfull(){
        
        return successfull;
        
    }


}
