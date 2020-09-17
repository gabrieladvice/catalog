/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phones;


import java.awt.Font;
import java.awt.print.PrinterException;
import java.sql.SQLException;
import javax.swing.JTextArea;

/**
 *
 * @author Gabika
 */
public class PhonePrint {
    
    public PhonePrint() {

    }
    catalog.Model mainModel = new catalog.Model();
    PhoneModel phoneModel = new PhoneModel();
    
    public void soldContract() throws PrinterException, SQLException{
        phoneModel.getSoldPhone();
        phoneModel.getPhoneDetails(phoneModel.getPhoneName(), phoneModel.getImei());
        String bat = null;
        String chr = null;
        String box = null;
        int phoneGarantee = 0;
        if(phoneModel.getBattery().equals("1")){
            bat = "x";
        }else{
            bat = "";
        }
        if(phoneModel.getCharger().equals("1")){
            chr = "x";
        }else{
            chr = "";
        }
        if(phoneModel.getBox().equals("1")){
            box = "x";
        }else{
            box = "";
        }
        if(phoneModel.getStatus().equals("1")){
            
            phoneGarantee = 365;
        }else{
            phoneGarantee = 7;
        }

        
        JTextArea textArea = new JTextArea();
        String sold = "                                     "+mainModel.getOfficeName()+" ÜZLETHÁLÓZAT\n" +
        "\n" +
        "                                                       "+mainModel.getOfficePhone()+"\n" +
        "\n" +
        "                                               ADÁS-VÉTELI SZERZÕDÉS\n" +
        "\n" +
        "amely létrejött a "+mainModel.getCompanyName()+".("+mainModel.getOfficeTown()+", "+mainModel.getOfficeAddress()+"), mint Eladó, \n"+
        "valamint\n" +
        "\n" +
        "       Név: "+phoneModel.getCustomerName()+"\n" +
        "\n" +
        "       Szem.ig.szám: "+phoneModel.getCustomerIdCard()+"\n" +
        "\n" +
        "       Cím: "+phoneModel.getCustomerZipCode()+" "+phoneModel.getCustomerTown()+", "+phoneModel.getCustomerAddress()+"\n" +
        "\n" +
        "       Telefon: "+phoneModel.getCustomerPhone()+"\n" +
        "\n" +
        "mint Vevõ között, az alábbi rádiótelefon adás-vételérõl:\n" +
        "\n" +
        "       Típus: "+phoneModel.getPhoneName()+" \n" +
        "	\n" +
        "       IMEI szám: "+phoneModel.getImei()+"\n" +
        "\n" +
        "       Tartozékai: akkumulátor "+bat+" töltõ "+chr+" doboz "+box+"\n" +
        "\n" +
        "       Ár: "+phoneModel.getSale()+" Ft  \n" +
        "\n" +
        "Az Eladó kijelenti, hogy a fent említett mobiltelefon saját per-, teher- és igénymentes "+
        "tulajdona. A készüléket és esetleges tartozékait a Vevõ kipróbált állapotban veszi át "+
        "az Eladótól. Amennyiben a készülék nem rendelkezik érvényes gyári garanciával, úgy "+
        "az Eladó a készülékre "+phoneGarantee+" nap garanciát vállal. Az Eladó az akkumulátorra nem vállal "+
        "garanciát! Ha a készülék javíthatatlan, akkor az Eladó a készüléket egy azonos típusra, "+
        "vagy értékegyeztetéssel más típusra cseréli ki. A garancia elvész, ha a készüléken "+
        "fizikai sérülés vagy beázás történt, vagy ha a készüléket nem rendeltetésszerûen használták. "+
        "Garanciális szervizelés esetén az Eladó csak 20.000 Ft értéket meg nem haladó cserekészüléket "+
        "tud biztosítani. Vitás esetekben az Eladó csak a saját szervize véleményét veszi figyelembe. "+
        "Az Eladó más szerviz szakvéleményét és számláját nem fogadja el. Az Eladó a készülék esetleges "+
        "gyári garanciájára felelõsséget nem vállal.\n" +
        "\n" +
        "Gyõr, "+phoneModel.getSaleDate()+".\n" +
        "\n" +
        "\n" +
        "................................................................                      ................................................................\n"+
        "                           Vevõ                                                                "+mainModel.getOfficeName()+"";
        
        textArea.append(sold);
        textArea.setFont(new Font("Arial",Font.PLAIN,10));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.print();
       
    }
    
    public void boughtContract() throws PrinterException, SQLException{
        phoneModel.getBoughtPhone();
        phoneModel.getPhoneDetails(phoneModel.getPhoneName(), phoneModel.getImei());
        String bat = null;
        String chr = null;
        String box = null;
        
        if(phoneModel.getBattery().equals("1")){
            bat ="x";
        }else{
            bat = "";
        }
        if(phoneModel.getCharger().equals("1")){
            chr ="x";
        }else{
            chr = "";
        }
        if(phoneModel.getBox().equals("1")){
            box = "x";
        }else{
            box = "";
        }
        
        
        JTextArea textArea = new JTextArea();
        String sold = "                                     "+mainModel.getOfficeName()+" ÜZLETHÁLÓZAT\n" +
        "\n" +
        "                                                       "+mainModel.getOfficePhone()+"\n" +
        "\n" +
        "                                               ADÁS-VÉTELI SZERZÕDÉS\n" +
        "\n" +
        "amely létrejött a "+mainModel.getCompanyName()+".("+mainModel.getOfficeTown()+", "+mainModel.getOfficeAddress()+"), mint Vevő, \n"+
        "valamint\n" +
        "\n" +
        "       Név: "+phoneModel.getCustomerName()+"\n" +
        "\n" +
        "       Szem.ig.szám: "+phoneModel.getCustomerIdCard()+"\n" +
        "\n" +
        "       Cím: "+phoneModel.getCustomerZipCode()+" "+phoneModel.getCustomerTown()+", "+phoneModel.getCustomerAddress()+"\n" +
        "\n" +
        "       Telefon: "+phoneModel.getCustomerPhone()+"\n" +
        "\n" +
        "mint Eladó között, az alábbi rádiótelefon adás-vételérõl:\n" +
        "\n" +
        "       Típus: "+phoneModel.getPhoneName()+" \n" +
        "	\n" +
        "       IMEI szám: "+phoneModel.getImei()+"\n" +
        "\n" +
        "       Tartozékai: akkumulátor "+bat+"  töltõ "+chr+" doboz "+box+"\n" +
        "\n" +
        "       Ár: "+phoneModel.getBuy()+" Ft  \n" +
        "\n" +
        "Az Eladó kijelenti, hogy a fenti rádiótelefon a saját per-, teher- és igénymentes tulajdona. "+
        "A rádiótelefon eredetére vonatkozó felelõsség a továbbiakban is az Eladót terheli. "+
        "Az Eladó felelõsséget vállal arra, hogy a készüléket nem fogják letiltani, illetve megtéríti "+
        "a Vevõ ez ügyében keletkezett anyagi kárát. A készülék és az esetleges tartozékait a Vevõ kipróbált állapotban veszi át az Eladótól.\n"+
        "\n" +
        "Gyõr, "+phoneModel.getBuyDate()+".\n" +
        "\n" +
        "\n" +
        "................................................................                      ................................................................\n"+
        "                           Vevõ                                                                "+mainModel.getOfficeName()+"";
        
        textArea.append(sold);
        textArea.setFont(new Font("Arial",Font.PLAIN,10));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.print();
       
    }

}
