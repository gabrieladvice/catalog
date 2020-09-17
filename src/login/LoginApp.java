/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

/**
 *
 * @author Gabika
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class LoginApp{
    LoginController logincontroller = new LoginController();
    
    public void LoginApps(){
        
        JFrame f = new JFrame("Login");

        JLabel user = new JLabel("Username");
        user.setBounds(10,25,100,20);

        JTextField username = new JTextField();
        username.setBounds(110,25,175,30);

        JLabel pass = new JLabel("Password");
        pass.setBounds(10,60,100,20);

        JPasswordField password = new JPasswordField();
        password.setBounds(110,60,175,30);

        JButton login = new JButton("Login");
        login.setBounds(80,100,150,30);

        JLabel server = new JLabel("");
        server.setBounds(10,150,150,30);

        if(logincontroller.getDbConnectted()){
            server.setText("AdviceNas is available");
        }else{
            server.setText("AdviceNas is down");
        }

        username.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                password.requestFocus();
            }

        });

        password.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(logincontroller.Login(username.getText(),password.getText())){
                    f.dispose();
                }else{
                    password.setText("");
                }
            } 
        });


        login.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e) {
                if(logincontroller.Login(username.getText(),password.getText())){
                    f.dispose();
                }else{
                    password.setText("");
                }
            } 


        });


        f.add(user);
        f.add(username);
        f.add(pass);
        f.add(password); 
        f.add(login);
        f.add(server);
        f.setSize(300,300);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);

    }

}
