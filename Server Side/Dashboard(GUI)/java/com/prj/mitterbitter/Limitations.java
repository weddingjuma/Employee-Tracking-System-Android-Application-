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
public class Limitations extends ActionSupport implements  ServletRequestAware
{
    HttpServletRequest request;
    
    int actualCallLimit;
    int thresholdCallLimit;
    int actualSMSLimit;
    int thresholdSMSLimit;
    float actualDataLimit;
    float thresholdDataLimit;

    public int getActualCallLimit() {
        return actualCallLimit;
    }

    public void setActualCallLimit(int actualCallLimit) {
        this.actualCallLimit = actualCallLimit;
    }

    public float getActualDataLimit() {
        return actualDataLimit;
    }

    public void setActualDataLimit(float actualDataLimit) {
        this.actualDataLimit = actualDataLimit;
    }

    public int getActualSMSLimit() {
        return actualSMSLimit;
    }

    public void setActualSMSLimit(int actualSMSLimit) {
        this.actualSMSLimit = actualSMSLimit;
    }

    public int getThresholdCallLimit() {
        return thresholdCallLimit;
    }

    public void setThresholdCallLimit(int thresholdCallLimit) {
        this.thresholdCallLimit = thresholdCallLimit;
    }

    public float getThresholdDataLimit() {
        return thresholdDataLimit;
    }

    public void setThresholdDataLimit(float thresholdDataLimit) {
        this.thresholdDataLimit = thresholdDataLimit;
    }

    public int getThresholdSMSLimit() {
        return thresholdSMSLimit;
    }

    public void setThresholdSMSLimit(int thresholdSMSLimit) {
        this.thresholdSMSLimit = thresholdSMSLimit;
    }
    
    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public void setServletRequest(HttpServletRequest hsr) 
    {
        this.request = hsr;
       // throw new UnsupportedOperationException("Not supported yet.");
    }
                
    
    public String execute()
    {   
        String url = getText("app.connectionurl");
        String usernameDB = getText("app.usernameDB");
        String passwordDB = getText("app.passwordDB");
        
        LimitationsDAO vdao = new LimitationsDAO();
        if(vdao.viewAllLimits(this,request,url,usernameDB,passwordDB).equals("SUCCESS"))
        {
            return "SUCCESS";
        }
        return "FAIL";
    }
    
   
}

