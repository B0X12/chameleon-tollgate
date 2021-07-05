package com.chameleon.tollgate.pattern;

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
import com.chameleon.tollgate.rest.define.*;
import com.google.gson.Gson;

public class RestTask extends AsyncTask<Void, Void, Boolean> {
    PatternPack entry;
    private Context context;
    private Handler handler;

    public RestTask(PatternPack entry, Context context, Handler handler) {
        this.entry = entry;
        this.context = context;
        this.handler = handler;
    }

    @Override
    protected  void onPreExecute(){
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        RestConnection rest = new RestConnection(this.context, Path.PATTERN, Method.POST);
        rest.setBody(entry);

        try {
            RestResult result = rest.request();
            Log.d(LogTag.REST_PATTERN, "Result : " + result.toString());
            if(result.responseCode != HttpStatus.OK.value) {
                ErrorResponse err = new Gson().fromJson(result.result, ErrorResponse.class);
                Message msg = this.handler.obtainMessage(PatternMsg.TOAST_ERROR, err.getMessage());
                this.handler.sendMessage(msg);
                return false;
            }

            Response<Boolean> respon = new Gson().fromJson(result.result, Response.class);
            if(this.entry.getTimestamp() != respon.getTimestamp()) {
                Message msg = this.handler.obtainMessage(PatternMsg.TOAST_ERROR, "Invalid response.");
                this.handler.sendMessage(msg);
                return false;
            }
            return respon.getResult();
        } catch (Exception ex) {
            Log.d(LogTag.REST_PATTERN, "Exception : " + ex.getMessage());
            return false;
        }
    }

    @Override
    protected void onProgressUpdate(Void... params){}

    @Override
    protected void onPostExecute(Boolean result){
        super.onPostExecute(result);
    }
}