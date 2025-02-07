package org.liberty.grbr.application.service;

import static java.time.temporal.ChronoUnit.SECONDS;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import org.liberty.grbr.application.dto.GitRepositoryDto;
import org.liberty.grbr.application.dto.RepositoryMapper;
import org.liberty.grbr.application.exception.FetchRepoClientException;
import org.liberty.grbr.domain.model.Config;
import org.liberty.grbr.domain.model.Repository;
import org.liberty.grbr.domain.service.ConfigService;

public class FetchRepositoryService {

  protected final ConfigService configService;
  private final ObjectMapper objectMapper;

  public FetchRepositoryService(ConfigService configService, ObjectMapper objectMapper) {
    this.configService = configService;
    this.objectMapper = objectMapper;
  }

  public HttpRequest createFetchRequest(int pageNumber) {
    try {
      Config config = this.configService.fetchConfig();
      return HttpRequest.newBuilder()
          .uri(
              new URI(
                  String.format(
                      "https://api.github.com/user/repos?per_page=10&page=%d", pageNumber)))
          .header("Accept", "application/vnd.github+json")
          .header("X-GitHub-Api-Version", "2022-11-28")
          .header("Authorization", String.format("Bearer %s", config.getToken()))
          .timeout(Duration.of(10, SECONDS))
          .GET()
          .build();
    } catch (URISyntaxException e) {
      throw new FetchRepoClientException(e);
    }
  }

  public List<GitRepositoryDto> transform(HttpResponse<InputStream> response) {
    try {
      return objectMapper.readValue(
          response.body(),
          objectMapper
              .getTypeFactory()
              .constructCollectionType(List.class, GitRepositoryDto.class));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public List<Repository> transform(List<GitRepositoryDto> combileResultList) {
    return combileResultList.stream().map(RepositoryMapper.MAPPER::to).toList();
  }
}
