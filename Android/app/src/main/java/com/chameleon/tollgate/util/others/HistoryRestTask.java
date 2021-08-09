package com.chameleon.tollgate.util.others;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.chameleon.tollgate.define.LogTag;
import com.chameleon.tollgate.rest.ErrorResponse;
import com.chameleon.tollgate.rest.HttpStatus;
import com.chameleon.tollgate.rest.Response;
import com.chameleon.tollgate.rest.RestConnection;
import com.chameleon.tollgate.rest.RestResult;
import com.chameleon.tollgate.rest.define.Method;
import com.chameleon.tollgate.rest.define.Path;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

public class HistoryRestTask extends AsyncTask<Void, Void, ArrayList<HistoryRecord>> {
    HistoryPack entry;
    private Context context;
    private Handler handler;

    public HistoryRestTask(@NonNull HistoryPack entry, @NonNull Context context, @NonNull Handler handler) {
        this.entry = entry;
        this.context = context;
        this.handler = handler;
    }

    @Override
    protected ArrayList<HistoryRecord> doInBackground(Void... voids) {

        RestConnection rest = new RestConnection(this.context, Path.HISTORY, Method.POST);

        if(rest == null)
            return null;

        rest.setBody(this.entry);

        try {
            RestResult result = rest.request();
            Log.d(LogTag.HISTORY, "Result : " + result.toString());

            if(result.responseCode != HttpStatus.OK.value) {
                ErrorResponse err = new Gson().fromJson(result.result, ErrorResponse.class);

                if(err.getMessage().compareTo("Invalid request.") == 0){
                    return null;
                }

                Message msg = this.handler.obtainMessage(HistoryMsg.TOAST_ERROR, err.getMessage());
                this.handler.sendMessage(msg);
                return null;
            }


            Type type = new TypeToken<Response<ArrayList<HistoryRecord>>>(){}.getType();

            Response<ArrayList<HistoryRecord>> respon = new Gson().fromJson(result.result, type);

            if(this.entry.getTimestamp() != respon.getTimestamp()) {
                Message msg = this.handler.obtainMessage(HistoryMsg.TOAST_ERROR, "Invalid response.");
                this.handler.sendMessage(msg);
                return null;
            }

            return respon.getResult();
        } catch (Exception ex) {
            Log.d(LogTag.HISTORY, "Exception : " + ex.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<HistoryRecord> result){
        super.onPostExecute(result);

        // doInBackground()로 부터 반환된 값이 매개변수로 넘어온다.
        Log.d(HistoryMsg.TAG, "replied result : "+result);
    }
}
