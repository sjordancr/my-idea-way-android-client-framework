package com.myideaway.android.common;

import com.myideaway.android.util.DataUtil;
import com.myideaway.android.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: cdm
 * Date: 12-4-21
 * Time: AM11:53
 */
public class Config {
    public static Config instance = new Config();
    public static int REMOTE_SERVICE_TIME_OUT = 20000;

    private Config(){

    }

    public static Config getInstance(){
        return instance;
    }

    public void readConfig(InputStream inStream) throws IOException {
        Properties props = new Properties();
        props.load(inStream);

        String timeOutStr = props.getProperty("remote_service_time_out");
        if(!StringUtil.isNullOrEmpty(timeOutStr)){
            REMOTE_SERVICE_TIME_OUT = DataUtil.parseInt(timeOutStr);
        }

    }

}
