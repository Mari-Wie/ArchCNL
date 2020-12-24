package impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.stardog.stark.Statement;
import com.stardog.stark.Values;
import com.complexible.stardog.api.Connection;
import com.complexible.stardog.api.ConnectionConfiguration;
import com.complexible.stardog.icv.Constraint;
import com.complexible.stardog.icv.api.ICVConnection;
import com.complexible.stardog.reasoning.Proof;
import com.complexible.stardog.reasoning.ProofType;
import com.complexible.stardog.reasoning.ProofWriter;
import com.stardog.stark.io.RDFFormats;


import com.complexible.stardog.ContextSets;// TODO: only temporary

import api.StardogDatabaseAPI;
import api.StardogICVAPI;
import datatypes.ConstraintViolation;
import datatypes.ConstraintViolationsResultSet;

public class StardogICVAPIImpl implements StardogICVAPI {

	private static int id = 0;
	
	private static final Logger LOG = LogManager.getLogger(StardogICVAPI.class);

	private ConstraintViolationsResultSet result;

	private StardogDatabaseAPI db;
	
	/**
	 * Constructor.
	 * @param database The database to use.
	 */
	public StardogICVAPIImpl(StardogDatabaseAPI database) {
		db = database;
	}
	
	@Override
	public String addIntegrityConstraint(String pathToConstraint)
			throws FileNotFoundException {
		LOG.info("Adding contraints file: " + pathToConstraint);
		// Obtain a connection to the database
		try (Connection aConn = ConnectionConfiguration.to(db.getDatabaseName()).server(db.getServer()).reasoning(false)
				.credentials(db.getUserName(), db.getPassword()).connect()) {

			ICVConnection aValidator = aConn.as(ICVConnection.class);

			
			aConn.begin();
			aValidator.addConstraints().format(RDFFormats.RDFXML).stream(new FileInputStream(pathToConstraint));
			aConn.commit();

			return aValidator.getConstraints().iterator().next().toString();

		}
	}

	@Override
	public void removeIntegrityConstraints() {
		LOG.info("Removing all constraints");
		try (Connection aConn = ConnectionConfiguration.to(db.getDatabaseName()).server(db.getServer()).reasoning(false)
				.credentials("admin", "admin").connect()) { // TODO: avoid hard-coded credentials
			ICVConnection aValidator = aConn.as(ICVConnection.class);

			
			aValidator.begin();
			aValidator.clearConstraints();
			aValidator.commit();

		}
	}

	@Override
	public void explainViolationsForContext(String context) {

		LOG.info("Explaining violations for context: " + context);
		
		try (Connection aConn = ConnectionConfiguration.to(db.getDatabaseName()).server(db.getServer()).reasoning(true)
				.credentials(db.getUserName(), db.getPassword()).connect()) {

			ICVConnection aValidator = aConn.as(ICVConnection.class);
			
			Set<Constraint> constraints = aValidator.getConstraints();
			
			Collection<com.stardog.stark.IRI> selectedContext = new ArrayList<>();
			selectedContext.add(Values.iri(context));
			
			LOG.info("is valid: " + aValidator.isValid(ContextSets.DEFAULT_ONLY));
			
			for (Constraint constraint : constraints) {
				Iterable<Proof> proofs = aValidator.explain(constraint).activeGraphs(selectedContext).countLimit(600)
						.proofs();
				for(Proof p: proofs) {
					System.out.println(ProofWriter.toString(p));
				}
				storeViolations(id, constraint, proofs);
				id++;
			}
		}
	}

	@Override
	public ConstraintViolationsResultSet getResult() {
		return result;
	}
	

	private void storeViolations(int id, Constraint constraint, Iterable<Proof> proofs) {
		result = new ConstraintViolationsResultSet();
		for (Proof proof : proofs) {
			Iterable<Statement> asserted = proof.getStatements(ProofType.ASSERTED);
			ConstraintViolation violation = new ConstraintViolation();
			for (Statement statement : asserted) {
				violation.setViolation(statement.subject().toString(), statement.predicate().toString(),
						statement.object().toString());
			}
			Iterable<Statement> notInferred = proof.getStatements(ProofType.NOT_INFERRED);
			for (Statement statement : notInferred) {
				violation.setNotInferred(statement.subject().toString(), statement.predicate().toString(),
						statement.object().toString());
			}

			System.out.println(proof.toString());
			result.addViolation(violation);
		}
	}
}
