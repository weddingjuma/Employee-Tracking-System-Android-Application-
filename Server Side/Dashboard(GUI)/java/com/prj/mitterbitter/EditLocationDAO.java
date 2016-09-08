/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prj.mitterbitter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;


/**
 *
 * @author Ajinkya
 */
public class EditLocationDAO
{
    public String updateLimit(EditLocation el,String url,String usernameDB,String passwordDB,Map application)
    {
        String username=usernameDB;
        String password=passwordDB;
        String connectionurl = url;
	String driverclass ="com.mysql.jdbc.Driver";
        ResultSet rs = null;
         try{
                    Class.forName(driverclass);
                    Connection con=DriverManager.getConnection(connectionurl,username,password);
                    Statement smnt=con.createStatement();
                    String qr="select * from CompnayLocation";
                    rs = smnt.executeQuery(qr);
                    if(rs.next())
                    {
                      application.put("longitude",rs.getDouble("longitude"));
                      application.put("latitude",rs.getDouble("latitude"));
                      
                      rs.close();
                      smnt.close();
                      con.close();
                      return "SUCCESS";
                    }
            }
            catch(Exception e)
            {
                    System.out.println("Error in EditLocationDAO :"+e.getMessage());
            }
        return "FAIL";
      }
}
