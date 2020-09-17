/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package catalog;

import dashboard.DashboardModel;
import java.awt.Color;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author Gabika
 */
public class Dashboard {
    
    public Dashboard(JPanel f, String username, int officeId) throws SQLException{
        
        f.removeAll();
        f.repaint();
        
        DashboardModel model = new DashboardModel(username, officeId);
        
        model.setTotalBuyPhoneDay();
        model.setTotalSalePhoneDay();
        model.setTotalProfitPhoneDay();

        JLabel totalPhoneSale = new JLabel(model.getTotalPhoneSaleDay()+" Ft");
        totalPhoneSale.setBounds(30,10,100,20);

        JLabel totalPhoneSaleLabel = new JLabel("Total Phone Sale");
        totalPhoneSaleLabel.setBounds(10,30,150,20);
        
        JLabel totalPhoneBuy = new JLabel(model.getTotalPhoneBuyDay()+" Ft");
        totalPhoneBuy.setBounds(180,10,150,20);

        JLabel totalPhoneBuyLabel = new JLabel("Total Phone Buy");
        totalPhoneBuyLabel.setBounds(160,30,150,20);
        
        JLabel totalPhoneProfit = new JLabel(model.getTotalPhoneProfitDay()+" Ft");
        totalPhoneProfit.setBounds(330,10,150,20);

        JLabel totalPhoneProfitLabel = new JLabel("Total Phone Profit");
        totalPhoneProfitLabel.setBounds(310,30,150,20);
        
        JLabel motd = new JLabel("Message of the day");
        motd.setBounds(10,80,150,20);
        
        JTextArea motdArea = new JTextArea();
        motdArea.setBorder(BorderFactory.createLineBorder(Color.black));
        motdArea.setBounds(10,110,400,200);

        
        f.add(totalPhoneSale);
        f.add(totalPhoneSaleLabel);
        f.add(totalPhoneBuy);
        f.add(totalPhoneBuyLabel);
        f.add(totalPhoneProfit);
        f.add(totalPhoneProfitLabel);
        f.add(motd);
        f.add(motdArea);
        f.setVisible(true);
        f.setLayout(null);
        f.revalidate();

    }




    
}
