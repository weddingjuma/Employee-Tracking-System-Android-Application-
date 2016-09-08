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

public class CustomReports extends ActionSupport implements ServletRequestAware
{

    HttpServletRequest request;
    
    public HttpServletRequest getRequest() 
    {
        return request;
    }

    public void setRequest(HttpServletRequest request) 
    {
        this.request = request;
    }
    @Override
    public void setServletRequest(HttpServletRequest hsr) 
    {
       this.request = hsr;
    }
   
    public String callSearch()
    {
        String url = getText("app.connectionurl");
        String usernameDB = getText("app.usernameDB");
        String passwordDB = getText("app.passwordDB");
    
        String keyWord = request.getParameter("keyWord");
        String selectCategory = request.getParameter("selectCategory");
        String selectType = request.getParameter("selectType");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
        
        String empIdName = request.getParameter("empIdName");       
        
        System.out.println(keyWord);
        System.out.println(selectType);
        System.out.println(fromDate);
        System.out.println(toDate);
        
        if(selectCategory.equalsIgnoreCase("Calls"))
        {
            if(selectType.equalsIgnoreCase("All Call Details"))
            {
                String qr="select * from Calls_Incomming WHERE CAST(convert(int, DATEADD(MILLISECOND, Date % 1000, DATEADD(SECOND, Date / 1000, '19700101')), 105) AS datetime)>='"+fromDate+"' AND CAST(convert(int, DATEADD(MILLISECOND, Date % 1000, DATEADD(SECOND, Date / 1000, '19700101')), 105) AS datetime)<='"+toDate+"' AND Number LIKe '%"+keyWord+"%' AND (EmployeeID Like '%"+empIdName+"%' OR EmployeeName Like '%"+empIdName+"%') union select * from Calls_Outgoing WHERE CAST(convert(int, DATEADD(MILLISECOND, Date % 1000, DATEADD(SECOND, Date / 1000, '19700101')), 105) AS datetime)>='"+fromDate+"' AND CAST(convert(int, DATEADD(MILLISECOND, Date % 1000, DATEADD(SECOND, Date / 1000, '19700101')), 105) AS datetime)<='"+toDate+"' AND Number LIKe '%"+keyWord+"%' AND (EmployeeID Like '%"+empIdName+"%' OR EmployeeName Like '%"+empIdName+"%') union select * from Calls_Missedcalls WHERE CAST(convert(int, DATEADD(MILLISECOND, Date % 1000, DATEADD(SECOND, Date / 1000, '19700101')), 105) AS datetime)>= '"+fromDate+"' AND CAST(convert(int, DATEADD(MILLISECOND, Date % 1000, DATEADD(SECOND, Date / 1000, '19700101')), 105) AS datetime)<='"+toDate+"' AND Number LIKE'%"+keyWord+"%' AND (EmployeeID Like '%"+empIdName+"%' OR EmployeeName Like '%"+empIdName+"%')";
                System.out.println(qr);
                ViewAllReportDAO vdo = new ViewAllReportDAO();
                if(vdo.viewCallDetails(request,url,usernameDB,passwordDB,qr).equals("SUCCESS"))
                {
                        return "SUCCESS";
                }
            }
            else if(selectType.equalsIgnoreCase("Incomming"))
            {
                String qr="select * from Calls_Incomming WHERE CAST(convert(int, DATEADD(MILLISECOND, Date % 1000, DATEADD(SECOND, Date / 1000, '19700101')), 105) AS datetime)>='"+fromDate+"' AND CAST(convert(int, DATEADD(MILLISECOND, Date % 1000, DATEADD(SECOND, Date / 1000, '19700101')), 105) AS datetime)<='"+toDate+"' AND Number LIKe '%"+keyWord+"%' AND (EmployeeID Like '%"+empIdName+"%' OR EmployeeName Like '%"+empIdName+"%')";
                System.out.println(qr);
                ViewAllReportDAO vdo = new ViewAllReportDAO();
                if(vdo.viewCallDetails(request,url,usernameDB,passwordDB,qr).equals("SUCCESS"))
                {
                        return "SUCCESS";
                }
            }
            else if(selectType.equalsIgnoreCase("Outgoing"))
            {
                String qr="select * from Calls_Outgoing WHERE CAST(convert(int, DATEADD(MILLISECOND, Date % 1000, DATEADD(SECOND, Date / 1000, '19700101')), 105) AS datetime)>='"+fromDate+"' AND CAST(convert(int, DATEADD(MILLISECOND, Date % 1000, DATEADD(SECOND, Date / 1000, '19700101')), 105) AS datetime)<='"+toDate+"' AND Number LIKe '%"+keyWord+"%' AND (EmployeeID Like '%"+empIdName+"%' OR EmployeeName Like '%"+empIdName+"%')";
                System.out.println(qr);
                ViewAllReportDAO vdo = new ViewAllReportDAO();
                if(vdo.viewCallDetails(request,url,usernameDB,passwordDB,qr).equals("SUCCESS"))
                {
                        return "SUCCESS";
                }
            }
            else if(selectType.equalsIgnoreCase("Missed"))
            {
                String qr="select * from Calls_Missedcalls WHERE CAST(convert(int, DATEADD(MILLISECOND, Date % 1000, DATEADD(SECOND, Date / 1000, '19700101')), 105) AS datetime)>='"+fromDate+"' AND CAST(convert(int, DATEADD(MILLISECOND, Date % 1000, DATEADD(SECOND, Date / 1000, '19700101')), 105) AS datetime)<='"+toDate+"' AND Number LIKe '%"+keyWord+"%' AND (EmployeeID Like '%"+empIdName+"%' OR EmployeeName Like '%"+empIdName+"%')";
                System.out.println(qr);
                ViewAllReportDAO vdo = new ViewAllReportDAO();
                if(vdo.viewCallDetails(request,url,usernameDB,passwordDB,qr).equals("SUCCESS"))
                {
                        return "SUCCESS";
                }
            }
            
        }
        return "FAIL";
    }
    
    public String smsSearch()
    {
        String url = getText("app.connectionurl");
        String usernameDB = getText("app.usernameDB");
        String passwordDB = getText("app.passwordDB");
    
        String keyWord = request.getParameter("keyWord");
        String selectCategory = request.getParameter("selectCategory");
        String selectType = request.getParameter("selectType");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
        String empIdName = request.getParameter("empIdName");       
               
        System.out.println(keyWord);
        System.out.println(selectType);
        System.out.println(fromDate);
        System.out.println(toDate);
        
        if(selectCategory.equalsIgnoreCase("Sms"))
        {
            if(selectType.equalsIgnoreCase("All Content"))
            {
                String  qr="select * from Sms_Inbox WHERE Date >= '"+fromDate+"' AND Date <= '"+toDate+"' AND Number LIKE '%"+keyWord+"%' AND (EmployeeID Like '%"+empIdName+"%' OR EmployeeName Like '%"+empIdName+"%') union select * from Sms_Draft WHERE Date >= '"+fromDate+"' AND Date <= '"+toDate+"' AND Number LIKE '%"+keyWord+"%' AND (EmployeeID Like '%"+empIdName+"%' OR EmployeeName Like '%"+empIdName+"%') union select * from Sms_Sent WHERE Date >= '"+fromDate+"' AND Date <= '"+toDate+"' AND Number LIKE '%"+keyWord+"%' AND (EmployeeID Like '%"+empIdName+"%' OR EmployeeName Like '%"+empIdName+"%')";
                System.out.println(qr);
                ViewAllReportDAO vdo = new ViewAllReportDAO();
                if(vdo.viewSmsDetails(request,url,usernameDB,passwordDB,qr).equals("SUCCESS"))
                {
                        return "SUCCESS";
                }
            }
            else if(selectType.equalsIgnoreCase("Inbox"))
            {
                String qr="select * from Sms_Inbox WHERE Date >= '"+fromDate+"' AND Date <= '"+toDate+"' AND Number LIKE '%"+keyWord+"%' AND (EmployeeID Like '%"+empIdName+"%' OR EmployeeName Like '%"+empIdName+"%')";
                System.out.println(qr);
                ViewAllReportDAO vdo = new ViewAllReportDAO();
                if(vdo.viewSmsDetails(request,url,usernameDB,passwordDB,qr).equals("SUCCESS"))
                {
                        return "SUCCESS";
                }
            }
            else if(selectType.equalsIgnoreCase("Draft"))
            {
                String qr="select * from Sms_Draft WHERE Date >= '"+fromDate+"' AND Date <= '"+toDate+"' AND Number LIKE '%"+keyWord+"%' AND (EmployeeID Like '%"+empIdName+"%' OR EmployeeName Like '%"+empIdName+"%')";
                System.out.println(qr);
                ViewAllReportDAO vdo = new ViewAllReportDAO();
                if(vdo.viewSmsDetails(request,url,usernameDB,passwordDB,qr).equals("SUCCESS"))
                {
                        return "SUCCESS";
                }
            }
            else if(selectType.equalsIgnoreCase("Sent"))
            {
                String qr="select * from Sms_Sent WHERE Date >= '"+fromDate+"' AND Date <= '"+toDate+"' AND Number LIKE '%"+keyWord+"%' AND (EmployeeID Like '%"+empIdName+"%' OR EmployeeName Like '%"+empIdName+"%')";
                System.out.println(qr);
                ViewAllReportDAO vdo = new ViewAllReportDAO();
                if(vdo.viewSmsDetails(request,url,usernameDB,passwordDB,qr).equals("SUCCESS"))
                {
                        return "SUCCESS";
                }
            }
            
        }
        return "FAIL";
    }
    
    public String webSearch()
    {
        String url = getText("app.connectionurl");
        String usernameDB = getText("app.usernameDB");
        String passwordDB = getText("app.passwordDB");
    
        String keyWord = request.getParameter("keyWord");
        String selectCategory = request.getParameter("selectCategory");
        String selectType = request.getParameter("selectType");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
               
        System.out.println(keyWord);
        System.out.println(selectType);
        System.out.println(fromDate);
        System.out.println(toDate);
        
        if(selectCategory.equalsIgnoreCase("Web"))
        {
            if(selectType.equalsIgnoreCase("EmployeeID"))
            {
                String qr="select * from WebData WHERE CAST(convert(int, DATEADD(MILLISECOND, Date % 1000, DATEADD(SECOND, Date / 1000, '19700101')), 105) AS datetime)>='"+fromDate+"' AND CAST(convert(int, DATEADD(MILLISECOND, Date % 1000, DATEADD(SECOND, Date / 1000, '19700101')), 105) AS datetime)<='"+toDate+"' AND EmployeeID Like '%"+keyWord+"%'";
                String total= "select (sum(Received) + sum(Transmited))/1073741824 as totalDataUsage from WebData a where CAST(convert(int, DATEADD(MILLISECOND, Date % 1000, DATEADD(SECOND, Date / 1000, '19700101')), 105) AS datetime)>='"+fromDate+"' AND CAST(convert(int, DATEADD(MILLISECOND, Date % 1000, DATEADD(SECOND, Date / 1000, '19700101')), 105) AS datetime)<='"+toDate+"' AND EmployeeID Like '%"+keyWord+"%'";
                System.out.println(qr);
                System.out.println(total);
                ViewAllReportDAO vdo = new ViewAllReportDAO();
                if(vdo.viewWebDetails(request,url,usernameDB,passwordDB,qr,total).equals("SUCCESS"))
                {
                        return "SUCCESS";
                }
            }
            else if(selectType.equalsIgnoreCase("EmployeeName"))
            {
                String qr="select * from WebData WHERE CAST(convert(int, DATEADD(MILLISECOND, Date % 1000, DATEADD(SECOND, Date / 1000, '19700101')), 105) AS datetime)>='"+fromDate+"' AND CAST(convert(int, DATEADD(MILLISECOND, Date % 1000, DATEADD(SECOND, Date / 1000, '19700101')), 105) AS datetime)<='"+toDate+"' AND EmployeeName Like '%"+keyWord+"%'";
                String total= "select (sum(Received) + sum(Transmited))/1073741824 as totalDataUsage from WebData a where CAST(convert(int, DATEADD(MILLISECOND, Date % 1000, DATEADD(SECOND, Date / 1000, '19700101')), 105) AS datetime)>='"+fromDate+"' AND CAST(convert(int, DATEADD(MILLISECOND, Date % 1000, DATEADD(SECOND, Date / 1000, '19700101')), 105) AS datetime)<='"+toDate+"' AND EmployeeName Like '%"+keyWord+"%'";
                System.out.println(qr);
                System.out.println(total);
                ViewAllReportDAO vdo = new ViewAllReportDAO();
                if(vdo.viewWebDetails(request,url,usernameDB,passwordDB,qr,total).equals("SUCCESS"))
                {
                        return "SUCCESS";
                }
            }
         }
        return "FAIL";
    }
    
    public String browserSearch()
    {
        
        String url = getText("app.connectionurl");
        String usernameDB = getText("app.usernameDB");
        String passwordDB = getText("app.passwordDB");
    
        String keyWord = request.getParameter("keyWord");
        String selectCategory = request.getParameter("selectCategory");
        String selectType = request.getParameter("selectType");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
               
        System.out.println(keyWord);
        System.out.println(selectType);
        System.out.println(fromDate);
        System.out.println(toDate);
        
        ViewAllReportDAO vdo = new ViewAllReportDAO();
        
        if(selectCategory.equalsIgnoreCase("Browser"))
        {
            if(selectType.equalsIgnoreCase("EmployeeID"))
            {
                String qr="select * from BrowserData where EmployeeID='"+keyWord+"'";
                
                if(vdo.viewBrowserDetails(request,url,usernameDB,passwordDB,qr).equals("SUCCESS"))
                {
                    return "SUCCESS";
                }
                
            }
            else if(selectType.equalsIgnoreCase("EmployeeName"))
            {
                String qr="select * from BrowserData where EmployeeName LIKE '%"+keyWord+"%'";
                
                if(vdo.viewBrowserDetails(request,url,usernameDB,passwordDB,qr).equals("SUCCESS"))
                {
                    return "SUCCESS";
                }
        
            }
            else if(selectType.equalsIgnoreCase("URL"))
            {
                String qr="select * from BrowserData where URL LIKE '%"+keyWord+"%'";
                
                if(vdo.viewBrowserDetails(request,url,usernameDB,passwordDB,qr).equals("SUCCESS"))
                {
                    return "SUCCESS";
                }
            }
         }
        return "FAIL";
    }
    
    public String locationSearch()
    {
        
        String url = getText("app.connectionurl");
        String usernameDB = getText("app.usernameDB");
        String passwordDB = getText("app.passwordDB");
    
        String keyWord = request.getParameter("keyWord");
        String selectCategory = request.getParameter("selectCategory");
        String selectType = request.getParameter("selectType");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
               
        System.out.println(keyWord);
        System.out.println(selectType);
        System.out.println(fromDate);
        System.out.println(toDate);
        
        ViewAllReportDAO vdo = new ViewAllReportDAO();
        
        if(selectCategory.equalsIgnoreCase("Geo-Location"))
        {
            if(selectType.equalsIgnoreCase("ALL"))
            {
                String qr="select * from EmployeeLocations WHERE CAST(convert(int, DATEADD(MILLISECOND, Date % 1000, DATEADD(SECOND, Date / 1000, '19700101')), 105) AS datetime) >= '"+fromDate+"' AND CAST(convert(int, DATEADD(MILLISECOND, Date % 1000, DATEADD(SECOND, Date / 1000, '19700101')), 105) AS datetime)<='"+toDate+"' AND EmployeeID Like '%"+keyWord+"%' OR EmployeeName Like '%"+keyWord+"%'";
                System.out.println(qr);
                if(vdo.viewLocationDetails(request,url,usernameDB,passwordDB,qr).equals("SUCCESS"))
                {
                    return "SUCCESS";
                }

            }
            else if(selectType.equalsIgnoreCase("EmployeeID"))
            {
                String qr="select * from EmployeeLocations WHERE CAST(convert(int, DATEADD(MILLISECOND, Date % 1000, DATEADD(SECOND, Date / 1000, '19700101')), 105) AS datetime) >= '"+fromDate+"' AND CAST(convert(int, DATEADD(MILLISECOND, Date % 1000, DATEADD(SECOND, Date / 1000, '19700101')), 105) AS datetime)<='"+toDate+"' AND EmployeeID Like '%"+keyWord+"%'";
                System.out.println(qr);
                if(vdo.viewLocationDetails(request,url,usernameDB,passwordDB,qr).equals("SUCCESS"))
                {
                    return "SUCCESS";
                }

            }
            else if(selectType.equalsIgnoreCase("EmployeeName"))
            {
                String qr="select * from EmployeeLocations WHERE CAST(convert(int, DATEADD(MILLISECOND, Date % 1000, DATEADD(SECOND, Date / 1000, '19700101')), 105) AS datetime) >= '"+fromDate+"' AND CAST(convert(int, DATEADD(MILLISECOND, Date % 1000, DATEADD(SECOND, Date / 1000, '19700101')), 105) AS datetime)<='"+toDate+"' AND EmployeeName Like '%"+keyWord+"%'";
                System.out.println(qr);
                if(vdo.viewLocationDetails(request,url,usernameDB,passwordDB,qr).equals("SUCCESS"))
                {
                    return "SUCCESS";
                }

            }
         }
        return "FAIL";
    }
}
