package browser.log;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Handler;
import android.provider.Browser;
import android.telephony.TelephonyManager;
import android.util.Log;


public class BrowserObserver extends ContentObserver 
{
	private Context myContext;
	private TelephonyManager tManager;
	private String tag="BrowserTag";
	private int matchID;
	private int changeNo=0;
	private static final Uri STATUS_URI = Uri.parse("content://browser/bookmarks");
	public String IMEI;

	public BrowserObserver(Handler handler,Context context) 
	{
		super(handler);
		myContext = context;
		tManager=(TelephonyManager)myContext.getSystemService(Context.TELEPHONY_SERVICE);
		IMEI=tManager.getDeviceId();
	}
	
	@Override
	public void onChange(boolean selfChange)
	{
		super.onChange(selfChange);
		Log.i(tag,"Browser Observer Activated");
		changeNo++;
		
		if(changeNo==4 || changeNo==1)
		{
			dispTest();
			loadInsertData();
			//dispDataLog();
			changeNo=0;
		}
		
	}
	private void dispTest() 
	{
	        ContentResolver cr = myContext.getContentResolver();
	        Cursor c = cr.query(STATUS_URI,null, null, null, null);
	        Log.v(tag,"Toatal Count :"+c.getCount());
	        /*
	        while(c.moveToNext())
	        {
	        	for (int i = 0; i < c.getColumnCount(); i++)
	            {
	                Log.v(tag, c.getColumnName(i).toString()+":"+c.getString(i));
	            }
	        } 
	        */ 
	        c.close();
	}
	@Override
	public boolean deliverSelfNotifications() 
	{
		// TODO Auto-generated method stub
		Log.i(tag,"SelfChange");
		return true;
	}
	private void loadInsertData() 
	{
		my.employeetrack.app.DbHelper dh = new my.employeetrack.app.DbHelper(myContext, "MitterBitterDB",	null);

		SQLiteDatabase db = dh.getWritableDatabase();

		String[] requestedColumns =
		{
				Browser.BookmarkColumns._ID,
				Browser.BookmarkColumns.CREATED,
				Browser.BookmarkColumns.DATE,
				Browser.BookmarkColumns.TITLE,
				Browser.BookmarkColumns.URL,
				Browser.BookmarkColumns.VISITS,
		};
		
		Cursor cur = myContext.getContentResolver().query(Browser.BOOKMARKS_URI,requestedColumns, null, null, null);
		
		Cursor data = db.rawQuery("select * from BrowserInfo", null);

		if (data.getCount() == 0) 
		{
			Log.i(tag,"Data Count 0");
			
			if(cur!=null)cur=null;
			
			cur = myContext.getContentResolver().query(Browser.BOOKMARKS_URI,requestedColumns, null, null, null);
			
			try 
			{
				int	flag = 0;
				while (cur.moveToNext()) 
				{
					data.deactivate();
					data.requery();
					Log.i(tag,"Cur Count :"+cur.getCount());
					Log.i(tag,"Data Count :"+data.getCount());
					while (data.moveToNext())
					{
						Log.i(tag, "Data :"+data.getString(4));
						Log.i(tag, "Cur :"+cur.getString(4));
						Log.i(tag, "Match Region :"+matchRegion(cur.getString(4),data.getString(4)));
						if
						(		
								matchRegion(cur.getString(4),data.getString(4))
						)
						{ 							
							Log.i(tag,"Record Found");
							flag = 1;
							matchID = data.getInt(0);
							Log.i(tag,"Record Should Update");
							break;
						}
					}
					if (flag == 0)
					{
						Log.i(tag,"Record Not Found Inserted");
						ContentValues values = new ContentValues();
						values.put("id", cur.getInt(0));
						values.put("created", cur.getLong(1));
						values.put("date", cur.getLong(2));
						values.put("title", cur.getString(3));
						values.put("url", cur.getString(4));
						values.put("visits",cur.getInt(5));
						values.put("updateServer",0);
						
						db.insert("BrowserInfo", null, values);
					}
					else if(flag == 1)
					{
						Log.i(tag,"Record Updated");
						ContentValues values = new ContentValues();
						values.put("created", cur.getLong(1));
						values.put("date", cur.getLong(2));
						values.put("visits",cur.getInt(5));
						values.put("updateServer",0);
						db.update("BrowserInfo",values,"id" + "= ?",new String[]{""+matchID} );
					}
					flag=0;
					
				}

				
			} catch (Exception e)
			{
				Log.i(tag,"Ex : "+e.getMessage());
				
			}finally
			{
				cur.deactivate();
				data.deactivate();
				db.close();
			}
		}
		else if (data.getCount() > 0)
		{
			Log.i(tag,"Data Count > 0");
			
			if(cur!=null)cur=null;
			
			cur = myContext.getContentResolver().query(Browser.BOOKMARKS_URI,requestedColumns, null, null, null);

			int flag = 0;
			
			while (cur.moveToNext()) 
			{
				data.deactivate();
				data.requery();
				Log.i(tag,"Cur Count :"+cur.getCount());
				Log.i(tag,"Data Count :"+data.getCount());
				while (data.moveToNext())
				{
					Log.i(tag, "Data :"+data.getString(4));
					Log.i(tag, "Cur :"+cur.getString(4));
					Log.i(tag, "Match Region :"+matchRegion(cur.getString(4),data.getString(4)));
					
					if
					(		
							matchRegion(cur.getString(4),data.getString(4))
					)
					{
						flag = 1;
						matchID = data.getInt(0);
						Log.i(tag,"Record Found");
						Log.i(tag,"Record Should Update");
						break;
					}
				}
				if (flag == 0)
				{
					Log.i(tag,"Record Not Found Inserted");
					ContentValues values = new ContentValues();
					values.put("id", cur.getInt(0));
					values.put("created", cur.getLong(1));
					values.put("date", cur.getLong(2));
					values.put("title", cur.getString(3));
					values.put("url", cur.getString(4));
					values.put("visits",cur.getInt(5));
					values.put("updateServer",0);
					
					db.insert("BrowserInfo", null, values);
				}
				else if(flag == 1)
				{
					Log.i(tag,"Record Updated");
					ContentValues values = new ContentValues();
					values.put("created", cur.getLong(1));
					values.put("date", cur.getLong(2));
					values.put("visits",cur.getInt(5));
					values.put("updateServer",0);
					db.update("BrowserInfo",values,"id" + "= ?",new String[]{""+matchID} );
				}
				flag = 0;
			}

			cur.deactivate();
			data.deactivate();
			db.close();
		}
	}
	private boolean matchRegion(String search,String find) 
	{
		String searchMe = search;
    	String findMe = find;
    	int searchMeLength = searchMe.length();
    	int findMeLength = findMe.length();
    	boolean foundIt = false;
    	for (int i = 0; i <= (searchMeLength - findMeLength); i++) 
    	{
      		if (searchMe.regionMatches(i, findMe, 0, findMeLength)) 
      		{
      			foundIt = true;
      			return foundIt;
      		}
    	}
    	return foundIt;	
	}
	@SuppressWarnings("unused")
	private void dispDataLog() 
	{
		my.employeetrack.app.DbHelper dh = new my.employeetrack.app.DbHelper(myContext,"MitterBitterDB", null);
		SQLiteDatabase db = dh.getWritableDatabase();
		Cursor disp = db.rawQuery("select * from BrowserInfo", null);
		viewDatabase(disp);
		db.close();
		dh=null;
	}
	private void viewDatabase(Cursor data) 
	{
		try 
		{
			Log.i(tag,""+data.getCount());
			
		} catch (Exception e) 
		{
			Log.i(tag,""+e.getMessage());
			
		}finally
		{
			data.close();
		}
	}
		
}
