package com.gruita.kb.misc.alarms.simple;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

/**
 * Triggers the decoupling WS calls. 
 * Is a singleton, since we only need one object of this type
 * 
 * @author cristian.gruita
 *
 */
public class AlarmScheduler {

//	tag for logging
	private final static String TAG = AlarmScheduler.class.getSimpleName();
	
//	action that the intent is performing. Same string exits in the manifest, too
	private final static String START_ALARM_ACTION = "REFRESH_THIS";
	
//	how much time between cycles, in ms
	private static final int PERIOD = 1000 * 3;
	
//	the instance of the singleton
	private static AlarmScheduler INSTANCE = null;
	
//	pending intent used to start the alarm
	private PendingIntent pendingIntent = null;
	
//	alarm manager needed too 
	private AlarmManager alarmManager = null;
	
//	the context
	private Context context;
	
	
	/**
	 * Typical singleton functionality, returns the instance
	 * @param context
	 * @return
	 */
	public static AlarmScheduler getInstance(Context context){
		
		if(INSTANCE == null){
			INSTANCE = new AlarmScheduler(context);
			
		}
		
		return INSTANCE;
	}

	/**
	 * Hiding the constructor
	 * @param context
	 */
	private AlarmScheduler(Context context){
		this.context = context;
	}
	
	/**
	 * triggers the alarm manager to start
	 * (which starts the thread that calls the WSs each time)
	 */
	public void startAlarm(FirstCallResultReceiver receiver){
		Log.d(TAG, "WSDecoupling Scheduler: startAlarm");
		Intent intent = new Intent(START_ALARM_ACTION);

		intent.putExtra(Util.RECEIVERETAG, receiver);

		
		alarmManager = (AlarmManager) context.getSystemService(Activity.ALARM_SERVICE);
		
		
		pendingIntent = PendingIntent.getBroadcast( context, 0, intent, 0 );
		int type = AlarmManager.ELAPSED_REALTIME_WAKEUP;
		long triggerTime = SystemClock.elapsedRealtime();
		
		alarmManager.setInexactRepeating(type, triggerTime, PERIOD, pendingIntent );
	}
	
	/**
	 * cancels the timer, and thus the WS calls
	 */
	public void stopAlarm(){
		Log.d(TAG, "WSDecoupling Scheduler: stopAlarm");
		alarmManager.cancel(pendingIntent);
	}
}
