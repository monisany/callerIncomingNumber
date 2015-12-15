package com.orderlord.caller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;


public class CallerNumberReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        CallerNumberListener listener = new CallerNumberListener();
        tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
//        after phone state changed receiver is unregistered, because onReceive method is done
        tm.listen(listener, PhoneStateListener.LISTEN_NONE);
    }

    private class CallerNumberListener extends PhoneStateListener {

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);

            if (state == TelephonyManager.CALL_STATE_RINGING)
                Log.v("Orderlord", "Orderlord: incomingNumber= " + incomingNumber);
        }

        private class PostNumber extends AsyncTask<String, Void, Integer> {

            protected Integer doInBackground(String... incomingNumber) {

                return 0;
            }
        }

    }
}
