package com.orderlord.caller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class CallerNumberReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        CallerNumberListener listener = new CallerNumberListener();
        tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
        tm.listen(listener, PhoneStateListener.LISTEN_NONE);
    }

    private class CallerNumberListener extends PhoneStateListener {

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);

            if (state == TelephonyManager.CALL_STATE_RINGING) {
                if (incomingNumber != null) {
                    new PostNumber().execute(incomingNumber);
                }
            }
        }

        private class PostNumber extends AsyncTask<String, Void, Integer> {

            protected Integer doInBackground(String... incomingNumbers) {

                String url = "http://requestb.in/1clcbul1";
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                List<NameValuePair> params = new ArrayList();
                params.add(new BasicNameValuePair("number", incomingNumbers[0]));

                // Url Encoding the POST parameters
                try {
                    httpPost.setEntity(new UrlEncodedFormEntity(params));
                }
                catch (UnsupportedEncodingException e) {
                    // writing error to Log
                    e.printStackTrace();
                }

                // Making HTTP Request
                try {
                    HttpResponse response = httpClient.execute(httpPost);
                    // writing response to log
                    Log.v("Orderlord response", response.toString());

                } catch (ClientProtocolException e) {
                    // writing exception to log
                    e.printStackTrace();

                } catch (IOException e) {
                    // writing exception to log
                    e.printStackTrace();
                }

                return 0;
            }

        }

    }
}
