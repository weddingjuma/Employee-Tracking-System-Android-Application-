package my.employeetrack.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BroadcastReciver extends BroadcastReceiver {

	private String tag = "MitterBitterMain";

	@Override
	public void onReceive(Context context, Intent i) 
	{
		// TODO Auto-generated method stub
		Log.i(tag  ,"Boot Completed");
		Intent intent = new Intent();
		//Activating Main Service
		Toast.makeText(context.getApplicationContext(), "MitterBitterStarted",Toast.LENGTH_LONG).show();
		intent.setAction("MitterBitterServiceStarted");
		context.startService(intent);
	}
}
   