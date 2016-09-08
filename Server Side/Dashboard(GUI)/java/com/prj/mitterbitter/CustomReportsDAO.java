/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prj.mitterbitter;

import com.prj.mitterbitter.Pagination;
import com.prj.mitterbitter.ViewAllEmployee;
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
public class CustomReportsDAO 
{
    public String viewAllEmployee(HttpServletRequest request,String url,String usenameDB,String passwordDB,String qr,String countQuery,String table)
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
                    System.out.println(qr);
                    rs = smnt.executeQuery(qr);
                    
                    List<ViewAllEmployee> li = null;
        	    li = new ArrayList<ViewAllEmployee>();   
                    
                    ViewAllEmployee mb = null;
    
                    while(rs.next())
                    {
                        mb = new ViewAllEmployee();
                        mb.setEmployeeID(rs.getString("EmployeeID"));
                        mb.setEmployeeName(rs.getString("EmployeeName"));
                        mb.setEmployeeAdd(rs.getString("EmployeeAdd"));
                        mb.setEmployeeDOB(rs.getDate("EmployeeDOB"));
                        mb.setEmployeeDesign(rs.getString("EmployeeDesign"));
                        mb.setHandsetImei(rs.getString("HandsetImei"));
                        mb.setHandsetModel(rs.getString("HandsetModel"));
                        mb.setHandsetType(rs.getString("HandsetType"));
                        mb.setHandsetProvider(rs.getString("HandsetProvider"));
                        mb.setCarrierProvider(rs.getString("CarrierProvider"));
                       
                        li.add(mb);
		    }
         
                     smnt.close();
                     rs.close();
                     con.close();
                     
                     request.setAttribute("empdata", li);
                     return "SUCCESS";
                    
            }
            catch(Exception e)
            {
                    System.out.println("Error in SearchEmployeeDAO :"+e.getMessage());
            }
     return "FAIL";
    }
    
}
