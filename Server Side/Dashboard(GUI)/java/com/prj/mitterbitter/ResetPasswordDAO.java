/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prj.mitterbitter;

import java.sql.*;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;



/**
 *
 * @author Ajinkya
 */
public class ResetPasswordDAO 
{
        
        
    public static String resetPassword(ResetPassword rp,String url,String usernameDB,String passwordDB)
    {
        String username=usernameDB;
        String password=passwordDB;
        String connectionurl = url;
	String driverclass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        ResultSet rs = null;
         try{
                    Class.forName(driverclass);
                    Connection con=DriverManager.getConnection(connectionurl,username,password);
                    Statement smnt=con.createStatement();
                    
                    String qr="select * from Manager_Master where Username='"+rp.username+"' And MobileNo='"+rp.mobileno+"'";
                    System.out.println(qr);
                    rs = smnt.executeQuery(qr);
                    String managerPassword=null;
                    String managerEmail=null;
                    String managerName=null;
                    if(rs != null)
                    {
                       while(rs.next())
                       {
                            managerPassword = rs.getString("Password");
                            managerName = rs.getString("Name");
                            managerEmail = rs.getString("Email_ID");
                       }
                       if(managerPassword.toString()!=null && managerPassword.toString().length()>0 &&
                               managerEmail.toString()!=null && managerEmail.toString().length()>0)
                       {
                           final String user = "Your ID";
                           final String pass = "Your Password";  
                                   
                             Properties props = new Properties();
                             props.setProperty("mail.smtp.auth", "true");
                             props.setProperty("mail.smtp.starttls.enable", "true");
                             props.setProperty("mail.smtp.host", "smtp.gmail.com");
                             props.setProperty("mail.smtp.port", "587");
                             props.setProperty("mail.smtp.user", user);
                             props.setProperty("mail.smtp.password",pass);
                            
                             
                             Session mailSession;
                             mailSession = Session.getDefaultInstance(props, new javax.mail.Authenticator() 
                             {
                                 protected PasswordAuthentication getPasswordAuthentication()
                                 {
                                       return new PasswordAuthentication(user, pass);
                                 };
                             } 
                             );
                             //3) Create the email message:
                             MimeMessage msg = new MimeMessage(mailSession);
                             msg.setFrom(new InternetAddress("Gavin@gmail87.com","HCL Mitter-Bitter ADMINISTRATOR"));
                             msg.addRecipient(Message.RecipientType.TO,
                             new InternetAddress(managerEmail,managerName));
                             msg.setSubject("Mitter-Bitter Password Recovery Message");
                             msg.setText("Your Password is : "+managerPassword);
                             //4) Send the message:
                             Transport. send(msg);
                       }
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
