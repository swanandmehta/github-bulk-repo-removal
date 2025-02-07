package org.liberty.grbr.application.exception;

public class FetchRepoClientException extends RuntimeException {
  public FetchRepoClientException(Exception e) {
    super(e);
  }
}
