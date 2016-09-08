package com.prj.mitterbitter;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ajinkya
 */
import java.sql.*;

public class ContactUsDAO 
{
    static int i=0;
       public static String addContact(ContactUs cu,String url,String usernameDB,String passwordDB)
       {
     
        String username=usernameDB;
        String password=passwordDB;
        String connectionurl = url;
	String driverclass ="com.mysql.jdbc.Driver";
	
        try{
                    Class.forName(driverclass);
                    Connection con=DriverManager.getConnection(connectionurl,username,password);
                    Statement smnt=con.createStatement();
                    String qr="insert into ContactUs values('"+cu.name+"','"+cu.email+"','"+cu.message+"')";
                    i=smnt.executeUpdate(qr);
                   
                    if(i>0)
                      return "SUCCESS";
                    
            }
            catch(Exception e)
            {
                    System.out.println("Error Cau :"+e.getMessage());
            }
     return "FAIL";
    }
}
