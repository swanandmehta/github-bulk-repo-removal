package org.liberty.grbr.application.port;

import java.util.List;
import org.liberty.grbr.domain.model.Repository;

public interface FetchRepoClient {
    List<Repository> fetchAvailableRepository();
}
