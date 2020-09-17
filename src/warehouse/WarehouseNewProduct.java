/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warehouse;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author gabrieladvice
 */
public class WarehouseNewProduct {
    private static boolean successfull = false;
    private static JFrame product;
    private static WarehouseModel model;
    private JComboBox<String> phoneBrandField;
    private JComboBox<String> phoneModelField;
    private JComboBox<String> brandField;
    private JComboBox<String> productField;    
    private JComboBox<String> colorField;  
    private JComboBox<String> featureField;
    private static int selectedPhoneBrand = 0;
    private static int selectedPhoneModel = 0;
    private static int selectedBrand = 0;
    private static int selectedProduct = 0;
    private static int selectedFeature = 0;
    private static int selectedColor = 0;
    private static String categoryName;
    private static String productId;
    
    public WarehouseNewProduct(JFrame product) throws SQLException{
        this.product = product;
        
        AddProduct();
        
    }
   
    public WarehouseNewProduct(JFrame product, String id) throws SQLException{
        this.product = product;
        this.productId = id;
        
        UpdateProduct();
        
    }


    
    public void AddProduct() throws SQLException{

        model = new WarehouseModel(); 
        
        JLabel category = new JLabel("Category: ");
        category.setBounds(10,10,100,20);
        
        JButton categoryBtn = new JButton(model.getParentName());
        categoryBtn.setBounds(110,10,150,20);
        
        JLabel phoneBrand = new JLabel("Phone Brand: ");
        phoneBrand.setBounds(10,40,100,20);
        
        String[] phoneBrandFieldArray = model.getPhoneBrand().toArray(new String[model.getPhoneBrand().size()]);
        phoneBrandField = new JComboBox(phoneBrandFieldArray);
        phoneBrandField.setBounds(100,40,200,20);
        phoneBrandField.setSelectedIndex(selectedPhoneBrand);
        
        JLabel phoneModel = new JLabel("Phone Model: ");
        phoneModel.setBounds(10,70,200,20);
        
        String[] phoneModelFieldArray = model.getPhoneModel().toArray(new String[model.getPhoneModel().size()]);
        phoneModelField = new JComboBox(phoneModelFieldArray);
        phoneModelField.setBounds(100,70,200,20);
        phoneModelField.setSelectedIndex(selectedPhoneModel);
        if(selectedPhoneBrand != 0) {
            phoneModelField.enable(true);
        }else{
            phoneModelField.enable(false);
        }
        
        JLabel brand = new JLabel("Brand: ");
        brand.setBounds(10,100,100,20);
        
        String[] brandFieldArray = model.getBrand().toArray(new String[model.getBrand().size()]);
        brandField = new JComboBox(brandFieldArray);
        brandField.setBounds(100,100,200,20);
        brandField.setSelectedIndex(selectedBrand);     
        
        JLabel productLabel = new JLabel("Product: ");
        productLabel.setBounds(10,130,100,20);
        
        String[] productFieldArray = model.getProduct().toArray(new String[model.getProduct().size()]);
        productField = new JComboBox(productFieldArray);
        productField.setBounds(100,130,200,20);        
        productField.setSelectedIndex(selectedProduct);        

        JLabel feature = new JLabel("Feature: ");
        feature.setBounds(10,160,100,20);
        
        String[] featureFieldArray = model.getFeature().toArray(new String[model.getFeature().size()]);
        featureField = new JComboBox(featureFieldArray);
        featureField.setBounds(100,160,200,20);        
        featureField.setSelectedIndex(selectedFeature);
        
        JLabel color = new JLabel("Color: ");
        color.setBounds(10,190,100,20);
        
        String[] colorFieldArray = model.getColor().toArray(new String[model.getColor().size()]);
        colorField = new JComboBox(colorFieldArray);
        colorField.setBounds(100,190,200,20);
        colorField.setSelectedIndex(selectedColor);
        
        JLabel purchase = new JLabel("Purchase Price");
        purchase.setBounds(10,220,100,20);
        
        JTextField purchaseField = new JTextField();
        purchaseField.setBounds(100,220,200,20);        

        JLabel selling = new JLabel("Selling Price");
        selling.setBounds(10,250,100,20);
        
        JTextField sellingField = new JTextField();
        sellingField.setBounds(100,250,200,20);         
        
        JButton insertBtn = new JButton("Insert");
        insertBtn.setBounds(10,290,100,50);
        
        insertBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                int purchase = 0;
                int selling = 0;
                if(!purchaseField.getText().isEmpty()){
                    try{
                        purchase = Integer.parseInt(purchaseField.getText());                         
                    }catch(NumberFormatException ex){
                        purchase = 0;                         
                    }
                   
                }
                
                if(!sellingField.getText().isEmpty()){
                    try{
                        selling = Integer.parseInt(sellingField.getText());                         
                    }catch(NumberFormatException ex){
                        purchase = 0;                         
                    }
                   
                }               
                model.insertProduct(phoneBrandField.getSelectedItem().toString(), phoneModelField.getSelectedItem().toString(), brandField.getSelectedItem().toString(), productField.getSelectedItem().toString(), featureField.getSelectedItem().toString(), colorField.getSelectedItem().toString(), purchase, selling);
                product.dispose();
            }       
        });     
        
        phoneBrandField.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(phoneBrandField.getSelectedItem().equals("")){
                    phoneModelField.enable(false);
                }
                else if(phoneBrandField.getSelectedItem().equals("Add new phone brand")){
                    phoneModelField.enable(false);
                    JFrame phone_brand = new JFrame("New phone brand");
                    new WarehouseAddPhoneBrand(phone_brand);
                    phone_brand.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                try {
                                    removeComboBox();
                                    AddProduct();
                                } catch (SQLException ex) {
                                    Logger.getLogger(WarehouseNewProduct.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });
                }else{
                    try {
                        selectedPhoneBrand = phoneBrandField.getSelectedIndex();
                        selectedPhoneModel = 0;
                        model.setSelectedBrand(phoneBrandField.getSelectedItem().toString());
                        removeComboBox();
                        AddProduct();

                    } catch (SQLException ex) {
                        Logger.getLogger(WarehouseNewProduct.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
        });
        
        phoneModelField.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedPhoneModel = phoneModelField.getSelectedIndex();
                if(phoneModelField.getSelectedItem().equals("Add new phone model")){
                    JFrame phone_model = new JFrame("New phone model");
                    new WarehouseAddPhoneModel(phone_model,phoneBrandField.getSelectedItem().toString());
                    phone_model.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                try {
                                    removeComboBox();
                                    AddProduct();
                                } catch (SQLException ex) {
                                   Logger.getLogger(Warehouse.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });
                }
            }
            
        });
                 
        brandField.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedBrand = brandField.getSelectedIndex();
                if(brandField.getSelectedItem().equals("Add new brand")){
                    JFrame brand = new JFrame("New brand");
                    new WarehouseAddBrand(brand);
                    brand.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                try {
                                    removeComboBox();
                                    AddProduct();
                                } catch (SQLException ex) {
                                   Logger.getLogger(Warehouse.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });
                }
            }
            
        });
        
        productField.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedProduct = productField.getSelectedIndex();                 
                if(productField.getSelectedItem().equals("Add new product")){
                    JFrame product = new JFrame("New product");
                    new WarehouseAddProduct(product);
                    product.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                try {
                                    removeComboBox();
                                    AddProduct();
                                } catch (SQLException ex) {
                                   Logger.getLogger(Warehouse.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });
                }
            }
            
        });        
        
        featureField.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedFeature = featureField.getSelectedIndex();                
                if(featureField.getSelectedItem().equals("Add new feature")){
                    JFrame feature = new JFrame("New feature");
                    new WarehouseAddFeature(feature);
                    feature.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                try {
                                    removeComboBox();
                                    AddProduct();
                                } catch (SQLException ex) {
                                   Logger.getLogger(Warehouse.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });
                }
            }
            
        });
        
        colorField.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedColor = colorField.getSelectedIndex();                 
                if(colorField.getSelectedItem().equals("Add new color")){
                    JFrame color = new JFrame("New color");
                    new WarehouseAddColor(color);
                    color.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                try {
                                    removeComboBox();
                                    AddProduct();
                                } catch (SQLException ex) {
                                   Logger.getLogger(Warehouse.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });
                }
            }
            
        });
        
        categoryBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame category = new JFrame();
                new WarehouseCategory(category);
                category.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            categoryBtn.setText(model.getParentName());
                        }
                    });
            }
            
        });
        
        product.add(category);
        product.add(categoryBtn);
        product.add(phoneBrand);
        product.add(phoneBrandField);
        product.add(phoneModel);
        product.add(phoneModelField);
        product.add(brand);
        product.add(brandField);
        product.add(feature);
        product.add(featureField);         
        product.add(color);
        product.add(colorField); 
        product.add(productLabel);
        product.add(productField);
        product.add(purchase);
        product.add(purchaseField);
        product.add(selling);
        product.add(sellingField);
        product.add(insertBtn);
        product.setSize(300,400);
        product.setLayout(null);
        product.setVisible(true);
        product.setLocationRelativeTo(null);
    }
    
    public void UpdateProduct() throws SQLException{

        model = new WarehouseModel();
        
        model.getProductDetails(this.productId);
        
        JLabel category = new JLabel("Category: ");
        category.setBounds(10,10,100,20);
        
        JButton categoryBtn = new JButton(model.getParentName());
        categoryBtn.setBounds(110,10,150,20);
        
        JLabel phoneBrand = new JLabel("Phone Brand: ");
        phoneBrand.setBounds(10,40,100,20);
        
        String[] phoneBrandFieldArray = model.getPhoneBrand().toArray(new String[model.getPhoneBrand().size()]);
        phoneBrandField = new JComboBox(phoneBrandFieldArray);
        phoneBrandField.setBounds(100,40,200,20);
        phoneBrandField.setSelectedItem(model.getPhoneBrandName());
        model.setSelectedBrand(phoneBrandField.getSelectedItem().toString());
        
        JLabel phoneModel = new JLabel("Phone Model: ");
        phoneModel.setBounds(10,70,200,20);
        
        String[] phoneModelFieldArray = model.getPhoneModel().toArray(new String[model.getPhoneModel().size()]);
        phoneModelField = new JComboBox(phoneModelFieldArray);
        phoneModelField.setBounds(100,70,200,20);
        phoneModelField.setSelectedItem(model.getPhoneModelName());

        JLabel brand = new JLabel("Brand: ");
        brand.setBounds(10,100,100,20);
        
        String[] brandFieldArray = model.getBrand().toArray(new String[model.getBrand().size()]);
        brandField = new JComboBox(brandFieldArray);
        brandField.setBounds(100,100,200,20);
        brandField.setSelectedItem(model.getBrandName());     
        
        JLabel productLabel = new JLabel("Product: ");
        productLabel.setBounds(10,130,100,20);
        
        String[] productFieldArray = model.getProduct().toArray(new String[model.getProduct().size()]);
        productField = new JComboBox(productFieldArray);
        productField.setBounds(100,130,200,20);        
        productField.setSelectedItem(model.getProductName());        

        JLabel feature = new JLabel("Feature: ");
        feature.setBounds(10,160,100,20);
        
        String[] featureFieldArray = model.getFeature().toArray(new String[model.getFeature().size()]);
        featureField = new JComboBox(featureFieldArray);
        featureField.setBounds(100,160,200,20);        
        featureField.setSelectedItem(model.getFeatureName());
        
        JLabel color = new JLabel("Color: ");
        color.setBounds(10,190,100,20);
        
        String[] colorFieldArray = model.getColor().toArray(new String[model.getColor().size()]);
        colorField = new JComboBox(colorFieldArray);
        colorField.setBounds(100,190,200,20);
        colorField.setSelectedItem(model.getColorName());
        
        JLabel purchase = new JLabel("Purchase Price");
        purchase.setBounds(10,220,100,20);
        
        JTextField purchaseField = new JTextField();
        purchaseField.setBounds(100,220,200,20);
        purchaseField.setText(model.getPurchasePrice());

        JLabel selling = new JLabel("Selling Price");
        selling.setBounds(10,250,100,20);
        
        JTextField sellingField = new JTextField();
        sellingField.setBounds(100,250,200,20);
        sellingField.setText(model.getSellingPrice());
        
        JButton updateBtn = new JButton("Update");
        updateBtn.setBounds(10,290,100,50);
        
        updateBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                int purchase = 0;
                int selling = 0;
                if(!purchaseField.getText().isEmpty()){
                    try{
                        purchase = Integer.parseInt(purchaseField.getText());                         
                    }catch(NumberFormatException ex){
                        purchase = 0;                         
                    }
                   
                }
                
                if(!sellingField.getText().isEmpty()){
                    try{
                        selling = Integer.parseInt(sellingField.getText());                         
                    }catch(NumberFormatException ex){
                        purchase = 0;                         
                    }
                   
                }               
                model.updateProduct(productId, phoneBrandField.getSelectedItem().toString(), phoneModelField.getSelectedItem().toString(), brandField.getSelectedItem().toString(), productField.getSelectedItem().toString(), featureField.getSelectedItem().toString(), colorField.getSelectedItem().toString(), purchase, selling);
                product.dispose();
            }       
        });
        
        phoneBrandField.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(phoneBrandField.getSelectedItem().equals("")){
                    phoneModelField.enable(false);
                }
                else if(phoneBrandField.getSelectedItem().equals("Add new phone brand")){
                    phoneModelField.enable(false);
                    JFrame phone_brand = new JFrame("New phone brand");
                    new WarehouseAddPhoneBrand(phone_brand);
                    phone_brand.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                try {
                                    removeComboBox();
                                    AddProduct();
                                } catch (SQLException ex) {
                                    Logger.getLogger(WarehouseNewProduct.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });
                }else{
                    try {
                        selectedPhoneBrand = phoneBrandField.getSelectedIndex();
                        selectedPhoneModel = 0;
                        model.setSelectedBrand(phoneBrandField.getSelectedItem().toString());
                        removeComboBox();
                        AddProduct();

                    } catch (SQLException ex) {
                        Logger.getLogger(WarehouseNewProduct.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
        });
        
        phoneModelField.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedPhoneModel = phoneModelField.getSelectedIndex();
                if(phoneModelField.getSelectedItem().equals("Add new phone model")){
                    JFrame phone_model = new JFrame("New phone model");
                    new WarehouseAddPhoneModel(phone_model,phoneBrandField.getSelectedItem().toString());
                    phone_model.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                try {
                                    removeComboBox();
                                    AddProduct();
                                } catch (SQLException ex) {
                                   Logger.getLogger(Warehouse.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });
                }
            }
            
        });
                 
        brandField.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedBrand = brandField.getSelectedIndex();
                if(brandField.getSelectedItem().equals("Add new brand")){
                    JFrame brand = new JFrame("New brand");
                    new WarehouseAddBrand(brand);
                    brand.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                try {
                                    removeComboBox();
                                    AddProduct();
                                } catch (SQLException ex) {
                                   Logger.getLogger(Warehouse.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });
                }
            }
            
        });
        
        productField.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedProduct = productField.getSelectedIndex();                 
                if(productField.getSelectedItem().equals("Add new product")){
                    JFrame product = new JFrame("New product");
                    new WarehouseAddProduct(product);
                    product.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                try {
                                    removeComboBox();
                                    AddProduct();
                                } catch (SQLException ex) {
                                   Logger.getLogger(Warehouse.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });
                }
            }
            
        });        
        
        featureField.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedFeature = featureField.getSelectedIndex();                
                if(featureField.getSelectedItem().equals("Add new feature")){
                    JFrame feature = new JFrame("New feature");
                    new WarehouseAddFeature(feature);
                    feature.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                try {
                                    removeComboBox();
                                    AddProduct();
                                } catch (SQLException ex) {
                                   Logger.getLogger(Warehouse.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });
                }
            }
            
        });
        
        colorField.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedColor = colorField.getSelectedIndex();                 
                if(colorField.getSelectedItem().equals("Add new color")){
                    JFrame color = new JFrame("New color");
                    new WarehouseAddColor(color);
                    color.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                try {
                                    removeComboBox();
                                    AddProduct();
                                } catch (SQLException ex) {
                                   Logger.getLogger(Warehouse.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });
                }
            }
            
        });
        
        categoryBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame category = new JFrame();
                new WarehouseCategory(category);
                category.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            categoryBtn.setText(model.getParentName());
                        }
                    });
            }
            
        });
        
        product.add(category);
        product.add(categoryBtn);
        product.add(phoneBrand);
        product.add(phoneBrandField);
        product.add(phoneModel);
        product.add(phoneModelField);
        product.add(brand);
        product.add(brandField);
        product.add(feature);
        product.add(featureField);         
        product.add(color);
        product.add(colorField); 
        product.add(productLabel);
        product.add(productField);
        product.add(purchase);
        product.add(purchaseField);
        product.add(selling);
        product.add(sellingField);
        product.add(updateBtn);
        product.setSize(300,400);
        product.setLayout(null);
        product.setVisible(true);
        product.setLocationRelativeTo(null);
    }
    
    public boolean getSuccessfull(){
        
        return successfull;
        
    }
    
    private void removeComboBox(){
        product.remove(phoneBrandField);
        product.remove(phoneModelField);
        product.remove(brandField);
        product.remove(productField);        
        product.remove(featureField);  
        product.remove(colorField);   
    }
}
