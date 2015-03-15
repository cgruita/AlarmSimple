package com.gruita.kb.misc.alarms.simple;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.ResultReceiver;
import android.util.Log;

/**
 * Receiver, used to be activated when the time (AlarmManager) is up,
 * so the IntentService should be triggered 
 * 
 * @author cristian.gruita
 *
 */
public class AlarmReceiver extends BroadcastReceiver{
	
//	tag for logging
	private final static String TAG = AlarmScheduler.class.getSimpleName();

	@Override
	public void onReceive(Context context, Intent intentIncoming) {
//		incoming
//		TODO: Add
		Log.d(TAG, "WS Decoupling Alarm Receiver: onReceive");
		ResultReceiver receiver = intentIncoming.getParcelableExtra(Util.RECEIVERETAG);

		
//		outgoing
//		TODO: Add
		Intent intentOutgoing = new Intent(context, AlarmIntentService.class);
		intentOutgoing.putExtra(Util.RECEIVERETAG, receiver);

		
//		Log.d(TAG, "    Sending To Service the receiver and the value: " + sendToServiceString);
		context.startService(intentOutgoing);
	}

}
