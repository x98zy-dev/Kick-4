package org.x98zy.webtask.exception;

public class WebTaskException extends Exception {

    public WebTaskException(String message) {
        super(message);
    }

    public WebTaskException(String message, Throwable cause) {
        super(message, cause);
    }
}