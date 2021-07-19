package com.chameleon.tollgate.Utils.Log;

import android.content.Context;

import com.chameleon.tollgate.Utils.Log.dto.logMode;
import com.chameleon.tollgate.Utils.Log.dto.logRecord;
import com.chameleon.tollgate.Utils.Log.dto.logResult;
import com.chameleon.tollgate.Utils.Log.dto.logType;
import com.chameleon.tollgate.Utils.Log.dto.logPriority;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class tollgateLog {
    private final File logFile;
    private static tollgateLog tollgateLogInstance;
    private static final String separator = ";";
    
    public tollgateLog(Context context){
        String logDir = "log";
        File dir = context.getDir(logDir, Context.MODE_PRIVATE);

        String logName = "auth.log";
        logFile = new File(dir, logName);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();

    }

    public static tollgateLog getInstance(Context applicationContext){
        if(tollgateLogInstance == null)
            tollgateLogInstance = new tollgateLog(applicationContext);
        return tollgateLogInstance;
    }

    public void put(logPriority logPriority, logMode mode, logType type, logResult result, String message){
        if(logFile != null){
            StringBuilder sb = new StringBuilder();
            FileOutputStream os = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String timestamp = sdf.format(new Timestamp(System.currentTimeMillis()));

            sb.append(timestamp).append(separator)                      // timestamp
                    .append(logPriority.getPriority()).append(separator)   // priority (alert)
                    .append(mode.getAuthMode()).append(separator)       // mode (register, authenticate)
                    .append(type.getAuthType()).append(separator)       // auth type (pattern, face, fingerprint, otp)
                    .append(result.getAuthResult()).append(separator)   // result
                    .append(message).append(separator)                  // message
                    .append("\n");

            try {
                os = new FileOutputStream(logFile, true);

                byte[] tmpbyte = String.valueOf(sb).getBytes();
                os.write(tmpbyte, 0, tmpbyte.length);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if (os != null)
                        os.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

    }

    public ArrayList<logRecord> get(){
        ArrayList<logRecord> logs = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(logFile);
            BufferedReader bufReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufReader.readLine()) != null) {
                logs.add(new logRecord(line, separator));
            }
            bufReader.close();
            fileReader.close();
        }
        catch (IOException e){

        }
        return logs;
    }

    public ArrayList<logRecord> get(logMode mode){
        ArrayList<logRecord> logs = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(logFile);
            BufferedReader bufReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufReader.readLine()) != null) {
                logRecord record = new logRecord(line, separator);
                if(record.getMode().compareTo(mode.getAuthMode()) == 0)
                    logs.add(record);
            }
            bufReader.close();
            fileReader.close();
        }
        catch (IOException e){

        }
        return logs;
    }

    public ArrayList<logRecord> get(logType type){
        ArrayList<logRecord> logs = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(logFile);
            BufferedReader bufReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufReader.readLine()) != null) {
                logRecord record = new logRecord(line, separator);
                if(record.getType().compareTo(type.getAuthType()) == 0)
                    logs.add(record);
            }
            bufReader.close();
            fileReader.close();
        }
        catch (IOException e){

        }
        return logs;
    }

    public ArrayList<logRecord> get(logPriority priority){
        ArrayList<logRecord> logs = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(logFile);
            BufferedReader bufReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufReader.readLine()) != null) {
                logRecord record = new logRecord(line, separator);
                if(record.getPriority().compareTo(priority.getPriority()) == 0)
                    logs.add(record);
            }
            bufReader.close();
            fileReader.close();
        }
        catch (IOException e){

        }
        return logs;
    }

    public ArrayList<logRecord> get(logResult result){
        ArrayList<logRecord> logs = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(logFile);
            BufferedReader bufReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufReader.readLine()) != null) {
                logRecord record = new logRecord(line, separator);
                if(record.getResult().compareTo(result.getAuthResult()) == 0)
                    logs.add(record);
            }
            bufReader.close();
            fileReader.close();
        }
        catch (IOException e){

        }
        return logs;
    }

    public boolean send2Server(){


        return false;
    }
}
