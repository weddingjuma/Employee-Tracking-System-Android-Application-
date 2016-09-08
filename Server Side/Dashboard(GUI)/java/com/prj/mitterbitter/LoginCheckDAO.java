/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prj.mitterbitter;

import java.sql.*;
import java.util.Map;
/**
 *
 * @author Ajinkya
 */
public class LoginCheckDAO
{
     
     public  String checkLogin(LoginCheck lc,Map session,String url,String usernameDB,String passwordDB)
    {
        
        String username=usernameDB;
        String password=passwordDB;
        String connectionurl = url;
	//String driverclass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String driverclass ="com.mysql.jdbc.Driver";
        ResultSet rs = null;
        
        try{
                    Class.forName(driverclass);
                    Connection con=DriverManager.getConnection(connectionurl,username,password);
                    Statement smnt=con.createStatement();
                    
                    String qr="select * from Manager_Master where Username='"+lc.username+"' And Password='"+lc.password+"'";
                    System.out.println(qr);
                //  System.out.println("Before :"+rs);
                    rs = smnt.executeQuery(qr);
                    
                    System.out.println("After :"+lc.username);
                    
                    
                    if(rs.next() && rs != null)
                    {
                       //System.out.println(rs.getString("Username"));
                        session.clear();
                        session.put("user",lc.username);
                      rs.close();
                      smnt.close();
                      con.close();
                      return "SUCCESS";
                    }
            }
            catch(Exception e)
            {
                    System.out.println("Error :"+e.getMessage());
            }
     return "FAIL";
    }

}
