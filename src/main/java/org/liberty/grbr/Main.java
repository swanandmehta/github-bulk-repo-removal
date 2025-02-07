package org.liberty.grbr;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import org.liberty.grbr.application.port.DeleteRepoClient;
import org.liberty.grbr.application.port.FetchRepoClient;
import org.liberty.grbr.application.service.DeleteRepoService;
import org.liberty.grbr.application.service.FetchRepositoryService;
import org.liberty.grbr.domain.model.DeleteStatus;
import org.liberty.grbr.domain.model.Repository;
import org.liberty.grbr.domain.service.ConfigService;
import org.liberty.grbr.domain.service.RepositoryService;
import org.liberty.grbr.infrastructure.adapter.rest.GitDeleteRepoClient;
import org.liberty.grbr.infrastructure.adapter.rest.GitFetchRepoClient;

public class Main {

  public static void main(String[] arg) {
    ConfigService configService = new ConfigService();
    RepositoryService repositoryService = getService(configService);
    List<Repository> avaliableRepoList = repositoryService.fetchAvailableRepository();
    Map<Repository, DeleteStatus> statusList =
        repositoryService.deleteRepositories(avaliableRepoList);

    statusList.forEach(
        (repository, status) ->
            System.out.printf("Repository Name %s Status %s%n", repository, status.name()));
  }

  private static RepositoryService getService(ConfigService configService) {
    ObjectMapper objectMapper = new ObjectMapper();
    FetchRepositoryService fetchRepositoryService =
        new FetchRepositoryService(configService, objectMapper);
    DeleteRepoService deleteRepoService = new DeleteRepoService(configService);
    FetchRepoClient fetchClient = new GitFetchRepoClient(fetchRepositoryService);
    DeleteRepoClient deleteClient = new GitDeleteRepoClient(deleteRepoService);

    return new RepositoryService(configService, fetchClient, deleteClient);
  }
}
