package org.liberty.grbr.application.service;

import static java.time.temporal.ChronoUnit.SECONDS;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.time.Duration;
import org.liberty.grbr.application.exception.DeleteRepoClientException;
import org.liberty.grbr.domain.model.Config;
import org.liberty.grbr.domain.model.Repository;
import org.liberty.grbr.domain.service.ConfigService;

public class DeleteRepoService {
  protected ConfigService configService;

  public DeleteRepoService(ConfigService configService) {
    this.configService = configService;
  }

  public HttpRequest createDeleteRequest(Repository repository) {
    try {
      Config config = this.configService.fetchConfig();
      return HttpRequest.newBuilder()
          .uri(
              new URI(
                  String.format(
                      "https://api.github.com/repos/%s/%s",
                      config.getUsername(), repository.name())))
          .header("Accept", "application/vnd.github+json")
          .header("X-GitHub-Api-Version", "2022-11-28")
          .header("Authorization", String.format("Bearer %s", config.getToken()))
          .timeout(Duration.of(10, SECONDS))
          .DELETE()
          .build();
    } catch (URISyntaxException e) {
      throw new DeleteRepoClientException(e);
    }
  }
}
