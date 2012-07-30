package com.myideaway.android.database;

/**
 * Created with IntelliJ IDEA.
 * User: cdm
 * Date: 12-5-12
 * Time: PM11:38
 */
public class DatabaseException extends Exception {
    public DatabaseException() {
        super();
    }

    public DatabaseException(String detailMessage) {
        super(detailMessage);
    }

    public DatabaseException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public DatabaseException(Throwable throwable) {
        super(throwable);
    }
}
