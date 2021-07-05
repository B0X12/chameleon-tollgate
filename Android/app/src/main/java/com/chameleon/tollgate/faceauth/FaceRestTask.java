package com.chameleon.tollgate.faceauth;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.chameleon.tollgate.R;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

public class FaceRestTask extends AsyncTask<Void, Void, Boolean> {
    private URL url;
    private ContentValues values;
    private Context context;
    private String text;
    private JsonObject body;

    public FaceRestTask(@NonNull String url, String text, Context context) throws MalformedURLException {
        this.url = new URL(url);
        this.context = context;
        this.text = text;
        this.body = new JsonObject();
    }

    private SSLContext getSSL() throws Exception {
        CertificateFactory cf;
        Certificate ca;

        cf = CertificateFactory.getInstance("X.509");
        InputStream caInput = this.context.getResources().openRawResource(R.raw.chameleon);
        try {
            ca = cf.generateCertificate(caInput);
        }finally {
            caInput.close();
        }

        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);

        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tmf.getTrustManagers(), null);

        return sslContext;
    }
    @Override
    protected Boolean doInBackground(Void... voids) {
        //파일 전송 코드 예제
        //https://www.codejava.net/java-se/networking/upload-files-by-sending-multipart-request-programmatically
        try{
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setSSLSocketFactory(getSSL().getSocketFactory());
            conn.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            body.addProperty("body", text);

            OutputStream out = conn.getOutputStream();
            byte[] value = body.toString().getBytes();
            out.write(value, 0, value.length);
            System.out.println(body.toString());
            Log.d("t", body.toString());
            out.close();

            // 보내고 응답 코드를 반환받는다.
            int responseCode = conn.getResponseCode();

            String response = "", line = null;
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF_8"));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
                if(response.compareTo("true") == 0) {
                    System.out.println("testtesttest"+response);
                    return true;
                }
                return false;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean result){
        System.out.println(result);
        super.onPostExecute(result);

        // doInBackground()로 부터 반환된 값이 매개변수로 넘어온다.
        if(result == true)
            Log.d("testtest", "succeeded!");
    }

}
