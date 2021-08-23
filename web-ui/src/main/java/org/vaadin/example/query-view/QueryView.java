package org.vaadin.example;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import java.util.Optional;
import org.archcnl.stardogwrapper.api.StardogAPIFactory;
import org.archcnl.stardogwrapper.api.StardogDatabaseAPI;
import org.archcnl.stardogwrapper.api.StardogICVAPI;
import org.archcnl.stardogwrapper.impl.StardogDatabase;

/**
 * A sample Vaadin view class.
 *
 * <p>To implement a Vaadin view just extend any Vaadin component and use @Route annotation to
 * announce it in a URL as a Spring managed bean. Use the @PWA annotation make the application
 * installable on phones, tablets and some desktop browsers.
 *
 * <p>A new instance of this class is created for every new user and every browser tab/window.
 */
@Route("QueryView")
public class QueryView extends HorizontalLayout {

    SideBarLayout sideBar = new SideBarLayout();
    QueryResults queryResults = new QueryResults();

    // TODO Extract into interface
    //
    String username = "admin";
    String password = "admin";
    String databaseName = "archcnl_it_db";
    String server = "http://localhost:5820";
    StardogICVAPI icvAPI;
    StardogDatabaseAPI db;

    public QueryView() {
        this.db = new StardogDatabase(server, databaseName, username, password);
        this.icvAPI = StardogAPIFactory.getICVAPI(db);

        setWidth(100, Unit.PERCENTAGE);
        setHeight(100, Unit.PERCENTAGE);
        sideBar.setWidth(20, Unit.PERCENTAGE);
        queryResults.setWidth(80, Unit.PERCENTAGE);
        addAndExpand(sideBar, queryResults);
        Registration reg =
                queryResults.addListener(
                        ResultUpdateEvent.class,
                        e -> {
                            Optional<StardogDatabaseAPI.Result> res = Optional.empty();
                            System.out.println("Event Received");
                            String q = queryResults.getQuery();
                            try {
                                db.connect(false);
                                res = db.executeSelectQuery(q);
                                db.closeConnectionToServer();
                            } catch (Exception lol) {
                                System.out.println("Exception Caught, fix this later");
                                lol.printStackTrace();
                            }
                            if (res.isPresent()) {
                                queryResults.updateGrid(res.get());
                            } else {
                                // TODO Decide what to do with errors or wrong queries
                            }
                        });
    }
}
