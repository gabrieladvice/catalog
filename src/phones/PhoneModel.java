/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phones;

import dbUtil.DbConnection;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Gabika
 */
public class PhoneModel {
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;
    private static PreparedStatement pstmt;
    private static ResultSetMetaData rsmd;
    private static String username;
    private static int employeeId;
    private static int officeId;
    private static DefaultTableModel model;
    private static String phoneName;
    private static String imei;
    private static String battery;
    private static String charger;
    private static String box;
    private static String buy;
    private static String sale;
    private static Date buyDate;
    private static Date saleDate;
    private static String providerName;
    private static String status;
    private static String comment;
    private static String sold;
    private static String supplierName;
    private static int phoneId;
    private static String customerName;
    private static String customerZipCode;
    private static String customerTown;
    private static String customerAddress;
    private static String customerPhone;
    private static String customerIdCard;
    private static int phoneGarantee;
    
    
    
    public PhoneModel(){
        
    }
    
    public PhoneModel(String username, int officeId) throws SQLException{
        
        PhoneModel.username = username;
        PhoneModel.officeId = officeId;
        con = DbConnection.getConnection();
        stmt = con.createStatement();
        rs =stmt.executeQuery("SELECT user_employee_id FROM users WHERE username='"+PhoneModel.username+"'");
        while(rs.next()){
            PhoneModel.employeeId=rs.getInt("user_employee_id");
        }

    }
    
    public DefaultTableModel getTableModel(){
        try{
            rs = stmt.executeQuery("SELECT * FROM phones WHERE office_id='"+PhoneModel.officeId+"'");
            String [] column = {"Phones","IMEI","Sale"};
            model = new DefaultTableModel(column,0){
                public boolean isCellEditable(int row, int m) {
                        return false;
                }
            };

            while(rs.next()){

                String [] data = {rs.getString("phone_name"),rs.getString("imei"),
                                  rs.getString("sale")};
                model.addRow(data);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }

        return PhoneModel.model;
    }
    
    public DefaultTableModel getTableModelNotSolded(){
        try{
            rs = stmt.executeQuery("SELECT * FROM phones WHERE office_id='"+PhoneModel.officeId+"' and sold='0'");
            String [] column = {"Phones","IMEI","Sale"};
            model = new DefaultTableModel(column,0){
                public boolean isCellEditable(int row, int m) {
                        return false;
                }
            };

            while(rs.next()){

                String [] data = {rs.getString("phone_name"),rs.getString("imei"),
                                  rs.getString("sale")};
                model.addRow(data);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }

        return PhoneModel.model;
    }
    
    public ArrayList<String> getProvider() throws SQLException{
        rs = stmt.executeQuery("SELECT provider_name FROM service_provider");
        rsmd = rs.getMetaData(); 
        int columnCount = rsmd.getColumnCount();
        ArrayList<String> providers = new ArrayList<>(columnCount);
        while(rs.next()){
            int i=0;
            while(i<columnCount){
                providers.add(rs.getString("provider_name"));
                i++;
            }
        }
        return providers;
    }
    
    public ArrayList<String> getSuppliers() throws SQLException{
        
        rs = stmt.executeQuery("SELECT supplier_name FROM suppliers");
        rsmd = rs.getMetaData(); 
        int columnCount = rsmd.getColumnCount();
        ArrayList<String> suppliers = new ArrayList<>(columnCount);
        while(rs.next()){
            int i=0;
            while(i<columnCount){
                suppliers.add(rs.getString("supplier_name"));
                i++;
            }
        }  
        return suppliers;
    }
    
    public void getPhoneDetails(String phoneName, String imei) throws SQLException{
        con = DbConnection.getConnection();
        stmt = con.createStatement();
        rs = stmt.executeQuery("SELECT * FROM phones inner join service_provider on phones.service_provider_id = service_provider.id inner join suppliers on phones.suppliers_id = suppliers.id WHERE phone_name='"+phoneName+"' AND imei='"+imei+"'");
                while(rs.next()){
                    PhoneModel.phoneId = rs.getInt("id");
                    PhoneModel.phoneName = rs.getString("phone_name");
                    PhoneModel.imei = rs.getString("imei");
                    PhoneModel.battery = rs.getString("acces_battery");
                    PhoneModel.charger = rs.getString("acces_charger");
                    PhoneModel.supplierName = rs.getString("supplier_name");
                    PhoneModel.box = rs.getString("acces_box");
                    PhoneModel.buy = rs.getString("buy") ;
                    PhoneModel.sale = rs.getString("sale");
                    PhoneModel.buyDate = rs.getDate("buy_date");
                    PhoneModel.saleDate = rs.getDate("sale_date");
                    PhoneModel.providerName = rs.getString("provider_name");
                    PhoneModel.status = rs.getString("status");
                    PhoneModel.comment = rs.getString("comment");
                    PhoneModel.sold = rs.getString("sold");
                    
                }
    }
    
    public String getPhoneName(){
        return PhoneModel.phoneName;
    }
    public String getImei(){
        return PhoneModel.imei;
    }
    public String getBattery(){
        return PhoneModel.battery;
    }
    public String getCharger(){
        return PhoneModel.charger;
    }
    public String getSupplierName(){
        return PhoneModel.supplierName;
    }
    public String getBox(){
        return PhoneModel.box;
    }
    public String getBuy(){
        return PhoneModel.buy;
    }
    public String getSale(){
        return PhoneModel.sale;
    }
    public Date getBuyDate(){
        return PhoneModel.buyDate;
    }
    public Date getSaleDate(){
        return PhoneModel.saleDate;
    }
    public String getProviderName(){
        return PhoneModel.providerName;
    }
    public String getStatus(){
        return PhoneModel.status;
    }
    public String getComment(){
        return PhoneModel.comment;
    }
    public String getSold(){
        return PhoneModel.sold;
    }
    public int getPhoneId(){
        return PhoneModel.phoneId;
    }
    
    
    public void updatePhoneDetails(String phoneName, String phoneImei, int buy, boolean bat, boolean chr, boolean box, boolean status, String comment, String provider, String supplier){
        int providerId = 0;
        int supplierId = 0;
        String query = "UPDATE phones SET phone_name=?,imei=?,buy=?,acces_battery=?,acces_charger=?,acces_box=?,status=?,comment=?, service_provider_id=?, suppliers_id=? WHERE id='"+PhoneModel.phoneId+"'";
        String providerQuery = "SELECT id FROM service_provider WHERE provider_name='"+provider+"'";
        String supplierQuery = "SELECT id FROM suppliers WHERE supplier_name='"+supplier+"'";        
        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery(providerQuery);
            while(rs.next()){
                providerId = rs.getInt("id");
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
        
        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery(supplierQuery);
            while(rs.next()){
                supplierId = rs.getInt("id");
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
        
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1,phoneName);
            pstmt.setString(2,phoneImei);
            pstmt.setInt(3,buy);
            if(bat){
                pstmt.setInt(4,1);
            }else{
                pstmt.setInt(4,0);
            }
            if(chr){
                pstmt.setInt(5,1);
            }else{
                pstmt.setInt(5,0);
            }
            if(box){
                pstmt.setInt(6,1);
            }else{
                pstmt.setInt(6,0);
            }
            if(status){
                pstmt.setInt(7,1);
            }else{
                pstmt.setInt(7,0);
            }
            pstmt.setString(8, comment);

            pstmt.setInt(9, providerId);
            pstmt.setInt(10, supplierId);
            pstmt.executeUpdate();
                            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public void insertNewPhone(String phoneName, String phoneImei, int buy, boolean bat, boolean chr, boolean box, boolean status, String comment, String provider, String supplier){
        int providerId = 0;
        int supplierId = 0;
        String query = "INSERT INTO phones (phone_name, imei, buy, acces_battery, acces_charger, acces_box, status, comment, service_provider_id, suppliers_id, buy_date, sold, office_id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        String providerQuery = "SELECT id FROM service_provider WHERE provider_name='"+provider+"'";
        String supplierQuery = "SELECT id FROM suppliers WHERE supplier_name='"+supplier+"'";        
        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery(providerQuery);
            while(rs.next()){
                providerId = rs.getInt("id");
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
        
        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery(supplierQuery);
            while(rs.next()){
                supplierId = rs.getInt("id");
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
        
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1,phoneName);
            pstmt.setString(2,phoneImei);
            pstmt.setInt(3,buy);
            if(bat){
                pstmt.setInt(4,1);
            }else{
                pstmt.setInt(4,0);
            }
            if(chr){
                pstmt.setInt(5,1);
            }else{
                pstmt.setInt(5,0);
            }
            if(box){
                pstmt.setInt(6,1);
            }else{
                pstmt.setInt(6,0);
            }
            if(status){
                pstmt.setInt(7,1);
            }else{
                pstmt.setInt(7,0);
            }
            pstmt.setString(8, comment);

            pstmt.setInt(9, providerId);
            pstmt.setInt(10, supplierId);
            pstmt.setDate(11, getCurrentDate());
            pstmt.setInt(12, 0);
            pstmt.setInt(13, officeId);
            pstmt.executeUpdate();

                            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    private static java.sql.Date getCurrentDate() {
        java.util.Date today = new java.util.Date();
        return new java.sql.Date(today.getTime());
    }
    
    public void salePhoneContract(String customer, String zipCode, String town, String address, String phone, String idCard, int sale){
        
        String saleQuery = "INSERT INTO phone_sale (customer_name, customer_zip_code, customer_town, customer_address, customer_phone, customer_id_card, customer_phone_id) VALUES (?,?,?,?,?,?,?)";
        String updateQuery = "UPDATE phones SET sale=?, sale_date=?, employee_sale_id=?, sold=?  WHERE id='"+PhoneModel.phoneId+"'";
        try{
            pstmt = con.prepareStatement(saleQuery);
            pstmt.setString(1,customer);
            pstmt.setString(2,zipCode);
            pstmt.setString(3,town);
            pstmt.setString(4,address);
            pstmt.setString(5,phone);
            pstmt.setString(6, idCard);
            pstmt.setInt(7, PhoneModel.phoneId);
            pstmt.executeUpdate();
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
        try{
            pstmt = con.prepareStatement(updateQuery);
            pstmt.setInt(1,sale);
            pstmt.setDate(2,getCurrentDate());
            pstmt.setInt(3, PhoneModel.employeeId);
            pstmt.setInt(4, 1);
            pstmt.executeUpdate();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void salePhone(int sale){

        String updateQuery = "UPDATE phones SET sale=?, sale_date=?, employee_sale_id=?, sold=?  WHERE id='"+PhoneModel.phoneId+"'";

        try{
            pstmt = con.prepareStatement(updateQuery);
            pstmt.setInt(1,sale);
            pstmt.setDate(2,getCurrentDate());
            pstmt.setInt(3, PhoneModel.employeeId);
            pstmt.setInt(4, 1);
            pstmt.executeUpdate();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    } 
    
    public void buyPhone(String phoneName, String phoneImei,String customer, int zipCode, String town, String address, String phone, String idCard){
        try{
            
        getPhoneDetails(phoneName, phoneImei);
        String buyQuery = "INSERT INTO phone_buy (customer_name, customer_zip_code, customer_town, customer_address, customer_phone, customer_id_card, customer_phone_id) VALUES (?,?,?,?,?,?,?)";
        String buyUpdate= "UPDATE phones SET employee_buy_id=? WHERE id='"+PhoneModel.phoneId+"'";
        try{
            pstmt = con.prepareStatement(buyQuery);
            pstmt.setString(1,customer);
            pstmt.setInt(2,zipCode);
            pstmt.setString(3,town);
            pstmt.setString(4,address);
            pstmt.setString(5,phone);
            pstmt.setString(6, idCard);
            pstmt.setInt(7, PhoneModel.phoneId);
            pstmt.executeUpdate();
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
        try{
            pstmt = con.prepareStatement(buyUpdate);
            pstmt.setInt(1,PhoneModel.employeeId);
            pstmt.executeUpdate();
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    
    public void getSoldPhone(){
        try{
            int count = 0;
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM phone_sale WHERE customer_phone_id ='"+PhoneModel.phoneId+"'");
                    while(rs.next()){
                        
                        PhoneModel.customerName = rs.getString("customer_name");
                        PhoneModel.customerZipCode = rs.getString("customer_zip_code");
                        PhoneModel.customerTown = rs.getString("customer_town");
                        PhoneModel.customerAddress = rs.getString("customer_address");
                        PhoneModel.customerPhone = rs.getString("customer_phone");
                        PhoneModel.customerIdCard = rs.getString("customer_id_card");
                        count++;
                    }
                    
                    if(count==0){
                        
                        PhoneModel.customerName = "";
                        PhoneModel.customerZipCode = "";
                        PhoneModel.customerTown = "";
                        PhoneModel.customerAddress = "";
                        PhoneModel.customerPhone = "";
                        PhoneModel.customerIdCard = "";

                    }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    public void getBoughtPhone(){
        try{
            int count = 0;
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM phone_buy WHERE customer_phone_id ='"+PhoneModel.phoneId+"'");
                    while(rs.next()){
                        
                        PhoneModel.customerName = rs.getString("customer_name");
                        PhoneModel.customerZipCode = rs.getString("customer_zip_code");
                        PhoneModel.customerTown = rs.getString("customer_town");
                        PhoneModel.customerAddress = rs.getString("customer_address");
                        PhoneModel.customerPhone = rs.getString("customer_phone");
                        PhoneModel.customerIdCard = rs.getString("customer_id_card");
                        count++;

                    }
                    if(count==0){
                        
                        PhoneModel.customerName = "";
                        PhoneModel.customerZipCode = "";
                        PhoneModel.customerTown = "";
                        PhoneModel.customerAddress = "";
                        PhoneModel.customerPhone = "";
                        PhoneModel.customerIdCard = "";

                    }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    
    public String getCustomerName(){
        return PhoneModel.customerName;
    }
    public String getCustomerZipCode(){
        return customerZipCode;
    }
    public String getCustomerTown(){
        return customerTown;
    }
    public String getCustomerAddress(){
        return customerAddress;
    }
    public String getCustomerPhone(){
        return customerPhone;
    }
    public String getCustomerIdCard(){
        return customerIdCard;
    }
    
        
    
}



