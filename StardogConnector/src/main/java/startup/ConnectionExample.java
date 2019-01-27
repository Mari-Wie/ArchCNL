package startup;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.openrdf.model.IRI;
import org.openrdf.model.Model;
import org.openrdf.model.Resource;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.SimpleValueFactory;
import org.openrdf.model.util.Models;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.query.TupleQueryResultHandlerException;
import org.openrdf.query.resultio.QueryResultIO;
import org.openrdf.query.resultio.UnsupportedQueryResultFormatException;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.Rio;

import com.complexible.common.openrdf.vocabulary.FOAF;
import com.complexible.common.rdf.model.StardogValueFactory.RDF;
import com.complexible.common.rdf.query.resultio.TextTableQueryResultWriter;
import com.complexible.stardog.StardogException;
import com.complexible.stardog.api.Connection;
import com.complexible.stardog.api.ConnectionConfiguration;
import com.complexible.stardog.api.ConnectionPool;
import com.complexible.stardog.api.ConnectionPoolConfig;
import com.complexible.stardog.api.Getter;
import com.complexible.stardog.api.SelectQuery;
import com.complexible.stardog.api.admin.AdminConnection;
import com.complexible.stardog.api.admin.AdminConnectionConfiguration;

import com.complexible.common.rdf.model.Values;

/**
 * Beispiel aus: https://github.com/stardog-union/stardog-examples/tree/develop/weblog/stardog-client
 * @author sandr
 *
 */

public class ConnectionExample {

	private static String url = "http://localhost:5820";
	private static String username = "admin";
	private static String password = "admin";
	private static String database = "myNewDB";
	
	 private static final String NS = "http://api.stardog.com/";

	public static void main(String[] args) {

		createAdminConnection();
		ConnectionPool connectionPool = createConnectionPool();
		try {
			addDataUsingRDFFile(connectionPool);
		} catch (StardogException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println("Not able to add data");
		}
		
		try {
			queryData(connectionPool);
		} catch (TupleQueryResultHandlerException | QueryEvaluationException | UnsupportedQueryResultFormatException
				| IOException e) {
			System.err.println("Not able to query data");
		}
		
		try {
			addDataProgrammatically(connectionPool);
		} catch (TupleQueryResultHandlerException | QueryEvaluationException | UnsupportedQueryResultFormatException
				| IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Not able to add data");
		}
		
		releaseConnection(connectionPool, connectionPool.obtain());
		connectionPool.shutdown();

	}

	public static void createAdminConnection() {
		try (final AdminConnection aConn = AdminConnectionConfiguration.toServer(url).credentials(username, password)
				.connect()) {

			// A look at what databases are currently in Stardog
			aConn.list().forEach(item -> System.out.println(item));

			// Checks to see if the 'myNewDB' is in Stardog. If it is, we are
			// going to drop it so we are starting fresh
			if (aConn.list().contains(database)) {
				aConn.drop(database);
			}
			// Convenience function for creating a persistent
			// database with all the default settings.
			aConn.disk(database).create();
		}
	}

	public static ConnectionPool createConnectionPool() {
		ConnectionConfiguration connectionConfig = ConnectionConfiguration.to(database).server(url).reasoning(false)
				.credentials(username, password);
		// creates the Stardog connection pool

		ConnectionPoolConfig poolConfig = ConnectionPoolConfig.using(connectionConfig);

		return poolConfig.create();
	}

	public static void addDataUsingRDFFile(ConnectionPool connectionPool) throws StardogException, FileNotFoundException {
		// first start a transaction. This will generate the contents of
		// the databse from the N3 file.
		Connection connection = connectionPool.obtain();
		connection.begin();
		// declare the transaction
		connection.add().io().format(RDFFormat.N3).stream(new FileInputStream("src/main/resources/example_data.rdf"));
		// and commit the change
		connection.commit();
	}

	public static void queryData(ConnectionPool connectionPool) throws TupleQueryResultHandlerException, QueryEvaluationException, UnsupportedQueryResultFormatException, IOException {
		
		Connection connection = connectionPool.obtain();
		SelectQuery query = connection.select("PREFIX foaf:<http://xmlns.com/foaf/0.1/> select * { ?s rdf:type foaf:Person }");
		TupleQueryResult tupleQueryResult = query.execute();
		QueryResultIO.writeTuple(tupleQueryResult, TextTableQueryResultWriter.FORMAT, System.out);
		
	}
	
	public static void addDataProgrammatically(ConnectionPool connectionPool) throws TupleQueryResultHandlerException, QueryEvaluationException, UnsupportedQueryResultFormatException, IOException {
		// first start a transaction - This will add
		// Tony Stark A.K.A Iron Man to the database
		Connection connection = connectionPool.obtain();
		connection.begin();
		// declare the transaction
		
		IRI ironMan = Values.iri(NS, "ironMan");
		IRI batman = Values.iri(NS,"batman");
		
		connection.add()
		        .statement(ironMan, RDF.TYPE, FOAF.ontology().Person)
		        .statement(ironMan, FOAF.ontology().name, Values.literal("Anthony Edward Stark"))
		        .statement(ironMan, FOAF.ontology().title, Values.literal("Iron Man"))
		        .statement(ironMan, FOAF.ontology().givenName, Values.literal("Anthony"))
		        .statement(ironMan, FOAF.ontology().familyName, Values.literal("Stark"))
		        .statement(ironMan, FOAF.ontology().knows, batman);

		// and commit the change
		connection.commit();
		
		SelectQuery query = connection.select("PREFIX foaf:<http://xmlns.com/foaf/0.1/> select * { ?s rdf:type foaf:Person }");
		TupleQueryResult tupleQueryResult = query.execute();
		QueryResultIO.writeTuple(tupleQueryResult, TextTableQueryResultWriter.FORMAT, System.out);
	}
	
	public static void releaseConnection(ConnectionPool connectionPool, Connection connection) {
        try {
            connectionPool.release(connection);
        } catch (StardogException e) {
            e.printStackTrace();
        }
    }
}
