package com.myideaway.android.common.service;

import com.myideaway.android.common.Config;
import com.myideaway.android.common.exception.RemoteDataEmptyException;
import com.myideaway.android.common.exception.RemoteServiceException;
import com.myideaway.android.util.HttpClientUtil;
import com.myideaway.android.util.LogUtil;
import org.apache.http.conn.EofSensorInputStream;

import java.io.InputStream;

public abstract class StreamRemoteService extends RemoteService {

    @Override
    protected Object onExecute() throws RemoteServiceException {
        try {

            InputStream inStream = null;

            if (formFiles != null) {
                LogUtil.debug("Send post mutipart url " + postUrl + ", param " + params);

                inStream = HttpClientUtil.getStreamByPostMultipart(postUrl, params, formFiles, getPostCallbackHandler(), Config.REMOTE_SERVICE_TIME_OUT);
            } else {
                if (requestMethod == REQUEST_METHOD_GET) {
                    inStream = (EofSensorInputStream) HttpClientUtil.getStreamByGet(postUrl, params,
                            Config.REMOTE_SERVICE_TIME_OUT);
                } else {
                    inStream = (EofSensorInputStream) HttpClientUtil.getStreamByPost(postUrl, params,
                            Config.REMOTE_SERVICE_TIME_OUT);
                }
            }

            if (inStream == null) {
                throw new RemoteDataEmptyException("The stream is empty");
            }

            return inStream;
        } catch (Exception e) {
            throw new RemoteServiceException(e);
        }
    }

    protected HttpClientUtil.HttpClientPostCallbackHandler getPostCallbackHandler() {
        return null;
    }
}
