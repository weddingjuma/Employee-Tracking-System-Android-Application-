package web.log;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.TrafficStats;
import android.net.NetworkInfo.State;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;

public class TraficWatcher 
{
	private String tag = "Traffic Service";
	private Handler mHandler = new Handler();
	private long mStartRX = 0;
	private long mStartTX = 0;
	long rxBytes,txBytes;
	TelephonyManager tManager;
	String IMEI;
	Context mContext = null;
	
	public TraficWatcher(Context context)
	{
		mContext = context;
		tManager=(TelephonyManager)mContext.getSystemService(Context.TELEPHONY_SERVICE);
	}
	
	public void startTracingTraffic() 
	{
		// TODO Auto-generated method stub
		
		mStartRX = TrafficStats.getTotalRxBytes();
        mStartTX = TrafficStats.getTotalTxBytes();

        if (mStartRX == TrafficStats.UNSUPPORTED || mStartTX == TrafficStats.UNSUPPORTED)
        {  
        	AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
        	alert.setTitle("Uh Oh!");
        	
        	alert.setMessage("Your device does not support traffic stat monitoring.");
        	
        	alert.show();

        } else
        {
        	mHandler.postDelayed(mRunnable, 10000);
        }
	}
	private final Runnable mRunnable = new Runnable()
	{
			public void run() 
			{
				rxBytes = TrafficStats.getTotalRxBytes()- mStartRX;
				Log.i(tag,"Date :"+(Date) Calendar.getInstance().getTime());
				IMEI=tManager.getDeviceId();
		        Log.i(tag, "IMEI :"+IMEI);
				Log.i(tag,"Received Bytes :"+Long.toString(rxBytes));	
				 txBytes = TrafficStats.getTotalTxBytes()- mStartTX;
				Log.i(tag,"Transmitted Bytes :"+Long.toString(txBytes));
				insertDataLocal();
				mHandler.postDelayed(mRunnable, 10000);		
			}
	};
	
	public final boolean isInternetOn() 
	{
		
		ConnectivityManager connec =  (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
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
	
	
	private void insertDataLocal() 
	{
		Log.i(tag,"Method IN");
		my.employeetrack.app.DbHelper dh=new my.employeetrack.app.DbHelper(mContext, "MitterBitterDB" , null);
		SQLiteDatabase db=dh.getWritableDatabase();
		Cursor data=db.rawQuery("select * from WebDetail",null);
		
		try
		{
				Date cDate;
				
				if(isInternetOn())
				{
					
					long serverDate = my.employeetrack.app.CallServices.getServerDate();
					cDate = new Date(serverDate);
					Log.i(tag,"Server Date :"+cDate);
				}
				else
				{	
					cDate = Calendar.getInstance().getTime();
					Log.i(tag,"Local Date :"+cDate);
				}
				
				
				
				SimpleDateFormat df = new SimpleDateFormat("dd-MMMM-yyyy");
				
				String strdate = df.format(cDate);
			
				long currentDate= Date.parse(strdate);
				  
				Log.i(tag,"Count :"+data.getCount());
				if(data.getCount()==0)
				{
					if(isInternetOn())  
        			{
        				Log.i(tag,"Call WebServices");
        				
        				int result = my.employeetrack.app.CallServices.serverUpdateWeb(currentDate,Long.parseLong(IMEI), rxBytes, txBytes);
        				
        				Log.i(tag,"value of result :"+result);
        				if(result == 1)
        				{
        			 		Log.i(tag,"Data Inserted WebServices");
        			 	}
        				else
        				{
        					Log.i(tag,"Connected But Data Not Inserted");
        					ContentValues values=new ContentValues();
        					values.put("date",currentDate);
        					values.put("imei", IMEI);
        					values.put("receive",rxBytes);
        					values.put("transfer",txBytes);
        					values.put("updateServer",0);
        					db.insert("WebDetail", null, values);
        					Log.i(tag,"Inserted data Locally");
        				}        					
        			}
					else
					{
						Log.i(tag,"Data == 0");
    					ContentValues values=new ContentValues();
    					values.put("date",currentDate);
    					values.put("imei", IMEI);
    					values.put("receive",rxBytes);
    					values.put("transfer",txBytes);
    					values.put("updateServer",0);
    					db.insert("WebDetail", null, values);
    					Log.i(tag,"Inserted data Locally");
						
					}
				} 
				else if(data.getCount()>0)  
				{
					int flag = 0;
					Log.i(tag,"Data > 0");
					
					if(isInternetOn())
        			{
        				Log.i(tag,"Call WebServices");
        				
        				int result = my.employeetrack.app.CallServices.serverUpdateWeb(currentDate,Long.parseLong(IMEI), rxBytes, txBytes);
        				Log.i(tag,"value of result :"+result);
        				if(result == 1)
        				{
        			 		Log.i(tag,"Data Inserted WebServices");
        			 	}
        				else
        				{
        					Cursor chk = db.rawQuery("select * from WebDetail where date=?", new String[]{""+currentDate});
        					Log.i(tag,"CHK Count "+chk.getCount());  
        					
        					if(chk.getCount()>0)
        					{
        						Log.i(tag,"Record Found");
        						flag = 1;
        						Log.i(tag,"Flag : "+flag);
        					}
        					chk.close();
        					while(data.moveToNext())
        					{	
        						if(currentDate == data.getLong(0)
        								&& rxBytes >= data.getLong(1) 
        								&& txBytes >= data.getLong(2)
        						   )
        						{
        							flag = 1;
        							Log.i(tag,"Same"+flag);
        							break;
        						}
        					}
        					if(flag == 1)
        					{
        						Log.i(tag,"In IF Loop Update");
        						ContentValues values=new ContentValues();
        						values.put("imei", IMEI);
        						values.put("receive", rxBytes);
        						values.put("transfer", txBytes);
        						values.put("updateServer",0);
        						db.update("WebDetail", values, "date" + "= ?",new String[]{""+currentDate});     
        						Log.i(tag,"Updated data Locally");
        					}
        					else if(flag == 0)
        					{
        						ContentValues values=new ContentValues();
        						values.put("date",currentDate);
        						values.put("imei", IMEI);
        						values.put("receive",rxBytes);
        						values.put("transfer",txBytes);
        						values.put("updateServer",0);
        						db.insert("WebDetail", null, values);
        						Log.i(tag,"Inserted data Locally");
        					}

        				}        					
        			}
					else
					{
						Cursor chk = db.rawQuery("select * from WebDetail where date=?", new String[]{""+currentDate});
						Log.i(tag,"CHK Count "+chk.getCount());  
						
						if(chk.getCount()>0)
						{
							Log.i(tag,"Record Found");
							flag = 1;
							Log.i(tag,"Flag : "+flag);
						}
						chk.close();
						while(data.moveToNext())
						{	
							if(currentDate == data.getLong(0)
									&& rxBytes >= data.getLong(1) 
									&& txBytes >= data.getLong(2)
							   )
							{
								flag = 1;
								Log.i(tag,"Same"+flag);
								break;
							}
						}
						if(flag == 1)
						{	
							ContentValues values=new ContentValues();
							values.put("imei", IMEI);
							values.put("receive", rxBytes);
							values.put("transfer", txBytes);
							values.put("updateServer",0);
							db.update("WebDetail", values, "date" + "= ?",new String[]{""+currentDate});     
							Log.i(tag,"Updated data Locally");
						}
						else if(flag == 0)
						{
							ContentValues values=new ContentValues();
							values.put("date",currentDate);
							values.put("imei", IMEI);
							values.put("receive",rxBytes);
							values.put("transfer",txBytes);
							values.put("updateServer",0);
							db.insert("WebDetail", null, values);
							Log.i(tag,"Inserted data Locally");
						}
					}
										
				}
				
			}
			catch (Exception e)
			{
				 Log.i(tag,"Error in Trafic Watcher :"+e.getMessage());
				// TODO: handle exception
			}
			finally
			{
				data.close();
				dh.close();
				db.close();
				
			}
	}
}
