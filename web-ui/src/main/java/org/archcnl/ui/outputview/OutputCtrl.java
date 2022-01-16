package org.archcnl.ui.outputview;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.application.exceptions.PropertyNotFoundException;
import org.archcnl.application.service.ConfigAppService;
import org.archcnl.domain.output.repository.ResultRepository;
import org.archcnl.domain.output.repository.ResultRepositoryImpl;

public class OutputCtrl {

    private static final Logger LOG = LogManager.getLogger(OutputCtrl.class);

    private ResultRepository repository;

    public OutputCtrl() {
        try {
            this.repository =
                    new ResultRepositoryImpl(
                            ConfigAppService.getDbUrl(),
                            ConfigAppService.getDbName(),
                            ConfigAppService.getDbUsername(),
                            ConfigAppService.getDbPassword());
        } catch (final PropertyNotFoundException e) {
            OutputCtrl.LOG.error(e.getMessage() + " Output controller can not be initialized.");
            e.printStackTrace();
        }
    }

    public ResultRepository getRepository() {
        return repository;
    }
}
