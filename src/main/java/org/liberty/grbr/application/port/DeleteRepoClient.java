package org.liberty.grbr.application.port;

import org.liberty.grbr.domain.model.DeleteStatus;
import org.liberty.grbr.domain.model.Repository;

public interface DeleteRepoClient {
    DeleteStatus deleteRepository(Repository repository);
}
