package com.prj.mitterbitter;

import com.opensymphony.xwork2.ActionSupport;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ajinkya
 */
public class ContactUs extends ActionSupport
{
    String name,email,message,submit;
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubmit() {
        return submit;
    }

    public void setSubmit(String submit) {
        this.submit = submit;
    }
    @Override
    public String execute()
    {
         String url = getText("app.connectionurl");
        String usernameDB = getText("app.usernameDB");
        String passwordDB = getText("app.passwordDB");
        
        if(ContactUsDAO.addContact(this,url,usernameDB,passwordDB).equals("SUCCESS")) {
            return "SUCCESS";
        }
        
        return "FAIL";
    }

    @Override
    public void validate() 
    {
        super.validate();
        if(name.equals("") || name==null) {
            addFieldError("name", "Please Fill the name");
        }
    }
    
}
