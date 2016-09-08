/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prj.mitterbitter;

import com.opensymphony.xwork2.ActionContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author Ajinkya
 */
public class LogoutAction 
{
    public String execute()throws Exception{
        
            ActionContext acon = ActionContext.getContext();
            HttpServletRequest request = (HttpServletRequest)acon.get(ServletActionContext.HTTP_REQUEST);
            HttpSession session = request.getSession();
            session.removeAttribute("user");
            return "SUCCESS";
            
    }
}
