package my.employeetrack.app;

import my.employeetrack.app.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MyApp extends Activity {
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);
}
public void CallMe(View c) {
	Intent intent = new Intent();
	//Activating Main Service
	Toast.makeText(getApplicationContext(), "MitterBitterStarted",Toast.LENGTH_LONG).show();
	intent.setAction("MitterBitterServiceStarted");
	startService(intent);
	Toast.makeText(getApplicationContext(), "service called",10).show();
}
}
