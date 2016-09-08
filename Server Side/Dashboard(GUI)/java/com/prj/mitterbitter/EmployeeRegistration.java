/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prj.mitterbitter;

import com.opensymphony.xwork2.ActionSupport;
import java.text.ParseException;
import java.util.Date;

/**
 *
 * @author Ajinkya
 */
public class EmployeeRegistration extends ActionSupport
{
    String  employeeID,
            employeeName,
            employeeAdd,
            employeeDesign,
            handsetImei,
            handsetModel,
            handsetType,
            handsetProvider,
            carrierProvider,
            submit,
            reset;
    
    Date employeeDOB;

    public Date getEmployeeDOB() 
    {
        System.out.println("Get : "+employeeDOB);
        return employeeDOB;
    }
    public void setEmployeeDOB(Date employeeDOB) throws ParseException
    {
        System.out.println("Set : "+employeeDOB);
        this.employeeDOB = employeeDOB;
    }
    public String getSubmit() {
        return submit;
    }
     public void setSubmit(String submit) {
        this.submit = submit;
    }
    public String getReset() {
        return reset;
    }
    public void setReset(String reset) {
        this.reset = reset;
    }
    public String getEmployeeID() {
        return employeeID;
    }
    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }
    public String getEmployeeName() {
        return employeeName;
    }
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
    public String getEmployeeAdd() {
        return employeeAdd;
    }
    public void setEmployeeAdd(String employeeAdd) {
        this.employeeAdd = employeeAdd;
    }
    public String getEmployeeDesign() {
        return employeeDesign;
    }
    public void setEmployeeDesign(String employeeDesign) {
        this.employeeDesign = employeeDesign;
    }
    public String getHandsetImei() {
        return handsetImei;
    }
    public void setHandsetImei(String handsetImei) {
        this.handsetImei = handsetImei;
    }
    public String getHandsetModel() {
        return handsetModel;
    }
    public void setHandsetModel(String handsetModel) {
        this.handsetModel = handsetModel;
    }

    public String getHandsetType() {
        return handsetType;
    }

    public void setHandsetType(String handsetType) {
        this.handsetType = handsetType;
    }
    public String getHandsetProvider() {
        return handsetProvider;
    }
    public void setHandsetProvider(String handsetProvider) {
        this.handsetProvider = handsetProvider;
    }
    public String getCarrierProvider() {
        return carrierProvider;
    }
    public void setCarrierProvider(String carrierProvider) {
        this.carrierProvider = carrierProvider;
    }
    @Override
    public String execute()
    {     
        String url = getText("app.connectionurl");
        String usernameDB = getText("app.usernameDB");
        String passwordDB = getText("app.passwordDB");
        if(EmployeeRegistrationDAO.addEmployee(this,url,usernameDB,passwordDB).equals("SUCCESS"))
        {
            return "SUCCESS";
        }
        return "FAIL";
    }

    @Override
    public void validate() 
    {
        super.validate();
        
        
        if(getHandsetImei().length() < 15)
        {
            addFieldError("handsetImei", "IMEI is required");
        }
        /*
        if(getEmployeeID().length() <=0 && getEmployeeID().equalsIgnoreCase("null"))
        {
            addFieldError("employeeID", "EmployeeID is required");
        }
        
        if(getEmployeeName().length() <=0 && getEmployeeName().equalsIgnoreCase("null"))
        {
            addFieldError("employeeName", "EmployeeName is required");
        }
        
        if(getEmployeeAdd().length() <=0 && getEmployeeAdd().equalsIgnoreCase("null"))
        {
            addFieldError("employeeAdd", "EmployeeAdd is required");
        }
        
        if(getEmployeeDOB().toString().length() <=0 && getEmployeeDOB() == null)
        {
            addFieldError("employeeDOB", "EmployeeDOB is required");
        }
        
        if(getHandsetModel().length() <=0 && getHandsetModel().equalsIgnoreCase("null"))
        {
            addFieldError("handsetModel", "HandsetModel is required");
        }
         
         */
    }

}
