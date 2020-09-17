/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static final String url = "jdbc:mysql://advicenas.synology.me:59713/catalog";
    private static final String userDB = "gabrieladvice";
    private static final String passwordDB = "Gabika597132";
    
    public static Connection getConnection() throws SQLException{
        
        return DriverManager.getConnection(url,userDB,passwordDB);
        
    }
    
}
