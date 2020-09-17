/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warehouse;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.MenuComponent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;


/**
 *
 * @author Gabika
 */
public class Warehouse {
    
    private static ArrayList<String> categories;
    private static JPanel mainPanel;
    private static JPanel backPanel;
    private static JPanel catPanel;
    private static JPanel tablePanel;
    private static JPanel searchPanel;
    private static JPanel filterPanel;
    private static JPanel newCatPanel;
    private static int companyId;
    private static WarehouseModel model;
    private static int selectedRow;
    private static int qty;
    private static int prewQty;
    private static JTable table;
    private static int selectedPhoneBrand = 0;
    private static int selectedPhoneModel = 0;
    private static int selectedBrand = 0;
    private static int selectedProduct = 0;
    private static int selectedFeature = 0;
    private static int selectedColor = 0;
    private JComboBox brandField;
    private JComboBox productField;
    private JComboBox featureField;
    private JComboBox colorField;
    private JComboBox phoneModelField;
    private JComboBox phoneBrandField;
    private String backText;
    private JPopupMenu popup;
    private JMenuItem removeItem;
    
    public Warehouse(JPanel panel, int companyId) throws SQLException {
        this.mainPanel = panel;
        Warehouse.companyId = companyId;
        mainPanel.removeAll();
        mainPanel.repaint();   
        
        model= new WarehouseModel(Warehouse.companyId);
        model.setCategoryId();
        model.setParentId();
        model.setCategory();
        
        tablePanel = new JPanel();
        tablePanel.setBackground(Color.white);
        tablePanel.setBorder(BorderFactory.createLineBorder(Color.black));
        tablePanel.setBounds(150,50,1000,500);
        tablePanel.setLayout(new BoxLayout(tablePanel,BoxLayout.PAGE_AXIS));
        
        searchPanel = new JPanel();
        searchPanel.setBackground(Color.white);
        searchPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        filterPanel = new JPanel();
        filterPanel.setBackground(Color.white);
        filterPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        filterPanel.setPreferredSize(filterPanel.getPreferredSize());
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.LINE_AXIS));
        
        backPanel = new JPanel();
        backPanel.setBackground(Color.white);
        backPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        backPanel.setBounds(0,50,150,50);      
        
        catPanel = new JPanel();
        catPanel.setBackground(Color.white);
        catPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        catPanel.setBounds(0,100,150,400);
        
        newCatPanel = new JPanel();
        newCatPanel.setBackground(Color.white);
        newCatPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        newCatPanel.setBounds(0,500,150,50);
        
        panel.add(backPanel);
        panel.add(tablePanel);
        panel.add(catPanel);
        panel.add(newCatPanel);
        panel.setLayout(null);
        panel.setVisible(true);
        StorageView();
        
    }
    
    public void StorageView() throws SQLException{
        
        repaintPanel();
        Warehouse.categories = model.getCategory();
 
        JButton back = new JButton();
        back.setText(getBackBtn());
        back.setSize(100,100);
        
        JButton newCat = new JButton("+ new category");
        newCat.setSize(100,100);
        
        JButton newProduct = new JButton("+ new product");
        newProduct.setSize(100,100);
        
        JLabel search = new JLabel("Search");
    
        JTextField searchField = new JTextField(20);
        searchField.setLocation(10, 110);
        searchField.setMaximumSize(searchField.getPreferredSize());
        
        String[] phoneBrandFieldArray = model.getPhoneBrand().toArray(new String[model.getPhoneBrand().size()]);
        phoneBrandField = new JComboBox(phoneBrandFieldArray);
        phoneBrandField.removeItemAt(0);
        phoneBrandField.insertItemAt("Phone Brand", 0);
        phoneBrandField.setSelectedIndex(0);
        phoneBrandField.removeItemAt(phoneBrandFieldArray.length-1);
        phoneBrandField.setSelectedIndex(selectedPhoneBrand);

        String[] phoneModelFieldArray = model.getPhoneModel().toArray(new String[model.getPhoneModel().size()]);
        phoneModelField = new JComboBox(phoneModelFieldArray);      
        phoneModelField.removeItemAt(0);
        phoneModelField.insertItemAt("Phone Model", 0);
        phoneModelField.setSelectedIndex(0);
        phoneModelField.removeItemAt(phoneModelFieldArray.length-1);
        phoneModelField.setSelectedIndex(selectedPhoneModel);
        
        String[] brandFieldArray = model.getBrand().toArray(new String[model.getBrand().size()]);
        brandField = new JComboBox(brandFieldArray);
        brandField.removeItemAt(0);
        brandField.insertItemAt("Brand", 0);
        brandField.setSelectedIndex(0);
        brandField.removeItemAt(brandFieldArray.length-1);
        brandField.setSelectedIndex(selectedBrand);
        
        String[] productFieldArray = model.getProduct().toArray(new String[model.getProduct().size()]);
        productField = new JComboBox(productFieldArray);       
        productField.removeItemAt(0);
        productField.insertItemAt("Product", 0);
        productField.setSelectedIndex(0);
        productField.removeItemAt(productFieldArray.length-1);
        productField.setSelectedIndex(selectedProduct);
        
        String[] featureFieldArray = model.getFeature().toArray(new String[model.getFeature().size()]);
        featureField = new JComboBox(featureFieldArray);
        featureField.removeItemAt(0);
        featureField.insertItemAt("Feature", 0);
        featureField.setSelectedIndex(0);
        featureField.removeItemAt(featureFieldArray.length-1);
        featureField.setSelectedIndex(selectedFeature);
        
        String[] colorFieldArray = model.getColor().toArray(new String[model.getColor().size()]);
        colorField = new JComboBox(colorFieldArray);
        colorField.removeItemAt(0);
        colorField.insertItemAt("Color", 0);
        colorField.setSelectedIndex(0);
        colorField.removeItemAt(colorFieldArray.length-1);
        colorField.setSelectedIndex(selectedColor);
        
        popup = new JPopupMenu();
        removeItem = new JMenuItem("Remove Product");
        popup.add(removeItem);
        
        newCatPanel.add(newCat);
        tablePanel.add(searchPanel);
        tablePanel.add(filterPanel);
        searchPanel.add(search);
        searchPanel.add(searchField);
        searchPanel.add(newProduct);
        filterPanel.add(phoneBrandField);
        filterPanel.add(phoneModelField);
        filterPanel.add(brandField);
        filterPanel.add(productField);
        filterPanel.add(featureField);
        filterPanel.add(colorField);
         
        if(model.getCategoryId()!=0 || model.getParentId()!=0){
            backPanel.add(back);
        }
        back.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {

                try {
                    model.setSubBackCategory();
                    setBackBtn(model.getParentName());
                    removePanel();
                    StorageView();
                } catch (SQLException ex) {
                    Logger.getLogger(Warehouse.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
        
        newCat.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                    JFrame category = new JFrame("New Category");
                    new WarehouseAddCategory(category);
                    category.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            try {
                                model.setCategory();
                                Warehouse.categories = model.getCategory();                                
                                removePanel();
                                StorageView();
                            } catch (SQLException ex) {
                               Logger.getLogger(Warehouse.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
            }
        });
              
        newProduct.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                    JFrame product = new JFrame("New product");
                try {
                    new WarehouseNewProduct(product);
                } catch (SQLException ex) {
                    Logger.getLogger(Warehouse.class.getName()).log(Level.SEVERE, null, ex);
                }
                    product.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            try {
                                setBackBtn(model.getParentName());                                
                                removePanel();
                                StorageView();
                            } catch (SQLException ex) {
                               Logger.getLogger(Warehouse.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        
                        @Override
                        public void windowClosed(WindowEvent e) {
                            try {
                                setBackBtn(model.getParentName());                                
                                removePanel();
                                StorageView();
                            } catch (SQLException ex) {
                               Logger.getLogger(Warehouse.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
            }
        });

        phoneBrandField.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    filterTable();
                    storeSelection();
                    removePanel();
                    model.setSelectedBrand((phoneBrandField.getSelectedItem().toString()));
                    StorageView();
                    
                } catch (SQLException ex) {
                    Logger.getLogger(Warehouse.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
        
        phoneModelField.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                storeSelection();
                filterTable();               
            }
            
        });
                 
        brandField.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                storeSelection();
                filterTable();
            }
            
        });
        
        productField.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                storeSelection();
                filterTable();
            }
            
        });      
        
        featureField.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                storeSelection();
                filterTable();
            }
            
        });
        
        colorField.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                storeSelection();
                filterTable();
            }
            
        });
        
             
        ActionListener e = new ActionListener(){
            public void actionPerformed(ActionEvent ae) {

                try {
                    model.setSubCategory(ae.getActionCommand());
                    setBackBtn(model.getParentName());
                    removePanel();
                    StorageView();
                } catch (SQLException ex) {
                    Logger.getLogger(Warehouse.class.getName()).log(Level.SEVERE, null, ex);
                }
            }   
        };
        
        
        for (int i = 0; i<Warehouse.categories.size() ; i++) {
            JButton btn = new JButton(Warehouse.categories.get(i));
            btn.setSize(50,50);
            btn.addActionListener(e);
            catPanel.add(btn);
        }
        
        if(model.getTableModel().getRowCount()>=0){                  
            table = new JTable(model.getTableModel());
            TableColumnModel columnModel = table.getColumnModel();
            columnModel.getColumn(1).setPreferredWidth(500);
            columnModel.removeColumn(columnModel.getColumn(0));
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBounds(50,50,1000,500);
            scrollPane.getViewport().setBackground(Color.white);
            tablePanel.add(scrollPane);
            table.setAutoCreateRowSorter(true);
            table.setComponentPopupMenu(popup);

            
            searchField.getDocument().addDocumentListener(new DocumentListener (){
                final TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model.getTableModel()); 
                @Override
                public void insertUpdate(DocumentEvent e) {
                    
                    String [] split = searchField.getText().trim().split(" ");
                    table.setRowSorter(sorter);
                    List<RowFilter<Object,Object>> filters = new ArrayList<>();
                    for (String s:split) {
                        filters.add(RowFilter.regexFilter("(?i)" + s));                        
                    }
                    RowFilter<Object,Object> serviceFilter = RowFilter.andFilter(filters);
                    sorter.setRowFilter(serviceFilter);
                    
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    
                    String [] split = searchField.getText().trim().split(" ");
                    table.setRowSorter(sorter);
                    List<RowFilter<Object,Object>> filters = new ArrayList<>();
                    for (String s:split) {
                        filters.add(RowFilter.regexFilter("(?i)" + s));                        
                    }
                    RowFilter<Object,Object> serviceFilter = RowFilter.andFilter(filters);
                    sorter.setRowFilter(serviceFilter);
                    
                    //tr.setRowFilter(RowFilter.regexFilter("(?i)" + searchField.getText().trim()));
                    //table.setRowSorter(tr); 
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            
            });
            
            ActionListener removeRow = new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    int ans = JOptionPane.showConfirmDialog(null, "Would you like to remove this product?","Remove",JOptionPane.YES_NO_OPTION);
                    if(ans == JOptionPane.YES_OPTION){
                        
                        try {
                            model.removeProduct(table.getModel().getValueAt(table.getSelectedRow(), 0).toString());
                            //model.getTableModel().removeRow(table.getSelectedRow());
                            removePanel();
                            repaintPanel();
                            StorageView();
                        } catch (SQLException ex) {
                            Logger.getLogger(Warehouse.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            };
            
            MouseListener mouse = new MouseListener(){
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println(table.getModel().getValueAt(table.getSelectedRow(), 0));
                    
                    if(SwingUtilities.isRightMouseButton(e)){
                        if(!table.getSelectionModel().isSelectionEmpty()){
                            removeItem.setEnabled(true);
                        }else{
                            removeItem.setEnabled(false);
                        }
                    }
                    if (e.getClickCount() == 2) {
                        int row = table.rowAtPoint(e.getPoint());
                        int column = table.columnAtPoint(e.getPoint());
                        
                        JFrame product = new JFrame("update product");
                        try {
                            new WarehouseNewProduct(product, table.getModel().getValueAt(table.getSelectedRow(), 0).toString());
                        } catch (SQLException ex) {
                            Logger.getLogger(Warehouse.class.getName()).log(Level.SEVERE, null, ex);
                        }
                            product.addWindowListener(new WindowAdapter() {
                                @Override
                                public void windowClosing(WindowEvent e) {
                                    try {
                                        setBackBtn(model.getParentName());                                
                                        removePanel();
                                        StorageView();
                                    } catch (SQLException ex) {
                                       Logger.getLogger(Warehouse.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }

                                @Override
                                public void windowClosed(WindowEvent e) {
                                    try {
                                        setBackBtn(model.getParentName());                                
                                        removePanel();
                                        StorageView();
                                    } catch (SQLException ex) {
                                       Logger.getLogger(Warehouse.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            });
                    } 
            }

                @Override
                public void mousePressed(MouseEvent e) {
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }
                
            };
                       
            removeItem.addActionListener(removeRow);
            
            table.addMouseListener(mouse);
                       
            table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if(table.hasFocus()){                         
                        if(qty!=prewQty){
                            table.getModel().setValueAt(prewQty, selectedRow, 2);
                        }
                        selectedRow = table.getSelectedRow();
                        prewQty = Integer.parseInt(table.getValueAt(selectedRow, 1).toString());
                    }                    
                }
                
            });

            table.addKeyListener(new KeyAdapter(){
                @Override
                public void keyPressed(KeyEvent e){

                    if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                        qty = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 1).toString()) ;
                        final int row = table.getSelectedRow();
                        table.getModel().setValueAt(++qty, row, 2);
                    }
                    
                    if(e.getKeyCode() == KeyEvent.VK_LEFT){
                        qty = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 1).toString()) ;
                        final int row = table.getSelectedRow();
                        if(qty!=0){
                            table.getModel().setValueAt(--qty, row, 2);                            
                        }
                    }
                    
                    if(e.getKeyCode() == KeyEvent.VK_ENTER){
                        prewQty=qty;
                        model.updateQty(table.getModel().getValueAt(selectedRow, 0).toString(), qty);
                    }
                }
            });
                       
        }
              
        filterTable();
    }
    
    private void removePanel(){
        backPanel.removeAll();
        tablePanel.removeAll();
        catPanel.removeAll();
        newCatPanel.removeAll();
        searchPanel.removeAll();
        filterPanel.removeAll();
    }
    
    private void repaintPanel(){
        catPanel.revalidate();
        catPanel.repaint();
        
        tablePanel.revalidate();
        tablePanel.repaint();
        
        backPanel.revalidate();
        backPanel.repaint();
        
        newCatPanel.revalidate();
        newCatPanel.repaint(); 
        
        searchPanel.revalidate();
        searchPanel.repaint();
        
        filterPanel.revalidate();
        filterPanel.repaint();
    }
    
    private void filterTable(){ 
        final TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model.getTableModel());
        table.setRowSorter(sorter);
        List<RowFilter<Object,Object>> filters = new ArrayList<>();
        if(phoneBrandField.getSelectedIndex() != 0){
            filters.add(RowFilter.regexFilter("(?i)" + (phoneBrandField.getSelectedItem().toString())));            
        }

        if(phoneModelField.getSelectedIndex() > 0){
            filters.add(RowFilter.regexFilter("(?i)" + (phoneModelField.getSelectedItem().toString())));            
        }

        if(brandField.getSelectedIndex() != 0){
            filters.add(RowFilter.regexFilter("(?i)" + (brandField.getSelectedItem().toString())));            
        }      

        if(productField.getSelectedIndex() != 0){
            filters.add(RowFilter.regexFilter("(?i)" + (productField.getSelectedItem().toString())));           
        }          

        if(featureField.getSelectedIndex() != 0){
            filters.add(RowFilter.regexFilter("(?i)" + (featureField.getSelectedItem().toString())));            
        }          

        if(colorField.getSelectedIndex() != 0){
            filters.add(RowFilter.regexFilter("(?i)" + (colorField.getSelectedItem().toString())));            
        }          
        RowFilter<Object,Object> serviceFilter = RowFilter.andFilter(filters);
        sorter.setRowFilter(serviceFilter);        
    }
    
    private void storeSelection(){
        selectedPhoneBrand = phoneBrandField.getSelectedIndex();
        selectedPhoneModel = phoneModelField.getSelectedIndex();
        selectedBrand = brandField.getSelectedIndex();
        selectedProduct = productField.getSelectedIndex();
        selectedFeature = featureField.getSelectedIndex();
        selectedColor = colorField.getSelectedIndex();
    }
    
    private void setBackBtn(String backText){
         
        this.backText=backText;       
    }
    
    private String getBackBtn(){
        return "< "+this.backText;
    }
    
}

