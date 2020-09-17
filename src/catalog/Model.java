/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package catalog;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import dbUtil.DbConnection;
import java.sql.SQLException;
/**
 *
 * @author Gabika
 */
public class Model {
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;
    protected static int userId;
    private static String username;
    private static String password;
    private static int employeeId;
    private static String employeeName;
    private static int officeId;
    private static String officeName;
    private static String companyName;
    private static int officeZipCode;
    private static String officeTown;
    private static String officeAddress;
    private static int companyZipCode;
    private static String companyTown;
    private static String companyAddress;
    private static String officePhone;
    private static int companyId;
    
    public Model(){
        
    }
    public Model(String username) throws SQLException{
        
        Model.username = username;
        con = DbConnection.getConnection();
        stmt = con.createStatement();
        rs =stmt.executeQuery("SELECT user_employee_id, password FROM users WHERE username='"+Model.username+"'");
        while(rs.next()){
            Model.employeeId=rs.getInt("user_employee_id");
            Model.password=rs.getString("password");
        }

        rs =stmt.executeQuery("select employee_name, office_name, office_id, company_id, office_zip_code, office_town, office_address, office_phone, company_name, company_zip_code, company_town, company_address from employees_relationships inner join employees on employees.id = employees_relationships.employee_id inner join office on employees_relationships.office_id = office.id inner join company on employees_relationships.company_id = company.id where employee_id in (select id from employees where id = "+Model.employeeId+")");
        while(rs.next()){
            Model.employeeName=rs.getString("employee_name");
            Model.officeName=rs.getString("office_name");
            Model.officeId = rs.getInt("office_id");
            Model.officeZipCode = rs.getInt("office_zip_code");
            Model.officeTown = rs.getString("office_town");
            Model.officeAddress = rs.getString("office_address");
            Model.officePhone = rs.getString("office_phone");
            Model.companyId = rs.getInt("company_id");
            Model.companyName=rs.getString("company_name");
            Model.companyZipCode = rs.getInt("company_zip_code");
            Model.companyTown = rs.getString("company_town");
            Model.companyAddress = rs.getString("company_address");

        }    
    }
    
    public int getUserId(){
        return Model.userId;
    }
    
    public int getEmployeeId(){
        return Model.employeeId;
    }
    
    public String getEmployeeName(){
        return Model.employeeName;
    }
    
    public int getOfficeId(){
        return Model.officeId;
    }
    
    public String getOfficeName(){
        return Model.officeName;
    }
    
    public int getOfficeZipCode(){
        return Model.officeZipCode;
    }
    
    public String getOfficeTown(){
        return Model.officeTown;
    }
    
    public String getOfficeAddress(){
        return Model.officeAddress;
    }
    
    public String getOfficePhone(){
        return Model.officePhone;
    }
    
    public int getComapnyId(){
        return Model.companyId;
    }
    
    public int getCompanyZipCode(){
        return Model.companyZipCode;
    }
    
    public String getCompanyTown(){
        return Model.companyTown;
    }
    
    public String getCompanyAddress(){
        return Model.companyAddress;
    }
    
    public String getCompanyName(){
        return Model.companyName;
    }
    
    public String getUsername(){
        return Model.username;
    }
    
    public String getPassword(){
        return Model.password;
    }
        
        
}
