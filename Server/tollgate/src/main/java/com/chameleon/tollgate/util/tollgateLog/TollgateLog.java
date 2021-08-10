package com.chameleon.tollgate.util.tollgateLog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.chameleon.tollgate.define.Property;
import com.chameleon.tollgate.util.tollgateLog.dto.LogDevice;
import com.chameleon.tollgate.util.tollgateLog.dto.LogFactor;
import com.chameleon.tollgate.util.tollgateLog.dto.LogLevel;
import com.chameleon.tollgate.util.tollgateLog.dto.LogRecord;
import com.chameleon.tollgate.util.tollgateLog.dto.code.LogCode;

/**
 * 로그를 기록하기 위한 클래스인 {@code tollgateLog} 이다.
 */
public class TollgateLog {
	private static File logFile = null;
    private static final String separator = ";";
    private static LogDevice device = LogDevice.SERVER;

    static {
    	File dir = new File(Property.LOG_DIR);
    	if(!dir.exists()) {	dir.mkdirs(); }
      
    	TollgateLog.logFile = new File(Property.LOG_DIR + Property.LOG_FILE);
    	if(!TollgateLog.logFile.exists()) {
        	try {
            	TollgateLog.logFile.createNewFile();
            	}
        	catch(IOException e){
            	e.printStackTrace();
            	}
        	}
    }
    
    public static void i(String ip, String id, LogFactor factor, LogCode code, String msg) {
    	if(TollgateLog.logFile != null){
            StringBuilder sb = new StringBuilder();
            FileOutputStream os = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String timestamp = sdf.format(new Timestamp(System.currentTimeMillis()));
            int codeNum = device.getCode() + factor.getCode() + code.getErrorCode() + LogLevel.ERROR.getCode();
            
            
            sb.append(timestamp).append(separator)                      	// timestamp
            		.append(ip).append(separator)
                    .append(LogLevel.INFO.getLevel()).append(separator)   	// priority (info)
                    .append(id).append(separator)							// user id
                    .append(factor.getFactor()).append(separator)       	// factor (register, authenticate)
                    .append(codeNum).append(separator)       				// auth type (pattern, face, fingerprint, otp)
                    .append(msg)                 							// message
                    .append("\n");

            try {
                os = new FileOutputStream(TollgateLog.logFile, true);

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
    public static void w(String ip, String id, LogFactor factor, LogCode code, String msg) {
    	if(TollgateLog.logFile != null){
            StringBuilder sb = new StringBuilder();
            FileOutputStream os = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String timestamp = sdf.format(new Timestamp(System.currentTimeMillis()));
            int codeNum = device.getCode() + factor.getCode() + code.getErrorCode() + LogLevel.ERROR.getCode();

            sb.append(timestamp).append(separator)                      	// timestamp
    				.append(ip).append(separator)
                    .append(LogLevel.WARN.getLevel()).append(separator)   	// priority (info)
                    .append(id).append(separator)							// user id
                    .append(factor.getFactor()).append(separator)       	// factor (register, authenticate)
                    .append(codeNum).append(separator)       				// auth type (pattern, face, fingerprint, otp)
                    .append(msg).append(separator)                  		// message
                    .append("\n");

            try {
                os = new FileOutputStream(TollgateLog.logFile, true);

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
    public static void e(String ip, String id, LogFactor factor, LogCode code, String msg) {
    	if(TollgateLog.logFile != null){
            StringBuilder sb = new StringBuilder();
            FileOutputStream os = null;
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String timestamp = sdf.format(new Timestamp(System.currentTimeMillis()));
            
            int codeNum = device.getCode() + LogLevel.ERROR.getCode() + factor.getCode() + code.getErrorCode();

            sb.append(timestamp).append(separator)                      	// timestamp
    				.append(ip).append(separator)
                    .append(LogLevel.ERROR.getLevel()).append(separator)   	// priority (info)
                    .append(id).append(separator)							// user id
                    .append(factor.getFactor()).append(separator)       	// factor (register, authenticate)
                    .append(codeNum).append(separator)       				// auth type (pattern, face, fingerprint, otp)
                    .append(msg).append(separator)                  		// message
                    .append("\n");

            try {
                os = new FileOutputStream(TollgateLog.logFile, true);

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
    
    public static ArrayList<LogRecord> get(){
        ArrayList<LogRecord> logArr = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(TollgateLog.logFile);
            BufferedReader bufReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufReader.readLine()) != null) {
            	logArr.add(new LogRecord(line, TollgateLog.separator));
            }
            bufReader.close();
            fileReader.close();
        }
        catch (IOException e){

        }
        return logArr;
    }
    public ArrayList<LogRecord> get(LogFactor factor){
        ArrayList<LogRecord> logArr = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(TollgateLog.logFile);
            BufferedReader bufReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufReader.readLine()) != null) {
            	LogRecord record = new LogRecord(line, TollgateLog.separator);
                if(record.getFactor().compareTo(factor.getFactor()) == 0)
                	logArr.add(record);
            }
            bufReader.close();
            fileReader.close();
        }
        catch (IOException e){

        }
        return logArr;
    }
    public ArrayList<LogRecord> get(LogLevel level){
        ArrayList<LogRecord> logArr = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(TollgateLog.logFile);
            BufferedReader bufReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufReader.readLine()) != null) {
            	LogRecord record = new LogRecord(line, TollgateLog.separator);
                if(record.getLevel().compareTo(level.getLevel()) == 0)
                	logArr.add(record);
            }
            bufReader.close();
            fileReader.close();
        }
        catch (IOException e){

        }
        return logArr;
    }
    public ArrayList<LogRecord> get(LogCode code){
        ArrayList<LogRecord> logArr = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(TollgateLog.logFile);
            BufferedReader bufReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufReader.readLine()) != null) {
            	LogRecord record = new LogRecord(line, TollgateLog.separator);
                if(record.getCode().compareTo(code.getErrorMessage()) == 0)
                	logArr.add(record);
            }
            bufReader.close();
            fileReader.close();
        }
        catch (IOException e){

        }
        return logArr;
    }

}
