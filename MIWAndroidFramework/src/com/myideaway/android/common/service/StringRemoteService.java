package com.myideaway.android.common.service;

import com.myideaway.android.common.Config;
import com.myideaway.android.common.exception.RemoteServiceException;
import com.myideaway.android.util.HttpClientUtil;
import com.myideaway.android.util.LogUtil;

public abstract class StringRemoteService extends RemoteService {


    @Override
    public Object onExecute() throws RemoteServiceException {

        try {

            String result = null;

            if (formFiles != null) {
                LogUtil.debug("Send post mutipart url " + postUrl + ", param " + params);


                result = HttpClientUtil.getStringByPostMultipart(postUrl, params, formFiles, getPostCallbackHandler(), Config.REMOTE_SERVICE_TIME_OUT);
            } else {
                if (requestMethod == REQUEST_METHOD_GET) {
                    LogUtil.debug("Send get url " + postUrl + ", param " + params);
                    result = HttpClientUtil.getStringByGet(postUrl, params, Config.REMOTE_SERVICE_TIME_OUT);
                } else {
                    LogUtil.debug("Send post url " + postUrl + ", param " + params);
                    result = HttpClientUtil.getStringByPost(postUrl, params, Config.REMOTE_SERVICE_TIME_OUT);
                }

            }

            LogUtil.debug("Result " + result);

            return result;
        } catch (Exception e) {
            throw new RemoteServiceException(e);
        }
    }

    protected HttpClientUtil.HttpClientPostCallbackHandler getPostCallbackHandler() {
        return null;
    }

}
