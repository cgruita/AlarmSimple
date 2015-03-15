package com.gruita.kb.misc.alarms.simple;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.gruita.kb.misc.alarms.simple.FirstCallResultReceiver.IReceiver;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * If we want to perform a certain action (or different actions) at regular intervals,
 * it's better to use an Alarm (exact or inexact) than a service.
 * We're gonna use an inexact alarm and a receiver.
 * Another assumption is that we'll do some networking work, so we'll use an intent service
 * every time the alarm will fire off.
 */
public class MainActivity extends Activity implements CompoundButton.OnCheckedChangeListener, IReceiver{
	
	private final static String TAG = AlarmScheduler.class.getSimpleName();

	public FirstCallResultReceiver mReceiver;
    private TextView tvMessage = null;
    private ToggleButton toggleButton = null;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		

		mReceiver = new FirstCallResultReceiver(new Handler());
		mReceiver.setReceiver(this);

        tvMessage = (TextView) findViewById(R.id.textView1);

        ToggleButton toggleButton= (ToggleButton) findViewById(R.id.toggleButton);
		toggleButton.setOnCheckedChangeListener(this);
	}

	@Override
	public void onDestroy() {
		 AlarmScheduler.getInstance(this).stopAlarm();
		super.onDestroy();
	}

	
	


	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        String dateFormatted = df.format(new Date());
		Log.d(TAG,"################## MainActivity:onReceiveResult"+resultData.getString(Util.SERVICETAG));
        Log.d(TAG, "time: " + dateFormatted);
        tvMessage.setText("Main Activity got message: \n" + dateFormatted);
		

		
		
	}


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

        AlarmScheduler scheduler = AlarmScheduler.getInstance(this);
        if(isChecked){
            scheduler.startAlarm(mReceiver);
        } else {
            scheduler.stopAlarm();
        }

    }
}
