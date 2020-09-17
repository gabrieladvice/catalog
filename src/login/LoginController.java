/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Gabika
 */
public final class LoginController {
    private String username;
    private String password;
    protected boolean dbconnectted;
    protected boolean logged;
    private final LoginModel loginmodel;
    
    public LoginController(){
        this.loginmodel = new LoginModel();
        setDbConnectted();
    }
    
    public boolean Login(String username,String password){
        this.username = username;
        this.password = password;

        if(getAutentication()){
            if(getDbConnectted()){
                Start();
                return true;
            }else{
                JOptionPane.showMessageDialog(null, "You cannot login when server is down");
                return false;
            } 
        }else{
            return false;
        }
        
    }
    public void setDbConnectted(){
            dbconnectted = loginmodel.isDatabaseConnected(); 
    }
    public boolean getDbConnectted(){
            return dbconnectted;
    }

    private void Autentication(){
        logged = loginmodel.Login(this.username, this.password);
    }
    
    private boolean getAutentication(){
        return loginmodel.Login(this.username, this.password);
    }
    
    public void Start(){
        if(getAutentication() && getDbConnectted()){
            try{
                new catalog.Main(this.username);
            }catch (SQLException e){
                JOptionPane.showMessageDialog(null,e);
            }
            

        }
    
    }
    
    
}
