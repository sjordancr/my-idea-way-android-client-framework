package com.myideaway.android.common.service;

import android.os.AsyncTask;

public abstract class Service {
    public static final int TASK_RESULT_SUCCESS = 1;
    public static final int TASK_RESULT_FAULT = 2;

    private AsyncTask<Integer, Integer, Integer> task;
    private boolean isCanceled = false;
    private Object executeResult;
    private OnSuccessHandler onSuccessHandler;
    private OnFaultHandler onFaultHandler;
    private Exception executeException;

    public final Object syncExecute() throws Exception {
        isCanceled = false;

        try {
            executeResult = doExecute();

            if (onSuccessHandler != null) {
                onSuccessHandler.onSuccess(executeResult);
            }

        } catch (Exception ex) {
            executeException = ex;

            if (onFaultHandler != null) {
                onFaultHandler.onFault(executeException);
            }

            throw ex;
        }


        return executeResult;
    }

    public final void asyncExecute() {
        isCanceled = false;

        task = new AsyncTask<Integer, Integer, Integer>() {

            @Override
            protected Integer doInBackground(Integer... arg0) {
                try {
                    executeResult = doExecute();
                    return TASK_RESULT_SUCCESS;
                } catch (Exception e) {
                    executeException = e;
                }

                return TASK_RESULT_FAULT;
            }

            protected void onPostExecute(Integer result) {
                if (result == TASK_RESULT_SUCCESS) {
                    if (onSuccessHandler != null) {
                        onSuccessHandler.onSuccess(executeResult);
                    }
                } else if (result == TASK_RESULT_FAULT) {
                    if (onFaultHandler != null) {
                        onFaultHandler.onFault(executeException);
                    }
                }
            }
        }.execute(0);

    }

    protected void willExecute() {

    }

    protected Object didExecute(Object result) {
        return result;
    }

    private Object doExecute() throws Exception {
        willExecute();
        Object result = onExecute();
        Object newResult = didExecute(result);

        return newResult;
    }

    public final void cancel() {
        isCanceled = true;
        task.cancel(true);
    }

    public Object getExecuteResult() {
        return executeResult;
    }

    protected abstract Object onExecute() throws Exception;

    public OnSuccessHandler getOnSuccessHandler() {
        return onSuccessHandler;
    }

    public void setOnSuccessHandler(OnSuccessHandler onSuccessHandler) {
        this.onSuccessHandler = onSuccessHandler;
    }

    public OnFaultHandler getOnFaultHandler() {
        return onFaultHandler;
    }

    public void setOnFaultHandler(OnFaultHandler onFaultHandler) {
        this.onFaultHandler = onFaultHandler;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public interface OnSuccessHandler {
        public void onSuccess(Object result);
    }

    public interface OnFaultHandler {
        public void onFault(Exception ex);
    }
}
