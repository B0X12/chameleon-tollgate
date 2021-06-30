package com.chameleon.tollgate.pattern;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.chameleon.tollgate.define.LogTag;
import com.chameleon.tollgate.rest.RestConnection;
import com.chameleon.tollgate.rest.RestResult;
import com.chameleon.tollgate.rest.define.*;

public class RestTask extends AsyncTask<Void, Void, Boolean> {
    private String pattern;
    private Context context;

    public RestTask(String pattern, Context context) {
        this.pattern = pattern;
        this.context = context;
    }

    @Override
    protected  void onPreExecute(){
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        RestConnection rest = new RestConnection(this.context, Path.PATTERN, Method.POST);
        rest.putParam("pattern", this.pattern);
        try {
            RestResult result = rest.request();
            if(result.result.compareTo("true") == 0)
                return true;
            else
                return false;
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