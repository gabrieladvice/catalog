/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warehouse;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author gabrieladvice
 */
public class WarehouseAddPhoneBrand {
    
    private static boolean successfull = false;
    private final JFrame phonebrand;
    public WarehouseAddPhoneBrand(JFrame phone_brand){
        
        this.phonebrand = phone_brand;
        
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

                if(model.insertNewPhoneBrand(nameField.getText())){
                    successfull = true;
                    phone_brand.dispose();
                };
                
            }
            
        });
        
        phonebrand.add(name);
        phonebrand.add(nameField);
        phonebrand.add(insertBtn);
        phonebrand.setSize(300,300);
        phonebrand.setLayout(null);
        phonebrand.setVisible(true);
        phonebrand.setLocationRelativeTo(null);
    }

    
    public boolean getSuccessfull(){
        
        return successfull;
        
    }


}
