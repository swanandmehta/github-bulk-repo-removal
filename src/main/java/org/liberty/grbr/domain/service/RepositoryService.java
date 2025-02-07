package org.liberty.grbr.domain.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.liberty.grbr.application.port.DeleteRepoClient;
import org.liberty.grbr.application.port.FetchRepoClient;
import org.liberty.grbr.domain.model.DeleteStatus;
import org.liberty.grbr.domain.model.Repository;

public class RepositoryService {

  protected final ConfigService configService;
  protected final FetchRepoClient fetchRepoClient;
  protected final DeleteRepoClient deleteRepoClient;

  public RepositoryService(
      ConfigService configService,
      FetchRepoClient fetchRepoClient,
      DeleteRepoClient deleteRepoClient) {
    this.fetchRepoClient = fetchRepoClient;
    this.deleteRepoClient = deleteRepoClient;
    this.configService = configService;
  }

  public List<Repository> fetchAvailableRepository() {
    return fetchRepoClient.fetchAvailableRepository();
  }

  public Map<Repository, DeleteStatus> deleteRepositories(List<Repository> repoList) {
    return repoList.stream()
        .filter(e -> !configService.fetchConfig().getIgnoreList().contains(e.name()))
        .map(e -> Map.entry(e, deleteRepoClient.deleteRepository(e)))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }
}
