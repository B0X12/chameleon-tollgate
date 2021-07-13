package com.chameleon.tollgate.login;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.chameleon.tollgate.Util;
import com.chameleon.tollgate.define.LogTag;
import com.chameleon.tollgate.rest.ErrorResponse;
import com.chameleon.tollgate.rest.HttpStatus;
import com.chameleon.tollgate.rest.Response;
import com.chameleon.tollgate.rest.RestConnection;
import com.chameleon.tollgate.rest.RestResult;
import com.chameleon.tollgate.rest.define.Method;
import com.chameleon.tollgate.rest.define.Path;
import com.google.gson.Gson;

public class CheckServerTask extends AsyncTask<Void, Void, Boolean> {
    private final Context context;
    private final Handler handler;

    public CheckServerTask(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

    @Override
    protected  void onPreExecute(){
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        RestConnection rest = new RestConnection(this.context, Path.TOLLGATE, Method.GET, false);
        rest.putParam("timestamp", Util.getTimestamp());

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
            return response.getResult().compareTo("Hello") == 0;
        } catch (Exception ex) {
            Log.d(LogTag.REST_ACCOUNT, "Exception : " + ex.getMessage());
            return null;
        }
    }

    @Override
    protected void onProgressUpdate(Void... params){}

    @Override
    protected void onPostExecute(Boolean result){
        super.onPostExecute(result);
    }
}
