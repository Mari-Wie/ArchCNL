package org.archcnl.domain.output.repository;

import java.util.Optional;
import org.archcnl.domain.output.model.query.Query;
import org.archcnl.stardogwrapper.api.StardogDatabaseAPI;
import org.archcnl.stardogwrapper.api.StardogDatabaseAPI.Result;
import org.archcnl.stardogwrapper.impl.StardogDatabase;

public class ResultRepositoryImpl implements ResultRepository {

    private StardogDatabaseAPI db;

    /**
     * Constructor stardog client.
     *
     * @param url Full host url. For example: http://localhost:5820
     * @param dbName DB name.
     * @param username DB username.
     * @param password DB password.
     */
    public ResultRepositoryImpl(
            final String url, final String dbName, final String username, final String password) {
        this.db = new StardogDatabase(url, dbName, username, password);
        db.connect(false);
    }

    @Override
    public Optional<Result> executeNativeSelectQuery(final Query query) {
        return db.executeSelectQuery(query.asFormattedString());
    }
}
