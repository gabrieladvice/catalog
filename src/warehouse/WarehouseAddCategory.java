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
public class WarehouseAddCategory {
    
    private static boolean successfull = false;
    private static JFrame category;
    public WarehouseAddCategory(JFrame category){
        this.category = category;
        
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

                if(model.insertNewCategory(nameField.getText())){
                    successfull = true;
                    category.dispose();
                };
                
            }
            
        });
        
        category.add(name);
        category.add(nameField);
        category.add(insertBtn);
        category.setSize(300,300);
        category.setLayout(null);
        category.setVisible(true);
        category.setLocationRelativeTo(null);
    }
    
    public boolean getSuccessfull(){
        
        return successfull;
        
    }


}
