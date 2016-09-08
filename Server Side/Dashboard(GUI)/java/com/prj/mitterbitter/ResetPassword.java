/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prj.mitterbitter;

import com.opensymphony.xwork2.ActionSupport;

/**
 *
 * @author Ajinkya
 */
public class ResetPassword extends ActionSupport
{
   String username,mobileno;

    public String getUsername() 
    {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }
    public String execute()
    {
        String url = getText("app.connectionurl");
        String usernameDB = getText("app.usernameDB");
        String passwordDB = getText("app.passwordDB");
        
        if(ResetPasswordDAO.resetPassword(this,url,usernameDB,passwordDB).equals("SUCCESS"))
        {
            return "SUCCESS";
        }
        return "FAIL";
    }
   
}
