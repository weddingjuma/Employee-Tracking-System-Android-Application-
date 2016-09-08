package sms.log;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.NetworkInfo.State;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;

public class SmsContentObserver extends ContentObserver 
{
	private Context myContext;
	private static final String TAG = "SMSTRACKER";
	private static final Uri STATUS_URI = Uri.parse("content://sms");
	TelephonyManager tManager;
	private int result;
	    
	public SmsContentObserver(Handler handler,Context context)
	{
		super(handler);
		myContext = context;
		tManager=(TelephonyManager)myContext.getSystemService(Context.TELEPHONY_SERVICE);
	}

	@Override   
	public boolean deliverSelfNotifications() 
	{
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public void onChange(boolean selfChange) 
	{
		// TODO Auto-generated method stub
		super.onChange(selfChange);
		Log.i(TAG,"ObserverActivated");
		insertTest();
		storeData();
		dispDatabase();
	}
	
	private void dispDatabase() 
	{
		my.employeetrack.app.DbHelper dh=new my.employeetrack.app.DbHelper(myContext, "MitterBitterDB" , null);
  	  	SQLiteDatabase db=dh.getWritableDatabase();
  	  	
  	  	Cursor cur=db.rawQuery("select _id from smslog",null);
  	  	
	  	  while(cur.moveToNext())
	      {
	      	for (int i = 0; i < cur.getColumnCount(); i++)
	          {
	              Log.v(TAG, cur.getColumnName(i).toString()+":"+cur.getString(i));
	          }
	      }  
      cur.close();
      db.close();
      dh.close();
     
	}

	
	private void insertTest()
	{
		my.employeetrack.app.DbHelper dh=new my.employeetrack.app.DbHelper(myContext, "MitterBitterDB" , null);
  	  	SQLiteDatabase db=dh.getWritableDatabase();
  	  	
  	  	String IMEI=tManager.getDeviceId();
      	
  	  	Uri sms = Uri.parse("content://sms");
  	  	
  	  	ContentResolver cr = myContext.getContentResolver();
  	  	String[] column = {"_id","thread_id","address","date","read","status","type","body","seen"};
  	  	
  	  	Cursor cur = cr.query(sms,column, null, null, null);
      
        while(cur.moveToNext())
        {
        	if(cur.getInt(6) == 1 && cur.getInt(6) == 2 && cur.getInt(6) == 3)
        	{
        		
		        ContentValues values=new ContentValues();
				values.put("_id", cur.getInt(0));
				values.put("thread_id",cur.getInt(1));
				values.put("imei",Long.parseLong(IMEI));
		    	values.put("address",cur.getLong(2)); 
		    	values.put("date",cur.getString(3));
		    	values.put("read",cur.getInt(4));
		    	values.put("status",cur.getInt(5));
		    	values.put("type",cur.getInt(6));
		    	values.put("body",cur.getString(7));
		    	values.put("seen",cur.getInt(8));
		    	
		    	db.insert("smslog", null, values);
        	}
        }
        cur.close();
		
	}

	private void storeData()
	{
		// TODO Auto-generated method stub
		my.employeetrack.app.DbHelper dh=new my.employeetrack.app.DbHelper(myContext, "MitterBitterDB" , null);
  	  	SQLiteDatabase db=dh.getWritableDatabase();
	  	String[] column = {"_id","thread_id","address","date","read","status","type","body","seen"};
  	  	Cursor cur=myContext.getContentResolver().query(STATUS_URI, column, null, null, null);
        
        String IMEI=tManager.getDeviceId();
        
        Log.i(TAG,"Count : "+cur.getCount());
        
        Cursor data=db.rawQuery("select * from smslog",null);
        
        if(data.getCount()==0) 
        {
      	      cur.requery();
        	  Log.i(TAG,"Count of cur : "+cur.getCount());
      		  while(cur.moveToNext())
      		  {  
      			if(cur.getInt(6) == 1 || cur.getInt(6) == 2 || cur.getInt(6) == 3)
            	{
      				 if(isInternetOn())
         			  {
         				Log.i(TAG,"Call WebServices");
         				result = my.employeetrack.app.CallServices.serverUpdateSMS(cur.getInt(0),cur.getInt(1),Long.parseLong(IMEI),cur.getString(2),cur.getString(3),cur.getInt(4),cur.getInt(5),cur.getInt(6),cur.getString(7),cur.getInt(8));

         				if(result == 1)
         				{
         			 		Log.i(TAG,"Data Inserted WebServices");
         			 	}
         				else
         				{
         					Log.i(TAG,"Connected But Data Not Inserted");
             				Log.i(TAG,"Data Stored in Local DB");
             				
             			    ContentValues values=new ContentValues();
		    				values.put("_id", cur.getInt(0));
		    				values.put("thread_id",cur.getInt(1));
		    				values.put("imei",Long.parseLong(IMEI));
		    		    	values.put("address",cur.getString(2)); 
		    		    	values.put("date",cur.getString(3));
		    		    	values.put("read",cur.getInt(4));
		    		    	values.put("status",cur.getInt(5));
		    		    	values.put("type",cur.getInt(6));
		    		    	values.put("body",cur.getString(7));
		    		    	values.put("seen",cur.getInt(8));
		    		    	values.put("updateServer",0);
	    		    	
		    		    	db.insert("smslog", null, values);
         				}
         			  }
         			  else
         			  {
		    		        ContentValues values=new ContentValues();
		    				values.put("_id", cur.getInt(0));
		    				values.put("thread_id",cur.getInt(1));
		    				values.put("imei",Long.parseLong(IMEI));
		    		    	values.put("address",cur.getString(2)); 
		    		    	values.put("date",cur.getString(3));
		    		    	values.put("read",cur.getInt(4));
		    		    	values.put("status",cur.getInt(5));
		    		    	values.put("type",cur.getInt(6));
		    		    	values.put("body",cur.getString(7));
		    		    	values.put("seen",cur.getInt(8));
		    		    	values.put("updateServer",0);
	    		    	
		    		    	db.insert("smslog", null, values);
         			  }
            	}
      		  } 
      	  cur.close();
      	  data.close();
      	  db.close();
      	  dh.close();
        }
        else if(data.getCount()>0)  
        { 
        	cur.requery();
      		Log.i(TAG,"Cur Count : "+cur.getCount());
      		int flag = 0;
      		while(cur.moveToNext())
      		{
      			data.deactivate();
      			data.requery();
      			while(data.moveToNext())
      			{
      				if(
      					cur.getInt(0) == data.getInt(0)
      					&& 	cur.getInt(1) == data.getInt(1)
      					&& 	Long.parseLong(IMEI) == data.getLong(2)
      					&& 	cur.getString(2).equalsIgnoreCase(""+data.getString(3))
      					&& 	cur.getLong(3) == data.getLong(4)
      					&& 	cur.getInt(6) == data.getInt(7)
      					&&  cur.getString(7).equals(data.getString(8))
      				) 
      				{  
      					Log.i(TAG,"Test Condition No Entry in DB");
      					flag=1;
      				}			        			
      			}
      		    if(flag == 0)
	        	{
      		    	if(cur.getInt(6) == 1 || cur.getInt(6) == 2 || cur.getInt(6) == 3)
                	{
      		    		if(isInternetOn())
  	      			  {
  	        			  	Log.i(TAG,"Call WebServices");
  	        			  	
  	        			  	int result = my.employeetrack.app.CallServices.serverUpdateSMS(cur.getInt(0),cur.getInt(1),Long.parseLong(IMEI),cur.getString(2),cur.getString(3),cur.getInt(4),cur.getInt(5),cur.getInt(6),cur.getString(7),cur.getInt(8));

  	        				if(result == 1)
  	        				{
  	        			 		Log.i(TAG,"Data Inserted WebServices");
  	        			 	}
  	        				else
  	        				{
  	        					Log.i(TAG,"Connected But Data Not Inserted");
  	            				Log.i(TAG,"Data Stored in Local DB");
  	            				ContentValues values=new ContentValues();
		        				values.put("_id", cur.getInt(0));
		        				values.put("thread_id",cur.getInt(1));
		        				values.put("imei",Long.parseLong(IMEI));
		        		    	values.put("address",cur.getString(2)); 
		        		    	values.put("date",cur.getString(3));
		        		    	values.put("read",cur.getInt(4));
		        		    	values.put("status",cur.getInt(5));
		        		    	values.put("type",cur.getInt(6));
		        		    	values.put("body",cur.getString(7));
		        		    	values.put("seen",cur.getInt(8));
		        		    	values.put("updateServer",0);
		        		    	
		        		    	db.insert("smslog", null, values);
  	            			}
  	      			  }
  	        		  else
  	        		  {
		        		        ContentValues values=new ContentValues();
		        				values.put("_id", cur.getInt(0));
		        				values.put("thread_id",cur.getInt(1));
		        				values.put("imei",Long.parseLong(IMEI));
		        		    	values.put("address",cur.getString(2)); 
		        		    	values.put("date",cur.getString(3));
		        		    	values.put("read",cur.getInt(4));
		        		    	values.put("status",cur.getInt(5));
		        		    	values.put("type",cur.getInt(6));
		        		    	values.put("body",cur.getString(7));
		        		    	values.put("seen",cur.getInt(8));
		        		    	values.put("updateServer",0);
		        		    	
		        		    	db.insert("smslog", null, values);
  	        		  }
                	}
	        	}
	        }
      	  cur.close();
      	  data.close();
      	  db.close();
      	  dh.close();
        }
	}
	/*
	private void dispAll() 
	{
		// TODO Auto-generated method stub
		// Display all Values in Column Feilds when SmS Found in Inbox
		// dispAll();
		Uri sms = Uri.parse("content://sms");
        ContentResolver cr = myContext.getContentResolver();
        //String[] column = {"_id","thread_id","address","date","read","status","type","body","seen"};
        String[] column = {"_id"};
        Cursor c = cr.query(sms,column, null, null, null);
        while(c.moveToNext())
        {
        	for (int i = 0; i < c.getColumnCount(); i++)
            {
                Log.v(TAG, c.getColumnName(i).toString()+":"+c.getString(i));
            }
        }  
        c.close();
    }
	*/
	public final boolean isInternetOn() 
	{
		
		ConnectivityManager connec =  (ConnectivityManager)myContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		// ARE WE CONNECTED TO THE NET
		if ( connec.getNetworkInfo(0).getState() == State.CONNECTED 
				||connec.getNetworkInfo(0).getState() == State.CONNECTING
				||connec.getNetworkInfo(1).getState() == State.CONNECTING  
				||connec.getNetworkInfo(1).getState() == State.CONNECTED ) 
		{
			// MESSAGE TO SCREEN FOR TESTING (IF REQ)
			//Toast.makeText(this, "connected", Toast.LENGTH_SHORT).show();
			
			return true;
			
		} else if (connec.getNetworkInfo(0).getState() == State.DISCONNECTED
					|| connec.getNetworkInfo(1).getState() == State.DISCONNECTED 
				   )
		{
			return false;
		}
		return false;
	}
	
}
