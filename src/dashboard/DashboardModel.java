/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dashboard;

import dbUtil.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Gabika
 */
public class DashboardModel {
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;
    private static ResultSetMetaData rsmd;
    private static PreparedStatement pst;
    private static String dashboardUsername;
    private static int dashboardOfficeId;
    private static String totalPhoneSaleDay;
    private static String totalPhoneBuyDay;
    private static String totalPhoneProfitDay;
    
    
    public DashboardModel(String username, int officeId) throws SQLException{
        con = DbConnection.getConnection();
        DashboardModel.dashboardUsername = username;
        DashboardModel.dashboardOfficeId = officeId;

        
    }
    
    private static java.sql.Date getCurrentDate() {
        java.util.Date today = new java.util.Date();
        return new java.sql.Date(today.getTime());
    }
    
    public void setTotalSalePhoneDay() throws SQLException{
            String query = "SELECT SUM(sale) FROM phones WHERE sale_date='"+getCurrentDate()+"'";
            stmt = con.createStatement();
            rs =stmt.executeQuery(query);
            while(rs.next()){
                
                DashboardModel.totalPhoneSaleDay = rs.getString("SUM(sale)");

            }
    }
    
    public void setTotalBuyPhoneDay(){
        try{
            String query = "SELECT SUM(buy) FROM phones WHERE buy_date='"+getCurrentDate()+"'";
            stmt = con.createStatement();
            rs =stmt.executeQuery(query);
            while(rs.next()){
                
                DashboardModel.totalPhoneBuyDay = rs.getString("SUM(buy)");

            }
 
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
            e.getStackTrace();
        }

    }
    
    public void setTotalProfitPhoneDay(){
        try{
            String query = "SELECT SUM(sale-buy) FROM phones WHERE sale_date='"+getCurrentDate()+"'";
            stmt = con.createStatement();
            rs =stmt.executeQuery(query);
            while(rs.next()){
                
                DashboardModel.totalPhoneProfitDay = rs.getString("SUM(sale-buy)");

            }
 
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
            e.getStackTrace();
        }
    }
    
    public String getTotalPhoneSaleDay(){
        if(DashboardModel.totalPhoneSaleDay==null){
            return "0";
        }else{
            return DashboardModel.totalPhoneSaleDay;              
        }
    }
    
    public String getTotalPhoneBuyDay(){
        if(DashboardModel.totalPhoneBuyDay==null){
            return "0";
        }else{
            return DashboardModel.totalPhoneBuyDay;           
        }


    }
    
    public String getTotalPhoneProfitDay(){
        if(DashboardModel.totalPhoneProfitDay==null){
            return "0";
        }else{
        return DashboardModel.totalPhoneProfitDay;         
        }

    }
    

}


