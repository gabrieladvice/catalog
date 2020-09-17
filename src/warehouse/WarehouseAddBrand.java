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
public class WarehouseAddBrand {
    
    private static boolean successfull = false;
    private final JFrame brand;
    public WarehouseAddBrand(JFrame brand){
        
        this.brand = brand;
        
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

                if(model.insertNewBrand(nameField.getText())){
                    successfull = true;
                    brand.dispose();
                };
                
            }
            
        });
        
        brand.add(name);
        brand.add(nameField);
        brand.add(insertBtn);
        brand.setSize(300,300);
        brand.setLayout(null);
        brand.setVisible(true);
        brand.setLocationRelativeTo(null);
    }

    
    public boolean getSuccessfull(){
        
        return successfull;
        
    }


}
