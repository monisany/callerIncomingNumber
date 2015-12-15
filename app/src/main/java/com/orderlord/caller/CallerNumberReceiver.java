package com.orderlord.caller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class CallerNumberReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "niekto vola", Toast.LENGTH_LONG).show();
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        CallerNumberListener listener = new CallerNumberListener();
        tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    private class CallerNumberListener extends PhoneStateListener {

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);

            if (state == TelephonyManager.CALL_STATE_RINGING)
                Log.v("Orderlord", "Orderlord: incomingNumber= " + incomingNumber);
        }
    }
}
