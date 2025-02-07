package org.liberty.grbr.application.exception;

public class ConfigException extends RuntimeException {
    public ConfigException(Exception exception) {
        super(exception);
    }
}
