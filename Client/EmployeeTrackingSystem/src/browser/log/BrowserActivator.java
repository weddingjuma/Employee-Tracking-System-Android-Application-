package browser.log;


import android.content.Context;
import android.os.Handler;
import android.provider.Browser;
import android.util.Log;



public class BrowserActivator 
{
	private BrowserObserver observer = null;
	private String tag="BrowserTag";
	private Context mContext;

	public BrowserActivator(Context mContext)
	{
		this.mContext = mContext;
	}
	
	public void activateBrowserObserver() 
	{
			try    
			{
				if(observer  == null)
				{
					observer = new BrowserObserver(new Handler(),mContext);
					mContext.getContentResolver().registerContentObserver(Browser.BOOKMARKS_URI,true,observer);
				}
				
			} catch (Exception e) 
			{
				Log.i(tag,"Ex : "+e.getMessage());
			}
	}
}
