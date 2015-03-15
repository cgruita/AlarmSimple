package com.gruita.kb.misc.alarms.simple;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
//http://stackoverflow.com/questions/4510974/using-resultreceiver-in-android
public class FirstCallResultReceiver extends ResultReceiver{

	private IReceiver mReceiver;
	
	public FirstCallResultReceiver(Handler handler) {
		super(handler);
	}
	
   
    public interface IReceiver {
        public void onReceiveResult(int resultCode, Bundle resultData);

    }

    public void setReceiver(IReceiver receiver) {
        mReceiver = receiver;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {

        if (mReceiver != null) {
            mReceiver.onReceiveResult(resultCode, resultData);
        }
    }

}
