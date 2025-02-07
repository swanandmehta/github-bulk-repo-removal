package org.liberty.grbr.application.dto;

import org.liberty.grbr.domain.model.Repository;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RepositoryMapper {

  RepositoryMapper MAPPER = Mappers.getMapper(RepositoryMapper.class);

  Repository to(GitRepositoryDto source);
}
