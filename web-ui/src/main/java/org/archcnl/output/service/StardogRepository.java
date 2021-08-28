package org.archcnl.output.service;

import java.util.Optional;
import org.archcnl.output.model.query.Query;
import org.archcnl.stardogwrapper.api.StardogDatabaseAPI;
import org.archcnl.stardogwrapper.api.StardogDatabaseAPI.Result;
import org.archcnl.stardogwrapper.impl.StardogDatabase;

public class StardogRepository {

  private StardogDatabaseAPI db;

  public StardogRepository(final String url, final String dbName, final String username,
      final String password) {
    this.db = new StardogDatabase(url, dbName, username, password);
    db.connect(false);
  }

  public Optional<Result> executeNativeSelectQuery(final Query query) {
    return db.executeSelectQuery(query.asFormattedString());
  }
}
