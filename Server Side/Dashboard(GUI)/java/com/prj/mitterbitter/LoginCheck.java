package com.prj.mitterbitter;

import com.opensymphony.xwork2.ActionSupport;
import java.util.Map;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.SessionAware;

/**
 *
 * @author Ajinkya
 */
public class LoginCheck extends ActionSupport implements SessionAware
{
    String username,password,login;
    Map session;
   
    /**
     *
     * @param map
     */
    
    @Override
    public void setSession(Map session)
    {
        this.session = session;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    @Override
    public String execute()
    {
        String url = getText("app.connectionurl");
         String usernameDB = getText("app.usernameDB");
        String passwordDB = getText("app.passwordDB");
        
        LoginCheckDAO lcd = new LoginCheckDAO();
        if(lcd.checkLogin(this,session,url,usernameDB,passwordDB).equals("SUCCESS")) 
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
    }
}
