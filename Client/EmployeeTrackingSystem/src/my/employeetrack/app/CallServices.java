package my.employeetrack.app;

import geo.log.MarshalDouble;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class CallServices 
{
	 static String URL = "http://192.168.0.21:8080/MBSer/RealTimeUpdater?WSDL";
	 static int i=0;
	 static String redata=null;
	 static long returnval=0L;
	public static int serverUpdateCallLogs(
										int id,
										String imei, 
										String number, 
										long date, 
										long time, 
										long duration, 
										String type)
	{
	
		final int cid=id;
		final String cimei=imei, cnumber=number, ctype=type;
		final long cdate=date, ctime=time, cduration=duration;
		new Thread(new Runnable()
		{	
		 public void run()
		 {
			
			 try    
		        {
				 
		        String methodname = "insertCalls";
		        String nameSpace = "http://services.mitterbitter.com/";
		        String soapaction = nameSpace + methodname;
		        
		        SoapObject objSoap = null;  
		        SoapSerializationEnvelope Env = null;
		        HttpTransportSE http = null;
		         
		       
		        	System.out.println("Call's Web Service");
		            objSoap = new SoapObject(nameSpace, methodname);
		            objSoap.addProperty("id", cid);
		            objSoap.addProperty("imei",cimei);
		            objSoap.addProperty("number", cnumber);
		            objSoap.addProperty("date",cdate);
		            objSoap.addProperty("time",ctime);
		            objSoap.addProperty("duration",cduration);
		            objSoap.addProperty("type", ctype);
		            
		            
		            Env = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		            Env.setOutputSoapObject(objSoap);
		              
		            http = new HttpTransportSE(URL);
		            http.call(soapaction, Env);
		            
		            SoapObject output = (SoapObject)Env.bodyIn;
		            System.out.println("Data Inserted :"+output.getProperty(0).toString());
		            if (output.getProperty(0).toString().equals("1")) 
		            {
		            	i=1;
					} 
		            else if(output.getProperty(0).toString().equals("10"))
		            {
		            	i=10;
		            }
		            else
		            {
		            	i= 0;
		            }
		            
		        } 
			 catch (Exception ex) 
		        {
		            System.out.println("Err : "+ex.getMessage());
		        }
			
		 }
		 }).start();
		 
		return i;
	 }
		
			
	
	public static int serverUpdateBrowser(long imei,long created,long date,String title,String url,int visits)
	{
	//	String URL = "http://10.39.7.154:8080/RealTimeUpdater/RealTimeUpdater?WSDL";
		final long bimei=imei, bcreated=created, bdate=date;
		final String btitle=title, burl=url;
		final int bvisits=visits;
		new Thread(new Runnable()
		{	
		 public void run()
		 {
			
				 try    
			        {
					 
	        String methodname = "insertBrowserData";  
	        String nameSpace = "http://services.mitterbitter.com/";
	        String soapaction = nameSpace + methodname;
	        SoapObject objSoap = null;
	        SoapSerializationEnvelope Env = null;
	        
	        System.out.println("WebService Called");
	        HttpTransportSE http = null;
	       
	            objSoap = new SoapObject(nameSpace, methodname);
	            
	            objSoap.addProperty("imei", bimei);
	            objSoap.addProperty("created", bcreated);
	            objSoap.addProperty("date", bdate);
	            objSoap.addProperty("title",btitle);
	            objSoap.addProperty("url", burl);
	            objSoap.addProperty("visits", bvisits);
	            
	            Env = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	            Env.setOutputSoapObject(objSoap);
	              
	            http = new HttpTransportSE(URL);
	            http.call(soapaction, Env);
	            
	            SoapObject output = (SoapObject)Env.bodyIn;
	            
	            System.out.println("Data Inserted :"+output.getProperty(0).toString());
	            
	            if (output.getProperty(0).toString().equals("1")) 
	            {
	            	i=1;
				}
	            else if(output.getProperty(0).toString().equals("10"))
	            {
	                i=10;
	            }
	            else
	            {
	            	i=0;
	            }
	            
	            
	        } catch (Exception ex) 
	        {
	            System.out.println("Err : "+ex.getMessage());
	        }
			 }
		}).start();
			return i;  
			
	}
	
	public static int serverUpdateGeoLocaion(long date,long imei,double longitude,double latitude)
	{
		//String URL = "http://10.39.7.154:8080/RealTimeUpdater/RealTimeUpdater?WSDL";
		final long gdate=date, gimei=imei;
		final double glongitude=longitude, glatitude=latitude;
		new Thread(new Runnable()
		{	
		 public void run()
		 {
			
				 try    
			        {
					 
	        String methodname = "insertGPSData";  
	        String nameSpace = "http://services.mitterbitter.com/";
	        String soapaction = nameSpace + methodname;
	        
	        SoapObject objSoap = null;
	        SoapSerializationEnvelope Env = null;
	        
	        System.out.println("WebService Called");
	        HttpTransportSE http = null;
	          objSoap = new SoapObject(nameSpace, methodname);
	            objSoap.addProperty("date", gdate);
	            objSoap.addProperty("imei", gimei);
	            objSoap.addProperty("longitude", glongitude);
	            objSoap.addProperty("latitude",glatitude);
	            	            
	            Env = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	            Env.setOutputSoapObject(objSoap);
	            MarshalDouble md = new MarshalDouble();
	            md.register(Env);
	            http = new HttpTransportSE(URL);
	            http.call(soapaction, Env);
	            
	            SoapObject output = (SoapObject)Env.bodyIn;
	            System.out.println("Data Inserted :"+output.getProperty(0).toString());
	            if (output.getProperty(0).toString().equals("1")) 
	            {
	            	i= 1;
				} 
	            else if(output.getProperty(0).toString().equals("10"))
	            {
	            	i= 10;
	            }
	            else
	            {
	            	i= 0;
	            }
	            
	            
	        } catch (Exception ex) 
	        {
	            System.out.println("Err Server Update : "+ex.getMessage());
	        }
			 }
		}).start();
			return i;  
	}
	public static String getCompnayLocation()
	{
		
		//String URL = "http://10.39.7.154:8080/RealTimeUpdater/RealTimeUpdater?WSDL";
		new Thread(new Runnable()
		{	
		 public void run()
		 {
			
				 try    
			        {
	        String methodname = "getCompnayLocation";  
	        String nameSpace = "http://services.mitterbitter.com/";
	        String soapaction = nameSpace + methodname;
	        
	        SoapObject objSoap = null;
	        SoapSerializationEnvelope Env = null;
	        
	        System.out.println("WebService Called for Compnay Location");
	        HttpTransportSE http = null;
	       
	            objSoap = new SoapObject(nameSpace, methodname);
	            objSoap.addProperty("compnayLocation", 1);
	            
	            Env = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	            Env.setOutputSoapObject(objSoap);
	              
	            http = new HttpTransportSE(URL);
	            http.call(soapaction, Env);
	            
	            SoapObject output = (SoapObject)Env.bodyIn;
	            System.out.println("Data Inserted :"+output.getProperty(0).toString());
	            				 
	            redata= output.getProperty(0).toString();
					            
	            
	        } catch (Exception ex) 
	        {
	            System.out.println("Err CompnayLocation : "+ex.getMessage());
	        }
			 }
		}).start();
			return redata;  
	}
	public static long getServerDate()
	{
	
		//String URL = "http://10.39.7.154:8080/RealTimeUpdater/RealTimeUpdater?WSDL";
		new Thread(new Runnable()
		{	
		 public void run()
		 {
			
				 try    
			        {
	        String methodname = "getCurrentDate";  
	        String nameSpace = "http://services.mitterbitter.com/";
	        String soapaction = nameSpace + methodname;
	        
	        SoapObject objSoap = null;
	        SoapSerializationEnvelope Env = null;
	        
	        System.out.println("WebService Called");
	        HttpTransportSE http = null;
	       
	            objSoap = new SoapObject(nameSpace, methodname);
	            objSoap.addProperty("date", 1);
	            
	            Env = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	            Env.setOutputSoapObject(objSoap);
	              
	            http = new HttpTransportSE(URL);
	            http.call(soapaction, Env);
	            
	            SoapObject output = (SoapObject)Env.bodyIn;
	            System.out.println("Data Inserted :"+output.getProperty(0).toString());
	            				 
	            returnval= Long.parseLong(output.getProperty(0).toString());
					            
	            
	        } catch (Exception ex) 
	        {
	           System.out.println("Err Server Date : "+ex.getMessage());
	        }
			 }
		}).start();
			return returnval;  
	}
	public static int serverUpdateSMS
	(
			int id,
			int threadid,
			long handsetimei,
			String number,
			String date,
			int read,
			int status,
			int type,
			String body,
			int seen
	)
	{
	//	String URL = "http://10.39.7.154:8080/RealTimeUpdater/RealTimeUpdater?WSDL";
		final int sid=id, sthreadid=threadid, sread=read, sstatus=status, stype=type, sseen=seen;
		final long shandsetimei=handsetimei;
		final String snumber=number, sdate=date, sbody=body;
		new Thread(new Runnable()
		{	
		 public void run()
		 {
			
				 try    
			        {
	        String methodname = "insertSMS";  
	        String nameSpace = "http://services.mitterbitter.com/";
	        String soapaction = nameSpace + methodname;
	        
	        SoapObject objSoap = null;
	        SoapSerializationEnvelope Env = null;
	        HttpTransportSE http = null;
	          
	            objSoap = new SoapObject(nameSpace, methodname);
	            objSoap.addProperty("id", sid);
	            objSoap.addProperty("thread_id", sthreadid);
	            objSoap.addProperty("imei",shandsetimei);
	            objSoap.addProperty("number", snumber);
	            objSoap.addProperty("date",sdate);
	            objSoap.addProperty("read",sread);
	            objSoap.addProperty("status",sstatus);
	            objSoap.addProperty("type", stype);
	            objSoap.addProperty("body", sbody);
	            objSoap.addProperty("seen", sseen);
	            
	            
	            
	            Env = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	            Env.setOutputSoapObject(objSoap);
	              
	            http = new HttpTransportSE(URL);
	            http.call(soapaction, Env);
	            
	            SoapObject output = (SoapObject)Env.bodyIn;
	            System.out.println("Data Inserted :"+output.getProperty(0).toString());
	            if (output.getProperty(0).toString().equals("1")) 
	            {
	            	i=1;
				} 
	            else if(output.getProperty(0).toString().equals("10"))
	            {
	            	i=10;
	            }
	            else
	            {
	            	i= 0;
	            }
	            
	            
	        } catch (Exception ex) 
	        {
	            System.out.println("Err : "+ex.getMessage());
	        }
			 }
		}).start();
			return i;  

	}
	public static int serverUpdateLocalDB(long date,long imei,long receive,long transfer)
	{		
		//String URL = "http://10.39.7.154:8080/RealTimeUpdater/RealTimeUpdater?WSDL";
		final long wdate=date, wimei=imei, wreceive=receive, wtransfer=transfer;
		new Thread(new Runnable()
		{	
		 public void run()
		 {
			
				 try    
			        {
	        String methodname = "insertWebData";  
	        String nameSpace = "http://services.mitterbitter.com/";
	        String soapaction = nameSpace + methodname;
	        SoapObject objSoap = null;
	        SoapSerializationEnvelope Env = null;
	        
	        System.out.println("WebService Called");
	        HttpTransportSE http = null;
	       objSoap = new SoapObject(nameSpace, methodname);
	            objSoap.addProperty("date", wdate);
	            objSoap.addProperty("imei", wimei);
	            objSoap.addProperty("receive",wreceive);
	            objSoap.addProperty("transfer", wtransfer);
	            
	            Env = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	            Env.setOutputSoapObject(objSoap);
	              
	            http = new HttpTransportSE(URL);
	            http.call(soapaction, Env);
	            
	            SoapObject output = (SoapObject)Env.bodyIn;
	            System.out.println("Data Inserted :"+output.getProperty(0).toString());
	            if (output.getProperty(0).toString().equals("1")) 
	            {
	            	i=1;
				}  
	            else
	            {
	            	i= 0;
	            }
	            
	            
	        } catch (Exception ex) 
	        {
	            System.out.println("Err : "+ex.getMessage());
	        }
			 }
		}).start();
			return i;  

	}
	public static int serverUpdateWeb(long date,long imei,long receive,long transfer)
	{		
		//String URL = "http://10.39.7.154:8080/RealTimeUpdater/RealTimeUpdater?WSDL";
		final long wdate=date, wimei=imei, wreceive=receive, wtransfer=transfer;
		new Thread(new Runnable()
		{	
		 public void run()
		 {
			
				  try    
			        {
	        String methodname = "insertWebData";  
	        String nameSpace = "http://services.mitterbitter.com/";
	        String soapaction = nameSpace + methodname;
	        SoapObject objSoap = null;
	        SoapSerializationEnvelope Env = null;
	        
	        System.out.println("WebService Called");
	        HttpTransportSE http = null;
	        objSoap = new SoapObject(nameSpace, methodname);
	            objSoap.addProperty("date", wdate);
	            objSoap.addProperty("imei", wimei);
	            objSoap.addProperty("receive",wreceive);
	            objSoap.addProperty("transfer", wtransfer);
	            
	            Env = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	            Env.setOutputSoapObject(objSoap);
	              
	            http = new HttpTransportSE(URL);
	            http.call(soapaction, Env);
	            
	            SoapObject output = (SoapObject)Env.bodyIn;
	            System.out.println("Data Inserted :"+output.getProperty(0).toString());
	            if (output.getProperty(0).toString().equals("1")) 
	            {
	            	i=1;
				}
	            else if(output.getProperty(0).toString().equals("10"))
	            {
	            	i=10;
	            }
	            else
	            {
	            	i=0;
	            }
	            
	            
	        } catch (Exception ex) 
	        {
	            System.out.println("Err : "+ex.getMessage());
	        }
			 }
		}).start();
			return i;  

	}
	
	
}
