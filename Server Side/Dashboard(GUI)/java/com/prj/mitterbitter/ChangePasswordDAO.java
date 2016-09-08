/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prj.mitterbitter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Ajinkya
 */
public class ChangePasswordDAO 
{
    
    public static String changePassword(ChangePassword cp,String url,String usernameDB,String passwordDB)
    {
        
        String username=usernameDB;
        String password=passwordDB;
        String connectionurl = url;
        String driverclass ="com.mysql.jdbc.Driver";
        ResultSet rs = null;
        String updateID = null;
                try{
                    Class.forName(driverclass);
                    Connection con=DriverManager.getConnection(connectionurl,username,password);
                    Statement smnt=con.createStatement();
                    String qr="select * from Manager_Master where Username='"+cp.username+"' And Password='"+cp.password+"'";
                    //System.out.println(qr);

                    rs = smnt.executeQuery(qr);
                    
                    if(rs!=null)
                    {
                        while(rs.next())
                        {
                            updateID = rs.getString("ID");
                        }
                        System.out.println("ID: "+updateID);
                        
                        if(updateID!=null)
                        {
                            
                            qr="update Manager_Master SET Password='"+cp.newpassword+"' where ID='"+updateID+"'";
                            //System.out.println(qr);
                            int i = smnt.executeUpdate(qr);
                            //update Manager_Master SET Password='xyz' where ID='1';
                            if(i>0)
                            {
                                return "SUCCESS";
                            }
                        }
                        
                        return "FAIL";
                    }
            }
            catch(Exception e)
            {
                    System.out.println("Error Cau :"+e.getMessage());
            }
       return "FAIL";
    }
}
