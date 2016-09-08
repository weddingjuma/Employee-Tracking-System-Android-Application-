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
public class LocationsDAO 
{
    
    
    public String viewAllLimits(Locations loc,HttpServletRequest request,String url,String usenameDB,String passwordDB)
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
                    
                    String qr="SELECT * from CompnayLocation";
                    smnt=con.createStatement();
                    
                    System.out.println(qr);
                    rs = smnt.executeQuery(qr);
                    
                    List<Locations> li = null;
        	    li = new ArrayList<Locations>();   
                    
                    Locations mb = null;
    
                    while(rs.next())
                    {
                        mb = new Locations();
                        
                        mb.setLongitude(rs.getDouble("longitude"));
                        mb.setLatitude(rs.getDouble("latitude")); 
                        
                        li.add(mb);
		    }
         
                     smnt.close();
                     rs.close();
                     con.close();
                     request.setAttribute("locationdata", li);
                     return "SUCCESS";
                    
            }
            catch(Exception e)
            {
                    System.out.println("Error in LocationDAO :"+e.getMessage());
            }
     return "FAIL";
    }
  }