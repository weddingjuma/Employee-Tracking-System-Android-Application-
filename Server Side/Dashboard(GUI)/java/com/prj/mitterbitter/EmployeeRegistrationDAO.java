/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prj.mitterbitter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
/**
 *
 * @author Ajinkya
    */


public class EmployeeRegistrationDAO 
{
    static int i=0;
    
    public static String addEmployee(EmployeeRegistration er,String url,String usernameDB,String passwordDB)
    {
        String username=usernameDB;
        String password=passwordDB;
        String connectionurl = url;
	String driverclass ="com.mysql.jdbc.Driver";
	
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("MM-dd-YYYY");
        String employeedob = sdf.format(er.getEmployeeDOB());
        try{
                    System.out.println("Converted : "+employeedob);
                    Class.forName(driverclass);
                    Connection con=DriverManager.getConnection(connectionurl,username,password);
                    Statement smnt=con.createStatement();
                    String qr="insert into EmployeeRegistration values('"+er.employeeID+"','"+er.employeeName+"','"+er.employeeAdd+"','"+employeedob+"','"+er.employeeDesign+"','"+er.handsetImei+"','"+er.handsetModel+"','"+er.handsetType+"','"+er.handsetProvider+"','"+er.carrierProvider+"')";
                    System.out.println(qr);
                    i=smnt.executeUpdate(qr);
                   
                    if(i>0)
                    {
                        smnt.close();
                        con.close();
                        return "SUCCESS";
                    }
            }
            catch(Exception e)
            {
                    System.out.println("Error Cau :"+e.getMessage());
            }
         return "FAIL";
    }
}
