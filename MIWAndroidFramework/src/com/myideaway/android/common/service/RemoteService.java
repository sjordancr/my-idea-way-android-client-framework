package com.myideaway.android.common.service;

import com.myideaway.android.common.exception.RemoteServiceException;
import com.myideaway.android.util.HttpClientUtil;
import com.myideaway.android.common.exception.RemoteServiceException;

import java.util.List;
import java.util.Map;

public abstract class RemoteService extends Service {
    public static final int REQUEST_METHOD_POST = 1;
    public static final int REQUEST_METHOD_GET = 2;

	protected Map<String, Object> params;
    protected String postUrl;
    protected List<HttpClientUtil.FormFile> formFiles;
    protected int requestMethod = REQUEST_METHOD_POST;

    @Override
    protected void willExecute() {
        params = getParams();
        postUrl = getURL();
        formFiles = getFormFiles();
    }

    protected abstract Map<String, Object> getParams();

	protected abstract String getURL();

    protected List<HttpClientUtil.FormFile> getFormFiles(){
        return null;
    }

    public int getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(int requestMethod) {
        this.requestMethod = requestMethod;
    }
}
