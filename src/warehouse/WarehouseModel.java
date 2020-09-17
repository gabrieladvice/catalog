/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warehouse;

import dbUtil.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Gabika
 */
public class WarehouseModel {
    
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;
    private static PreparedStatement pstmt;
    private static ResultSetMetaData rsmd;
    private static int companyId;
    private static ArrayList<String> category;
    private static int categoryId = 0;
    private static DefaultTableModel model;
    private static int parentId = 0;
    private static String categoryLine = "-";
    private static String prewCategoryLine;
    private static int brandId;
    private static int selectedBrandId;
    private static String selectedBrand;
    private static String parentName;
    private String phoneBrandName;
    private String phoneModelName;
    private String brandName;
    private String productName;
    private String featureName;
    private String colorName;
    private String qty;
    private String purchasePrice;
    private String sellingPrice;
    
    public WarehouseModel(int companyId) throws SQLException{
        WarehouseModel.companyId = companyId;
        con = DbConnection.getConnection();
    }
    
    public WarehouseModel(){
        
    }

    
    public void setCategory() throws SQLException{
        
        stmt = con.createStatement();
        if(WarehouseModel.categoryId == 0){
            rs = stmt.executeQuery("SELECT category_name FROM warehouse_category WHERE company_id='"+WarehouseModel.companyId+"' and parent_id IS NULL");            
        }else{
            rs = stmt.executeQuery("SELECT category_name FROM warehouse_category WHERE company_id='"+WarehouseModel.companyId+"' and parent_id='"+WarehouseModel.categoryId+"'");            
        }

        rsmd = rs.getMetaData(); 
        int columnCount = rsmd.getColumnCount();
        category = new ArrayList<>(columnCount);
        while(rs.next()){
            int i=0;
            while(i<columnCount){
                category.add(rs.getString("category_name"));
                i++;
            }
        }
    }
    
    public void setParentId(){
        WarehouseModel.parentId = 0;
    }
    
    public void setCategoryId(){
        WarehouseModel.categoryId = 0;
    }
    
    public void setSubCategory(String categoryName) throws SQLException{
        WarehouseModel.parentId = WarehouseModel.categoryId;
        String query;
        if(parentId == 0){
            query="SELECT id, parent_id FROM warehouse_category WHERE company_id='"+WarehouseModel.companyId+"' and category_name='"+categoryName+"' and parent_id is null";            
        }else{
            query="SELECT id, parent_id FROM warehouse_category WHERE company_id='"+WarehouseModel.companyId+"' and  category_name='"+categoryName+"' and parent_id='"+WarehouseModel.parentId+"'";              
        }

        stmt = con.createStatement();
        rs = stmt.executeQuery(query);
        rsmd = rs.getMetaData(); 
        while(rs.next()){
            WarehouseModel.categoryId = rs.getInt("id");
            WarehouseModel.parentId = rs.getInt("parent_id");
        }
        if(categoryLine.equals("-")){
            categoryLine = ("-"+Integer.toString(getCategoryId()) + "-");
        }else{
            prewCategoryLine = categoryLine;
            categoryLine = prewCategoryLine.concat(Integer.toString(getCategoryId()) + "-");               
        }
        
        query ="SELECT category_name FROM warehouse_category WHERE company_id='"+WarehouseModel.companyId+"' and id='"+WarehouseModel.categoryId+"'";
        
        rs = stmt.executeQuery(query);
        rsmd = rs.getMetaData(); 
        while(rs.next()){
            WarehouseModel.parentName = rs.getString("category_name");
        }
        
        
        rs = stmt.executeQuery("SELECT category_name FROM warehouse_category WHERE company_id='"+WarehouseModel.companyId+"' and parent_id='"+WarehouseModel.categoryId+"'");
        rsmd = rs.getMetaData(); 
        int columnCount = rsmd.getColumnCount();
        category = new ArrayList<>(columnCount);
        while(rs.next()){
            int i=0;
            while(i<columnCount){
                category.add(rs.getString("category_name"));
                i++;
            }
        }

    }
    
    public void setSubBackCategory() throws SQLException{
        int last_in = categoryLine.indexOf(categoryId+"-");
        WarehouseModel.categoryId = WarehouseModel.parentId;

        String query ="SELECT parent_id FROM warehouse_category WHERE company_id='"+WarehouseModel.companyId+"' and id='"+WarehouseModel.categoryId+"'";
        
        rs = stmt.executeQuery(query);
        rsmd = rs.getMetaData(); 
        while(rs.next()){
            WarehouseModel.parentId = rs.getInt("parent_id");
        }
        
        query ="SELECT category_name FROM warehouse_category WHERE company_id='"+WarehouseModel.companyId+"' and id='"+WarehouseModel.categoryId+"'";
        
        rs = stmt.executeQuery(query);
        rsmd = rs.getMetaData(); 
        while(rs.next()){
            WarehouseModel.parentName = rs.getString("category_name");
        }
        
        if(WarehouseModel.categoryId == 0 && WarehouseModel.parentId == 0){
            query = "SELECT category_name FROM warehouse_category WHERE company_id='"+WarehouseModel.companyId+"' and parent_id IS NULL";
        }else{
            query = "SELECT category_name FROM warehouse_category WHERE company_id='"+WarehouseModel.companyId+"' and parent_id='"+WarehouseModel.categoryId+"'";
        }
        

        categoryLine = categoryLine.substring(0, last_in);

        
        rs = stmt.executeQuery(query);
        rsmd = rs.getMetaData(); 
        int columnCount = rsmd.getColumnCount();
        category = new ArrayList<>(columnCount);
        while(rs.next()){
            int i=0;
            while(i<columnCount){
                category.add(rs.getString("category_name"));
                i++;
            }

        }
        

    }
    
    public ArrayList<String> getCategory(){
        return category;
    }
    
    public int getParentId(){
        return WarehouseModel.parentId;
    }
    
    public int getCategoryId(){
        return WarehouseModel.categoryId;
    }
    
    public String getParentName(){
        return WarehouseModel.parentName;
    }
   
    public DefaultTableModel getTableModel(){
        try{
            if(WarehouseModel.categoryId==0){
                rs = stmt.executeQuery("SELECT * FROM warehouse_relationships "
                                     + "inner join phone_brand on warehouse_relationships.phone_brand = phone_brand.id "
                                     + "inner join phone_model on warehouse_relationships.phone_model = phone_model.id "
                                     + "inner join warehouse_brand on warehouse_relationships.brand = warehouse_brand.id "                    
                                     + "inner join warehouse_product on warehouse_relationships.product = warehouse_product.id "
                                     + "inner join warehouse_feature on warehouse_relationships.feature = warehouse_feature.id "
                                     + "inner join warehouse_color on warehouse_relationships.color = warehouse_color.id "
                                     + "WHERE companyId='"+WarehouseModel.companyId+"'");
  
            }else{

                //rs = stmt.executeQuery("SELECT product_name, qty FROM warehouse_product WHERE category_id LIKE '%"+getCategoryId()+"%'");
                rs = stmt.executeQuery("SELECT * FROM warehouse_relationships "
                                     + "inner join phone_brand on warehouse_relationships.phone_brand = phone_brand.id "
                                     + "inner join phone_model on warehouse_relationships.phone_model = phone_model.id "
                                     + "inner join warehouse_brand on warehouse_relationships.brand = warehouse_brand.id "                    
                                     + "inner join warehouse_product on warehouse_relationships.product = warehouse_product.id "
                                     + "inner join warehouse_feature on warehouse_relationships.feature = warehouse_feature.id "
                                     + "inner join warehouse_color on warehouse_relationships.color = warehouse_color.id "
                                     + "WHERE category_line LIKE '%-"+getCategoryId()+"-%'"
                                     + "AND companyId='"+WarehouseModel.companyId+"'");
            }
            //String [] column = {"Phone Brand", "Phone Model", "Brand", "Product", "Feature", "Color", "Quantity"};
            String [] column = {"id","Product", "Quantity", "Purchase Price", "Selling Price"};
            model = new DefaultTableModel(column,0){
                @Override
                public boolean isCellEditable(int row, int m) {
                    return false;
                }
                
                
                
            };

            while(rs.next()){
                String [] incData = {rs.getString("phone_brand_name"), 
                                     rs.getString("phone_model_name"), 
                                     rs.getString("brand_name"),
                                     rs.getString("product_name"), 
                                     rs.getString("feature_name"), 
                                     rs.getString("color_name")};                      
                String str = String.join(" ", incData);
                String [] data = {rs.getString("id"), str, rs.getString("qty"), rs.getString("purchase_price"),rs.getString("selling_price")};
                model.addRow(data);
                
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }

        return WarehouseModel.model;
    }
    
    public boolean insertNewCategory(String category_name){
        String query = "INSERT INTO warehouse_category (category_name, parent_id, company_id) VALUES (?,?,?)";   
        
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1,category_name);
            if(getCategoryId() == 0){
                pstmt.setNull(2,getCategoryId());
            }else{
                pstmt.setInt(2,getCategoryId());                
            }
            pstmt.setInt(3,WarehouseModel.companyId);
            pstmt.executeUpdate();
                            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            return false;
        }
        return true;
    }
    
    public boolean insertNewPhoneBrand(String phone_brand_name){
        String query = "INSERT INTO phone_brand (phone_brand_name, company_id) VALUES (?,?)";   
        
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1,phone_brand_name);
            pstmt.setInt(2,WarehouseModel.companyId);
            pstmt.executeUpdate();
                            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            return false;
        }
        return true;
    }

    public ArrayList<String> getPhoneBrand() throws SQLException{

        rs = stmt.executeQuery("SELECT phone_brand_name FROM phone_brand");
        rsmd = rs.getMetaData(); 
        int columnCount = rsmd.getColumnCount();
        ArrayList<String> phone_brands = new ArrayList<>(columnCount);
        phone_brands.add("");
        while(rs.next()){
            int i=0;
            while(i<columnCount){
                phone_brands.add(rs.getString("phone_brand_name"));
                i++;
            }
        }
        phone_brands.add("Add new phone brand");
        return phone_brands;
    }
    
    public boolean insertNewPhoneModel(String phone_model_name, String phone_brand) throws SQLException{
        String query = "INSERT INTO phone_model (phone_model_name, brand_id, company_id) VALUES (?,?,?)";   

        stmt = con.createStatement();
        rs = stmt.executeQuery("SELECT id FROM phone_brand WHERE phone_brand_name='"+phone_brand+"'");
        while(rs.next()){
            WarehouseModel.brandId = rs.getInt("id");
        }
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1,phone_model_name);
            pstmt.setInt(2,WarehouseModel.brandId);
            pstmt.setInt(3,WarehouseModel.companyId);
            pstmt.executeUpdate();
                            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            return false;
        }
        return true;
    }
    
    public ArrayList<String> getPhoneModel() throws SQLException{

        rs = stmt.executeQuery("SELECT id FROM phone_brand WHERE phone_brand_name='"+this.selectedBrand+"'");
        while(rs.next()){
            WarehouseModel.selectedBrandId = rs.getInt("id");
        }
        rs = stmt.executeQuery("SELECT phone_model_name FROM phone_model WHERE brand_id='"+WarehouseModel.selectedBrandId+"'");
        rsmd = rs.getMetaData(); 
        int columnCount = rsmd.getColumnCount();
        ArrayList<String> phone_models = new ArrayList<>(columnCount);
        phone_models.add("");
        while(rs.next()){
            int i=0;
            while(i<columnCount){
                phone_models.add(rs.getString("phone_model_name"));
                i++;
            }
        }
        phone_models.add("Add new phone model");
        return phone_models;
        
    }
    
    public void setSelectedBrand(String selectedBrand){
        
        WarehouseModel.selectedBrand = selectedBrand;
    }
    
    public boolean insertNewBrand(String brand_name){
        String query = "INSERT INTO warehouse_brand (brand_name, company_id) VALUES (?,?)";   
        
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1,brand_name);
            pstmt.setInt(2,WarehouseModel.companyId);
            pstmt.executeUpdate();
                            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            return false;
        }
        return true;
    }
    
    public ArrayList<String> getBrand() throws SQLException{

        rs = stmt.executeQuery("SELECT brand_name FROM warehouse_brand");
        rsmd = rs.getMetaData(); 
        int columnCount = rsmd.getColumnCount();
        ArrayList<String> brands = new ArrayList<>(columnCount);
        brands.add("");
        while(rs.next()){
            int i=0;
            while(i<columnCount){
                brands.add(rs.getString("brand_name"));
                i++;
            }
        }
        brands.add("Add new brand");
        return brands;
    }
    
    public boolean insertNewProduct(String product_name){
        String query = "INSERT INTO warehouse_product (product_name, company_id) VALUES (?,?)";   
        
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1,product_name);
            pstmt.setInt(2,WarehouseModel.companyId);
            pstmt.executeUpdate();
                            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            return false;
        }
        return true;
    }
    
    public ArrayList<String> getProduct() throws SQLException{

        rs = stmt.executeQuery("SELECT product_name FROM warehouse_product");
        rsmd = rs.getMetaData(); 
        int columnCount = rsmd.getColumnCount();
        ArrayList<String> products = new ArrayList<>(columnCount);
        products.add("");
        while(rs.next()){
            int i=0;
            while(i<columnCount){
                products.add(rs.getString("product_name"));
                i++;
            }
        }
        products.add("Add new product");
        return products;
    }
    
    public boolean insertNewColor(String color_name){
        String query = "INSERT INTO warehouse_color (color_name, company_id) VALUES (?,?)";   
        
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1,color_name);
            pstmt.setInt(2,WarehouseModel.companyId);
            pstmt.executeUpdate();
                            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            return false;
        }
        return true;
    }
    
    public ArrayList<String> getColor() throws SQLException{

        rs = stmt.executeQuery("SELECT color_name FROM warehouse_color");
        rsmd = rs.getMetaData(); 
        int columnCount = rsmd.getColumnCount();
        ArrayList<String> colors = new ArrayList<>(columnCount);
        colors.add("");
        while(rs.next()){
            int i=0;
            while(i<columnCount){
                colors.add(rs.getString("color_name"));
                i++;
            }
        }
        colors.add("Add new color");
        return colors;
    }
    
    public boolean insertNewFeature(String feature_name){
        String query = "INSERT INTO warehouse_feature (feature_name, company_id) VALUES (?,?)";   
        
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1,feature_name);
            pstmt.setInt(2,WarehouseModel.companyId);
            pstmt.executeUpdate();
                            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            return false;
        }
        return true;
    }
    
    public ArrayList<String> getFeature() throws SQLException{

        rs = stmt.executeQuery("SELECT feature_name FROM warehouse_feature");
        rsmd = rs.getMetaData(); 
        int columnCount = rsmd.getColumnCount();
        ArrayList<String> features = new ArrayList<>(columnCount);
        features.add("");
        while(rs.next()){
            int i=0;
            while(i<columnCount){
                features.add(rs.getString("feature_name"));
                i++;
            }
        }
        features.add("Add new feature");
        return features;
    } 
    
    public void insertProduct(String phoneBrand, String phoneModel, String brand, String product, String feature, String color, int purchase, int selling){
        int phoneBrandId = 0;
        int phoneModelId = 0;
        int brandIdQ = 0;
        int productId = 0;
        int featureId = 0;
        int colorId = 0;
        
        String query = "INSERT INTO warehouse_relationships (phone_brand, phone_model, brand, product, feature, color, companyId, category_line, qty, purchase_price, selling_price) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        
        String phoneBrandQuery = "SELECT id FROM phone_brand WHERE phone_brand_name='"+phoneBrand+"'";
        String phoneModelQuery = "SELECT id FROM phone_model WHERE phone_model_name='"+phoneModel+"'";
        String brandQuery = "SELECT id FROM warehouse_brand WHERE brand_name='"+brand+"'";
        String productQuery = "SELECT id FROM warehouse_product WHERE product_name='"+product+"'";
        String featureQuery = "SELECT id FROM warehouse_feature WHERE feature_name='"+feature+"'";
        String colorQuery = "SELECT id FROM warehouse_color WHERE color_name='"+color+"'";        
        
        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery(phoneBrandQuery);
            while(rs.next()){
                phoneBrandId = rs.getInt("id");
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
        
        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery(phoneModelQuery);
            while(rs.next()){
                phoneModelId = rs.getInt("id");
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        } 
        
        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery(brandQuery);
            while(rs.next()){
                brandIdQ = rs.getInt("id");
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
        
        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery(productQuery);
            while(rs.next()){
                productId = rs.getInt("id");
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }  
        
        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery(featureQuery);
            while(rs.next()){
                featureId = rs.getInt("id");
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
        
        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery(colorQuery);
            while(rs.next()){
                colorId = rs.getInt("id");
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }  

        
        try {

            pstmt = con.prepareStatement(query);
            pstmt.setInt(1,phoneBrandId);
            pstmt.setInt(2,phoneModelId);
            pstmt.setInt(3,brandIdQ);
            pstmt.setInt(4, productId);
            pstmt.setInt(5, featureId);
            pstmt.setInt(6, colorId);
            pstmt.setInt(7, WarehouseModel.companyId);
            pstmt.setString(8, WarehouseModel.categoryLine);
            pstmt.setInt(9, 0);
            pstmt.setInt(10, purchase);
            pstmt.setInt(11, selling);
            pstmt.executeUpdate();

                            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public boolean updateQty(String product_id, int qty){
        String query = "UPDATE warehouse_relationships SET qty=? WHERE id='"+product_id+"'";
        
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1,qty);
            pstmt.executeUpdate();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            return false;
        }
        return true;
    }
    
    public void removeProduct(String product_id){
        
        try {
            stmt.execute("DELETE FROM warehouse_relationships WHERE Id ="+product_id+"");
        } catch (SQLException ex) {
            Logger.getLogger(WarehouseModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void getProductDetails(String id) throws SQLException{
        
        
        con = DbConnection.getConnection();
        stmt = con.createStatement();
        rs = stmt.executeQuery("SELECT * FROM warehouse_relationships "
                             + "inner join phone_brand on warehouse_relationships.phone_brand = phone_brand.id "
                             + "inner join phone_model on warehouse_relationships.phone_model = phone_model.id "
                             + "inner join warehouse_brand on warehouse_relationships.brand = warehouse_brand.id "                    
                             + "inner join warehouse_product on warehouse_relationships.product = warehouse_product.id "
                             + "inner join warehouse_feature on warehouse_relationships.feature = warehouse_feature.id "
                             + "inner join warehouse_color on warehouse_relationships.color = warehouse_color.id "
                             + "WHERE companyId='"+WarehouseModel.companyId+"' AND warehouse_relationships.id='"+id+"'");
        while(rs.next()){
                this.phoneBrandName = rs.getString("phone_brand_name");
                this.phoneModelName = rs.getString("phone_model_name"); 
                this.brandName = rs.getString("brand_name");
                this.productName= rs.getString("product_name"); 
                this.featureName = rs.getString("feature_name"); 
                this.colorName = rs.getString("color_name");   
                this.qty = rs.getString("qty");
                this.purchasePrice = rs.getString("purchase_price");
                this.sellingPrice = rs.getString("selling_price");
                WarehouseModel.categoryLine = rs.getString("category_line");
        }
        
        String tmp = WarehouseModel.categoryLine.substring(0, WarehouseModel.categoryLine.length()-1);
        WarehouseModel.categoryId = Integer.parseInt(tmp.substring(tmp.lastIndexOf('-')+1, tmp.length()));
        WarehouseModel.prewCategoryLine = tmp.substring(0,tmp.lastIndexOf('-')+1);
      
        
        String query ="SELECT category_name FROM warehouse_category WHERE company_id='"+WarehouseModel.companyId+"' and id='"+WarehouseModel.categoryId+"'";
        
        rs = stmt.executeQuery(query);
        rsmd = rs.getMetaData(); 
        while(rs.next()){
            WarehouseModel.parentName = rs.getString("category_name");
        }
        WarehouseModel.parentId = Integer.parseInt(WarehouseModel.prewCategoryLine.substring(WarehouseModel.prewCategoryLine.lastIndexOf('-')-1, WarehouseModel.prewCategoryLine.length()-1));
        
        setCategory();
        
        System.out.println(WarehouseModel.parentId);
        System.out.println(WarehouseModel.categoryLine);
        System.out.println(WarehouseModel.categoryId);
        System.out.println(WarehouseModel.prewCategoryLine);
                    
    }
    
    public String getPhoneBrandName() {
        return phoneBrandName;
    }

    public String getPhoneModelName() {
        return phoneModelName;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getProductName() {
        return productName;
    }

    public String getFeatureName() {
        return featureName;
    }

    public String getColorName() {
        return colorName;
    }

    public String getQty() {
        return qty;
    }

    public String getPurchasePrice() {
        return purchasePrice;
    }

    public String getSellingPrice() {
        return sellingPrice;
    }
    
    public void updateProduct(String id, String phoneBrand, String phoneModel, String brand, String product, String feature, String color, int purchase, int selling){
        
        int phoneBrandId = 0;
        int phoneModelId = 0;
        int brandIdQ = 0;
        int productId = 0;
        int featureId = 0;
        int colorId = 0;
        
        String query = "UPDATE warehouse_relationships SET phone_brand=?, phone_model=?, brand=?, product=?, feature=?, color=?, category_line=?, purchase_price=?, selling_price=? WHERE warehouse_relationships.id='"+id+"'";
           
        String phoneBrandQuery = "SELECT id FROM phone_brand WHERE phone_brand_name='"+phoneBrand+"'";
        String phoneModelQuery = "SELECT id FROM phone_model WHERE phone_model_name='"+phoneModel+"'";
        String brandQuery = "SELECT id FROM warehouse_brand WHERE brand_name='"+brand+"'";
        String productQuery = "SELECT id FROM warehouse_product WHERE product_name='"+product+"'";
        String featureQuery = "SELECT id FROM warehouse_feature WHERE feature_name='"+feature+"'";
        String colorQuery = "SELECT id FROM warehouse_color WHERE color_name='"+color+"'";        
        
        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery(phoneBrandQuery);
            while(rs.next()){
                phoneBrandId = rs.getInt("id");
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
        
        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery(phoneModelQuery);
            while(rs.next()){
                phoneModelId = rs.getInt("id");
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        } 
        
        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery(brandQuery);
            while(rs.next()){
                brandIdQ = rs.getInt("id");
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
        
        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery(productQuery);
            while(rs.next()){
                productId = rs.getInt("id");
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }  
        
        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery(featureQuery);
            while(rs.next()){
                featureId = rs.getInt("id");
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
        
        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery(colorQuery);
            while(rs.next()){
                colorId = rs.getInt("id");
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }  

        
        try {

            pstmt = con.prepareStatement(query);
            pstmt.setInt(1,phoneBrandId);
            pstmt.setInt(2,phoneModelId);
            pstmt.setInt(3,brandIdQ);
            pstmt.setInt(4,productId);
            pstmt.setInt(5,featureId);
            pstmt.setInt(6,colorId);
            pstmt.setString(7,WarehouseModel.categoryLine);
            pstmt.setInt(8,purchase);
            pstmt.setInt(9,selling);
            pstmt.executeUpdate();

                            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
}


