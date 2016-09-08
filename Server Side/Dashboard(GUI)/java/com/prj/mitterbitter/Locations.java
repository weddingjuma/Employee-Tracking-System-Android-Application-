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
public class Locations extends ActionSupport implements  ServletRequestAware
{
    HttpServletRequest request;
    double longitude;
    double latitude;

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
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
        
        LocationsDAO vdao = new LocationsDAO();
        if(vdao.viewAllLimits(this,request,url,usernameDB,passwordDB).equals("SUCCESS"))
        {
            return "SUCCESS";
        }
        return "FAIL";
    }
    
   
}

