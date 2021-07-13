package com.chameleon.tollgate.rest;

import android.content.Context;
import android.util.Log;

import com.chameleon.tollgate.login.MainActivity;
import com.chameleon.tollgate.R;
import com.chameleon.tollgate.define.LogTag;
import com.chameleon.tollgate.rest.define.*;
import com.chameleon.tollgate.rest.exception.*;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

public class RestConnection {
    private static final int DEFAULT_TIMEOUT = 3000;
    private static final String USER_AGENT = "Tollgate-client";

    private static SSLContext sslContext = null;

    private final int TIMEOUT;
    private final String METHOD;
    private ArrayList<String> params;
    private JsonObject body;
    private StringBuilder uri;
    private final Context context;

    public RestConnection(Context context, Path path, Method method) {
        this(context, path, method, RestConnection.DEFAULT_TIMEOUT, true);
    }

    public RestConnection(Context context, Path path, Method method, boolean useID) {
        this(context, path, method, RestConnection.DEFAULT_TIMEOUT, useID);
    }

    public RestConnection(Context context, Path path, Method method, int timeout, boolean useID) {
        this.METHOD = method.toString();
        this.TIMEOUT = timeout;
        this.params = new ArrayList<>();
        this.uri = new StringBuilder();
        uri.append("https://")
                .append(MainActivity.SERVER_IP)
                .append(":" + Define.SERVER_PORT)
                .append(path);
        if(useID)
            uri.append(MainActivity.USER_ID);
        this.body = new JsonObject();
        this.context = context;

        if(RestConnection.sslContext == null)
            setSSLContext();
    }

    public void putParam (String key, Object value) {
        this.params.add(key + "=" + value);
    }

    public void setBody(Object obj) {
        if(!this.METHOD.equals(Method.POST.toString()) && !this.METHOD.equals(Method.PUT.toString()))
            throw new MethodException("Method is not Post or Put.");
        String json = new Gson().toJson(obj);
        this.body = new Gson().fromJson(json, JsonObject.class);
    }

    public void putBody(String key, String value) {
        if(!this.METHOD.equals(Method.POST.toString()) && !this.METHOD.equals(Method.PUT.toString()))
            throw new MethodException("Method is not Post or Put.");
        this.body.addProperty(key, value);
    }

    public void putBody(String key, Number value) {
        if(!this.METHOD.equals(Method.POST.toString()) && !this.METHOD.equals(Method.PUT.toString()))
            throw new MethodException("Method is not Post or Put.");
        this.body.addProperty(key, value);
    }

    public void putBody(String key, Boolean value) {
        if(!this.METHOD.equals(Method.POST.toString()) && !this.METHOD.equals(Method.PUT.toString()))
            throw new MethodException("Method is not Post or Put.");
        this.body.addProperty(key, value);
    }

    public void putBody(String key, JsonElement value) {
        if(!this.METHOD.equals(Method.POST.toString()) && !this.METHOD.equals(Method.PUT.toString()))
            throw new MethodException("Method is not Post or Put.");
        this.body.add(key, value);
    }

    public void putBody(String key, Object object) {
        if(!this.METHOD.equals(Method.POST.toString()) && !this.METHOD.equals(Method.PUT.toString()))
            throw new MethodException("Method is not Post or Put.");
        this.body.addProperty(key, new Gson().toJson(object));
    }

    public RestResult request() throws Exception {
        String strUrl = makeURI();
        Log.d(LogTag.REST, "URI : " + this.METHOD + "/" + strUrl);
        URL url = new URL(strUrl);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setSSLSocketFactory(RestConnection.sslContext.getSocketFactory());
        connection.setRequestMethod(this.METHOD);
        connection.setConnectTimeout(this.TIMEOUT);
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setRequestProperty("User-Agent", RestConnection.USER_AGENT);
        connection.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });

        if(this.METHOD.equals(Method.POST.toString()) || this.METHOD.equals(Method.PUT.toString())) {
            OutputStream output = connection.getOutputStream();
            byte[] value = this.body.toString().getBytes();
            output.write(value, 0, value.length);
            output.close();
        }

        int resCode = connection.getResponseCode();
        BufferedReader reader;
        if(resCode == HttpStatus.OK.value)
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF_8"));
        else
            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream(), "UTF_8"));
        StringBuilder result = new StringBuilder();
        String line;
        while((line = reader.readLine()) != null)
            result.append(line);
        reader.close();

        return new RestResult(resCode, result.toString());
    }

    private String makeURI(){
        boolean isFirst = true;
        for(String entry : this.params) {
            if(isFirst) {
                this.uri.append("?");
                isFirst = false;
            }
            else
                this.uri.append("&");
            this.uri.append(entry);
        }

        return this.uri.toString();
    }

    private void setSSLContext() {
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            InputStream input = this.context.getResources().openRawResource(R.raw.chameleon);
            Certificate ca;
            try {
                ca = cf.generateCertificate(input);
            } finally {
                input.close();
            }

            String ksType = KeyStore.getDefaultType();
            KeyStore ks = KeyStore.getInstance(ksType);
            ks.load(null, null);
            ks.setCertificateEntry("ca", ca);

            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(ks);

            this.sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);
        } catch (Exception ex){
            Log.d(LogTag.REST, "Exception : " + ex.getMessage());
        }
    }
}
