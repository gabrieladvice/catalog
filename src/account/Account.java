/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account;

import catalog.Model;
import dbUtil.DbConnection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Gabika
 */
public class Account {
    private static String encryptedpass;
    private static Connection con;
    private static PreparedStatement pstmt;
    public Account(String getUsername) throws SQLException{
        
            catalog.Model model = new Model(getUsername);
            
            JFrame acc = new JFrame();
        
            JLabel user = new JLabel("Username");
            user.setBounds(10,25,100,25);
    
            JTextField username = new JTextField(getUsername);
            username.setBounds(110,25,100,25);
            username.setEnabled(false);
                
            JButton changepass = new JButton("Change password");
            changepass.setBounds(10,70,150,25);
                
            changepass.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent ae) {
                    JFrame changepass = new JFrame();
                    JLabel currentpass = new JLabel("Current password");
                    currentpass.setBounds(10,30,120,25);
                    JPasswordField currentpassword = new JPasswordField();
                    currentpassword.setBounds(130,30,120,25);
                    
                    JLabel newpass = new JLabel("New password");
                    newpass.setBounds(10,60,120,25);
                    JPasswordField newpassword = new JPasswordField();
                    newpassword.setBounds(130,60,120,25);
                    
                    JLabel confirmpass = new JLabel("Confirm password");
                    confirmpass.setBounds(10,90,120,25);
                    JPasswordField confirmpassword = new JPasswordField();
                    confirmpassword.setBounds(130,90,120,25);
                    
                    JButton confirm = new JButton("Confirm");
                    confirm.setBounds(80,140,150,30);
                    
                    confirm.addActionListener(new ActionListener(){

                        public void actionPerformed(ActionEvent ae) {
                            encryptedpass = model.getPassword();
                            try {         
                                if(new Cryptography().EncryptPass(currentpassword.getText()).equalsIgnoreCase(encryptedpass)){
                                    if(newpassword.getText().equalsIgnoreCase(confirmpassword.getText())){
                                        
                                        String query = "UPDATE users SET password=? WHERE username='"+getUsername+"'";
                                        con = DbConnection.getConnection();
                                        pstmt = con.prepareStatement(query);
                                        pstmt.setString (1, new Cryptography().EncryptPass(newpassword.getText()));
                                        pstmt.execute();
                                        JOptionPane.showMessageDialog(null, "Password has been overloaded");
                                        changepass.dispose();

                                    }else{
                                        JOptionPane.showMessageDialog(null,"New passwords don't match");
                                    }

                                }else{
                                    JOptionPane.showMessageDialog(null,"Current password don't match");
                                }
                            } catch (NoSuchAlgorithmException ex) {
                                JOptionPane.showMessageDialog(null,ex,"",JOptionPane.ERROR_MESSAGE);
                                Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (NoSuchPaddingException ex) {
                                JOptionPane.showMessageDialog(null,ex,"",JOptionPane.ERROR_MESSAGE);
                                Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (InvalidKeyException ex) {
                                JOptionPane.showMessageDialog(null,ex,"",JOptionPane.ERROR_MESSAGE);
                                Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IllegalBlockSizeException ex) {
                                JOptionPane.showMessageDialog(null,ex,"",JOptionPane.ERROR_MESSAGE);
                                Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (BadPaddingException ex) {
                                JOptionPane.showMessageDialog(null,ex,"",JOptionPane.ERROR_MESSAGE);
                                Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (UnsupportedEncodingException ex) {
                                JOptionPane.showMessageDialog(null,ex,"",JOptionPane.ERROR_MESSAGE);
                                Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(null,ex,"",JOptionPane.ERROR_MESSAGE);
                                Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        
                        
                    
                    });
                    changepass.add(currentpass);
                    changepass.add(currentpassword);
                    changepass.add(newpass);
                    changepass.add(newpassword);
                    changepass.add(confirmpass);
                    changepass.add(confirmpassword);
                    changepass.add(confirm);
                    changepass.setSize(350,200);
                    changepass.setLayout(null);
                    changepass.setVisible(true);
                    changepass.setLocationRelativeTo(null);                   
                }               
            });
            
            acc.add(user);
            acc.add(username);
            acc.add(changepass);
            acc.setSize(300,300);
            acc.setLayout(null);
            acc.setVisible(true);
            acc.setLocationRelativeTo(null);
            
    }
}
