package com.chameleon.tollgate.login;

import android.content.Context;
import android.os.Handler;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.chameleon.tollgate.R;
import com.chameleon.tollgate.Util;
import com.chameleon.tollgate.define.CfgKey;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class LoginService {
    public void beforeSetServer(AppCompatActivity activity) {
        EditText editServer = activity.findViewById(R.id.edit_server);
        editServer.setEnabled(true);
        EditText editID = activity.findViewById(R.id.edit_id);
        editID.setEnabled(false);
        EditText editPwd = activity.findViewById(R.id.edit_pwd);
        editPwd.setEnabled(false);
    }

    public void beforeSetID(AppCompatActivity activity) {
        EditText editServer = activity.findViewById(R.id.edit_server);
        editServer.setEnabled(false);
        EditText editID = activity.findViewById(R.id.edit_id);
        editID.setEnabled(true);
        EditText editPwd = activity.findViewById(R.id.edit_pwd);
        editPwd.setEnabled(true);
    }

    public void setServerIP(AppCompatActivity activity, String ip) {
        EditText editServer = activity.findViewById(R.id.edit_server);
        editServer.setText(ip);
        editServer.setEnabled(false);
        MainActivity.SERVER_IP = ip;
    }

    public void unsetServerIP(AppCompatActivity activity) {
        EditText editServer = activity.findViewById(R.id.edit_server);
        editServer.setEnabled(true);
        MainActivity.SERVER_IP = null;
    }

    public void setID(AppCompatActivity activity, String id) {
        EditText editID = activity.findViewById(R.id.edit_id);
        editID.setText(id);
        editID.setEnabled(false);
        MainActivity.USER_ID = id;
    }

    public String getServerIP(Context context) throws IOException {
        File cfgFile = new File(context.getFilesDir(), MainActivity.CFGFIlE);
        cfgFile.createNewFile();
        try (BufferedReader reader = new BufferedReader(new FileReader(cfgFile))) {
            String line = reader.readLine();
            if (line == null || line.isEmpty())
                return null;
            String[] entry = line.split("=");
            if (entry[0].compareTo(CfgKey.SERVER_IP.getValue()) == 0)
                return entry[1];
        }
        return null;
    }

    public boolean saveServerIP(Context context) {
        File cfgFile = new File(context.getFilesDir(), MainActivity.CFGFIlE);
        try {
            cfgFile.createNewFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(cfgFile))) {
                writer.write(CfgKey.SERVER_IP.getValue() + "=" + MainActivity.SERVER_IP);
            }
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    public boolean checkValidIP(String ip) {
        String[] tokens = ip.split("\\.");
        if(tokens.length != 4)
            return false;

        for(String token : tokens){
            int octet = Integer.parseInt(token);
            if(octet > 255)
                return false;
        }
        return true;
    }

    public boolean checkServer(Context context, Handler handler) throws ExecutionException, InterruptedException {
        CheckServerTask checkServer = new CheckServerTask(context, handler);
        boolean result = checkServer.execute().get();
        return result;
    }

    public String getID(Context context, Handler handler) throws ExecutionException, InterruptedException {
        String token = FirebaseMessaging.getInstance().getToken().getResult();
        GetIDTask getID = new GetIDTask(context, handler, token, Util.getTimestamp());
        return getID.execute().get();
    }
}
