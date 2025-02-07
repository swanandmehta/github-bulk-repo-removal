package org.liberty.grbr.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GitRepositoryDto(@JsonProperty("full_name") String fullName, String name) {}
