/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package catalog;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import phones.PhoneModel;


/**
 *
 * @author Gabika
 */
public class Phones {
    
    private static String username;
    private static int officeId;
    
    public Phones(JPanel f, String username, int officeId) throws SQLException {
    Phones.username = username;
    Phones.officeId = officeId;
    f.removeAll();
    f.repaint();
    phones.PhoneModel model = new PhoneModel(username,officeId);
    
    JLabel search = new JLabel("Search");
    search.setBounds(10,10,200,20);
    
    JTextField searchField = new JTextField();
    searchField.setBounds(70,10,200,20);
    
    JCheckBox solded = new JCheckBox("Show not solded items");
    solded.setBounds(300,10,200,20);
    
    JButton insert = new JButton("Insert");
    insert.setBounds(350,500,100,20);

    JTable table = new JTable(model.getTableModel());
    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setBounds(10,50,800,400);
    scrollPane.getViewport().setBackground(Color.white);
    
    
    solded.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent ae) {
                if(solded.isSelected()){
                    
                    searchField.setText("");
                    table.setModel(model.getTableModelNotSolded());
                    

                }else{
                    
                    searchField.setText("");
                    table.setModel(model.getTableModel());
                }
        }
        
    });

    searchField.addKeyListener(new KeyAdapter() {
      public void keyReleased(KeyEvent e) {
        if(solded.isSelected()){
            TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(model.getTableModelNotSolded());
            table.setRowSorter(tr);
            tr.setRowFilter(RowFilter.regexFilter(searchField.getText()));
        }else{
            TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(model.getTableModel());
            table.setRowSorter(tr);
            tr.setRowFilter(RowFilter.regexFilter(searchField.getText()));
        }  

      }
    });
    
    table.addMouseListener(new MouseAdapter() {
    public void mouseClicked(final MouseEvent e) {
        if (e.getClickCount() == 1) {
            final int row = table.getSelectedRow();
            final int column = table.getSelectedColumn();
            final String valueInCell = (String)table.getValueAt(row, column);
        }
        else if (e.getClickCount() == 2) {
        
            try {
                new PhoneDetails(table);
            } catch (SQLException ex) {
                Logger.getLogger(Phones.class.getName()).log(Level.SEVERE, null, ex);
            }

        }         
    }
    });
    
    insert.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                new PhoneInsert();
            } catch (SQLException ex) {
                Logger.getLogger(Phones.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    });
    f.getFocusTraversalKeysEnabled();
    f.add(search);
    f.add(searchField);
    f.add(solded);
    f.add(insert);
    f.add(scrollPane);

}
}
