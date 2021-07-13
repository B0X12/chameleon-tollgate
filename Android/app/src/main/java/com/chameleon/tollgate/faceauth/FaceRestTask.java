package com.chameleon.tollgate.faceauth;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.chameleon.tollgate.define.LogTag;
import com.chameleon.tollgate.pattern.PatternMsg;
import com.chameleon.tollgate.pattern.dto.PatternPack;
import com.chameleon.tollgate.rest.ErrorResponse;
import com.chameleon.tollgate.rest.HttpStatus;
import com.chameleon.tollgate.rest.Response;
import com.chameleon.tollgate.rest.RestConnection;
import com.chameleon.tollgate.rest.RestResult;
import com.chameleon.tollgate.rest.define.Method;
import com.chameleon.tollgate.rest.define.Path;
import com.google.gson.Gson;

public class FaceRestTask extends AsyncTask<Void, Void, Boolean> {
    FacePack entry;
    private Context context;
    private Handler handler;

    public FaceRestTask(@NonNull FacePack entry, @NonNull Context context, @NonNull Handler handler) {
        this.entry = entry;
        this.context = context;
        this.handler = handler;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        RestConnection rest = null;
        if(entry.getMode().compareTo("train")==0)
            rest = new RestConnection(this.context, Path.FACEID_REG, Method.POST);
        else if(entry.getMode().compareTo("auth")==0)
            rest = new RestConnection(this.context, Path.FACEID, Method.POST);

        if(rest == null)
            return false;

        rest.setBody(entry);

        try {
            RestResult result = rest.request();
            Log.d(LogTag.REST_FACEID, "Result : " + result.toString());

            if(result.responseCode != HttpStatus.OK.value) {
                ErrorResponse err = new Gson().fromJson(result.result, ErrorResponse.class);
                Message msg = this.handler.obtainMessage(FaceMsg.TOAST_ERROR, err.getMessage());
                this.handler.sendMessage(msg);
                return false;
            }

            Response<Boolean> respon = new Gson().fromJson(result.result, Response.class);
            if(this.entry.getTimestamp() != respon.getTimestamp()) {
                Message msg = this.handler.obtainMessage(FaceMsg.TOAST_ERROR, "Invalid response.");
                this.handler.sendMessage(msg);
                return false;
            }
            return respon.getResult();
        } catch (Exception ex) {
            Log.d(LogTag.REST_FACEID, "Exception : " + ex.getMessage());
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean result){
        super.onPostExecute(result);

        // doInBackground()로 부터 반환된 값이 매개변수로 넘어온다.
        Log.d(FaceVar.TAG, "replied result : "+result);
    }

}
