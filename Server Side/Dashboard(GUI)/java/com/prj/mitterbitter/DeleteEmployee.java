/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prj.mitterbitter;

import com.opensymphony.xwork2.ActionSupport;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;

/**
 *
 * @author Ajinkya
 */
public class DeleteEmployee extends ActionSupport implements ServletRequestAware
{
    HttpServletRequest request;	    
    
    @Override
    public void setServletRequest(HttpServletRequest hsr) 
    {
        this.request = hsr;
    }
    public String execute()
    {
       String url = getText("app.connectionurl");
        String usernameDB = getText("app.usernameDB");
        String passwordDB = getText("app.passwordDB");
        
        String ID = request.getParameter("rdel");
        DeleteEmployeeDAO deldao = new DeleteEmployeeDAO();
        if(deldao.deleteEmployee(ID, url,usernameDB,passwordDB).equals("SUCCESS"))
        {
            return "SUCCESS";
        }
            
        return "FAIL";
    }

    
}
