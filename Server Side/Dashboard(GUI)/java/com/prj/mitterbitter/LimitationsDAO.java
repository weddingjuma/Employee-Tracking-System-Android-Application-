/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prj.mitterbitter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Ajinkya
 */
public class LimitationsDAO 
{
    
    
    public String viewAllLimits(Limitations limt,HttpServletRequest request,String url,String usenameDB,String passwordDB)
    {
        String username=usenameDB;
        String password=passwordDB;
        String connectionurl = url;
	String driverclass ="com.mysql.jdbc.Driver";
        ResultSet rs = null;
        try{
                    Class.forName(driverclass);
                    Connection con=DriverManager.getConnection(connectionurl,username,password);
                    Statement smnt = con.createStatement();
                    
                    String qr="SELECT * from EmployeeLimits";
                    smnt=con.createStatement();
                    
                    System.out.println(qr);
                    rs = smnt.executeQuery(qr);
                    
                    List<Limitations> li = null;
        	    li = new ArrayList<Limitations>();   
                    
                    Limitations mb = null;
    
                    while(rs.next())
                    {
                        mb = new Limitations();
                        
                        mb.setActualCallLimit(rs.getInt("Calls"));
                        mb.setThresholdCallLimit(rs.getInt("CallsThresholdLimit"));
                        mb.setActualSMSLimit(rs.getInt("Sms"));
                        mb.setThresholdSMSLimit(rs.getInt("SmsThresholdLimit"));
                        mb.setActualDataLimit(rs.getFloat("Data"));
                        mb.setThresholdDataLimit(rs.getFloat("DataThresholdLimit"));
                        
                        li.add(mb);
		    }
         
                     smnt.close();
                     rs.close();
                     con.close();
                     request.setAttribute("limitdata", li);
                     return "SUCCESS";
                    
            }
            catch(Exception e)
            {
                    System.out.println("Error in LIMITATIONDAO :"+e.getMessage());
            }
     return "FAIL";
    }
    
  }

