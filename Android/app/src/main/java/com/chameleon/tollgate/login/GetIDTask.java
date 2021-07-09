package com.chameleon.tollgate.login;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.chameleon.tollgate.define.LogTag;
import com.chameleon.tollgate.rest.ErrorResponse;
import com.chameleon.tollgate.rest.HttpStatus;
import com.chameleon.tollgate.rest.Response;
import com.chameleon.tollgate.rest.RestConnection;
import com.chameleon.tollgate.rest.RestResult;
import com.chameleon.tollgate.rest.define.Method;
import com.chameleon.tollgate.rest.define.Path;
import com.google.gson.Gson;

public class GetIDTask extends AsyncTask<Void, Void, String> {
    private final Context context;
    private final Handler handler;
    private final String token;
    private final long timestamp;

    public GetIDTask(Context context, Handler handler, String token, long timestamp)  {
        this.context = context;
        this.handler = handler;
        this.timestamp = timestamp;
        this.token = token;
    }

    @Override
    protected  void onPreExecute(){
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... params) {
        RestConnection rest = new RestConnection(this.context, Path.AD_ID, Method.GET, false);
        rest.putParam("token", this.token);
        rest.putParam("timestamp", this.timestamp);

        try {
            RestResult result = rest.request();
            Log.d(LogTag.REST_ACCOUNT, "Result : " + result);

            if(result.responseCode != HttpStatus.OK.value) {
                ErrorResponse err = new Gson().fromJson(result.result, ErrorResponse.class);
                Log.d(LogTag.REST_ACCOUNT, "Exception : " + err.getMessage());
                Message msg = this.handler.obtainMessage(LoginMsg.TOAST_ERROR, err.getMessage());
                this.handler.sendMessage(msg);
                return null;
            }

            Response<String> response = new Gson().fromJson(result.result, Response.class);
            if(this.timestamp != response.getTimestamp()) {
                Log.d(LogTag.REST_ACCOUNT, "Exception : Invalid response");
                Message msg = this.handler.obtainMessage(LoginMsg.TOAST_ERROR, "Invalid response.");
                this.handler.sendMessage(msg);
                return null;
            }
            return response.getResult();
        } catch (Exception ex) {
            Log.d(LogTag.REST_ACCOUNT, "Exception : " + ex.getMessage());
            return null;
        }
    }

    @Override
    protected void onProgressUpdate(Void... params){}

    @Override
    protected void onPostExecute(String result){
        super.onPostExecute(result);
    }
}
