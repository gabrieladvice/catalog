/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;


import java.sql.Connection;
import dbUtil.DbConnection;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JOptionPane;
/**
 *
 * @author Gabika
 */
public class LoginModel {
    
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;
    protected static String resUser;
    private static String resPass;
    
    public LoginModel(){
        try{
          LoginModel.con = DbConnection.getConnection();
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null,e);
        }
        if(LoginModel.con == null){
            JOptionPane.showMessageDialog(null,"Cannot connect to server");           
        }
    }
    
    public boolean isDatabaseConnected(){
        return LoginModel.con != null;
    }
    
    public boolean Login(String username, String password){
        
        try{
            LoginModel.stmt = LoginModel.con.createStatement();
            LoginModel.rs = LoginModel.stmt.executeQuery("SELECT * FROM users WHERE username='"+username+"'");
            while(LoginModel.rs.next()){
               resUser = LoginModel.rs.getString("username");
               resPass = LoginModel.rs.getString("password");
            }

        } catch (SQLException sqlEx) {
            JOptionPane.showMessageDialog(null, sqlEx);
        } finally {
            //close connection ,stmt and resultset here
            try { LoginModel.stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { LoginModel.rs.close(); } catch(SQLException se) { /*can't do anything */ }
        }
        
            try {
                if(new Cryptography().EncryptPass(password).equalsIgnoreCase(resPass) && username.equalsIgnoreCase(resUser)){                  
                    return true;
                }else{
                    JOptionPane.showMessageDialog(null,"Incorrect username or password");
                    return false;
                }
            }catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException|BadPaddingException |UnsupportedEncodingException ex) {
                JOptionPane.showMessageDialog(null,ex,"",JOptionPane.ERROR_MESSAGE);
                return false;
            } 

        
    }
}
