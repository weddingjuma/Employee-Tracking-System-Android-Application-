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
public class EditLimitDAO
{
    public String updateLimit(EditLimit el,String url,String usernameDB,String passwordDB,Map application)
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
                    String qr="select * from EmployeeLimits";
                    rs = smnt.executeQuery(qr);
                    if(rs.next())
                    {
                      application.put("call",rs.getInt("Calls"));
                      application.put("callThreshold",rs.getInt("CallsThresholdLimit"));
                      application.put("sms",rs.getInt("Sms"));
                      application.put("smsThreshold",rs.getInt("SmsThresholdLimit"));
                      application.put("data",rs.getFloat("Data"));
                      application.put("dataThreshold",rs.getFloat("DataThresholdLimit"));
                      
                      rs.close();
                      smnt.close();
                      con.close();
                      return "SUCCESS";
                    }
            }
            catch(Exception e)
            {
                    System.out.println("Error in EditLimitDAO :"+e.getMessage());
            }
        return "FAIL";
      }
}
