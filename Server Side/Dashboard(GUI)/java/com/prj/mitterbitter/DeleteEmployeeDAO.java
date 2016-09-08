/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prj.mitterbitter;

import com.opensymphony.xwork2.ActionSupport;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

/**
 *
 * @author Ajinkya
 */

public class DeleteEmployeeDAO
{
    public String deleteEmployee(String ID,String url,String usernameDB,String passwordDB)
    {   
        String username=usernameDB;
        String password=passwordDB;
        String connectionurl = url;
	String driverclass ="com.mysql.jdbc.Driver";
        ResultSet rs = null;
         try{
                    
                    Class.forName(driverclass);
                    Connection con=DriverManager.getConnection(connectionurl,username,password);
                    String qr="delete from EmployeeRegistration where EmployeeID=?";
                    PreparedStatement smnt=con.prepareStatement(qr);
                    smnt.setString(1,ID);

                    smnt.executeUpdate();
                    
                    smnt.close();
                    con.close();
                    return "SUCCESS";
            }
            catch(Exception e)
            {
                    System.out.println("Error :"+e.getMessage());
            }
        return "FAIL";
    }    
    
}
