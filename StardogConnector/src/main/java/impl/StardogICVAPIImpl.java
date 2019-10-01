package impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.jena.rdf.model.Model;
import org.openrdf.model.IRI;
import org.openrdf.model.Statement;
import org.openrdf.rio.RDFFormat;

import com.complexible.common.rdf.model.Values;
import com.complexible.stardog.StardogException;
import com.complexible.stardog.api.Connection;
import com.complexible.stardog.api.ConnectionConfiguration;
import com.complexible.stardog.icv.Constraint;
import com.complexible.stardog.icv.api.ICVConnection;
import com.complexible.stardog.jena.SDJenaFactory;
import com.complexible.stardog.reasoning.Proof;
import com.complexible.stardog.reasoning.ProofType;
import com.complexible.stardog.reasoning.ProofWriter;

import api.StardogConnectionAPI;
import api.StardogConstraintViolation;
import api.StardogConstraintViolationResult;
import api.StardogConstraintViolationsResultSet;
import api.StardogICVAPI;
import api.exceptions.ConstraintsNotAddedException;
import api.exceptions.StardogDatabaseDoesNotExist;

public class StardogICVAPIImpl implements StardogICVAPI {

	// private Map<Constraint, Proof> explanations;
	private Map<StardogConstraint, Iterable<Proof>> explanations;

	private Map<String, StardogConstraintViolationResult> violations;

	private Map<Integer, String> constraintsAsString;

	private static int id = 0;

	public StardogICVAPIImpl() {

		violations = new HashMap<>();
		explanations = new HashMap<>();
		constraintsAsString = new HashMap<>();
	}

	@Override
	public void validateIntegrityConstraints(String pathToConstraints, StardogConnectionAPI api)
			throws StardogDatabaseDoesNotExist, FileNotFoundException, ConstraintsNotAddedException {

		ICVConnection aValidator = api.getConnection().as(ICVConnection.class);

		api.getConnection().begin();
		aValidator.addConstraints().format(RDFFormat.RDFXML).stream(new FileInputStream(new File(pathToConstraints)));
		api.getConnection().commit();

		// System.out.println("The data " +
		// (aValidator.isValid(ContextSets.DEFAULT_ONLY) ? "is" : "is NOT") + "
		// valid!");

	}

	@Override
	public void addIntegrityConstraints(String pathToConstraints, String server, String database)
			throws FileNotFoundException {
		// Obtain a connection to the database
		try (Connection aConn = ConnectionConfiguration.to(database).server(server).reasoning(false)
				.credentials("admin", "admin").connect()) {

			ICVConnection aValidator = aConn.as(ICVConnection.class);

			aConn.begin();
			aValidator.addConstraints().format(RDFFormat.RDFXML).stream(new FileInputStream(pathToConstraints));
			aConn.commit();

		}
	}

	@Override
	public String addIntegrityConstraint(int id, String pathToConstraint, String server, String database)
			throws FileNotFoundException {
		// Obtain a connection to the database
		try (Connection aConn = ConnectionConfiguration.to(database).server(server).reasoning(false)
				.credentials("admin", "admin").connect()) {

			ICVConnection aValidator = aConn.as(ICVConnection.class);

			aConn.begin();
			aValidator.addConstraints().format(RDFFormat.RDFXML).stream(new FileInputStream(pathToConstraint));
			aConn.commit();

//			String current = "";
//			for (Object object : aValidator.getConstraints().toArray()) {
//				if (object instanceof Constraint) {
//					Constraint c = (Constraint) object;
//					current = c.toString();
//					getConstraintsAsString().put(id, current);
//				}
//			}

			return aValidator.getConstraints().iterator().next().toString();

		}
	}

	@Override
	public void removeIntegrityConstraints(String pathToConstraints, String server, String database)
			throws FileNotFoundException {
		try (Connection aConn = ConnectionConfiguration.to(database).server(server).reasoning(false)
				.credentials("admin", "admin").connect()) {
			ICVConnection aValidator = aConn.as(ICVConnection.class);

			aValidator.begin();
//			aValidator.remove().;
			aValidator.clearConstraints();
			aValidator.commit();

		}
	}

	@Override
	public void validateIntegrityConstraintsInContext(String pathToConstraints, String server, String database,
			String context) throws FileNotFoundException {
		// Obtain a connection to the database
		try (Connection aConn = ConnectionConfiguration.to(database).server(server).reasoning(false)
				.credentials("admin", "admin").connect()) {

			ICVConnection aValidator = aConn.as(ICVConnection.class);

			aConn.begin();
			aValidator.addConstraints().format(RDFFormat.RDFXML).stream(new FileInputStream(pathToConstraints));
			aConn.commit();

		}
	}

	public void explainViolations(String server, String database) {
		try (Connection aConn = ConnectionConfiguration.to(database).server(server).reasoning(false)
				.credentials("admin", "admin").connect()) { // TODO remove hard coded username and password

			ICVConnection aValidator = aConn.as(ICVConnection.class);

			Set<Constraint> constraints = aValidator.getConstraints();

			for (Constraint constraint : constraints) {
				Iterable<Proof> proofs = aValidator.explain(constraint).countLimit(600).proofs();
				// explanations.put(constraint, proofs);
			}

			storeViolations();

		}
	}

	public void explainViolationsForContext(String server, String database, String context) {
		try (Connection aConn = ConnectionConfiguration.to(database).server(server).reasoning(false)
				.credentials("admin", "admin").connect()) { // TODO remove hard coded username and password

			ICVConnection aValidator = aConn.as(ICVConnection.class);

			Set<Constraint> constraints = aValidator.getConstraints();

			Collection<IRI> selectedContext = new ArrayList<>();
			selectedContext.add(Values.iri(context));
			for (Constraint constraint : constraints) {
				// System.out.println(constraint);
//				StardogConstraint cons = new StardogConstraint(id, constraint);
				Iterable<Proof> proofs = aValidator.explain(constraint).activeGraphs(selectedContext).countLimit(600)
						.proofs();
				storeViolations(id, constraint, proofs);
//				explanations.put(cons, proofs);
				id++;
			}

//			storeViolations();

		}
	}

	@Override
	public StardogConstraintViolationsResultSet getResult() {
		return result;
	}

	private StardogConstraintViolationsResultSet result;

	private void storeViolations(int id, Constraint constraint, Iterable<Proof> proofs) {
		result = new StardogConstraintViolationsResultSet(id);
//		System.out.println(constraint.toString());
		for (Proof proof : proofs) {
			Iterable<Statement> asserted = proof.getStatements(ProofType.ASSERTED);
			StardogConstraintViolation violation = new StardogConstraintViolation();
			for (Statement statement : asserted) {
				violation.setViolation(statement.getSubject().stringValue(), statement.getPredicate().stringValue(),
						statement.getObject().stringValue());
			}
			Iterable<Statement> notInferred = proof.getStatements(ProofType.NOT_INFERRED);
			for (Statement statement : notInferred) {
				violation.setNotInferred(statement.getSubject().stringValue(), statement.getPredicate().stringValue(),
						statement.getObject().stringValue());
			}
			violation.addProof(proof.toString());
			System.out.println(proof.toString());
//			System.out.println(proof);
			result.addViolation(violation);
		}
	}

	private void storeViolations() {

		StardogConstraintViolationResult violationResultForConstraint;
		StardogConstraintViolation violation;
		for (StardogConstraint constraint : explanations.keySet()) {
			violationResultForConstraint = new StardogConstraintViolationResult(constraint);
			Iterable<Proof> proofs = explanations.get(constraint);
			if (proofs != null) {
				for (Proof proof : proofs) {
					violation = new StardogConstraintViolation();
					//System.out.println(ProofWriter.toString(proof));
					Iterable<Statement> asserted = proof.getStatements(ProofType.ASSERTED);
					for (Statement statement : asserted) {
						violation.setViolation(statement.getSubject().stringValue(),
								statement.getPredicate().stringValue(), statement.getObject().stringValue());
					}
					Iterable<Statement> notInferred = proof.getStatements(ProofType.NOT_INFERRED);
					for (Statement statement : notInferred) {
						violation.setNotInferred(statement.getSubject().stringValue(),
								statement.getPredicate().stringValue(), statement.getObject().stringValue());
					}
					System.out.println("NOT INFERRED: " + violation.getNotInferred().size());

					violationResultForConstraint.addViolation(violation);
				}
			}
			violations.put(constraint.getConstraint().toString(), violationResultForConstraint);
		}
	}

	@Override
	public Map<String, StardogConstraintViolationResult> getViolations() {
		return violations;
	}

	@Override
	public Map<Integer, String> getConstraintsAsString() {
		return constraintsAsString;
	}

}
