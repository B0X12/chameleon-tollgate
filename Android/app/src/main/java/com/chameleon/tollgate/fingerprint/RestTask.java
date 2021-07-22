package com.chameleon.tollgate.fingerprint;

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

/*
    AsyncTask는 비동기 Thread를 생성하기 위한 추상 클래스
    추상 클래스 - 소속은 여기에 돼있지만 상속을 받는 애가 구현을 해줄 거다!
                 때문에 얘를 상속받게 되면 거기 있는 함수들을
                 @Override라는 어노테이션을 써서 새로 구현해줘야함

    AsyncTask<1, 2, 3>
    (원형) AsyncTask<Params, Progress, Result>
    1: doInBackground(<Void>... params)
    2: protected void onProgressUpdate(<Void>... params)
    3: <Boolean> doInBackground() (이 함수의 반환형)

    - 서버의 url을 입력해서 timestamp값을 json으로 받아옴

 */

public class RestTask extends AsyncTask<Void, Void, Boolean>
{
    public static final int ERROR_MSG = -1;

    private final long timestamp;
    private boolean restResult = false;
    private final Context context;
    private final Handler handler;

    public RestTask(long timestamp, boolean restResult, Context context, Handler handler) // 생성자
    {
        this.timestamp = timestamp;
        this.restResult = restResult;
        this.context = context;
        this.handler = handler;
    }

    @Override
    protected void onPreExecute() // doIn~을 수행하기 전 실행되는 사전작업 함수
    {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Void... params) // .execute()를 호출하면 이부분이 수행됨
    {
        RestResult result; // 요청 결과를 저장할 변수 선언
        RestConnection rest = new RestConnection(this.context, Path.FINGERPRINT, Method.POST);
        rest.putParam("timestamp", this.timestamp); // timestamp = value
        rest.putParam("restResult", this.restResult); // restResult = value로 세팅

        try {
            result = rest.request(); // Path.FINGERPRINT(/auth/fingerprint/)로부터 결과를 요청하고 얻어옴
            Log.d(LogTag.REST_FINGERPRINT, "Result : " + result);

            // OK | BAD_REQUEST | NOT_FOUND(404) | INTERNAL_SERVER_ERROR(500)
            if (result.responseCode != HttpStatus.OK.value)
            {
                ErrorResponse okErr = new Gson().fromJson(result.result, ErrorResponse.class);
                Log.d(LogTag.REST_FINGERPRINT, "Exception : " + okErr.getMessage());
                Message msg = this.handler.obtainMessage(ERROR_MSG, okErr.getMessage()); // 호출 결과로 메시지 객체를 리턴받음
                this.handler.sendMessage(msg); // msg를 handler에게 보내줌
                return false;
            }

            Response<Boolean> response = new Gson().fromJson(result.result, Response.class);

            if (this.timestamp != response.getTimestamp()) // 타임스탬프 값이 일치하지 않으면
            {
                Log.d(LogTag.REST_FINGERPRINT, "Exception : Invalid response");
                Message msg = this.handler.obtainMessage(ERROR_MSG, "Invalid response");
                this.handler.sendMessage(msg);
                return false;
            }
            return response.getResult(); // 요청 결과를 반환

        } catch (Exception e) {
            Log.d(LogTag.REST_FINGERPRINT, "Exception : " + e.getMessage());
            return false;
        }
    }

    @Override
    protected void onProgressUpdate(Void... params) { }

    @Override
    protected void onPostExecute(Boolean result)
     {
        super.onPostExecute(result);
    }
}
