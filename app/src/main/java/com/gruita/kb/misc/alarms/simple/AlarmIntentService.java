package com.gruita.kb.misc.alarms.simple;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

/**
 * Service that is called periodically when the alarm fires off.
 *
 *
 * @author cristian.gruita
 */
public class AlarmIntentService extends IntentService{
	
//	tag for logging
	private final static String TAG = AlarmScheduler.class.getSimpleName();
	
	/**
	 * Empty constructor needed, too, otherwise the intent service cannot be instantiated 
	 */
	public AlarmIntentService(){
		this("");
	}

	public AlarmIntentService(String name) {
		super(name);
	}

	@Override
	protected void onHandleIntent(Intent intent) {

        Log.d(TAG, "WSDecouplingSyncThread: postPendingToServer: ");

        try {
            doActualWork();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }

//
//		posting data
        Log.d(TAG, "    sending the message to MainActivity " );
        ResultReceiver receiver = intent.getParcelableExtra(Util.RECEIVERETAG);
        Bundle bundleOutgoing = new Bundle();
        bundleOutgoing.putString(Util.SERVICETAG, "Call from IntentService to MainActivity");

        receiver.send(0, bundleOutgoing);

		
	}
	
	/**
	 * posts all the pending tasks to server
	 * @throws InterruptedException 
	 */
	private void postPendingToServer(Intent intent) throws InterruptedException {


	}
	
	private void doActualWork() throws InterruptedException {
		Log.d(TAG, "PostPending, Start actual work");
        Thread.sleep(1500L);
		Log.d(TAG, "PostPending, End actual work");
	}


}
