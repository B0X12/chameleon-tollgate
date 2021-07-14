package com.chameleon.tollgate.Utils.Log;

import android.content.Context;

import com.chameleon.tollgate.Utils.Log.define.logMode;
import com.chameleon.tollgate.Utils.Log.define.logRecord;
import com.chameleon.tollgate.Utils.Log.define.logType;
import com.chameleon.tollgate.Utils.Log.define.priority;

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

    public void put(priority priority, logMode mode, logType type, String message){
        if(logFile != null){
            String separator = ";";
            StringBuilder sb = new StringBuilder();
            FileOutputStream os = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String timestamp = sdf.format(new Timestamp(System.currentTimeMillis()));

            sb.append(timestamp)
                    .append(separator)
                    .append(priority.getPriority()+separator)
                    .append(mode.getAuthMode()).append(separator)
                    .append(type.getAuthType()).append(separator)
                    .append(message).append(separator)
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
                logs.add(new logRecord(line));
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
                logs.add(new logRecord(line));
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
