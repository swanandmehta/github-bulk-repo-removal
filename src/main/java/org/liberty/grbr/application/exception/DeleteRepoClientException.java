package org.liberty.grbr.application.exception;

public class DeleteRepoClientException extends RuntimeException {
  public DeleteRepoClientException(Exception e) {
    super(e);
  }
}
