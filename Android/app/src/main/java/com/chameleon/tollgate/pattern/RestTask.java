package com.chameleon.tollgate.pattern;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.chameleon.tollgate.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

public class RestTask extends AsyncTask<Void, Void, Boolean> {
    private URL url;
    private Context context;

    public RestTask(String url, Context context) throws MalformedURLException {
        this.url = new URL(url);
        this.context = context;
    }

    @Override
    protected  void onPreExecute(){
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setSSLSocketFactory(test().getSocketFactory());
            connection.setRequestMethod("POST");
            connection.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            //connection.("pattern", "123654");

            //if(connection.getResponseCode() != HttpURLConnection.HTTP_OK)
            //    return false;
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF_8"));
            String result = reader.readLine();
            Log.d("Tollgate_Rest_Pattern", result);
            if(result.compareTo("true") == 0)
                return true;
            return false;
        }
        catch (Exception ex) {
            Log.d("Tollgate_Rest_Pattern", "Exception : " + ex.getMessage() + "\n" + ex.getStackTrace());
            return false;
        }
    }

    @Override
    protected void onProgressUpdate(Void... params){

    }

    @Override
    protected void onPostExecute(Boolean result){
        super.onPostExecute(result);
    }

    private SSLContext test() throws Exception {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        InputStream input = this.context.getResources().openRawResource(R.raw.chameleon);
        Certificate ca;
        try{
            ca = cf.generateCertificate(input);
            Log.d("Tollgate_Rest_Pattern", "ca = " + ((X509Certificate)ca).getSubjectDN());
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

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tmf.getTrustManagers(), null);
        return sslContext;
    }
}