/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prj.mitterbitter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Map;


/**
 *
 * @author Ajinkya
 */
public class EditEmployeeDAO
{
    public String updateEmployee(EditEmployee ee,String url,String usernameDB,String passwordDB,String id,Map application)
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
                    String qr="select * from EmployeeRegistration where EmployeeID='"+id+"'";
                    rs = smnt.executeQuery(qr);
                    if(rs.next())
                    {
                      application.put("EmployeeID",rs.getString("EmployeeID"));
                      application.put("EmployeeName",rs.getString("EmployeeName"));
                      application.put("EmployeeAdd",rs.getString("EmployeeAdd"));
                      application.put("EmployeeDOB",rs.getDate("EmployeeDOB"));
                      application.put("EmployeeDesign",rs.getString("EmployeeDesign"));
                      application.put("HandsetImei",rs.getString("HandsetImei"));
                      application.put("HandsetModel",rs.getString("HandsetModel"));
                      application.put("HandsetType",rs.getString("HandsetType"));
                      application.put("HandsetProvider",rs.getString("HandsetProvider"));
                      application.put("CarrierProvider",rs.getString("CarrierProvider")); 
                      
                      rs.close();
                      smnt.close();
                      con.close();
                      return "SUCCESS";
                    }
            }
            catch(Exception e)
            {
                    System.out.println("Error in EditEmployeeDAO :"+e.getMessage());
            }
        return "FAIL";
      }
}
