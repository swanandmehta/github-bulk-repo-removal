package org.liberty.grbr.infrastructure.adapter.rest;

import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import org.liberty.grbr.application.dto.GitRepositoryDto;
import org.liberty.grbr.application.exception.FetchRepoClientException;
import org.liberty.grbr.application.port.FetchRepoClient;
import org.liberty.grbr.application.service.FetchRepositoryService;
import org.liberty.grbr.domain.model.Repository;

public class GitFetchRepoClient implements FetchRepoClient {

  protected final FetchRepositoryService fetchService;
  private final HttpClient client = HttpClient.newHttpClient();

  public GitFetchRepoClient(FetchRepositoryService fetchService) {
    this.fetchService = fetchService;
  }

  @Override
  public List<Repository> fetchAvailableRepository() {
    List<GitRepositoryDto> combileResultList = new ArrayList<>();
    List<GitRepositoryDto> pageResultList;
    int pageCount = 1;
    do {
      pageResultList = getPaginatedResult(pageCount);
      combileResultList.addAll(pageResultList);
      pageCount++;
    } while(!pageResultList.isEmpty());

    return fetchService.transform(combileResultList);
  }

  private List<GitRepositoryDto> getPaginatedResult(int pageNumber) {
    try {
      HttpRequest request = fetchService.createFetchRequest(pageNumber);
      HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
      return fetchService.transform(response);
    } catch (IOException | InterruptedException e) {
      throw new FetchRepoClientException(e);
    }
  }
}
