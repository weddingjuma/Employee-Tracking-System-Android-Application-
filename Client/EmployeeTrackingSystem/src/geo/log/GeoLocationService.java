package geo.log;

import java.util.Calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;

public class GeoLocationService 
{
	Context myContext = null;
	private String tag = "GPS Tracking";
	private Handler mHandler = new Handler();
	GPSTracker gps;
	TelephonyManager tManager;
	
	public GeoLocationService(Context context) 
	{
		// TODO Auto-generated constructor stub
		myContext = context;
		
	}
	public void checkLocationServer() 
	{
		// TODO Auto-generated method stub
		
		my.employeetrack.app.DbHelper dh=new my.employeetrack.app.DbHelper(myContext, "MitterBitterDB" , null);
		SQLiteDatabase db=dh.getWritableDatabase();
		Cursor data=db.rawQuery("select * from GPSServer",null);
		
		if(data.getCount()>0)
		{
			if(isInternetOn())
			{
				String location = my.employeetrack.app.CallServices.getCompnayLocation();
				String[] datalocation = location.split(":",2);
				double longitude = Double.parseDouble(datalocation[0]);
				double latitude = Double.parseDouble(datalocation[1]);
				
				if(data.moveToNext())
				{
					if(data.getDouble(0) == longitude && data.getDouble(1) == latitude)
					{
						Log.i(tag, "Same in Database");
					}
					else
					{
						ContentValues values=new ContentValues();
						values.put("longitude", longitude);
						values.put("latitude", latitude);
						db.update("GPSServer", values, null,null);     
						Log.i(tag,"update data");
					}
				}
				
			}
			else
			{
				Log.i(tag,"NO INTERNET");
			}
				
		}
		else
		{
			if(isInternetOn())
			{
				String location = my.employeetrack.app.CallServices.getCompnayLocation();
				String[] datalocation = location.split(":",2);
				double longitude = Double.parseDouble(datalocation[0]);
				double latitude = Double.parseDouble(datalocation[1]);
				
				ContentValues values=new ContentValues();
				values.put("longitude", longitude);
				values.put("latitude", latitude);
				db.insert("GPSServer", null, values);     
				Log.i(tag,"New Insert Data");
			}
			else
			{
				Log.i(tag,"NO INTERNET");
			}
		}
		data.close();
		db.close();
		dh.close();
	}
	public void startLocationService() 
	{
		mHandler.postDelayed(mRunnable, 1000);
	}
	private final Runnable mRunnable = new Runnable()
	{
		
			public void run() 
			{
				Log.i(tag, "Test");
				checkLocationServer();
				checkLocation();
				mHandler.postDelayed(mRunnable, 1000);		
			}

			private void checkLocation() 
			{
				// TODO Auto-generated method stub
				// create class object
		        gps = new GPSTracker(myContext);
				
		        // check if GPS enabled		
		        if(gps.canGetLocation())
		        {
		        	double latitude = (float)gps.getLatitude();
		        	double longitude = (float)gps.getLongitude();
		        	
		        	Log.i(tag ,"MyLatitude : "+latitude);
					Log.i(tag,"MyLongitude : "+longitude);
					matchLocationServer(latitude,longitude);
		        		
		        }else
		        {
		        	// can't get location
		        	// GPS or Network is not enabled
		        	// Ask user to enable GPS/network in settings
		        	gps.showSettingsAlert();
		        }
				
				
			}

	private void matchLocationServer(double latitude, double longitude) 
	{
		
		tManager=(TelephonyManager)myContext.getSystemService(Context.TELEPHONY_SERVICE);
		my.employeetrack.app.DbHelper dh=new my.employeetrack.app.DbHelper(myContext, "MitterBitterDB" , null);
		SQLiteDatabase db=dh.getWritableDatabase();
		Cursor data=db.rawQuery("select longitude,latitude from GPSServer",null);
		String IMEI = tManager.getDeviceId();
		if(data.moveToNext())
		{
			long currentDate = getDate();
			
			//if((float)data.getDouble(0) != longitude ||(float)data.getDouble(1) != latitude)
			
			//System.out.println("Distance : "+distance(18.46463, 73.86028, 18.50049, 73.86690));
			
			if(calculateDistance(data.getDouble(1), data.getDouble(0), latitude, longitude) >= 500)
			{
				Log.i(tag,"Not Same Logitude Or Latitude");
				Log.i(tag,"Employee :"+calculateDistance(data.getDouble(1), data.getDouble(0), latitude, longitude)+" Meter Away");
				if(isInternetOn())  
    			{
    				Log.i(tag,"Call WebServices");
    				
    				int result = my.employeetrack.app.CallServices.serverUpdateGeoLocaion(currentDate,Long.parseLong(IMEI), longitude,latitude);
    				
    				Log.i(tag,"value of result :"+result);
    				
    				if(result == 1)
    				{
    			 		Log.i(tag,"Data Inserted WebServices");
    			 	}
    				else
    				{
    					String query = "select * from GPSLocal where longitude="+longitude+" AND latitude="+latitude+"";
    					Log.i(tag,query);
    					Cursor chk = db.rawQuery("select * from GPSLocal where longitude="+longitude+" AND latitude="+latitude+"",null);
    					Log.i(tag,"chk count :"+chk.getCount());
    					if(!(chk.getCount() > 0))
    					{
    						Log.i(tag,"Connected But Data Not Inserted");
    						
        					ContentValues values=new ContentValues();
        					values.put("date",currentDate);
        					values.put("imei", IMEI);
        					values.put("longitude",longitude);
        					values.put("latitude",latitude);
        					values.put("updateServer",0);
        					db.insert("GPSLocal", null, values);
        					
        					Log.i(tag,"Inserted data Locally");
    					}
    					else
    					{
    						Log.i(tag,"Data Already Present");
    					}
    					chk.close();
    					
    				}        					
    			}
				else
				{
					Cursor chk = db.rawQuery("select * from GPSLocal where longitude=? AND latitude=?", new String[]{""+longitude,""+latitude});
					if(!(chk.getCount() > 0))
					{
						Log.i(tag,"Connected But Data Not Inserted");
    					ContentValues values=new ContentValues();
    					values.put("date",currentDate);
    					values.put("imei", IMEI);
    					values.put("longitude",longitude);
    					values.put("latitude",latitude);
    					values.put("updateServer",0);
    					db.insert("GPSLocal", null, values);
    					Log.i(tag,"Inserted data Locally");
					}
					else
					{
						Log.i(tag,"Data Already Present");
					}
					chk.close();
				}
			}
			
		}
		else
		{
			checkLocationServer();
		}
		data.close();
		db.close();
		dh.close();
	}

	private long getDate()
	{
		// TODO Auto-generated method stub
		long currentDate;
		if(isInternetOn())
		{
			
			currentDate = my.employeetrack.app.CallServices.getServerDate();
		}
		else
		{	
			currentDate = Calendar.getInstance().getTimeInMillis();
		}
			
		return currentDate;
	}
			
	};
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
			System.out.println("Not Connected");
			
			return false;
		}
		
		return false;
	}
	public double calculateDistance(double CompnayLatitude,double CompnayLongitude,double EmployeeLatitude,double EmployeeLongitude) 
	 {
		//System.out.println("Distance : "+distance(18.46463, 73.86028, 18.50049, 73.86690));
		int R = 6371; // km (change this constant to get miles)
		double dLat = (EmployeeLatitude-CompnayLatitude) * Math.PI / 180;
		double dLon = (EmployeeLongitude-CompnayLongitude) * Math.PI / 180;
		double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
			Math.cos(CompnayLatitude * Math.PI / 180 ) * Math.cos(EmployeeLatitude * Math.PI / 180 ) *
			Math.sin(dLon/2) * Math.sin(dLon/2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		double d = R * c;
		
		return Math.round(d*1000);
	}
}
