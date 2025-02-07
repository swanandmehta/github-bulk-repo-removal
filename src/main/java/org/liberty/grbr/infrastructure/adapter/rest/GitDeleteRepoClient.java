package org.liberty.grbr.infrastructure.adapter.rest;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.liberty.grbr.application.exception.FetchRepoClientException;
import org.liberty.grbr.application.port.DeleteRepoClient;
import org.liberty.grbr.application.service.DeleteRepoService;
import org.liberty.grbr.domain.model.DeleteStatus;
import org.liberty.grbr.domain.model.Repository;

public class GitDeleteRepoClient implements DeleteRepoClient {

  protected final DeleteRepoService deleteService;
  private final HttpClient client = HttpClient.newHttpClient();

  public GitDeleteRepoClient(DeleteRepoService deleteService) {
    this.deleteService = deleteService;
  }

  @Override
  public DeleteStatus deleteRepository(Repository repository) {
    try {
      HttpRequest request = deleteService.createDeleteRequest(repository);
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      if (response.statusCode() == 204) {
        return DeleteStatus.DELETED;
      }
      return DeleteStatus.NOT_DELETED;
    } catch (IOException | InterruptedException e) {
      throw new FetchRepoClientException(e);
    }
  }
}
