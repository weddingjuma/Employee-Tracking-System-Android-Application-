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
public class ChangePassword extends ActionSupport
{
    String username,password,confpassword,newpassword,submit;

    public String getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfpassword() {
        return confpassword;
    }

    public void setConfpassword(String confpassword) {
        this.confpassword = confpassword;
    }

    public String getSubmit() {
        return submit;
    }

    public void setSubmit(String submit) {
        this.submit = submit;
    }
    
    public String execute()
    {
         String url = getText("app.connectionurl");
        String usernameDB = getText("app.usernameDB");
        String passwordDB = getText("app.passwordDB");
        
        if(ChangePasswordDAO.changePassword(this,url,usernameDB,passwordDB).equals("SUCCESS"))
        {
            return "SUCCESS";
        }
        return "FAIL";
    }

    @Override
    public void validate() 
    {
        super.validate();
        
         if(getUsername() == null || getUsername().length() <= 0)
        {
            addFieldError("username", "UserName is Required");
        }
        
        if(getPassword() == null || getPassword().length() <= 0)
        {
            addFieldError("password", "Password is Required");
        }
        
        if(getNewpassword() == null || getNewpassword().length() <= 0)
        {
            addFieldError("newpassword", "New Password is Required");
        }
        
        if(getConfpassword() == null || getConfpassword().length() <= 0)
        {
            addFieldError("confpassword", "Conform Password is Required");
        }
        
        if(getConfpassword().equals(getNewpassword()))
        {
            addFieldError("confpassword", "Conform & New Password Must Match");
        }
    }
    
}
