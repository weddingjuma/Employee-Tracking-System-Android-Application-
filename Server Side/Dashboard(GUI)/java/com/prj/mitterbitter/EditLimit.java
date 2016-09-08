/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prj.mitterbitter;

import com.opensymphony.xwork2.ActionSupport;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.ServletRequestAware;

/**
 *
 * @author Ajinkya
 */
public class EditLimit extends ActionSupport implements ServletRequestAware,ApplicationAware
{
    HttpServletRequest request;
    Map application;

    @Override
    public void setServletRequest(HttpServletRequest hsr) 
    {
        this.request = hsr;
       
    }
    public HttpServletRequest getServletRequest() 
    {
        return request;
    }
    
    @Override
    public void setApplication(Map<String, Object> map) 
    {
        this.application = map;
       
    }
    
    @Override
    public String execute()
    {  
       String url = getText("app.connectionurl");
        String usernameDB = getText("app.usernameDB");
        String passwordDB = getText("app.passwordDB");
        
        EditLimitDAO edo = new EditLimitDAO();
        if(edo.updateLimit(this,url,usernameDB,passwordDB,application).equals("SUCCESS"))
        {
            return "SUCCESS";
        }
        return "FAIL";
    }
}
